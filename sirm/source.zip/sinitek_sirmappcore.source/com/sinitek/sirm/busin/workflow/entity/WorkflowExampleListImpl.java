// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowExampleListImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowExampleList

public class WorkflowExampleListImpl extends MetaObjectImpl
    implements IWorkflowExampleList
{

    public WorkflowExampleListImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFEXAMPLELIST"));
    }

    public WorkflowExampleListImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getKey()
    {
        return (Integer)get("Key");
    }

    public void setKey(Integer value)
    {
        put("Key", value);
    }

    public String getValue()
    {
        return (String)get("Value");
    }

    public void setValue(String value)
    {
        put("Value", value);
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

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }
}
