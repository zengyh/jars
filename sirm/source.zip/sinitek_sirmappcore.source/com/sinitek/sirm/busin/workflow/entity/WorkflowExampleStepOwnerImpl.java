// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowExampleStepOwnerImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowExampleStepOwner

public class WorkflowExampleStepOwnerImpl extends MetaObjectImpl
    implements IWorkflowExampleStepOwner
{

    public WorkflowExampleStepOwnerImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFEXAMPLESTEPOWNER"));
    }

    public WorkflowExampleStepOwnerImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getExampleId()
    {
        return (Integer)get("ExampleId");
    }

    public void setExampleId(Integer value)
    {
        put("ExampleId", value);
    }

    public Integer getExampleStepId()
    {
        return (Integer)get("ExampleStepId");
    }

    public void setExampleStepId(Integer value)
    {
        put("ExampleStepId", value);
    }

    public Integer getOwnerId()
    {
        return (Integer)get("OwnerId");
    }

    public void setOwnerId(Integer value)
    {
        put("OwnerId", value);
    }

    public Integer getPreOwnerId()
    {
        return (Integer)get("PreOwnerId");
    }

    public void setPreOwnerId(Integer value)
    {
        put("PreOwnerId", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public Date getApproveTime()
    {
        return (Date)get("ApproveTime");
    }

    public void setApproveTime(Date value)
    {
        put("ApproveTime", value);
    }

    public String getApproveOpinion()
    {
        return (String)get("ApproveOpinion");
    }

    public void setApproveOpinion(String value)
    {
        put("ApproveOpinion", value);
    }

    public Integer getApproveStatus()
    {
        return (Integer)get("ApproveStatus");
    }

    public void setApproveStatus(Integer value)
    {
        put("ApproveStatus", value);
    }

    public String getApproveBrief()
    {
        return (String)get("ApproveBrief");
    }

    public void setApproveBrief(String value)
    {
        put("ApproveBrief", value);
    }

    public Date getStartTime()
    {
        return (Date)get("StartTime");
    }

    public void setStartTime(Date value)
    {
        put("StartTime", value);
    }

    public Integer getValue()
    {
        return (Integer)get("Value");
    }

    public void setValue(Integer value)
    {
        put("Value", value);
    }
}
