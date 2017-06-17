// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowModelOneListImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowModelOneList

public class WorkflowModelOneListImpl extends MetaObjectImpl
    implements IWorkflowModelOneList
{

    public WorkflowModelOneListImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFMODELONELIST"));
    }

    public WorkflowModelOneListImpl(IEntity entity)
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

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }

    public String getRules()
    {
        return (String)get("Rules");
    }

    public void setRules(String value)
    {
        put("Rules", value);
    }

    public String getExecutor()
    {
        return (String)get("Executor");
    }

    public void setExecutor(String value)
    {
        put("Executor", value);
    }

    public String getTaskRemind()
    {
        return (String)get("TaskRemind");
    }

    public void setTaskRemind(String value)
    {
        put("TaskRemind", value);
    }

    public Integer getProcessId()
    {
        return (Integer)get("ProcessId");
    }

    public void setProcessId(Integer value)
    {
        put("ProcessId", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }
}
