// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessStepLinkImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessStepLink

public class WorkflowProcessStepLinkImpl extends MetaObjectImpl
    implements IWorkflowProcessStepLink
{

    public WorkflowProcessStepLinkImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSSTEPLINK"));
    }

    public WorkflowProcessStepLinkImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getAftStepId()
    {
        return (Integer)get("AftStepId");
    }

    public void setAftStepId(Integer value)
    {
        put("AftStepId", value);
    }

    public Integer getProcessId()
    {
        return (Integer)get("ProcessId");
    }

    public void setProcessId(Integer value)
    {
        put("ProcessId", value);
    }

    public Integer getSort()
    {
        return (Integer)get("Sort");
    }

    public void setSort(Integer value)
    {
        put("Sort", value);
    }

    public Integer getPreStepId()
    {
        return (Integer)get("PreStepId");
    }

    public void setPreStepId(Integer value)
    {
        put("PreStepId", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }
}
