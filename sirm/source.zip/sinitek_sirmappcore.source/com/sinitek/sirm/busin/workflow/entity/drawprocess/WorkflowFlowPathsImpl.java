// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowFlowPathsImpl.java

package com.sinitek.sirm.busin.workflow.entity.drawprocess;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity.drawprocess:
//            IWorkflowFlowPaths

public class WorkflowFlowPathsImpl extends MetaObjectImpl
    implements IWorkflowFlowPaths
{

    public WorkflowFlowPathsImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFFLOWPATHS"));
    }

    public WorkflowFlowPathsImpl(IEntity entity)
    {
        super(entity);
    }

    public String getType()
    {
        return (String)get("type");
    }

    public void setType(String value)
    {
        put("type", value);
    }

    public Integer getToid()
    {
        return (Integer)get("toid");
    }

    public void setToid(Integer value)
    {
        put("toid", value);
    }

    public Integer getFromid()
    {
        return (Integer)get("fromid");
    }

    public void setFromid(Integer value)
    {
        put("fromid", value);
    }

    public Integer getProcessid()
    {
        return (Integer)get("processid");
    }

    public void setProcessid(Integer value)
    {
        put("processid", value);
    }
}
