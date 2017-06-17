// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CompanyDocumentTableAction.java

package com.sinitek.sirm.web.routine;

import com.sinitek.sirm.busin.routine.entity.*;
import com.sinitek.sirm.busin.routine.func.DocumentRightFunction;
import com.sinitek.sirm.busin.routine.service.*;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.attachment.entity.IAttachment;
import com.sinitek.sirm.common.attachment.service.IAttachmentService;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.utils.*;
import com.sinitek.sirm.common.web.RequestContext;
import com.sinitek.sirm.framework.utils.io.Uploader;
import com.sinitek.sirm.framework.utils.io.UploaderFileList;
import com.sinitek.sirm.org.busin.entity.Employee;
import com.sinitek.sirm.org.busin.service.*;
import com.sinitek.sirm.org.busin.service.impl.RightServiceImpl;
import com.sinitek.spirit.right.client.RightFactory;
import com.sinitek.spirit.right.core.*;
import com.sinitek.spirit.webcontrol.table.ICheckBoxPluginAware;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// Referenced classes of package com.sinitek.sirm.web.routine:
//            KeyReaderAction

public class CompanyDocumentTableAction
    implements ITableAware, ICheckBoxPluginAware
{

    public CompanyDocumentTableAction()
    {
    }

    public Object callCheckBoxPlugin(List maps, Map map, HttpServletRequest request1)
        throws Exception
    {
        String result = "";
        String docids = "";
        int type = NumberTool.safeToInteger(map.get("type"), Integer.valueOf(-1)).intValue();
        IRTDocumentsService documentsService = BaseRoutineServiceFactory.getDocumentsService();
        if(type == 1)
        {
            Iterator i$ = maps.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map _param = (Map)i$.next();
                String docid = StringUtil.safeToString(_param.get("objid"), "");
                if(!"".equals(docid))
                    if(docids.equals(""))
                        docids = (new StringBuilder()).append(docids).append(docid).toString();
                    else
                        docids = (new StringBuilder()).append(docids).append("-").append(docid).toString();
            } while(true);
            result = docids;
        } else
        if(type == 0)
        {
            Iterator i$ = maps.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map _map = (Map)i$.next();
                int objid = NumberTool.safeToInteger(_map.get("objid"), Integer.valueOf(0)).intValue();
                IRTDocuments irtDocuments = documentsService.getDocumentById(objid);
                if(irtDocuments != null)
                {
                    irtDocuments.setStatus(Integer.valueOf(0));
                    documentsService.saveDocument(irtDocuments);
                }
            } while(true);
            result = "ok";
        }
        return result;
    }

    public Object getResult(Map map, HttpServletRequest request1)
        throws Exception
    {
        String orgid = RequestContext.getCurrentUser().getOrgId();
        String rightauths[] = {
            "READ", "REREAD", "REWRITE", "REWRITE", "DOWNLOAD", "REDOWNLOAD"
        };
        int directoryid = NumberTool.safeToInteger(map.get("directoryid"), Integer.valueOf(0)).intValue();
        IOrgService iOrgService = OrgServiceFactory.getOrgService();
        List list = BaseRoutineServiceFactory.getDocumentsService().findAllDocuments(1, directoryid, null);
        List documentslist = new ArrayList();
        Iterator i$;
        if(DocumentRightFunction.checkDirectoryRight(orgid, directoryid, rightauths))
        {
            documentslist = list;
        } else
        {
            i$ = list.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map documentMap = (Map)i$.next();
                int documentid = NumberTool.convertMapKeyToInt(documentMap, "objid").intValue();
                if(DocumentRightFunction.checkDocumentRight(orgid, documentid, rightauths))
                    documentslist.add(documentMap);
            } while(true);
        }
        i$ = documentslist.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Map _map = (Map)i$.next();
            Employee employee = iOrgService.getEmployeeById(String.valueOf(_map.get("Documenter")));
            if(employee != null)
                _map.put("DocumenterName", employee.getEmpName());
        } while(true);
        return documentslist;
    }

    public String setDefaultOrderBy(Map map, HttpServletRequest request1)
        throws Exception
    {
        return "createtime DESC";
    }

    public String getDocname(Map map, HttpServletRequest request1)
        throws Exception
    {
        String docname = StringUtil.safeToString(map.get("documentname"), "");
        return docname;
    }

    public String getOpert(Map map, HttpServletRequest request1)
        throws Exception
    {
        IRTDocumentAuthService service = BaseRoutineServiceFactory.getDocumentAuthService();
        String result = "";
        String orgid = RequestContext.getCurrentUser().getOrgId();
        String documenter = StringUtil.safeToString(map.get("documenter"), "");
        int objid = NumberTool.safeToInteger(map.get("objid"), Integer.valueOf(-1)).intValue();
        IRTDocuments irtDocuments = BaseRoutineServiceFactory.getDocumentsService().getDocumentById(objid);
        String righttype = "";
        if(orgid.equals(documenter))
            result = (new StringBuilder()).append("<a href=\"#\" onclick=\"modifydocument(").append(NumberTool.safeToInteger(map.get("objid"), Integer.valueOf(-1))).append(")\">\u4FEE\u6539</a>|").append("<a href=\"#\" onclick=\"manageauth(").append(NumberTool.safeToInteger(map.get("objid"), Integer.valueOf(-1))).append(")\">\u6743\u9650\u7BA1\u7406</a>").toString();
        else
        if(irtDocuments != null)
        {
            if(checkRight(objid, orgid, "WRITE,REWRITE", "RTDOCUMENTS") || checkRight(irtDocuments.getDirectoryId().intValue(), orgid, "WRITE,REWRITE", "RTDIRECTORY"))
                result = (new StringBuilder()).append("<a href=\"#\" onclick=\"modifydocument(").append(NumberTool.safeToInteger(map.get("objid"), Integer.valueOf(-1))).append(")\">\u4FEE\u6539</a>").toString();
            if(checkRight(objid, orgid, "REREAD,REWRITE,REDOWNLOAD", "RTDOCUMENTS") || checkRight(irtDocuments.getDirectoryId().intValue(), orgid, "REREAD,REWRITE,REDOWNLOAD", "RTDIRECTORY"))
            {
                if(!result.equals(""))
                    result = (new StringBuilder()).append(result).append("|").toString();
                result = (new StringBuilder()).append(result).append("<a href=\"#\" onclick=\"manageauth(").append(NumberTool.safeToInteger(map.get("objid"), Integer.valueOf(-1))).append(")\">\u6743\u9650\u7BA1\u7406</a>").toString();
            }
        }
        return result;
    }

    public Object checkAuth(Map map, HttpServletRequest request1)
        throws Exception
    {
        String entityname = StringUtil.safeToString(map.get("entityname"), "");
        boolean result = false;
        String righttype = StringUtil.safeToString(map.get("righttype"), "");
        String orgid = RequestContext.getCurrentUser().getOrgId();
        int dirid = NumberTool.safeToInteger(map.get("dirid"), Integer.valueOf(0)).intValue();
        if(dirid == 0)
            return Boolean.valueOf(true);
        if(!"".equals(righttype))
            result = DocumentRightFunction.checkDirectoryRight(orgid, dirid, righttype.split(","));
        return Boolean.valueOf(result);
    }

    private boolean checkRight(int objid, String orgid, String righttype, String entityname)
    {
        boolean result = false;
        if(!"".equals(righttype) || righttype != null)
            if("RTDIRECTORY".equals(entityname))
                result = DocumentRightFunction.checkDirectoryRight(orgid, objid, righttype.split(","));
            else
            if("RTDOCUMENTS".equals(entityname))
                result = DocumentRightFunction.checkDocumentRight(orgid, objid, righttype.split(","));
        return result;
    }

    public String saveDocument(Map param, Map fileMap, HttpServletRequest request)
        throws Exception
    {
        IRTDocumentsService documentsService = BaseRoutineServiceFactory.getDocumentsService();
        IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        IRightService rightService = OrgServiceFactory.getRightService();
        String result = "";
        String entity = "RTDOCUMENTS";
        int documentid = NumberTool.convertMapKeyToInt(param, "documentid").intValue();
        String type = (String)param.get("type");
        int parentid = NumberTool.convertMapKeyToInt(param, "directoryid").intValue();
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

            result = "\u4FDD\u5B58\u6210\u529F";
        }
        String keyreaders = StringUtil.safeToString(param.get("keyreaders"), "");
        if(!"".equals(keyreaders))
        {
            documentAuthService.deleteKeyReaderByDocumentId(documentid);
            String readerOrgids[] = keyreaders.contains(",") ? keyreaders.split(",") : (new String[] {
                keyreaders
            });
            String arr$[] = readerOrgids;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                String org = arr$[i$];
                String orgobject[] = org.split(":");
                IRT_KeyReader keyReader = new RT_KeyReaderImpl();
                keyReader.setAuthId(Integer.valueOf(document.getId()));
                keyReader.setOrgid(orgid);
                keyReader.setReaderId(orgobject[0]);
                keyReader.setAuthEntity(entity);
                documentAuthService.saveKeyReader(keyReader);
                IRTDocumentAuth documentAuth = new RTDocumentAuthImpl();
                documentAuth.setAuthid(Integer.valueOf(document.getId()));
                documentAuth.setBegintime(null);
                documentAuth.setEndtime(null);
                documentAuth.setOrgid(orgid);
                documentAuth.setSharinger(orgobject[0]);
                documentAuth.setAuthEntity(entity);
                documentAuthService.saveCompanyDocumentAuth(documentAuth);
                IRightType _rightTypes[] = new IRightType[1];
                _rightTypes[0] = new com.sinitek.sirm.org.busin.service.impl.RightServiceImpl.RightType("READ");
                if(!_rightUpdater.hasRightDefine(entity))
                    _rightUpdater.createRightDefine(entity, (new StringBuilder()).append("\u6388\u6743\uFF1A").append(entity).toString(), AuthType.OBJECT_KEY, null);
                _rightUpdater.addRightAuth(orgobject[0], String.valueOf(documentAuth.getId()), String.valueOf(document.getId()), entity, false, _rightTypes);
            }

            Map remindInfo = new HashMap();
            String title = (new StringBuilder()).append("\u8BF7\u67E5\u770B\u5171\u4EAB\u6587\u4EF6\u300A").append(document.getDocumentName()).append("\u300B").toString();
            String context = (new StringBuilder()).append(RequestContext.getCurrentUser().getDisplayName()).append("\u7ED9\u4F60\u5171\u4EAB\u4E86\u4E00\u4E2A\u6587\u4EF6\u300A").append(document.getDocumentName()).append("\u300B,").append("\u8BF7\u5728\u4E2A\u4EBA\u6587\u6863\u9875\u9762\u4E2D\u7684\u5171\u4EAB\u6587\u4EF6\u67E5\u770B\uFF0C\u5E76<a href=\"/routine/job/viewdocument.jsp?_ssid_=").append(StringUtil.safeToString(request.getSession().getAttribute("_ssid_"), null)).append("&companytype=true&documentid=").append(document.getId()).append("\">\u56DE\u590D</a>\u3002").toString();
            remindInfo.put("recieverNames", keyreaders);
            remindInfo.put("title", title);
            remindInfo.put("context", context);
            KeyReaderAction.sendRemindMessage("RT_KEYREADER_REMIND1", remindInfo);
        }
        return result;
    }
}
