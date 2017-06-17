// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CompanyDocumentAuthAction.java

package com.sinitek.sirm.web.routine;

import com.sinitek.sirm.busin.routine.entity.*;
import com.sinitek.sirm.busin.routine.func.DocumentRightFunction;
import com.sinitek.sirm.busin.routine.service.*;
import com.sinitek.sirm.busin.workflow.enumerate.WorkflowStepCondition;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.utils.*;
import com.sinitek.sirm.common.web.RequestContext;
import com.sinitek.sirm.common.web.tag.PageLoadContext;
import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.sirm.org.busin.service.IRightService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.spirit.webcontrol.table.ICheckBoxPluginAware;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

public class CompanyDocumentAuthAction
    implements ITableAware, ICheckBoxPluginAware
{

    public CompanyDocumentAuthAction()
    {
    }

    public Object callCheckBoxPlugin(List maps, Map map, HttpServletRequest request1)
        throws Exception
    {
        IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        IRightService rightService = OrgServiceFactory.getRightService();
        String rightkey = String.valueOf(map.get("entity"));
        if(!"".equals(rightkey))
        {
            IRTDocumentAuth documentAuth;
            for(Iterator i$ = maps.iterator(); i$.hasNext(); documentAuthService.delDocumentAuth(documentAuth))
            {
                Map objids = (Map)i$.next();
                int objid = NumberTool.convertMapKeyToInt(objids, "objid").intValue();
                documentAuth = (IRTDocumentAuth)documentAuthService.getDocumentAuth(objid);
                rightService.deleteRightAuth(new OrgObject(documentAuth.getSharinger(), 8), String.valueOf(documentAuth.getAuthid()), rightkey, new String[] {
                    "READ", "WRITE", "DOWNLOAD", "REREAD", "REWRITE", "REDOWNLOAD"
                });
            }

        }
        return "ok";
    }

    public Object getResult(Map map, HttpServletRequest request1)
        throws Exception
    {
        int documentid = NumberTool.safeToInteger(map.get("documentid"), Integer.valueOf(-1)).intValue();
        return BaseRoutineServiceFactory.getDirectoryService().searchCompanyDocAuthByDocid(documentid);
    }

    public String setDefaultOrderBy(Map map, HttpServletRequest request1)
        throws Exception
    {
        return "CREATETIMESTAMP DESC";
    }

    public String getTime(Map stringMap, HttpServletRequest httpServletRequest)
    {
        String _result = "";
        Object starttime = stringMap.get("begintime");
        Object endtime = stringMap.get("endtime");
        if(starttime == null && endtime == null)
            _result = "\u6C38\u4E45\u6709\u6548";
        else
            _result = (new StringBuilder()).append(TimeUtil.formatDateToString(String.valueOf(starttime), "yyyy-MM-dd")).append("-").append(TimeUtil.formatDateToString(String.valueOf(endtime), "yyyy-MM-dd")).toString();
        return _result;
    }

    public String saveApplyInfo(Map map, HttpServletRequest request1)
    {
        int documentid = NumberTool.safeToInteger(map.get("documentid"), Integer.valueOf(-1)).intValue();
        String rights = StringUtil.safeToString(map.get("auths"), "");
        String starttime = StringUtil.safeToString(map.get("starttime"), "");
        String endtime = StringUtil.safeToString(map.get("endtime"), "");
        String applyreason = StringUtil.safeToString(map.get("applyreason"), "");
        String orgid = RequestContext.getCurrentUser().getOrgId();
        int authapplyid = NumberTool.safeToInteger(map.get("authapplyid"), Integer.valueOf(-1)).intValue();
        int exampleid = NumberTool.safeToInteger(map.get("exampleid"), Integer.valueOf(-1)).intValue();
        IRTApplyDocAuth irtApplyDocAuth = BaseRoutineServiceFactory.getDocumentAuthService().getApplyDocAuthById(authapplyid);
        IRTDocuments irtDocuments = BaseRoutineServiceFactory.getDocumentsService().getDocumentById(documentid);
        if(irtDocuments != null)
        {
            IRTDirectoryAppend irtDirectoryAppend = BaseRoutineServiceFactory.getDirectoryService().getDirectoryAppendByDirectoryId(irtDocuments.getDirectoryId().intValue());
            if(irtDirectoryAppend.getAllowapplyautor().intValue() != 1)
                return "\u8BE5\u6587\u6863\u4E0D\u80FD\u7533\u8BF7\u6743\u9650\uFF01";
            Integer workflowId = NumberTool.safeToInteger(irtDirectoryAppend.getWorkflowid(), Integer.valueOf(-1));
            if(workflowId == null || workflowId.intValue() == -1 || workflowId.equals(Integer.valueOf(-1)))
                return "\u5DE5\u4F5C\u6D41\u914D\u7F6E\u6709\u8BEF\uFF01";
            if("".equals(rights))
                return "\u8BF7\u9009\u62E9\u6743\u9650!";
            if(!"".equals(starttime))
                starttime = (new StringBuilder()).append(starttime).append(" 00:00:00").toString();
            else
                starttime = null;
            if(!"".equals(endtime))
                endtime = (new StringBuilder()).append(endtime).append(" 23:59:59").toString();
            else
                endtime = null;
            if(irtApplyDocAuth == null)
                irtApplyDocAuth = new RTApplyDocAuthImpl();
            irtApplyDocAuth.setApplyreason(applyreason);
            irtApplyDocAuth.setApplytime(new Date());
            irtApplyDocAuth.setDocumentid(Integer.valueOf(documentid));
            irtApplyDocAuth.setEndtime(TimeUtil.formatDate(endtime, "yyyy-MM-dd HH:mm:ss"));
            irtApplyDocAuth.setStarttime(TimeUtil.formatDate(starttime, "yyyy-MM-dd HH:mm:ss"));
            irtApplyDocAuth.setOrgid(Integer.valueOf(orgid));
            irtApplyDocAuth.setStatus(Integer.valueOf(0));
            irtApplyDocAuth.setApplyauth(rights);
            BaseRoutineServiceFactory.getDocumentAuthService().saveApplyDocAuth(irtApplyDocAuth);
            String brief = "\u6587\u6863\u6743\u9650\u5BA1\u6279\u6D41\u7A0B";
            if(workflowId.intValue() > 0)
            {
                Map _map = new HashMap();
                Map _mainmap = new HashMap();
                Map _ownermap = new HashMap();
                Map submap = new HashMap();
                if(exampleid == -1)
                {
                    _mainmap.put("syscode", workflowId);
                    _mainmap.put("starterid", orgid);
                    _mainmap.put("brief", brief);
                    List _entrylist = new ArrayList();
                    Map _entrymap = new HashMap();
                    _entrymap.put("sourcename", "RTAPPLYDOCAUTH");
                    _entrymap.put("sourceid", Integer.valueOf(irtApplyDocAuth.getId()));
                    _entrylist.add(_entrymap);
                    _map.put("mainmap", _mainmap);
                    _map.put("entrylist", _entrylist);
                    Map resultmap = WorkflowServiceFactory.getWorkflowSupportService().startProcess(_map);
                    List _resultlist = (List)resultmap.get("resultlist");
                    for(Iterator i$ = _resultlist.iterator(); i$.hasNext();)
                    {
                        Map resmap = (Map)i$.next();
                        irtApplyDocAuth.setExampleid((Integer)resmap.get("exampleid"));
                        BaseRoutineServiceFactory.getDocumentAuthService().saveApplyDocAuth(irtApplyDocAuth);
                        _ownermap.put("condition", Integer.valueOf(WorkflowStepCondition.WF_STEP_SUBMIT.getEnumItemValue()));
                        _ownermap.put("approveopinion", "\u63D0\u4EA4\u6587\u6863\u6743\u9650\u5BA1\u6279\u4FE1\u606F\u6210\u529F");
                        submap.put("ownermap", _ownermap);
                        submap.put("exampleid", resmap.get("exampleid"));
                        submap.put("examplestepid", resmap.get("examplestepid"));
                        submap.put("exampleownerid", resmap.get("exampleownerid"));
                        Map _resultmap = WorkflowServiceFactory.getWorkflowSupportService().subProcessStep(submap);
                    }

                } else
                {
                    List list = WorkflowServiceFactory.getWorkflowAppService().getExampleOwnerListByExampleid(exampleid, 0);
                    Iterator i$ = list.iterator();
                    do
                    {
                        if(!i$.hasNext())
                            break;
                        Map _T_map = (Map)i$.next();
                        if(orgid.equals(StringUtil.safeToString(_T_map.get("ownerid"), "")))
                        {
                            _ownermap.put("condition", Integer.valueOf(WorkflowStepCondition.WF_STEP_SUBMIT.getEnumItemValue()));
                            _ownermap.put("approveopinion", "\u4FEE\u6539\u6587\u6863\u6743\u9650\u5BA1\u6279\u4FE1\u606F\u6210\u529F");
                            _map.put("ownermap", _ownermap);
                            _map.put("exampleid", Integer.valueOf(exampleid));
                            _map.put("examplestepid", StringUtil.safeToString(_T_map.get("examplestepid"), ""));
                            _map.put("exampleownerid", StringUtil.safeToString(_T_map.get("exampleownerid"), ""));
                            Map _resultmap = WorkflowServiceFactory.getWorkflowSupportService().subProcessStep(_map);
                        }
                    } while(true);
                }
            } else
            {
                BaseRoutineServiceFactory.getDocumentAuthService().removeApplyDocAuth(irtApplyDocAuth);
                return "\u5DE5\u4F5C\u6D41\u914D\u7F6E\u6709\u8BEF\uFF01";
            }
        } else
        {
            return "\u7CFB\u7EDF\u51FA\u9519\uFF0C\u7A0D\u540E\u91CD\u8BD5\uFF01";
        }
        return "success";
    }

    public Object getDocumentInfo(Map map, HttpServletRequest request1)
    {
        String path = "";
        String documentname = "";
        int documentid = NumberTool.safeToInteger(map.get("documentid"), Integer.valueOf(0)).intValue();
        int docid = 0;
        IRTDocuments irtDocuments = BaseRoutineServiceFactory.getDocumentsService().getDocumentById(documentid);
        if(irtDocuments != null)
        {
            docid = irtDocuments.getDirectoryId().intValue();
            documentname = irtDocuments.getDocumentName();
        }
        List list = BaseRoutineServiceFactory.getDirectoryService().getAllParentDirByDocid(docid);
        if(list != null && list.size() > 0)
        {
            for(int i = 0; i < list.size(); i++)
                path = (new StringBuilder()).append(path).append("\\").append(((Map)list.get(i)).get("directoryname")).toString();

        }
        Map returnMap = new HashMap();
        returnMap.put("path", path);
        returnMap.put("documentname", documentname);
        return returnMap;
    }

    public Object getApplyDocInfo(Map map, HttpServletRequest request1)
    {
        int documentid = NumberTool.safeToInteger(map.get("documentid"), Integer.valueOf(-1)).intValue();
        int orgid = NumberTool.safeToInteger(RequestContext.getCurrentUser().getOrgId(), Integer.valueOf(-1)).intValue();
        String authtime = "";
        String path = "";
        String documentname = "";
        String applyreason = "";
        String applyauth = "";
        String statusstr = "";
        int status = -1;
        IRTApplyDocAuth irtApplyDocAuth = BaseRoutineServiceFactory.getDocumentAuthService().getApplyDocAuth(documentid, orgid, -1);
        if(irtApplyDocAuth != null)
        {
            if(irtApplyDocAuth.getApplyreason() != null && !"".equals(irtApplyDocAuth.getApplyreason()))
                applyreason = irtApplyDocAuth.getApplyreason().replace("\n", "<br>");
            applyauth = irtApplyDocAuth.getApplyauth().replace(",", "&nbsp&nbsp");
            applyauth = applyauth.replace("READ", "\u67E5\u770B");
            applyauth = applyauth.replace("WRITE", "\u4FEE\u6539");
            applyauth = applyauth.replace("DOWNLOAD", "\u4E0B\u8F7D");
            if(irtApplyDocAuth.getStarttime() == null && irtApplyDocAuth.getEndtime() == null)
                authtime = "\u6C38\u4E45\u6709\u6548";
            else
                authtime = (new StringBuilder()).append(TimeUtil.formatDate(irtApplyDocAuth.getStarttime(), "yyyy-MM-dd")).append("~").append(TimeUtil.formatDate(irtApplyDocAuth.getEndtime(), "yyyy-MM-dd")).toString();
            status = irtApplyDocAuth.getStatus().intValue();
            if(status == 0)
                statusstr = "\u5BA1\u6279\u4E2D";
            else
            if(status == 1)
                statusstr = "\u5BA1\u6279\u901A\u8FC7";
            else
            if(status == 2)
                statusstr = "\u5BA1\u6279\u9A73\u56DE";
        }
        IRTDocuments irtDocuments = BaseRoutineServiceFactory.getDocumentsService().getDocumentById(documentid);
        if(irtDocuments != null)
        {
            documentname = irtDocuments.getDocumentName();
            List list = BaseRoutineServiceFactory.getDirectoryService().getAllParentDirByDocid(irtDocuments.getDirectoryId().intValue());
            if(list != null && list.size() > 0)
            {
                for(int i = 0; i < list.size(); i++)
                    path = (new StringBuilder()).append(path).append("\\").append(((Map)list.get(i)).get("directoryname")).toString();

            }
        }
        Map returnMap = new HashMap();
        returnMap.put("documentname", documentname);
        returnMap.put("path", path);
        returnMap.put("authtime", authtime);
        returnMap.put("applyreason", applyreason);
        returnMap.put("applyauth", applyauth);
        returnMap.put("status", statusstr);
        returnMap.put("status1", Integer.valueOf(status));
        return returnMap;
    }

    public String endWorkFlow(Map map, HttpServletRequest request1)
    {
        int exampleid = NumberTool.safeToInteger(map.get("exampleid"), Integer.valueOf(-1)).intValue();
        if(exampleid != -1)
        {
            List entityList = WorkflowServiceFactory.getWorkflowSupportService().getExampleEntryList(exampleid);
            int appdocid = NumberTool.convertMapKeyToInt((Map)entityList.get(0), "sourceid").intValue();
            IRTApplyDocAuth irtApplyDocAuth = BaseRoutineServiceFactory.getDocumentAuthService().getApplyDocAuthById(appdocid);
            WorkflowServiceFactory.getWorkflowWebService().endProcess(exampleid);
            irtApplyDocAuth.setStatus(Integer.valueOf(-1));
            BaseRoutineServiceFactory.getDocumentAuthService().saveApplyDocAuth(irtApplyDocAuth);
            BaseRoutineServiceFactory.getDocumentAuthService().removeApplyDocAuth(irtApplyDocAuth);
            return "ok";
        } else
        {
            return "";
        }
    }

    public static void loadpage(PageLoadContext context)
    {
        List rightlist = new ArrayList();
        rightlist.add("REREAD");
        rightlist.add("REWRITE");
        rightlist.add("REDOWNLOAD");
        int issame = 0;
        int count = 0;
        String righttype = "";
        String orgid = RequestContext.getCurrentUser().getOrgId();
        int documentid = NumberTool.safeToInteger(context.getRequest().getParameter("documentid"), Integer.valueOf(-1)).intValue();
        IRTDocuments irtDocuments = BaseRoutineServiceFactory.getDocumentsService().getDocumentById(documentid);
        if(irtDocuments != null)
        {
            if(irtDocuments.getDocumenter().equals(orgid))
                issame = 1;
            for(int i = 0; i < rightlist.size(); i++)
            {
                righttype = (String)rightlist.get(i);
                if(DocumentRightFunction.checkDirectoryRight(orgid, irtDocuments.getDirectoryId().intValue(), righttype) || DocumentRightFunction.checkDocumentRight(orgid, irtDocuments.getId(), righttype))
                {
                    context.getRequest().setAttribute(righttype, righttype);
                    count++;
                }
            }

        }
        context.getRequest().setAttribute("count", Integer.valueOf(count));
        context.getRequest().setAttribute("issame", Integer.valueOf(issame));
    }
}
