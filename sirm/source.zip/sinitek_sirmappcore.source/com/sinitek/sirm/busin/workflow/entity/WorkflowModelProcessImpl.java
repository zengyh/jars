// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowModelProcessImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowModelProcess

public class WorkflowModelProcessImpl extends MetaObjectImpl
    implements IWorkflowModelProcess
{

    public WorkflowModelProcessImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFMODELPROCESS"));
    }

    public WorkflowModelProcessImpl(IEntity entity)
    {
        super(entity);
    }

    public String getTemplateType()
    {
        return (String)get("TemplateType");
    }

    public void setTemplateType(String value)
    {
        put("TemplateType", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public Integer getTemplateId()
    {
        return (Integer)get("TemplateId");
    }

    public void setTemplateId(Integer value)
    {
        put("TemplateId", value);
    }

    public Integer getProcessId()
    {
        return (Integer)get("ProcessId");
    }

    public void setProcessId(Integer value)
    {
        put("ProcessId", value);
    }
}
