// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DirectoryAuthAction.java

package com.sinitek.sirm.web.routine;

import com.sinitek.sirm.busin.routine.entity.*;
import com.sinitek.sirm.busin.routine.func.DocumentRightFunction;
import com.sinitek.sirm.busin.routine.service.*;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.utils.*;
import com.sinitek.sirm.common.web.RequestContext;
import com.sinitek.sirm.common.web.tag.PageLoadContext;
import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.sirm.org.busin.service.IRightService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.sirm.org.busin.service.impl.RightServiceImpl;
import com.sinitek.spirit.right.client.RightFactory;
import com.sinitek.spirit.right.core.*;
import com.sinitek.spirit.webcontrol.table.ICheckBoxPluginAware;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

public class DirectoryAuthAction
    implements ITableAware, ICheckBoxPluginAware
{

    public DirectoryAuthAction()
    {
    }

    public Object callCheckBoxPlugin(List maps, Map map, HttpServletRequest request1)
        throws Exception
    {
        IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        IRightService rightService = OrgServiceFactory.getRightService();
        String rightkey = String.valueOf(map.get("entity"));
        String rights = "'READ', 'REREAD', 'WRITE', 'REWRITE', 'DOWNLOAD', 'REDOWNLOAD'";
        if(!"".equals(rightkey))
        {
            Iterator i$ = maps.iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Map objids = (Map)i$.next();
                int objid = NumberTool.convertMapKeyToInt(objids, "objid").intValue();
                IRTDocumentAuth documentAuth = (IRTDocumentAuth)documentAuthService.getDocumentAuth(objid);
                rightService.deleteRightAuth(new OrgObject(documentAuth.getSharinger(), 8), String.valueOf(documentAuth.getAuthid()), rightkey, new String[] {
                    "MODIFY", "CREATESUB", "CREATEDOC", "REMODIFY", "RECREATESUB", "RECREATEDOC"
                });
                if(!documentAuthService.checkAuth(documentAuth.getAuthid().intValue(), documentAuth.getSharinger(), rights, rightkey))
                    documentAuthService.delDocumentAuth(documentAuth);
            } while(true);
        }
        return "ok";
    }

    public Object getResult(Map map, HttpServletRequest request1)
        throws Exception
    {
        IRTDirectoryService service = BaseRoutineServiceFactory.getDirectoryService();
        int directoryid = NumberTool.safeToInteger(map.get("directoryid"), Integer.valueOf(-1)).intValue();
        return service.searchAllCompanyDirAuthEmpByDirId(directoryid);
    }

    public String setDefaultOrderBy(Map map, HttpServletRequest request1)
        throws Exception
    {
        return "CREATETIMESTAMP desc";
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

    public String saveAuth(Map map, HttpServletRequest request1)
        throws Exception
    {
        IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        IRightService rightService = OrgServiceFactory.getRightService();
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        String orgid = RequestContext.getCurrentUser().getOrgId();
        String entity = StringUtil.safeToString(map.get("entity"), "");
        int directoryid = NumberTool.safeToInteger(map.get("directoryid"), Integer.valueOf(-1)).intValue();
        int documentid = NumberTool.safeToInteger(map.get("documentid"), Integer.valueOf(-1)).intValue();
        String authorgids = StringUtil.safeToString(map.get("authorgids"), "");
        String starttime = StringUtil.safeToString(map.get("starttime"), "");
        String endtime = StringUtil.safeToString(map.get("endtime"), "");
        if(!"".equals(starttime))
            starttime = (new StringBuilder()).append(starttime).append(" 00:00:00").toString();
        else
            starttime = null;
        if(!"".equals(endtime))
            endtime = (new StringBuilder()).append(endtime).append(" 23:59:59").toString();
        else
            endtime = null;
        List authtypeList = new ArrayList();
        int id = -1;
        List orglist = new ArrayList();
        if("".equals(authorgids))
            return "\u8BF7\u9009\u62E9\u8981\u8D4B\u6743\u7684\u4EBA!";
        String tempStr[] = authorgids.split(",");
        for(int i = 0; i < tempStr.length; i++)
            orglist.add(tempStr[i].split(":")[0]);

        int write = NumberTool.safeToInteger(map.get("write"), Integer.valueOf(0)).intValue();
        int rewrite = NumberTool.safeToInteger(map.get("rewrite"), Integer.valueOf(0)).intValue();
        int read = NumberTool.safeToInteger(map.get("read"), Integer.valueOf(0)).intValue();
        int reread = NumberTool.safeToInteger(map.get("reread"), Integer.valueOf(0)).intValue();
        int download = NumberTool.safeToInteger(map.get("download"), Integer.valueOf(0)).intValue();
        int redownload = NumberTool.safeToInteger(map.get("redownload"), Integer.valueOf(0)).intValue();
        if("RTDIRECTORY".equals(entity))
        {
            id = directoryid;
            int modify = NumberTool.safeToInteger(map.get("modify"), Integer.valueOf(0)).intValue();
            int remodify = NumberTool.safeToInteger(map.get("remodify"), Integer.valueOf(0)).intValue();
            int createsub = NumberTool.safeToInteger(map.get("createsub"), Integer.valueOf(0)).intValue();
            int recreatesub = NumberTool.safeToInteger(map.get("recreatesub"), Integer.valueOf(0)).intValue();
            int createdoc = NumberTool.safeToInteger(map.get("createdoc"), Integer.valueOf(0)).intValue();
            int recreatedoc = NumberTool.safeToInteger(map.get("recreatedoc"), Integer.valueOf(0)).intValue();
            if(modify != 0)
                if(remodify != 0)
                    authtypeList.add("REMODIFY");
                else
                    authtypeList.add("MODIFY");
            if(createsub != 0)
                if(recreatesub != 0)
                    authtypeList.add("RECREATESUB");
                else
                    authtypeList.add("CREATESUB");
            if(createdoc != 0)
                if(recreatedoc != 0)
                    authtypeList.add("RECREATEDOC");
                else
                    authtypeList.add("CREATEDOC");
            if(read != 0)
                if(reread != 0)
                    authtypeList.add("REREAD");
                else
                    authtypeList.add("READ");
            if(write != 0)
                if(rewrite != 0)
                    authtypeList.add("REWRITE");
                else
                    authtypeList.add("WRITE");
            if(download != 0)
                if(redownload != 0)
                    authtypeList.add("REDOWNLOAD");
                else
                    authtypeList.add("DOWNLOAD");
            if(authtypeList.size() == 0)
                return "\u8BF7\u9009\u62E9\u8981\u8D4B\u4E88\u7684\u6743\u9650!";
        } else
        {
            id = documentid;
            if(read != 0)
                if(reread != 0)
                    authtypeList.add("REREAD");
                else
                    authtypeList.add("READ");
            if(write != 0)
                if(rewrite != 0)
                    authtypeList.add("REWRITE");
                else
                    authtypeList.add("WRITE");
            if(download != 0)
                if(redownload != 0)
                    authtypeList.add("REDOWNLOAD");
                else
                    authtypeList.add("DOWNLOAD");
            if(authtypeList.size() == 0)
                return "\u8BF7\u9009\u62E9\u8981\u8D4B\u4E88\u7684\u6743\u9650!";
        }
        IRightType _rightTypes[] = new IRightType[authtypeList.size()];
        int i = 0;
        for(int length = authtypeList.size(); i < length; i++)
            _rightTypes[i] = new com.sinitek.sirm.org.busin.service.impl.RightServiceImpl.RightType((String)authtypeList.get(i));

        List savedAuths = new ArrayList();
        for(int k = 0; k < orglist.size(); k++)
        {
            IRTDocumentAuth documentAuth = new RTDocumentAuthImpl();
            documentAuth.setAuthid(Integer.valueOf(id));
            documentAuth.setBegintime(TimeUtil.formatDate(starttime, "yyyy-MM-dd HH:mm:ss"));
            documentAuth.setEndtime(TimeUtil.formatDate(endtime, "yyyy-MM-dd HH:mm:ss"));
            documentAuth.setOrgid(orgid);
            documentAuth.setSharinger((String)orglist.get(k));
            documentAuth.setAuthEntity(entity);
            documentAuthService.saveCompanyDocumentAuth(documentAuth);
            savedAuths.add(documentAuth);
            if(!_rightUpdater.hasRightDefine(entity))
                _rightUpdater.createRightDefine(entity, (new StringBuilder()).append("\u6388\u6743\uFF1A").append(entity).toString(), AuthType.OBJECT_KEY, null);
            _rightUpdater.addRightAuth((String)orglist.get(k), String.valueOf(documentAuth.getId()), String.valueOf(id), entity, false, _rightTypes);
        }

        int applyTo = NumberTool.safeToInteger(map.get("applyTo"), Integer.valueOf(1)).intValue();
        if(applyTo == 2 && "RTDIRECTORY".equals(entity))
            documentAuthService.copyToChildren(id, _rightTypes, savedAuths);
        return "ok";
    }

    public Object checkDirectoryCreater(Map map, HttpServletRequest request1)
        throws Exception
    {
        int id = NumberTool.safeToInteger(map.get("id"), Integer.valueOf(0)).intValue();
        String orgid = RequestContext.getCurrentUser().getOrgId();
        String entity = StringUtil.safeToString(map.get("entityname"), "");
        if("RTDIRECTORY".equals(entity))
        {
            IRTDirectory irtDirectory = BaseRoutineServiceFactory.getDirectoryService().getDirectoryById(id);
            if(irtDirectory != null && orgid.equals(irtDirectory.getDirectoryer()))
                return Boolean.valueOf(true);
        } else
        {
            IRTDocuments irtDocuments = BaseRoutineServiceFactory.getDocumentsService().getDocumentById(id);
            if(irtDocuments != null && orgid.equals(irtDocuments.getDocumenter()))
                return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public Object checkModifyAuth(Map param, HttpServletRequest request1)
    {
        IRTDocumentAuthService service = BaseRoutineServiceFactory.getDocumentAuthService();
        IRTDirectoryService irtDirectoryService = BaseRoutineServiceFactory.getDirectoryService();
        String modifyAuth[] = {
            "MODIFY", "REMODIFY"
        };
        boolean result = false;
        String orgid = RequestContext.getCurrentUser().getOrgId();
        String entity = StringUtil.safeToString(param.get("entityname"), "");
        int id = NumberTool.safeToInteger(param.get("id"), Integer.valueOf(-1)).intValue();
        IRTDirectory irtDirectory = irtDirectoryService.getDirectoryById(id);
        if(irtDirectory != null)
            if(irtDirectory.getDirectoryer().equals(orgid))
                result = true;
            else
            if("RTDIRECTORY".equals(entity))
                result = DocumentRightFunction.checkDirectoryRight(orgid, id, modifyAuth);
        return Boolean.valueOf(result);
    }

    public static void loadpage(PageLoadContext context)
    {
        List rightlist = new ArrayList();
        rightlist.add("REMODIFY");
        rightlist.add("RECREATESUB");
        rightlist.add("RECREATEDOC");
        rightlist.add("REREAD");
        rightlist.add("REWRITE");
        rightlist.add("REDOWNLOAD");
        String orgid = RequestContext.getCurrentUser().getOrgId();
        int count = 0;
        int issame = 0;
        String righttype = "";
        int directoryid = NumberTool.safeToInteger(context.getRequest().getParameter("directoryid"), Integer.valueOf(-1)).intValue();
        for(int i = 0; i < rightlist.size(); i++)
        {
            righttype = (String)rightlist.get(i);
            if(DocumentRightFunction.checkDirectoryRight(orgid, directoryid, righttype))
            {
                context.getRequest().setAttribute(righttype, righttype);
                count++;
            }
        }

        IRTDirectory irtDirectory = BaseRoutineServiceFactory.getDirectoryService().getDirectoryById(directoryid);
        if(irtDirectory != null && orgid.equals(StringUtil.safeToString(irtDirectory.getDirectoryer(), "")))
            issame = 1;
        context.getRequest().setAttribute("count", Integer.valueOf(count));
        context.getRequest().setAttribute("issame", Integer.valueOf(issame));
    }
}
