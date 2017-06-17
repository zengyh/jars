// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessRuleImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessRule

public class WorkflowProcessRuleImpl extends MetaObjectImpl
    implements IWorkflowProcessRule
{

    public WorkflowProcessRuleImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSRULE"));
    }

    public WorkflowProcessRuleImpl(IEntity entity)
    {
        super(entity);
    }

    public String getTargetType()
    {
        return (String)get("targetType");
    }

    public void setTargetType(String value)
    {
        put("targetType", value);
    }

    public Integer getTargetId()
    {
        return (Integer)get("targetId");
    }

    public void setTargetId(Integer value)
    {
        put("targetId", value);
    }

    public String getTargetAdd()
    {
        return (String)get("targetAdd");
    }

    public void setTargetAdd(String value)
    {
        put("targetAdd", value);
    }

    public Integer getRuleId()
    {
        return (Integer)get("ruleId");
    }

    public void setRuleId(Integer value)
    {
        put("ruleId", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }

    public String getBrief()
    {
        return (String)get("brief");
    }

    public void setBrief(String value)
    {
        put("brief", value);
    }
}
