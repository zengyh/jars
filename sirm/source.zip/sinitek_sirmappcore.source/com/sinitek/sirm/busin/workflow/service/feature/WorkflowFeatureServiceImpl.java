// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowFeatureServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.feature;

import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.service.*;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.service.feature:
//            WorkflowFeatureException, IWorkflowFeatureService, WorkflowInsteadStepTask

public class WorkflowFeatureServiceImpl extends MetaDBContextSupport
    implements IWorkflowFeatureService
{

    public WorkflowFeatureServiceImpl()
    {
    }

    public Map dealTask(WorkflowInsteadStepTask task)
    {
        IWorkflowBaseService baseService = WorkflowServiceFactory.getWorkflowBaseService();
        IWorkflowAppService appService = WorkflowServiceFactory.getWorkflowAppService();
        int exampleOnwerId = task.getExampleOwnerId();
        IWorkflowExampleStepOwner stepOwner = appService.loadExampleStepOwner(exampleOnwerId);
        if(stepOwner == null || stepOwner.getId() == 0)
            throw new WorkflowFeatureException((new StringBuilder()).append("ExampleStepOwnerId\u4E3A").append(exampleOnwerId).append("\u7684\u4EFB\u52A1\u6CA1\u6709\u627E\u5230").toString());
        int processStepId = task.getProcessStepId();
        IWorkflowProcessStep processStep = baseService.loadProcessStep(processStepId);
        if(processStep == null || processStep.getId() == 0)
            throw new WorkflowFeatureException((new StringBuilder()).append("ProcessStepId\u4E3A").append(processStepId).append("\u7684\u6B65\u9AA4\u6CA1\u6709\u627E\u5230").toString());
        IWorkflowExample example = appService.loadExample(stepOwner.getExampleId().intValue());
        int processId1 = NumberTool.safeToInteger(example.getProcessId(), Integer.valueOf(0)).intValue();
        int processId2 = NumberTool.safeToInteger(processStep.getProcessId(), Integer.valueOf(0)).intValue();
        if(processId1 != processId2)
        {
            throw new WorkflowFeatureException((new StringBuilder()).append("\u6D41\u7A0B\u4E0D\u5339\u914D\uFF0C\u539F\u6D41\u7A0B\u4E3A").append(processId1).append("\uFF0C\u76EE\u6807\u6D41\u7A0B\u4E3A").append(processId2).toString());
        } else
        {
            IWorkflowSupportService supportService = WorkflowServiceFactory.getWorkflowSupportService();
            Map submitMap = new HashMap();
            submitMap.put("exampleid", stepOwner.getExampleId());
            submitMap.put("examplestepid", stepOwner.getExampleStepId());
            submitMap.put("exampleownerid", Integer.valueOf(exampleOnwerId));
            Map ownerMap = new HashMap();
            ownerMap.put("condition", Integer.valueOf(task.getCondition()));
            ownerMap.put("approveopinion", task.getApproveOpinion());
            submitMap.put("ownermap", ownerMap);
            Map insteadMap = new HashMap();
            List stepList = new ArrayList();
            Map stepMap = new HashMap();
            stepMap.put("stepid", Integer.valueOf(processStepId));
            stepList.add(stepMap);
            insteadMap.put("steplist", stepList);
            submitMap.put("insteadmap", insteadMap);
            submitMap.put("paramap", task.getParaMap());
            Map resultMap = supportService.subProcessStep(submitMap);
            return resultMap;
        }
    }
}
