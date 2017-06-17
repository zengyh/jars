// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowClaimDaoServiceImpl.java

package com.sinitek.sirm.busin.workflow.service.claim;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.sirm.busin.workflow.entity.*;
import com.sinitek.sirm.busin.workflow.service.IWorkflowAppService;
import com.sinitek.sirm.busin.workflow.service.WorkflowServiceFactory;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import java.util.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.service.claim:
//            ExampleTaskQuery, IWorkflowClaimDaoService

public class WorkflowClaimDaoServiceImpl extends MetaDBContextSupport
    implements IWorkflowClaimDaoService
{

    public WorkflowClaimDaoServiceImpl()
    {
    }

    public IWorkflowExampleAskFor loadExampleAskFor(int objid)
    {
        if(objid <= 0)
            return new WorkflowExampleAskForImpl();
        else
            return (IWorkflowExampleAskFor)getMetaDBContext().get("WFEXAMPLEASKFOR", objid);
    }

    public void saveExampleAskFor(IWorkflowExampleAskFor askFor)
    {
        boolean mark = false;
        if(askFor.getId() == 0)
            mark = true;
        askFor.save();
        if(mark)
        {
            IWorkflowAppService appService = WorkflowServiceFactory.getWorkflowAppService();
            IWorkflowExampleStepOwner exampleOwner = appService.loadExampleStepOwner(askFor.getExampleOwnerId().intValue());
            IWorkflowExampleStep exampleStep = appService.loadExampleStep(exampleOwner.getExampleStepId().intValue());
            IWorkflowExample example = appService.loadExample(exampleOwner.getExampleId().intValue());
            IWorkflowExampleTask task = loadExampleTask(0);
            task.setSourceEntity("Wf_ExampleAskFor");
            task.setSourceId(Integer.valueOf(askFor.getId()));
            task.setDealerId(askFor.getOwnerId());
            task.setOrginerId(askFor.getAskerId());
            task.setStatus(Integer.valueOf(0));
            task.setStartTime(new Date());
            task.setDescription(example.getBrief());
            task.setRemarks((new StringBuilder()).append(example.getProcessName()).append(" ").append(exampleStep.getProcessStepName()).toString());
            task.setSort(Integer.valueOf(0));
            saveExampleTask(task);
        } else
        {
            ExampleTaskQuery taskQuery = new ExampleTaskQuery();
            taskQuery.setSourceEntity(ExampleTaskQuery.EXAMPLE_ASKFOR);
            taskQuery.setSourceId(askFor.getId());
            List taskList = findExampleTask(taskQuery);
            if(taskList.size() > 0)
            {
                IWorkflowExampleTask task = (IWorkflowExampleTask)taskList.get(0);
                task.setStatus(askFor.getStatus());
                task.setEndTime(new Date());
                saveExampleTask(task);
            }
        }
    }

    public IWorkflowExampleTask loadExampleTask(int objid)
    {
        if(objid <= 0)
            return new WorkflowExampleTaskImpl();
        else
            return (IWorkflowExampleTask)getMetaDBContext().get("WFEXAMPLETASK", objid);
    }

    public void saveExampleTask(IWorkflowExampleTask task)
    {
        task.save();
    }

    public List findExampleTask(ExampleTaskQuery taskQuery)
    {
        StringBuilder hql = new StringBuilder();
        hql.append("1 = 1 ");
        Map paraMap = new HashMap();
        if(taskQuery.getSourceEntity() != null)
        {
            hql.append("and sourceEntity=:sourceEntity ");
            paraMap.put("sourceEntity", taskQuery.getSourceEntity());
        }
        if(taskQuery.getSourceId() != -1)
        {
            hql.append("and sourceId=:sourceId ");
            paraMap.put("sourceId", Integer.valueOf(taskQuery.getSourceId()));
        }
        hql.append("order by objid ");
        List taskList = getMetaDBContext().query("WFEXAMPLETASK", hql.toString(), paraMap);
        return taskList;
    }
}
