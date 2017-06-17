// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowInsteadStepTask.java

package com.sinitek.sirm.busin.workflow.service.feature;

import java.util.HashMap;
import java.util.Map;

public class WorkflowInsteadStepTask
{

    public WorkflowInsteadStepTask(int exampleOwnerId)
    {
        this.exampleOwnerId = exampleOwnerId;
        processStepId = 0;
        condition = 0;
        approveOpinion = "";
        paraMap = new HashMap();
    }

    public int getExampleOwnerId()
    {
        return exampleOwnerId;
    }

    public int getProcessStepId()
    {
        return processStepId;
    }

    public void setProcessStepId(int processStepId)
    {
        this.processStepId = processStepId;
    }

    public int getCondition()
    {
        return condition;
    }

    public void setCondition(int condition)
    {
        this.condition = condition;
    }

    public String getApproveOpinion()
    {
        return approveOpinion;
    }

    public void setApproveOpinion(String approveOpinion)
    {
        this.approveOpinion = approveOpinion;
    }

    public Map getParaMap()
    {
        return paraMap;
    }

    public void setParaMap(Map paraMap)
    {
        this.paraMap = paraMap;
    }

    public void addPara(String key, String value)
    {
        paraMap.put(key, value);
    }

    private int exampleOwnerId;
    private int processStepId;
    private int condition;
    private String approveOpinion;
    private Map paraMap;
}
