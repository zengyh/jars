// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowExampleRelationImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowExampleRelation

public class WorkflowExampleRelationImpl extends MetaObjectImpl
    implements IWorkflowExampleRelation
{

    public WorkflowExampleRelationImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFEXAMPLERELATION"));
    }

    public WorkflowExampleRelationImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getSort()
    {
        return (Integer)get("Sort");
    }

    public void setSort(Integer value)
    {
        put("Sort", value);
    }

    public Integer getExampleStepId()
    {
        return (Integer)get("ExampleStepId");
    }

    public void setExampleStepId(Integer value)
    {
        put("ExampleStepId", value);
    }

    public Integer getExampleOwnerId()
    {
        return (Integer)get("ExampleOwnerId");
    }

    public void setExampleOwnerId(Integer value)
    {
        put("ExampleOwnerId", value);
    }

    public Integer getSubExampleId()
    {
        return (Integer)get("SubExampleId");
    }

    public void setSubExampleId(Integer value)
    {
        put("SubExampleId", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public Integer getMark()
    {
        return (Integer)get("Mark");
    }

    public void setMark(Integer value)
    {
        put("Mark", value);
    }

    public String getBrief()
    {
        return (String)get("Brief");
    }

    public void setBrief(String value)
    {
        put("Brief", value);
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
