// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocumentFormAction.java

package com.sinitek.sirm.web.routine;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.routine.entity.*;
import com.sinitek.sirm.busin.routine.func.DocumentRightFunction;
import com.sinitek.sirm.busin.routine.service.*;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.attachment.entity.IAttachment;
import com.sinitek.sirm.common.attachment.service.IAttachmentService;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.utils.*;
import com.sinitek.sirm.common.web.RequestContext;
import com.sinitek.sirm.common.web.tag.PageLoadContext;
import com.sinitek.sirm.framework.utils.io.Uploader;
import com.sinitek.sirm.framework.utils.io.UploaderFileList;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.sirm.org.busin.service.*;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;

public class DocumentFormAction
    implements ITableAware
{

    public DocumentFormAction()
    {
    }

    public static void loadpage(PageLoadContext context)
    {
        HttpServletRequest request = context.getRequest();
        String directoryid = request.getParameter("directoryid");
        request.setAttribute("directoryid", NumberTool.safeToInteger(directoryid, Integer.valueOf(0)));
    }

    public Object getResult(Map stringMap, HttpServletRequest httpServletRequest)
        throws Exception
    {
        String orgid = (String)stringMap.get("directoryid");
        String sharinger = RequestContext.getCurrentUser().getOrgId();
        List result = BaseRoutineServiceFactory.getDocumentAuthService().findDocumentsByOrgId(orgid, sharinger);
        String order = (String)stringMap.get("order");
        if(order != null && !"".equals(order))
        {
            String name = order.substring(0, order.indexOf(":"));
            Collections.sort(result, new ListComparator(name, order.substring(order.indexOf(":") + 1)));
        } else
        {
            Collections.sort(result, new ListComparator("documentname", "asc"));
        }
        Map h = new HashMap();
        List r = new ArrayList();
        Iterator i$ = result.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Map map = (Map)i$.next();
            String objid = String.valueOf(map.get("objid"));
            if(!h.containsKey(objid))
            {
                r.add(map);
                h.put(objid, objid);
            }
        } while(true);
        return r;
    }

    public String setDefaultOrderBy(Map stringStringMap, HttpServletRequest httpServletRequest)
        throws Exception
    {
        return null;
    }

    public String getDirectory(Map param, HttpServletRequest request)
    {
        String directoryer = RequestContext.getCurrentUser().getOrgId();
        List result = getTree(1, 2, 0, 0, directoryer, false);
        String str = null;
        str = (new JSONArray(result)).toString();
        return str;
    }

    public String getCompanyDirectory(Map param, HttpServletRequest request)
    {
        List result = getCompanyDirectoryTree(1, 1, 0, 0, null, true);
        return (new JSONArray(result)).toString();
    }

    public String getCurrentCompanyDirectory(Map param, HttpServletRequest request)
    {
        List result = getTree(1, 1, 0, 0, null, false);
        return (new JSONArray(result)).toString();
    }

    public String getSharingDirectory(Map param, HttpServletRequest request)
    {
        List result = new ArrayList();
        String orgid = RequestContext.getCurrentUser().getOrgId();
        List orgObjectList = BaseRoutineServiceFactory.getDocumentAuthService().getSharingerDiretoryByOrgId(orgid);
        Iterator i$ = orgObjectList.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            OrgObject orgObject = (OrgObject)i$.next();
            if(!orgid.equals(orgObject.getOrgId()))
            {
                Map map = new HashMap();
                map.put("id", orgObject.getOrgId());
                map.put("sort", "1");
                map.put("description", "");
                map.put("parentId", "0");
                map.put("name", orgObject.getOrgName());
                map.put("level", "0");
                result.add(map);
            }
        } while(true);
        return (new JSONArray(result)).toString();
    }

    public List getTree(int status, int directorytype, int id, int level, String directoryer, boolean authtf)
    {
        List result = new ArrayList();
        List _allList = BaseRoutineServiceFactory.getDirectoryService().findAllDirectoryByParentId(status, directorytype, id, directoryer != null ? directoryer : null);
        List _diredtoryList = new ArrayList();
        if(authtf && directorytype == 1)
        {
            String orgid = RequestContext.getCurrentUser().getOrgId();
            Iterator i$ = _allList.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                IRTDirectory directory = (IRTDirectory)i$.next();
                if(DocumentRightFunction.checkDirectoryRight(orgid, directory.getId(), "READ"))
                    _diredtoryList.add(directory);
            } while(true);
        } else
        {
            _diredtoryList = _allList;
        }
        for(int i = 0; i < _diredtoryList.size(); i++)
        {
            IRTDirectory _directory = (IRTDirectory)_diredtoryList.get(i);
            Map map = new HashMap();
            map.put("id", Integer.valueOf(_directory.getId()));
            map.put("sort", _directory.getSort());
            map.put("description", _directory.getDirectorycontent());
            map.put("parentId", _directory.getParentid());
            map.put("name", _directory.getDirectoryname());
            map.put("level", Integer.valueOf(level));
            result.add(map);
            result.addAll(getTree(status, directorytype, _directory.getId(), level + 1, directoryer, authtf));
        }

        return result;
    }

    public List getCompanyDirectoryTree(int status, int directorytype, int id, int level, String directoryer, boolean authtf)
    {
        List result = new ArrayList();
        String orgid = RequestContext.getCurrentUser().getOrgId();
        List objids = OrgServiceFactory.getRightService().findAuthedObjects(orgid, "RTDIRECTORY", "READ");
        IRTDirectoryService directoryService = BaseRoutineServiceFactory.getDirectoryService();
        List _allList = directoryService.findAllDirectoryByParentId(status, directorytype, id, directoryer != null ? directoryer : null);
        List _diredtoryList = new ArrayList();
        if(authtf && directorytype == 1)
        {
            Iterator i$ = _allList.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                IRTDirectory directory = (IRTDirectory)i$.next();
                if(objids.contains(String.valueOf(directory.getId())))
                {
                    _diredtoryList.add(directory);
                    objids.remove(String.valueOf(directory.getId()));
                }
            } while(true);
        } else
        {
            _diredtoryList = _allList;
        }
        IRTDirectory _directory;
        for(Iterator i$ = _diredtoryList.iterator(); i$.hasNext(); result.addAll(getCompanyDirectoryTree(status, directorytype, _directory.getId(), level + 1, directoryer, false)))
        {
            _directory = (IRTDirectory)i$.next();
            Map map = new HashMap();
            map.put("id", Integer.valueOf(_directory.getId()));
            map.put("sort", _directory.getSort());
            map.put("description", _directory.getDirectorycontent());
            map.put("parentId", _directory.getParentid());
            map.put("name", _directory.getDirectoryname());
            map.put("level", Integer.valueOf(level));
            result.add(map);
        }

        return result;
    }

    public String saveDirectory(Map param, HttpServletRequest request)
    {
        IRTDirectoryService directoryService = BaseRoutineServiceFactory.getDirectoryService();
        int directoryid = NumberTool.convertMapKeyToInt(param, "updatedirectoryid").intValue();
        String type = (String)param.get("type");
        int parentid = NumberTool.convertMapKeyToInt(param, "parentid").intValue();
        String directoryname = (String)param.get("directoryname");
        int directorysort = NumberTool.convertMapKeyToInt(param, "directorysort").intValue();
        String directorydec = param.get("directorydec") != null ? (String)param.get("directorydec") : "";
        int companytype = "true".equals(param.get("companytype")) ? 1 : 2;
        IRTDirectory directory = null;
        IRTDirectoryAppend directoryAppend = null;
        if("0".equals(type))
        {
            directory = new RTDirectoryImpl();
            directory.setDirectoryer(RequestContext.getCurrentUser().getOrgId());
            directory.setStatus(Integer.valueOf(1));
            directory.setDirectorytype(Integer.valueOf(companytype));
        } else
        if("1".equals(type))
            if(directoryid != 0)
                directory = directoryService.getDirectoryById(directoryid);
            else
                return "\u4FEE\u6539\u76EE\u5F55\u5931\u8D25";
        if(directory != null)
        {
            directory.setDirectorycontent(directorydec);
            directory.setDirectoryname(directoryname);
            directory.setParentid(Integer.valueOf(parentid));
            directory.setSort(Integer.valueOf(directorysort));
            directoryService.saveDirectory(directory);
        } else
        {
            return "\u4FEE\u6539\u76EE\u5F55\u5931\u8D25";
        }
        if(companytype == 1)
        {
            directoryAppend = directoryService.getDirectoryAppendByDirectoryId(directory.getId());
            if(directoryAppend == null)
                directoryAppend = new RTDirectoryAppendImpl();
            int allowsearch = NumberTool.safeToInteger(param.get("allowsearch"), Integer.valueOf(0)).intValue();
            int allowapply = NumberTool.safeToInteger(param.get("allowapply"), Integer.valueOf(0)).intValue();
            int allowsubapply = NumberTool.safeToInteger(param.get("allowsubapply"), Integer.valueOf(0)).intValue();
            int workflowid = NumberTool.safeToInteger(param.get("workflowname"), Integer.valueOf(-1)).intValue();
            directoryAppend.setAllowapplyautor(Integer.valueOf(allowapply));
            directoryAppend.setAllowsubapplyauthor(Integer.valueOf(allowsubapply));
            directoryAppend.setIssearch(Integer.valueOf(allowsearch));
            if(allowapply == 1)
                directoryAppend.setWorkflowid(Integer.valueOf(workflowid));
            else
                directoryAppend.setWorkflowid(Integer.valueOf(-1));
            directoryAppend.setDirectoryid(Integer.valueOf(directory.getId()));
            directoryService.saveDirectoryAppend(directoryAppend);
        }
        return "";
    }

    public static String saveDocument(Map param, Map fileMap, HttpServletRequest request)
        throws Exception
    {
        IRTDocumentsService documentsService = BaseRoutineServiceFactory.getDocumentsService();
        int documentid = NumberTool.convertMapKeyToInt(param, "documentid").intValue();
        String type = (String)param.get("type");
        int parentid = NumberTool.convertMapKeyToInt(param, "parentdirectoryid").intValue();
        String documentname = (String)param.get("documentname");
        String documentcontent = (String)param.get("documentcontent");
        IRTDocuments document = null;
        String orgid = RequestContext.getCurrentUser().getOrgId();
        String orgname = RequestContext.getCurrentUser().getDisplayName();
        if("0".equals(type))
        {
            document = new RTDocumentsImpl();
            document.setCreatetime(TimeUtil.getSysDateAsDate());
            document.setDocumenter(orgid);
            document.setStatus(Integer.valueOf(1));
        } else
        if("1".equals(type))
            if(documentid != 0)
                document = documentsService.getDocumentById(documentid);
            else
                return "\u4FEE\u6539\u6587\u6863\u5931\u8D25";
        if(document != null)
        {
            document.setDirectoryId(Integer.valueOf(parentid));
            document.setDocumentContent(documentcontent);
            document.setDocumentName(documentname);
            documentsService.saveDocument(document);
        }
        if("0".equals(type) && document != null)
        {
            String entity = "RTDocuments";
            int objid = document.getId();
            UploaderFileList fileList = Uploader.parseRequest(param);
            List _attachmentList = fileList.toAttachmentList(entity, Integer.valueOf(2));
            CommonServiceFactory.getAttachmentService().saveAttachmentList(objid, entity, _attachmentList);
            IRTDocumentAttachment documentAttachment;
            for(Iterator i$ = _attachmentList.iterator(); i$.hasNext(); documentsService.saveDocumentAttachment(documentAttachment))
            {
                IAttachment attachment = (IAttachment)i$.next();
                documentAttachment = new RTDocumentAttachmentImpl();
                documentAttachment.setAttachmenter(orgid);
                documentAttachment.setAttachmentid(Integer.valueOf(attachment.getId()));
                documentAttachment.setAttachmentname(attachment.getName());
                documentAttachment.setDocumentid(Integer.valueOf(objid));
                documentAttachment.setAttachmentername(orgname);
                documentAttachment.setCreatetime(TimeUtil.getSysDateAsDate());
                documentAttachment.setDocumentVersion("1");
                documentAttachment.setGroupid(Integer.valueOf(attachment.getId()));
            }

            return "\u4FDD\u5B58\u6210\u529F";
        } else
        {
            return "";
        }
    }

    public String getDocument(Map param, HttpServletRequest request)
    {
        Map _result = new HashMap();
        int documentid = NumberTool.convertMapKeyToInt(param, "documentid").intValue();
        if(documentid != 0)
        {
            IRTDocuments documents = BaseRoutineServiceFactory.getDocumentsService().getDocumentById(documentid);
            _result.put("Createtime", TimeUtil.formatDate(documents.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
            _result.put("DocumentContent", documents.getDocumentContent());
            _result.put("DocumentName", documents.getDocumentName());
            _result.put("DocumentId", Integer.valueOf(documents.getId()));
            Employee employee = OrgServiceFactory.getOrgService().getEmployeeById(documents.getDocumenter());
            _result.put("DocumenterName", employee != null ? ((Object) (employee.getEmpName())) : "");
            IRTDirectory directory = BaseRoutineServiceFactory.getDirectoryService().getDirectoryById(documents.getDirectoryId().intValue());
            _result.put("DirectoryName", directory != null ? ((Object) (directory.getDirectoryname())) : "");
            IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
            String orgid = RequestContext.getCurrentUser().getOrgId();
            IRT_KeyReader keyReader = documentAuthService.getKeyReaderByAuthIdAndReaderId(documentid, orgid);
            _result.put("reply", keyReader != null ? ((Object) (keyReader.getReply())) : "");
            _result.put("replyable", Integer.valueOf(keyReader != null ? 1 : 0));
        }
        return (new JSONObject(_result)).toString();
    }

    public String getDirectoryById(Map param, HttpServletRequest request)
    {
        Map _result = new HashMap();
        int directoryid = NumberTool.convertMapKeyToInt(param, "directoryid").intValue();
        if(directoryid != 0)
        {
            IRTDirectory directory = BaseRoutineServiceFactory.getDirectoryService().getDirectoryById(directoryid);
            if(directory != null)
            {
                _result.put("directoryer", directory.getDirectoryer());
                _result.put("directoryname", directory.getDirectoryname());
                _result.put("directorycontent", directory.getDirectorycontent());
            }
        }
        return (new JSONObject(_result)).toString();
    }

    public String getParentDirectory(Map stringMap, HttpServletRequest httpServletRequest)
    {
        String parentdirectory = (String)stringMap.get("parentdirectory");
        String type = (String)stringMap.get("companytype");
        String companytype = "true".equals(type) ? "\u516C\u53F8\u6587\u6863" : "\u4E2A\u4EBA\u6587\u6863";
        String companyid = "true".equals(type) ? "CompanyTree0" : "DirectoryTree0";
        String returnstr = "";
        if(parentdirectory != null)
        {
            if(parentdirectory.equals("true"))
                returnstr = (new StringBuilder()).append(returnstr).append("<option value=\"").append(companyid).append("\">").append(companytype).append("</option>").toString();
            else
                returnstr = (new StringBuilder()).append(returnstr).append("<optgroup label=\"").append(companytype).append("\">").toString();
            returnstr = (new StringBuilder()).append(returnstr).append(getDirectoryList(0, 1, "true".equals(type) ? 1 : 2)).toString();
            if(!parentdirectory.equals("true"))
                returnstr = (new StringBuilder()).append(returnstr).append("</optgroup>").toString();
        }
        return returnstr;
    }

    public String getDirectoryList(int id, int level, int directorytype)
    {
        String funs = "";
        String directoryer = directorytype != 1 ? RequestContext.getCurrentUser().getOrgId() : null;
        List _diredtoryList = BaseRoutineServiceFactory.getDirectoryService().findAllDirectoryByParentId(1, directorytype, id, directoryer);
        String levelStr = "";
        for(int i = 0; i < level; i++)
            levelStr = (new StringBuilder()).append(levelStr).append("&nbsp;&nbsp;").toString();

        for(Iterator i$ = _diredtoryList.iterator(); i$.hasNext();)
        {
            IRTDirectory directory = (IRTDirectory)i$.next();
            funs = (new StringBuilder()).append(funs).append("<option value=\"").append(directory.getId()).append("\" >").append(levelStr).append(directory.getDirectoryname()).append("</option>").toString();
            funs = (new StringBuilder()).append(funs).append(getDirectoryList(directory.getId(), level + 1, directorytype)).toString();
        }

        return funs;
    }

    public String getCurrentMenu(Map stringMap, HttpServletRequest httpServletRequest)
    {
        int _directoryid = NumberTool.convertMapKeyToInt(stringMap, "directoryid").intValue();
        List directoryList = new ArrayList();
        getCroup(_directoryid, directoryList);
        return (new JSONArray(directoryList)).toString();
    }

    private void getCroup(int _directoryid, List directoryList)
    {
        if(_directoryid != 0)
        {
            IRTDirectory directory = BaseRoutineServiceFactory.getDirectoryService().getDirectoryById(_directoryid);
            if(directory != null)
            {
                Map map = new HashMap();
                map.put("id", Integer.valueOf(directory.getId()));
                map.put("code", directory.getDirectoryname());
                directoryList.add(map);
                getCroup(directory.getParentid().intValue(), directoryList);
            }
        }
    }

    public String updateSelect(Map stringMap, HttpServletRequest httpServletRequest)
    {
        int _directoryid = NumberTool.convertMapKeyToInt(stringMap, "directoryid").intValue();
        int _directorysort = NumberTool.convertMapKeyToInt(stringMap, "directorysort").intValue();
        int _updateid = NumberTool.convertMapKeyToInt(stringMap, "updateid").intValue();
        int _updatesort = NumberTool.convertMapKeyToInt(stringMap, "updatesort").intValue();
        int ordertype = NumberTool.safeToInteger(stringMap.get("ordertype"), Integer.valueOf(0)).intValue();
        IRTDirectoryService directoryService = BaseRoutineServiceFactory.getDirectoryService();
        IRTDirectory _directory = null;
        IRTDirectory _updatedirectory = null;
        try
        {
            if(_directoryid != 0)
            {
                _directory = directoryService.getDirectoryById(_directoryid);
                if(_directory == null)
                    return "\u5F53\u524D\u76EE\u5F55\u4E0D\u5B58\u5728";
            }
        }
        catch(Exception ex)
        {
            return "\u6392\u5E8F\u5931\u8D25";
        }
        if(_updateid != 0)
        {
            _updatedirectory = directoryService.getDirectoryById(_updateid);
            if(_updatedirectory == null)
                return "\u76EE\u6807\u76EE\u5F55\u4E0D\u5B58\u5728";
        }
        if(_directory != null && _updatedirectory != null && _directory.getParentid().equals(_updatedirectory.getParentid()))
            if(ordertype == 0)
            {
                _directory.setSort(Integer.valueOf(_updatesort));
                directoryService.saveDirectory(_directory);
                _updatedirectory.setSort(Integer.valueOf(_directorysort));
                directoryService.saveDirectory(_updatedirectory);
            } else
            {
                _updatedirectory.setSort(Integer.valueOf(_directorysort));
                directoryService.saveDirectory(_updatedirectory);
                _directory.setSort(Integer.valueOf(_updatesort));
                directoryService.saveDirectory(_directory);
            }
        return "";
    }

    public String removeSelect(Map stringMap, HttpServletRequest httpServletRequest)
    {
        int directoryid = NumberTool.convertMapKeyToInt(stringMap, "directoryid").intValue();
        IRTDirectoryService directoryService = BaseRoutineServiceFactory.getDirectoryService();
        try
        {
            if(directoryid != 0)
            {
                IRTDirectory directory = directoryService.getDirectoryById(directoryid);
                directory.setStatus(Integer.valueOf(0));
                directoryService.saveDirectory(directory);
                IRTDirectoryAppend directoryAppend = directoryService.getDirectoryAppendByDirectoryId(directoryid);
                directoryService.deleteDirectoryAppend(directoryAppend);
            }
        }
        catch(Exception ex)
        {
            return "\u5220\u9664\u5931\u8D25";
        }
        return "";
    }

    public static String uploadFile(Map param, Map fileMap, HttpServletRequest request)
        throws Exception
    {
        Map _result = new HashMap();
        int groupid = 0;
        try
        {
            IRTDocumentsService documentsService = BaseRoutineServiceFactory.getDocumentsService();
            String orgid = RequestContext.getCurrentUser().getOrgId();
            String orgname = RequestContext.getCurrentUser().getDisplayName();
            String entity = "RTDocuments";
            int documentid = NumberTool.convertMapKeyToInt(param, "documentid").intValue();
            groupid = NumberTool.convertMapKeyToInt(param, "groupid").intValue();
            if(documentid != 0)
            {
                UploaderFileList fileList = Uploader.parseRequest(param);
                List _attachmentList = fileList.toAttachmentList(entity, Integer.valueOf(2));
                CommonServiceFactory.getAttachmentService().saveAttachmentList(documentid, entity, _attachmentList);
                IRTDocumentAttachment beforedocumentAttachment = null;
                if(groupid != 0)
                    beforedocumentAttachment = documentsService.getAttachmentOrderVersion(documentid, groupid);
                IRTDocumentAttachment documentAttachment;
                for(Iterator i$ = _attachmentList.iterator(); i$.hasNext(); documentsService.saveDocumentAttachment(documentAttachment))
                {
                    IAttachment attachment = (IAttachment)i$.next();
                    documentAttachment = new RTDocumentAttachmentImpl();
                    groupid = beforedocumentAttachment == null || beforedocumentAttachment.getGroupid().intValue() == 0 ? attachment.getId() : beforedocumentAttachment.getGroupid().intValue();
                    documentAttachment.setGroupid(Integer.valueOf(groupid));
                    documentAttachment.setAttachmenter(orgid);
                    documentAttachment.setDocumentid(Integer.valueOf(documentid));
                    documentAttachment.setAttachmentername(orgname);
                    documentAttachment.setCreatetime(TimeUtil.getSysDateAsDate());
                    documentAttachment.setAttachmentid(Integer.valueOf(attachment.getId()));
                    documentAttachment.setAttachmentname(attachment.getName());
                    String version = beforedocumentAttachment != null && beforedocumentAttachment.getDocumentVersion() != null ? String.valueOf(Integer.parseInt(beforedocumentAttachment.getDocumentVersion()) + 1) : "1";
                    documentAttachment.setDocumentVersion(version);
                }

                _result.put("result", "\u4E0A\u4F20\u6210\u529F");
                _result.put("groupid", Integer.valueOf(groupid));
                return (new JSONObject(_result)).toString();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        _result.put("result", "\u4E0A\u4F20\u5931\u8D25");
        _result.put("groupid", Integer.valueOf(groupid));
        return (new JSONObject(_result)).toString();
    }

    public String getChildObject(Map stringMap, HttpServletRequest httpServletRequest)
    {
        IRTDocumentsService documentsService = BaseRoutineServiceFactory.getDocumentsService();
        Integer objid = Integer.valueOf(0);
        if(stringMap.get("id") != null)
            objid = Integer.valueOf((String)stringMap.get("id"));
        List childNodeList = documentsService.getChildNodeList(objid, Integer.valueOf(1)).getResult();
        List childFileList = documentsService.getChildFileList(objid, Integer.valueOf(1)).getResult();
        if(childFileList.size() != 0 || childNodeList.size() != 0)
            return "notEmpty";
        try
        {
            return "empty";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "notEmpty";
    }

    public String editAttachmentName(Map stringMap, HttpServletRequest httpServletRequest)
    {
        try
        {
            IRTDocumentsService documentsService = BaseRoutineServiceFactory.getDocumentsService();
            IAttachmentService attachmentService = CommonServiceFactory.getAttachmentService();
            Integer objid = Integer.valueOf(0);
            if(stringMap.get("id") != null)
                objid = Integer.valueOf((String)stringMap.get("id"));
            String attachmentName = stringMap.get("attachmentName") != null ? (String)stringMap.get("attachmentName") : "";
            String remarks = stringMap.get("remarks") != null ? (String)stringMap.get("remarks") : "";
            IRTDocumentAttachment rtDocumentAttachment = documentsService.getDocumentAttachmentById(objid.intValue());
            rtDocumentAttachment.setAttachmentname(attachmentName);
            rtDocumentAttachment.setRemarks(remarks);
            documentsService.saveDocumentAttachment(rtDocumentAttachment);
            IAttachment attachment = attachmentService.getAttachmentById(rtDocumentAttachment.getAttachmentid().intValue());
            attachment.setName(attachmentName);
            attachmentService.saveAttachment(attachment);
            return "SUCCESS";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List getNewTree()
        throws Exception
    {
        String orgid = RequestContext.getCurrentUser().getOrgId();
        return null;
    }

    public Object getDirectoryDetail(Map map, HttpServletRequest request1)
        throws Exception
    {
        int id = NumberTool.safeToInteger(map.get("dirid"), Integer.valueOf(0)).intValue();
        IRTDirectory irtDirectory = BaseRoutineServiceFactory.getDirectoryService().getDirectoryById(id);
        List list = BaseRoutineServiceFactory.getDirectoryService().getDirectoryDetailById(id);
        if(list != null && list.size() > 0)
        {
            if(irtDirectory != null)
                if(irtDirectory.getParentid().intValue() == 0)
                    ((Map)list.get(0)).put("parentallowsub", Integer.valueOf(1));
                else
                if(irtDirectory.getParentid().intValue() > 0)
                {
                    IRTDirectoryAppend irtDirectoryAppend = BaseRoutineServiceFactory.getDirectoryService().getDirectoryAppendByDirectoryId(irtDirectory.getParentid().intValue());
                    if(irtDirectoryAppend != null)
                        if(irtDirectoryAppend.getAllowsubapplyauthor().intValue() == 1)
                            ((Map)list.get(0)).put("parentallowsub", Integer.valueOf(1));
                        else
                            ((Map)list.get(0)).put("parentallowsub", Integer.valueOf(0));
                }
            return list.get(0);
        } else
        {
            return new HashMap();
        }
    }

    public Object getDocDetail(Map map, HttpServletRequest request1)
        throws Exception
    {
        int docid = NumberTool.safeToInteger(map.get("docid"), Integer.valueOf(-1)).intValue();
        IRTDocumentsService irtDocumentsService = BaseRoutineServiceFactory.getDocumentsService();
        IRTDocuments irtDocuments = irtDocumentsService.getDocumentById(docid);
        Map result = new HashMap();
        if(irtDocuments != null)
        {
            result.put("directoryid", irtDocuments.getDirectoryId());
            result.put("documentname", irtDocuments.getDocumentName());
            result.put("documentcontent", irtDocuments.getDocumentContent());
            result.put("objid", Integer.valueOf(irtDocuments.getId()));
        }
        return result;
    }
}
