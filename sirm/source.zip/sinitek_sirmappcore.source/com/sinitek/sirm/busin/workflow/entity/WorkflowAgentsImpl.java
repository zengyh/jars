// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowAgentsImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowAgents

public class WorkflowAgentsImpl extends MetaObjectImpl
    implements IWorkflowAgents
{

    public WorkflowAgentsImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFAGENTS"));
    }

    public WorkflowAgentsImpl(IEntity entity)
    {
        super(entity);
    }

    public Date getEndTime()
    {
        return (Date)get("EndTime");
    }

    public void setEndTime(Date value)
    {
        put("EndTime", value);
    }

    public String getTypeSource()
    {
        return (String)get("TypeSource");
    }

    public void setTypeSource(String value)
    {
        put("TypeSource", value);
    }

    public Integer getTypeSourceId()
    {
        return (Integer)get("TypeSourceId");
    }

    public void setTypeSourceId(Integer value)
    {
        put("TypeSourceId", value);
    }

    public String getTypeAds()
    {
        return (String)get("TypeAds");
    }

    public void setTypeAds(String value)
    {
        put("TypeAds", value);
    }

    public Integer getOwnerId()
    {
        return (Integer)get("OwnerId");
    }

    public void setOwnerId(Integer value)
    {
        put("OwnerId", value);
    }

    public Integer getAgentsId()
    {
        return (Integer)get("AgentsId");
    }

    public void setAgentsId(Integer value)
    {
        put("AgentsId", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public Date getStartTime()
    {
        return (Date)get("StartTime");
    }

    public void setStartTime(Date value)
    {
        put("StartTime", value);
    }

    public Integer getProcessType()
    {
        return (Integer)get("ProcessType");
    }

    public void setProcessType(Integer value)
    {
        put("ProcessType", value);
    }

    public Integer getProcessId()
    {
        return (Integer)get("ProcessId");
    }

    public void setProcessId(Integer value)
    {
        put("ProcessId", value);
    }

    public Integer getProcessStepId()
    {
        return (Integer)get("ProcessStepId");
    }

    public void setProcessStepId(Integer value)
    {
        put("ProcessStepId", value);
    }
}
