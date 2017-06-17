// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowModelOneImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowModelOne

public class WorkflowModelOneImpl extends MetaObjectImpl
    implements IWorkflowModelOne
{

    public WorkflowModelOneImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFMODELONE"));
    }

    public WorkflowModelOneImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getTypeId()
    {
        return (Integer)get("TypeId");
    }

    public void setTypeId(Integer value)
    {
        put("TypeId", value);
    }

    public Integer getOperationId()
    {
        return (Integer)get("OperationId");
    }

    public void setOperationId(Integer value)
    {
        put("OperationId", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public String getDescription()
    {
        return (String)get("Description");
    }

    public void setDescription(String value)
    {
        put("Description", value);
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
