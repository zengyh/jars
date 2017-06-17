// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowActionTest2.java

package com.sinitek.sirm.busin.workflow.support.action;

import com.sinitek.sirm.busin.workflow.service.IWorkflowSupportService;
import com.sinitek.sirm.busin.workflow.service.WorkflowServiceFactory;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.common.web.RequestContext;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

public class WorkflowActionTest2
{

    public WorkflowActionTest2()
    {
    }

    public static Object startProcess(Map maps, HttpServletRequest request)
    {
        String syscode = StringUtil.safeToString(maps.get("syscode"), "");
        java.util.List entrylist = new LinkedList();
        Map _map = new HashMap();
        Map _mainmap = new HashMap();
        Map _paramap = new HashMap();
        _mainmap.put("syscode", syscode);
        _mainmap.put("starterid", RequestContext.getCurrentUser().getOrgId());
        _mainmap.put("brief", "");
        _map.put("mainmap", _mainmap);
        _map.put("entrylist", entrylist);
        Map _resultmap = WorkflowServiceFactory.getWorkflowSupportService().startProcess(_map);
        return "";
    }

    public static Object subProcessStep(Map maps, HttpServletRequest request)
    {
        Integer condition = NumberTool.safeToInteger(maps.get("condition"), Integer.valueOf(-1));
        String approveopinion = StringUtil.safeToString(maps.get("approveopinion"), "");
        Integer returntargetstepid = NumberTool.safeToInteger(maps.get("returntargetstepid"), Integer.valueOf(-1));
        Integer aftstepid = NumberTool.safeToInteger(maps.get("aftstepid"), Integer.valueOf(-1));
        Map examplemap = new HashMap();
        if(condition.intValue() == 2)
        {
            Map oncelinkmap = new HashMap();
            if(returntargetstepid.intValue() != -1)
                oncelinkmap.put("returntargetstepid", returntargetstepid);
            if(aftstepid.intValue() != -1)
                oncelinkmap.put("aftstepid", aftstepid);
            examplemap.put("oncelinkmap", oncelinkmap);
        }
        Map _ownermap = new HashMap();
        _ownermap.put("condition", condition);
        _ownermap.put("approveopinion", approveopinion);
        Map paraMap = new HashMap();
        examplemap.put("ownermap", _ownermap);
        examplemap.put("paramap", paraMap);
        examplemap.put("exampleid", NumberTool.safeToInteger(maps.get("exampleid"), Integer.valueOf(-1)));
        examplemap.put("examplestepid", NumberTool.safeToInteger(maps.get("examplestepid"), Integer.valueOf(-1)));
        examplemap.put("exampleownerid", NumberTool.safeToInteger(maps.get("exampleownerid"), Integer.valueOf(-1)));
        Map _resultmap2 = WorkflowServiceFactory.getWorkflowSupportService().subProcessStep(examplemap);
        return "";
    }
}
