// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessRuleInfoImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessRuleInfo

public class WorkflowProcessRuleInfoImpl extends MetaObjectImpl
    implements IWorkflowProcessRuleInfo
{

    public WorkflowProcessRuleInfoImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSRULEINFO"));
    }

    public WorkflowProcessRuleInfoImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getRuleType()
    {
        return (Integer)get("ruleType");
    }

    public void setRuleType(Integer value)
    {
        put("ruleType", value);
    }

    public String getInfo()
    {
        return (String)get("info");
    }

    public void setInfo(String value)
    {
        put("info", value);
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

    public String getRuleName()
    {
        return (String)get("ruleName");
    }

    public void setRuleName(String value)
    {
        put("ruleName", value);
    }
}
