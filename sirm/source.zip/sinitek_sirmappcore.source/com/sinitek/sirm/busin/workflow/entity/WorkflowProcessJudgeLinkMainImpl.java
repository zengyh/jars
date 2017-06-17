// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessJudgeLinkMainImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessJudgeLinkMain

public class WorkflowProcessJudgeLinkMainImpl extends MetaObjectImpl
    implements IWorkflowProcessJudgeLinkMain
{

    public WorkflowProcessJudgeLinkMainImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSJUDGELINKMAIN"));
    }

    public WorkflowProcessJudgeLinkMainImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getLinkId()
    {
        return (Integer)get("LinkId");
    }

    public void setLinkId(Integer value)
    {
        put("LinkId", value);
    }

    public String getLinkMain()
    {
        return (String)get("LinkMain");
    }

    public void setLinkMain(String value)
    {
        put("LinkMain", value);
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

    public Integer getStepId()
    {
        return (Integer)get("StepId");
    }

    public void setStepId(Integer value)
    {
        put("StepId", value);
    }
}
