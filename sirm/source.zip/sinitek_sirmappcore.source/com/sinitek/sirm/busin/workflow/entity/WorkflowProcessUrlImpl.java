// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessUrlImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessUrl

public class WorkflowProcessUrlImpl extends MetaObjectImpl
    implements IWorkflowProcessUrl
{

    public WorkflowProcessUrlImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSURL"));
    }

    public WorkflowProcessUrlImpl(IEntity entity)
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

    public String getActionUrl()
    {
        return (String)get("ActionUrl");
    }

    public void setActionUrl(String value)
    {
        put("ActionUrl", value);
    }

    public String getShowUrl()
    {
        return (String)get("ShowUrl");
    }

    public void setShowUrl(String value)
    {
        put("ShowUrl", value);
    }

    public Integer getType()
    {
        return (Integer)get("Type");
    }

    public void setType(Integer value)
    {
        put("Type", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public String getViewUrl()
    {
        return (String)get("viewUrl");
    }

    public void setViewUrl(String value)
    {
        put("viewUrl", value);
    }
}
