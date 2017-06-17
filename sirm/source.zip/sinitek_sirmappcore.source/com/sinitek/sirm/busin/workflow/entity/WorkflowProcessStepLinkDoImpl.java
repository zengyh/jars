// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessStepLinkDoImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessStepLinkDo

public class WorkflowProcessStepLinkDoImpl extends MetaObjectImpl
    implements IWorkflowProcessStepLinkDo
{

    public WorkflowProcessStepLinkDoImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSSTEPLINKDO"));
    }

    public WorkflowProcessStepLinkDoImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getLinkId()
    {
        return (Integer)get("LinkId");
    }

    public void setLinkId(Integer value)
    {
        put("LinkId", value);
    }

    public Integer getProcessId()
    {
        return (Integer)get("ProcessId");
    }

    public void setProcessId(Integer value)
    {
        put("ProcessId", value);
    }

    public Integer getDoType()
    {
        return (Integer)get("DoType");
    }

    public void setDoType(Integer value)
    {
        put("DoType", value);
    }

    public String getDoAds()
    {
        return (String)get("DoAds");
    }

    public void setDoAds(String value)
    {
        put("DoAds", value);
    }

    public Integer getDoMark()
    {
        return (Integer)get("DoMark");
    }

    public void setDoMark(Integer value)
    {
        put("DoMark", value);
    }

    public Integer getStepId()
    {
        return (Integer)get("StepId");
    }

    public void setStepId(Integer value)
    {
        put("StepId", value);
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
