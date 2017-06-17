// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessOwnerLinkImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessOwnerLink

public class WorkflowProcessOwnerLinkImpl extends MetaObjectImpl
    implements IWorkflowProcessOwnerLink
{

    public WorkflowProcessOwnerLinkImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSOWNERLINK"));
    }

    public WorkflowProcessOwnerLinkImpl(IEntity entity)
    {
        super(entity);
    }

    public String getOwnerStarter()
    {
        return (String)get("OwnerStarter");
    }

    public void setOwnerStarter(String value)
    {
        put("OwnerStarter", value);
    }

    public String getOwnerEnder()
    {
        return (String)get("OwnerEnder");
    }

    public void setOwnerEnder(String value)
    {
        put("OwnerEnder", value);
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

    public Integer getLinkRoot()
    {
        return (Integer)get("LinkRoot");
    }

    public void setLinkRoot(Integer value)
    {
        put("LinkRoot", value);
    }

    public Integer getLinkLeaf()
    {
        return (Integer)get("LinkLeaf");
    }

    public void setLinkLeaf(Integer value)
    {
        put("LinkLeaf", value);
    }

    public Integer getStepId()
    {
        return (Integer)get("StepId");
    }

    public void setStepId(Integer value)
    {
        put("StepId", value);
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
