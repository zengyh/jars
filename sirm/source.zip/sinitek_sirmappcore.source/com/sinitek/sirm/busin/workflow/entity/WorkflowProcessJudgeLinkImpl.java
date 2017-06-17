// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessJudgeLinkImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessJudgeLink

public class WorkflowProcessJudgeLinkImpl extends MetaObjectImpl
    implements IWorkflowProcessJudgeLink
{

    public WorkflowProcessJudgeLinkImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSJUDGELINK"));
    }

    public WorkflowProcessJudgeLinkImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getStepId()
    {
        return (Integer)get("StepId");
    }

    public void setStepId(Integer value)
    {
        put("StepId", value);
    }

    public Integer getResult()
    {
        return (Integer)get("Result");
    }

    public void setResult(Integer value)
    {
        put("Result", value);
    }

    public String getResultAds()
    {
        return (String)get("ResultAds");
    }

    public void setResultAds(String value)
    {
        put("ResultAds", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public Integer getType()
    {
        return (Integer)get("Type");
    }

    public void setType(Integer value)
    {
        put("Type", value);
    }

    public Integer getSort()
    {
        return (Integer)get("Sort");
    }

    public void setSort(Integer value)
    {
        put("Sort", value);
    }
}
