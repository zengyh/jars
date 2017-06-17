// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowActionMarklast.java

package com.sinitek.sirm.busin.workflow.support.action;

import com.sinitek.sirm.busin.workflow.entity.IWorkflowExampleStep;
import com.sinitek.sirm.busin.workflow.service.IWorkflowAppService;
import com.sinitek.sirm.busin.workflow.service.WorkflowServiceFactory;
import com.sinitek.sirm.busin.workflow.support.IWorkflowAction;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.Map;

public class WorkflowActionMarklast
    implements IWorkflowAction
{

    public WorkflowActionMarklast()
    {
    }

    public void action(Map map, Map returnmap)
    {
        IWorkflowAppService _workapp = WorkflowServiceFactory.getWorkflowAppService();
        int examplestepid = NumberTool.convertMapKeyToInt(map, "examplestepid", Integer.valueOf(0)).intValue();
        IWorkflowExampleStep examplestep = _workapp.loadExampleStep(examplestepid);
        int laststepid = NumberTool.safeToInteger(examplestep.getProcessStepId(), Integer.valueOf(0)).intValue();
        returnmap.put("WF_LASTSTEP", Integer.valueOf(laststepid));
    }
}
