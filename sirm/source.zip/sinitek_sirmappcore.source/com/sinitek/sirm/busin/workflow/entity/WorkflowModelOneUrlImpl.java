// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowModelOneUrlImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowModelOneUrl

public class WorkflowModelOneUrlImpl extends MetaObjectImpl
    implements IWorkflowModelOneUrl
{

    public WorkflowModelOneUrlImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFMODELONEURL"));
    }

    public WorkflowModelOneUrlImpl(IEntity entity)
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

    public String getApproveUrl()
    {
        return (String)get("ApproveUrl");
    }

    public void setApproveUrl(String value)
    {
        put("ApproveUrl", value);
    }

    public String getEditUrl()
    {
        return (String)get("EditUrl");
    }

    public void setEditUrl(String value)
    {
        put("EditUrl", value);
    }

    public String getWatchUrl()
    {
        return (String)get("WatchUrl");
    }

    public void setWatchUrl(String value)
    {
        put("WatchUrl", value);
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
