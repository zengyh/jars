// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CompanyDirDocAuthAction.java

package com.sinitek.sirm.web.routine;

import com.sinitek.sirm.busin.routine.entity.IRTDocumentAuth;
import com.sinitek.sirm.busin.routine.service.*;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.TimeUtil;
import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.sirm.org.busin.service.IRightService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.spirit.webcontrol.table.ICheckBoxPluginAware;
import com.sinitek.spirit.webcontrol.table.ITableAware;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

public class CompanyDirDocAuthAction
    implements ITableAware, ICheckBoxPluginAware
{

    public CompanyDirDocAuthAction()
    {
    }

    public Object callCheckBoxPlugin(List maps, Map map, HttpServletRequest request1)
        throws Exception
    {
        IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        IRightService rightService = OrgServiceFactory.getRightService();
        String rightkey = String.valueOf(map.get("entity"));
        String rights = "'MODIFY','REMODIFY','CREATESUB','RECREATESUB','CREATEDOC','RECREATEDOC'";
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
                if(documentAuth != null)
                {
                    rightService.deleteRightAuth(new OrgObject(documentAuth.getSharinger(), 8), String.valueOf(documentAuth.getAuthid()), rightkey, new String[] {
                        "READ", "REREAD", "WRITE", "REWRITE", "DOWNLOAD", "REDOWNLOAD"
                    });
                    if(!documentAuthService.checkAuth(documentAuth.getAuthid().intValue(), documentAuth.getSharinger(), rights, rightkey))
                        documentAuthService.delDocumentAuth(documentAuth);
                }
            } while(true);
        }
        return "ok";
    }

    public Object getResult(Map map, HttpServletRequest request1)
        throws Exception
    {
        IRTDirectoryService service = BaseRoutineServiceFactory.getDirectoryService();
        int directoryid = NumberTool.safeToInteger(map.get("directoryid"), Integer.valueOf(-1)).intValue();
        return service.searchAllCompanyDirDocAuthByDirId(directoryid);
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
}
