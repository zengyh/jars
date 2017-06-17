// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessStepLinkIfImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessStepLinkIf

public class WorkflowProcessStepLinkIfImpl extends MetaObjectImpl
    implements IWorkflowProcessStepLinkIf
{

    public WorkflowProcessStepLinkIfImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSSTEPLINKIF"));
    }

    public WorkflowProcessStepLinkIfImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getLinkId()
    {
        return (Integer)get("LinkId");
    }

    public void setLinkId(Integer value)
    {
        put("LinkId", value);
    }

    public Integer getProcessId()
    {
        return (Integer)get("ProcessId");
    }

    public void setProcessId(Integer value)
    {
        put("ProcessId", value);
    }

    public Integer getIfType()
    {
        return (Integer)get("IfType");
    }

    public void setIfType(Integer value)
    {
        put("IfType", value);
    }

    public Integer getStepId()
    {
        return (Integer)get("StepId");
    }

    public void setStepId(Integer value)
    {
        put("StepId", value);
    }

    public Integer getIfAnd()
    {
        return (Integer)get("IfAnd");
    }

    public void setIfAnd(Integer value)
    {
        put("IfAnd", value);
    }

    public String getIfAds()
    {
        return (String)get("IfAds");
    }

    public void setIfAds(String value)
    {
        put("IfAds", value);
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
