// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessListImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessList

public class WorkflowProcessListImpl extends MetaObjectImpl
    implements IWorkflowProcessList
{

    public WorkflowProcessListImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSLIST"));
    }

    public WorkflowProcessListImpl(IEntity entity)
    {
        super(entity);
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

    public Integer getKey()
    {
        return (Integer)get("Key");
    }

    public void setKey(Integer value)
    {
        put("Key", value);
    }

    public String getValueAds()
    {
        return (String)get("ValueAds");
    }

    public void setValueAds(String value)
    {
        put("ValueAds", value);
    }

    public Integer getType()
    {
        return (Integer)get("Type");
    }

    public void setType(Integer value)
    {
        put("Type", value);
    }
}
