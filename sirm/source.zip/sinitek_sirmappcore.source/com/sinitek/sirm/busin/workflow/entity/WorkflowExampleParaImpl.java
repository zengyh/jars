// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowExampleParaImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowExamplePara

public class WorkflowExampleParaImpl extends MetaObjectImpl
    implements IWorkflowExamplePara
{

    public WorkflowExampleParaImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFEXAMPLEPARA"));
    }

    public WorkflowExampleParaImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getType()
    {
        return (Integer)get("Type");
    }

    public void setType(Integer value)
    {
        put("Type", value);
    }

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }

    public String getValue()
    {
        return (String)get("Value");
    }

    public void setValue(String value)
    {
        put("Value", value);
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

    public Integer getExampleOwnerId()
    {
        return (Integer)get("ExampleOwnerId");
    }

    public void setExampleOwnerId(Integer value)
    {
        put("ExampleOwnerId", value);
    }
}
