// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowFlowNodeImpl.java

package com.sinitek.sirm.busin.workflow.entity.drawprocess;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity.drawprocess:
//            IWorkflowFlowNode

public class WorkflowFlowNodeImpl extends MetaObjectImpl
    implements IWorkflowFlowNode
{

    public WorkflowFlowNodeImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFFLOWNODE"));
    }

    public WorkflowFlowNodeImpl(IEntity entity)
    {
        super(entity);
    }

    public String getAttr()
    {
        return (String)get("attr");
    }

    public void setAttr(String value)
    {
        put("attr", value);
    }

    public String getType()
    {
        return (String)get("type");
    }

    public void setType(String value)
    {
        put("type", value);
    }

    public Integer getProcessid()
    {
        return (Integer)get("processid");
    }

    public void setProcessid(Integer value)
    {
        put("processid", value);
    }

    public String getText()
    {
        return (String)get("text");
    }

    public void setText(String value)
    {
        put("text", value);
    }
}
