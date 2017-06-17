// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CompanyBatchAuthAction.java

package com.sinitek.sirm.web.routine;

import com.sinitek.sirm.busin.routine.entity.*;
import com.sinitek.sirm.busin.routine.service.*;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.utils.*;
import com.sinitek.sirm.common.web.RequestContext;
import com.sinitek.sirm.common.web.tag.PageLoadContext;
import com.sinitek.sirm.org.busin.service.IRightService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.sirm.org.busin.service.impl.RightServiceImpl;
import com.sinitek.spirit.right.client.RightFactory;
import com.sinitek.spirit.right.core.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

public class CompanyBatchAuthAction
{

    public CompanyBatchAuthAction()
    {
    }

    public String saveAuth(Map map, HttpServletRequest request1)
        throws Exception
    {
        IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        IRightService rightService = OrgServiceFactory.getRightService();
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        String orgid = RequestContext.getCurrentUser().getOrgId();
        String entity = StringUtil.safeToString(map.get("entity"), "");
        String documentids = StringUtil.safeToString(map.get("documentids"), "");
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

        if("".equals(documentids))
            return "\u8BF7\u9009\u62E9\u6388\u6743\u6587\u4EF6\uFF01";
        int write = NumberTool.safeToInteger(map.get("write"), Integer.valueOf(0)).intValue();
        int rewrite = NumberTool.safeToInteger(map.get("rewrite"), Integer.valueOf(0)).intValue();
        int read = NumberTool.safeToInteger(map.get("read"), Integer.valueOf(0)).intValue();
        int reread = NumberTool.safeToInteger(map.get("reread"), Integer.valueOf(0)).intValue();
        int download = NumberTool.safeToInteger(map.get("download"), Integer.valueOf(0)).intValue();
        int redownload = NumberTool.safeToInteger(map.get("redownload"), Integer.valueOf(0)).intValue();
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
        IRightType _rightTypes[] = new IRightType[authtypeList.size()];
        int i = 0;
        for(int length = authtypeList.size(); i < length; i++)
            _rightTypes[i] = new com.sinitek.sirm.org.busin.service.impl.RightServiceImpl.RightType((String)authtypeList.get(i));

        String documentidArr[] = documentids.split("-");
        for(int m = 0; m < documentidArr.length; m++)
        {
            id = NumberTool.safeToInteger(documentidArr[m], Integer.valueOf(-1)).intValue();
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
                if(!_rightUpdater.hasRightDefine(entity))
                    _rightUpdater.createRightDefine(entity, (new StringBuilder()).append("\u6388\u6743\uFF1A").append(entity).toString(), AuthType.OBJECT_KEY, null);
                _rightUpdater.addRightAuth((String)orglist.get(k), String.valueOf(documentAuth.getId()), String.valueOf(id), entity, false, _rightTypes);
            }

        }

        return "ok";
    }

    public static void loadpage(PageLoadContext context)
    {
        IRTDocumentsService irtDocumentsService = BaseRoutineServiceFactory.getDocumentsService();
        IRTDocuments irtDocuments = null;
        String docnames = "";
        String ids = context.getRequest().getParameter("documentids");
        if(ids != null && !"".equals(ids))
        {
            String idsarr[] = ids.split("-");
            for(int i = 0; i < idsarr.length; i++)
            {
                irtDocuments = irtDocumentsService.getDocumentById(NumberTool.safeToInteger(idsarr[i], Integer.valueOf(-1)).intValue());
                if(irtDocuments == null)
                    continue;
                if(docnames.equals(""))
                    docnames = (new StringBuilder()).append(docnames).append(irtDocuments.getDocumentName()).toString();
                else
                    docnames = (new StringBuilder()).append(docnames).append(",").append(irtDocuments.getDocumentName()).toString();
            }

        }
        context.getRequest().setAttribute("docnames", docnames);
    }
}
