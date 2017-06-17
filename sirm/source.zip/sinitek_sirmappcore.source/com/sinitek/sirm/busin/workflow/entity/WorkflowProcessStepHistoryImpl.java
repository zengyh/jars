// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessStepHistoryImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessStepHistory

public class WorkflowProcessStepHistoryImpl extends MetaObjectImpl
    implements IWorkflowProcessStepHistory
{

    public WorkflowProcessStepHistoryImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSSTEPHISTORY"));
    }

    public WorkflowProcessStepHistoryImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getPreStepId()
    {
        return (Integer)get("PreStepId");
    }

    public void setPreStepId(Integer value)
    {
        put("PreStepId", value);
    }

    public Integer getPreProcessId()
    {
        return (Integer)get("PreProcessId");
    }

    public void setPreProcessId(Integer value)
    {
        put("PreProcessId", value);
    }

    public Integer getNewProcessId()
    {
        return (Integer)get("NewProcessId");
    }

    public void setNewProcessId(Integer value)
    {
        put("NewProcessId", value);
    }

    public Integer getNewStepId()
    {
        return (Integer)get("NewStepId");
    }

    public void setNewStepId(Integer value)
    {
        put("NewStepId", value);
    }
}
