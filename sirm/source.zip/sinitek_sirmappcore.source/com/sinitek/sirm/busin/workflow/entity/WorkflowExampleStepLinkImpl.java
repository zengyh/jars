// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowExampleStepLinkImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowExampleStepLink

public class WorkflowExampleStepLinkImpl extends MetaObjectImpl
    implements IWorkflowExampleStepLink
{

    public WorkflowExampleStepLinkImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFEXAMPLESTEPLINK"));
    }

    public WorkflowExampleStepLinkImpl(IEntity entity)
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

    public Integer getAftStepId()
    {
        return (Integer)get("AftStepId");
    }

    public void setAftStepId(Integer value)
    {
        put("AftStepId", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public Integer getExampleId()
    {
        return (Integer)get("ExampleId");
    }

    public void setExampleId(Integer value)
    {
        put("ExampleId", value);
    }
}
