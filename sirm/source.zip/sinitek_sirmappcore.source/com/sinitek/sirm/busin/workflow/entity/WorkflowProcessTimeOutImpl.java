// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessTimeOutImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessTimeOut

public class WorkflowProcessTimeOutImpl extends MetaObjectImpl
    implements IWorkflowProcessTimeOut
{

    public WorkflowProcessTimeOutImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSTIMEOUT"));
    }

    public WorkflowProcessTimeOutImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getPollType()
    {
        return (Integer)get("PollType");
    }

    public void setPollType(Integer value)
    {
        put("PollType", value);
    }

    public String getPollTime()
    {
        return (String)get("PollTime");
    }

    public void setPollTime(String value)
    {
        put("PollTime", value);
    }

    public Integer getAccuracy()
    {
        return (Integer)get("Accuracy");
    }

    public void setAccuracy(Integer value)
    {
        put("Accuracy", value);
    }

    public Integer getDealType()
    {
        return (Integer)get("DealType");
    }

    public void setDealType(Integer value)
    {
        put("DealType", value);
    }

    public Integer getDealKey()
    {
        return (Integer)get("DealKey");
    }

    public void setDealKey(Integer value)
    {
        put("DealKey", value);
    }

    public String getDealValue()
    {
        return (String)get("DealValue");
    }

    public void setDealValue(String value)
    {
        put("DealValue", value);
    }

    public String getTimeOut()
    {
        return (String)get("TimeOut");
    }

    public void setTimeOut(String value)
    {
        put("TimeOut", value);
    }

    public String getDealValueAds()
    {
        return (String)get("DealValueAds");
    }

    public void setDealValueAds(String value)
    {
        put("DealValueAds", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public Integer getSort()
    {
        return (Integer)get("Sort");
    }

    public void setSort(Integer value)
    {
        put("Sort", value);
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
}
