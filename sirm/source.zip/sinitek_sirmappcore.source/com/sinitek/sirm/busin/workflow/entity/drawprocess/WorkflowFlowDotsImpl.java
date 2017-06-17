// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowFlowDotsImpl.java

package com.sinitek.sirm.busin.workflow.entity.drawprocess;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity.drawprocess:
//            IWorkflowFlowDots

public class WorkflowFlowDotsImpl extends MetaObjectImpl
    implements IWorkflowFlowDots
{

    public WorkflowFlowDotsImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFFLOWDOTS"));
    }

    public WorkflowFlowDotsImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getSort()
    {
        return (Integer)get("sort");
    }

    public void setSort(Integer value)
    {
        put("sort", value);
    }

    public Integer getPathid()
    {
        return (Integer)get("pathid");
    }

    public void setPathid(Integer value)
    {
        put("pathid", value);
    }

    public String getPos()
    {
        return (String)get("pos");
    }

    public void setPos(String value)
    {
        put("pos", value);
    }
}
