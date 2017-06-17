// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowExampleStepImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowExampleStep

public class WorkflowExampleStepImpl extends MetaObjectImpl
    implements IWorkflowExampleStep
{

    public WorkflowExampleStepImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFEXAMPLESTEP"));
    }

    public WorkflowExampleStepImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getRecoverFlag()
    {
        return (Integer)get("RecoverFlag");
    }

    public void setRecoverFlag(Integer value)
    {
        put("RecoverFlag", value);
    }

    public Date getEndTime()
    {
        return (Date)get("EndTime");
    }

    public void setEndTime(Date value)
    {
        put("EndTime", value);
    }

    public Integer getProcessStepId()
    {
        return (Integer)get("ProcessStepId");
    }

    public void setProcessStepId(Integer value)
    {
        put("ProcessStepId", value);
    }

    public Integer getProcessId()
    {
        return (Integer)get("ProcessId");
    }

    public void setProcessId(Integer value)
    {
        put("ProcessId", value);
    }

    public Integer getExampleId()
    {
        return (Integer)get("ExampleId");
    }

    public void setExampleId(Integer value)
    {
        put("ExampleId", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public String getProcessStepName()
    {
        return (String)get("ProcessStepName");
    }

    public void setProcessStepName(String value)
    {
        put("ProcessStepName", value);
    }

    public String getBrief()
    {
        return (String)get("Brief");
    }

    public void setBrief(String value)
    {
        put("Brief", value);
    }

    public String getStepBeigin()
    {
        return (String)get("StepBeigin");
    }

    public void setStepBeigin(String value)
    {
        put("StepBeigin", value);
    }

    public String getStepEnd()
    {
        return (String)get("StepEnd");
    }

    public void setStepEnd(String value)
    {
        put("StepEnd", value);
    }

    public Integer getStepCondition()
    {
        return (Integer)get("StepCondition");
    }

    public void setStepCondition(Integer value)
    {
        put("StepCondition", value);
    }

    public Integer getStepType()
    {
        return (Integer)get("StepType");
    }

    public void setStepType(Integer value)
    {
        put("StepType", value);
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

    public Integer getUrlMark()
    {
        return (Integer)get("UrlMark");
    }

    public void setUrlMark(Integer value)
    {
        put("UrlMark", value);
    }

    public Date getStartTime()
    {
        return (Date)get("StartTime");
    }

    public void setStartTime(Date value)
    {
        put("StartTime", value);
    }
}
