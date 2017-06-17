// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowExampleEntryImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowExampleEntry

public class WorkflowExampleEntryImpl extends MetaObjectImpl
    implements IWorkflowExampleEntry
{

    public WorkflowExampleEntryImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFEXAMPLEENTRY"));
    }

    public WorkflowExampleEntryImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public String getSourceName()
    {
        return (String)get("SourceName");
    }

    public void setSourceName(String value)
    {
        put("SourceName", value);
    }

    public Integer getSourceId()
    {
        return (Integer)get("SourceId");
    }

    public void setSourceId(Integer value)
    {
        put("SourceId", value);
    }

    public Date getChangeTime()
    {
        return (Date)get("ChangeTime");
    }

    public void setChangeTime(Date value)
    {
        put("ChangeTime", value);
    }

    public Integer getExampleId()
    {
        return (Integer)get("ExampleId");
    }

    public void setExampleId(Integer value)
    {
        put("ExampleId", value);
    }
}
