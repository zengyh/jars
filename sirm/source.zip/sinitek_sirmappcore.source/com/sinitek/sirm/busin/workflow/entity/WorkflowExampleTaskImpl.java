// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowExampleTaskImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowExampleTask

public class WorkflowExampleTaskImpl extends MetaObjectImpl
    implements IWorkflowExampleTask
{

    public WorkflowExampleTaskImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFEXAMPLETASK"));
    }

    public WorkflowExampleTaskImpl(IEntity entity)
    {
        super(entity);
    }

    public String getSourceEntity()
    {
        return (String)get("sourceEntity");
    }

    public void setSourceEntity(String value)
    {
        put("sourceEntity", value);
    }

    public Integer getSourceId()
    {
        return (Integer)get("sourceId");
    }

    public void setSourceId(Integer value)
    {
        put("sourceId", value);
    }

    public Integer getDealerId()
    {
        return (Integer)get("dealerId");
    }

    public void setDealerId(Integer value)
    {
        put("dealerId", value);
    }

    public Integer getOrginerId()
    {
        return (Integer)get("orginerId");
    }

    public void setOrginerId(Integer value)
    {
        put("orginerId", value);
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

    public Date getEndTime()
    {
        return (Date)get("endTime");
    }

    public void setEndTime(Date value)
    {
        put("endTime", value);
    }

    public String getDescription()
    {
        return (String)get("description");
    }

    public void setDescription(String value)
    {
        put("description", value);
    }

    public String getRemarks()
    {
        return (String)get("remarks");
    }

    public void setRemarks(String value)
    {
        put("remarks", value);
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
