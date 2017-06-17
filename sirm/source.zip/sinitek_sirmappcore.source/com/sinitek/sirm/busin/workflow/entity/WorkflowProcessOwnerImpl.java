// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessOwnerImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessOwner

public class WorkflowProcessOwnerImpl extends MetaObjectImpl
    implements IWorkflowProcessOwner
{

    public WorkflowProcessOwnerImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSOWNER"));
    }

    public WorkflowProcessOwnerImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getValue()
    {
        return (Integer)get("Value");
    }

    public void setValue(Integer value)
    {
        put("Value", value);
    }

    public Integer getOwnerGoto()
    {
        return (Integer)get("OwnerGoto");
    }

    public void setOwnerGoto(Integer value)
    {
        put("OwnerGoto", value);
    }

    public Integer getOwnerGotoId()
    {
        return (Integer)get("OwnerGotoId");
    }

    public void setOwnerGotoId(Integer value)
    {
        put("OwnerGotoId", value);
    }

    public Integer getProcessId()
    {
        return (Integer)get("ProcessId");
    }

    public void setProcessId(Integer value)
    {
        put("ProcessId", value);
    }

    public Integer getStepId()
    {
        return (Integer)get("StepId");
    }

    public void setStepId(Integer value)
    {
        put("StepId", value);
    }

    public String getOrgId()
    {
        return (String)get("OrgId");
    }

    public void setOrgId(String value)
    {
        put("OrgId", value);
    }

    public Integer getOrgType()
    {
        return (Integer)get("OrgType");
    }

    public void setOrgType(Integer value)
    {
        put("OrgType", value);
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
