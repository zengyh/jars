// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowExampleImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowExample

public class WorkflowExampleImpl extends MetaObjectImpl
    implements IWorkflowExample
{

    public WorkflowExampleImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFEXAMPLE"));
    }

    public WorkflowExampleImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public Date getEndTime()
    {
        return (Date)get("EndTime");
    }

    public void setEndTime(Date value)
    {
        put("EndTime", value);
    }

    public String getStarterAds()
    {
        return (String)get("StarterAds");
    }

    public void setStarterAds(String value)
    {
        put("StarterAds", value);
    }

    public String getBrief()
    {
        return (String)get("Brief");
    }

    public void setBrief(String value)
    {
        put("Brief", value);
    }

    public Integer getStarterId()
    {
        return (Integer)get("StarterId");
    }

    public void setStarterId(Integer value)
    {
        put("StarterId", value);
    }

    public Date getStartTime()
    {
        return (Date)get("StartTime");
    }

    public void setStartTime(Date value)
    {
        put("StartTime", value);
    }

    public Integer getProcessId()
    {
        return (Integer)get("ProcessId");
    }

    public void setProcessId(Integer value)
    {
        put("ProcessId", value);
    }

    public String getProcessName()
    {
        return (String)get("ProcessName");
    }

    public void setProcessName(String value)
    {
        put("ProcessName", value);
    }

    public String getDataFrom()
    {
        return (String)get("DataFrom");
    }

    public void setDataFrom(String value)
    {
        put("DataFrom", value);
    }
}
