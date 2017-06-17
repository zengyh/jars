// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExampleAskForQuery.java

package com.sinitek.sirm.busin.workflow.service.claim;

import java.util.Date;

public class ExampleAskForQuery
{

    public ExampleAskForQuery()
    {
        ownerId = -1;
        startId = -1;
        askerId = -1;
        processName = null;
        stepName = null;
        brief = null;
        status = -1;
        startTime1 = null;
        startTime2 = null;
    }

    public int getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(int ownerId)
    {
        this.ownerId = ownerId;
    }

    public int getStartId()
    {
        return startId;
    }

    public void setStartId(int startId)
    {
        this.startId = startId;
    }

    public int getAskerId()
    {
        return askerId;
    }

    public void setAskerId(int askerId)
    {
        this.askerId = askerId;
    }

    public String getProcessName()
    {
        return processName;
    }

    public void setProcessName(String processName)
    {
        this.processName = processName;
    }

    public String getStepName()
    {
        return stepName;
    }

    public void setStepName(String stepName)
    {
        this.stepName = stepName;
    }

    public String getBrief()
    {
        return brief;
    }

    public void setBrief(String brief)
    {
        this.brief = brief;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public Date getStartTime1()
    {
        return startTime1;
    }

    public void setStartTime1(Date startTime1)
    {
        this.startTime1 = startTime1;
    }

    public Date getStartTime2()
    {
        return startTime2;
    }

    public void setStartTime2(Date startTime2)
    {
        this.startTime2 = startTime2;
    }

    private int ownerId;
    private int startId;
    private int askerId;
    private String processName;
    private String stepName;
    private String brief;
    private int status;
    private Date startTime1;
    private Date startTime2;
}
