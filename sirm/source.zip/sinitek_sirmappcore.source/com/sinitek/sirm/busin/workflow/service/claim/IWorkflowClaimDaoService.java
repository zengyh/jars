// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowClaimDaoService.java

package com.sinitek.sirm.busin.workflow.service.claim;

import com.sinitek.sirm.busin.workflow.entity.IWorkflowExampleAskFor;
import com.sinitek.sirm.busin.workflow.entity.IWorkflowExampleTask;
import java.util.List;

// Referenced classes of package com.sinitek.sirm.busin.workflow.service.claim:
//            ExampleTaskQuery

/**
 * @deprecated Interface IWorkflowClaimDaoService is deprecated
 */

public interface IWorkflowClaimDaoService
{

    public abstract IWorkflowExampleAskFor loadExampleAskFor(int i);

    public abstract void saveExampleAskFor(IWorkflowExampleAskFor iworkflowexampleaskfor);

    public abstract IWorkflowExampleTask loadExampleTask(int i);

    public abstract void saveExampleTask(IWorkflowExampleTask iworkflowexampletask);

    public abstract List findExampleTask(ExampleTaskQuery exampletaskquery);
}
