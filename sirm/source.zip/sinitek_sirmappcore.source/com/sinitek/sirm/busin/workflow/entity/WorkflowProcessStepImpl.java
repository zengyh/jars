// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessStepImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessStep

public class WorkflowProcessStepImpl extends MetaObjectImpl
    implements IWorkflowProcessStep
{

    public WorkflowProcessStepImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSSTEP"));
    }

    public WorkflowProcessStepImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getPhoneshow()
    {
        return (Integer)get("phoneshow");
    }

    public void setPhoneshow(Integer value)
    {
        put("phoneshow", value);
    }

    public Integer getProcessId()
    {
        return (Integer)get("ProcessId");
    }

    public void setProcessId(Integer value)
    {
        put("ProcessId", value);
    }

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }

    public Integer getStepTypeId()
    {
        return (Integer)get("StepTypeId");
    }

    public void setStepTypeId(Integer value)
    {
        put("StepTypeId", value);
    }

    public String getCondition()
    {
        return (String)get("Condition");
    }

    public void setCondition(String value)
    {
        put("Condition", value);
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

    public Integer getSort()
    {
        return (Integer)get("Sort");
    }

    public void setSort(Integer value)
    {
        put("Sort", value);
    }

    public Integer getUrlMark()
    {
        return (Integer)get("UrlMark");
    }

    public void setUrlMark(Integer value)
    {
        put("UrlMark", value);
    }

    public Integer getStepCode()
    {
        return (Integer)get("StepCode");
    }

    public void setStepCode(Integer value)
    {
        put("StepCode", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public String getStepTypeAds()
    {
        return (String)get("StepTypeAds");
    }

    public void setStepTypeAds(String value)
    {
        put("StepTypeAds", value);
    }

    public Integer getPointTypeId()
    {
        return (Integer)get("PointTypeId");
    }

    public void setPointTypeId(Integer value)
    {
        put("PointTypeId", value);
    }

    public Integer getAgentType()
    {
        return (Integer)get("AgentType");
    }

    public void setAgentType(Integer value)
    {
        put("AgentType", value);
    }

    public String getStepSpecial()
    {
        return (String)get("StepSpecial");
    }

    public void setStepSpecial(String value)
    {
        put("StepSpecial", value);
    }
}
