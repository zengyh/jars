// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClaimSubject.java

package com.sinitek.sirm.busin.workflow.service.claim;

import com.sinitek.sirm.busin.workflow.entity.IWorkflowExampleStep;
import com.sinitek.sirm.busin.workflow.service.IWorkflowAppService;
import com.sinitek.sirm.busin.workflow.service.WorkflowServiceFactory;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.sinitek.sirm.busin.workflow.service.claim:
//            ClaimStep

public class ClaimSubject
{

    public ClaimStep getNextStep(int exampleStepId)
    {
        IWorkflowAppService appService = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStep exampleStep = appService.loadExampleStep(exampleStepId);
        Integer processStepId = exampleStep.getProcessStepId();
        if(processStepId == null || processStepId.intValue() == 0)
            throw new RuntimeException((new StringBuilder()).append("\u6B65\u9AA4\u5B9E\u4F8B").append(exampleStepId).append("\u4E0D\u5B58\u5728").toString());
        for(int i = 0; i < steps.size() - 1; i++)
        {
            ClaimStep step = (ClaimStep)steps.get(i);
            if(step.getStepId() == processStepId.intValue())
                return (ClaimStep)steps.get(i + 1);
        }

        throw new RuntimeException((new StringBuilder()).append("\u6B65\u9AA4\u5B9E\u4F8B").append(exampleStepId).append("\u6CA1\u6709\u4E0B\u4E00\u6B65").toString());
    }

    public ClaimStep getPrevStep(int exampleStepId)
    {
        IWorkflowAppService appService = WorkflowServiceFactory.getWorkflowAppService();
        IWorkflowExampleStep exampleStep = appService.loadExampleStep(exampleStepId);
        Integer processStepId = exampleStep.getProcessStepId();
        if(processStepId == null || processStepId.intValue() == 0)
            throw new RuntimeException((new StringBuilder()).append("\u6B65\u9AA4\u5B9E\u4F8B").append(exampleStepId).append("\u4E0D\u5B58\u5728").toString());
        for(int i = 1; i < steps.size(); i++)
        {
            ClaimStep step = (ClaimStep)steps.get(i);
            if(step.getStepId() == processStepId.intValue())
                return (ClaimStep)steps.get(i - 1);
        }

        throw new RuntimeException((new StringBuilder()).append("\u6B65\u9AA4\u5B9E\u4F8B").append(exampleStepId).append("\u6CA1\u6709\u4E0A\u4E00\u6B65").toString());
    }

    public ClaimSubject(int processId)
    {
        this.processId = processId;
        steps = new ArrayList();
    }

    public int getProcessId()
    {
        return processId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getProcessType()
    {
        return processType;
    }

    void setProcessType(int processType)
    {
        this.processType = processType;
    }

    public String getBrief()
    {
        return brief;
    }

    public void setBrief(String brief)
    {
        this.brief = brief;
    }

    public int getEditUrl()
    {
        return editUrl;
    }

    public void setEditUrl(int editUrl)
    {
        this.editUrl = editUrl;
    }

    public List getSteps()
    {
        return steps;
    }

    public void setSteps(List steps)
    {
        this.steps = steps;
    }

    void addStep(ClaimStep step)
    {
        steps.add(step);
    }

    private int processId;
    private String name;
    private int processType;
    private String brief;
    private int editUrl;
    private List steps;
}
