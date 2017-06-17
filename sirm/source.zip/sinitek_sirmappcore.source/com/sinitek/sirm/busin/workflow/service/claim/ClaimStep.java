// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClaimStep.java

package com.sinitek.sirm.busin.workflow.service.claim;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.sinitek.sirm.busin.workflow.service.claim:
//            ClaimOwner

public class ClaimStep
{

    public ClaimStep()
    {
        stepId = 0;
        owners = new ArrayList();
    }

    public ClaimStep(int stepId)
    {
        if(stepId < 0)
            stepId = 0;
        this.stepId = stepId;
        owners = new ArrayList();
    }

    void setStepId(int stepId)
    {
        this.stepId = stepId;
    }

    public int getStepId()
    {
        return stepId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getJudgeUrl()
    {
        return judgeUrl;
    }

    public void setJudgeUrl(int judgeUrl)
    {
        this.judgeUrl = judgeUrl;
    }

    public String getJudgeType()
    {
        return judgeType;
    }

    public void setJudgeType(String judgeType)
    {
        this.judgeType = judgeType;
    }

    public List getOwners()
    {
        return owners;
    }

    public void setOwners(List owners)
    {
        this.owners = owners;
    }

    public void addOwner(ClaimOwner owner)
    {
        owners.add(owner);
    }

    private int stepId;
    private String name;
    private int judgeUrl;
    private String judgeType;
    private List owners;
}
