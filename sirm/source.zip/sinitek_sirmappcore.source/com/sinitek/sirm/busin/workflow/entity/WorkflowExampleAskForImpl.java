// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowExampleAskForImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowExampleAskFor

public class WorkflowExampleAskForImpl extends MetaObjectImpl
    implements IWorkflowExampleAskFor
{

    public WorkflowExampleAskForImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFEXAMPLEASKFOR"));
    }

    public WorkflowExampleAskForImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getAskerId()
    {
        return (Integer)get("askerId");
    }

    public void setAskerId(Integer value)
    {
        put("askerId", value);
    }

    public Integer getParentId()
    {
        return (Integer)get("parentId");
    }

    public void setParentId(Integer value)
    {
        put("parentId", value);
    }

    public Integer getExampleOwnerId()
    {
        return (Integer)get("exampleOwnerId");
    }

    public void setExampleOwnerId(Integer value)
    {
        put("exampleOwnerId", value);
    }

    public Integer getOwnerId()
    {
        return (Integer)get("ownerId");
    }

    public void setOwnerId(Integer value)
    {
        put("ownerId", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }

    public Date getStartTime()
    {
        return (Date)get("startTime");
    }

    public void setStartTime(Date value)
    {
        put("startTime", value);
    }

    public Date getApproveTime()
    {
        return (Date)get("approveTime");
    }

    public void setApproveTime(Date value)
    {
        put("approveTime", value);
    }

    public Integer getApproveStatus()
    {
        return (Integer)get("approveStatus");
    }

    public void setApproveStatus(Integer value)
    {
        put("approveStatus", value);
    }

    public String getOpinion()
    {
        return (String)get("opinion");
    }

    public void setOpinion(String value)
    {
        put("opinion", value);
    }

    public String getBrief()
    {
        return (String)get("brief");
    }

    public void setBrief(String value)
    {
        put("brief", value);
    }
}
