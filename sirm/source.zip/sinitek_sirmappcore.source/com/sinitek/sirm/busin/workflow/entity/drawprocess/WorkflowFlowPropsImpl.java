// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowFlowPropsImpl.java

package com.sinitek.sirm.busin.workflow.entity.drawprocess;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity.drawprocess:
//            IWorkflowFlowProps

public class WorkflowFlowPropsImpl extends MetaObjectImpl
    implements IWorkflowFlowProps
{

    public WorkflowFlowPropsImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFFLOWPROPS"));
    }

    public WorkflowFlowPropsImpl(IEntity entity)
    {
        super(entity);
    }

    public String getKey()
    {
        return (String)get("key");
    }

    public void setKey(String value)
    {
        put("key", value);
    }

    public Integer getSort()
    {
        return (Integer)get("sort");
    }

    public void setSort(Integer value)
    {
        put("sort", value);
    }

    public String getSourcename()
    {
        return (String)get("sourcename");
    }

    public void setSourcename(String value)
    {
        put("sourcename", value);
    }

    public Integer getSourceid()
    {
        return (Integer)get("sourceid");
    }

    public void setSourceid(Integer value)
    {
        put("sourceid", value);
    }

    public String getName()
    {
        return (String)get("name");
    }

    public void setName(String value)
    {
        put("name", value);
    }

    public String getValue()
    {
        return (String)get("value");
    }

    public void setValue(String value)
    {
        put("value", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }
}
