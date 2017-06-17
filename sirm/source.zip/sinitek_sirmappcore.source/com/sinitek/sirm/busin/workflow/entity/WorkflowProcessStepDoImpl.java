// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessStepDoImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessStepDo

public class WorkflowProcessStepDoImpl extends MetaObjectImpl
    implements IWorkflowProcessStepDo
{

    public WorkflowProcessStepDoImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSSTEPDO"));
    }

    public WorkflowProcessStepDoImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getTaskid()
    {
        return (Integer)get("taskid");
    }

    public void setTaskid(Integer value)
    {
        put("taskid", value);
    }

    public Integer getType()
    {
        return (Integer)get("type");
    }

    public void setType(Integer value)
    {
        put("type", value);
    }

    public Integer getProcessid()
    {
        return (Integer)get("processid");
    }

    public void setProcessid(Integer value)
    {
        put("processid", value);
    }

    public Integer getProcessstepid()
    {
        return (Integer)get("processstepid");
    }

    public void setProcessstepid(Integer value)
    {
        put("processstepid", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }

    public Integer getSort()
    {
        return (Integer)get("sort");
    }

    public void setSort(Integer value)
    {
        put("sort", value);
    }
}
