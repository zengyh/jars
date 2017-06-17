// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExampleTaskQuery.java

package com.sinitek.sirm.busin.workflow.service.claim;

import java.util.Date;

public class ExampleTaskQuery
{

    public ExampleTaskQuery()
    {
        sourceEntity = null;
        sourceId = -1;
        dealerId = -1;
        orginerId = -1;
        status = -1;
        description = null;
        remarks = null;
        startTime1 = null;
        startTime2 = null;
        endTime1 = null;
        endTime2 = null;
        orderBy = null;
    }

    public String getSourceEntity()
    {
        return sourceEntity;
    }

    public void setSourceEntity(String sourceEntity)
    {
        this.sourceEntity = sourceEntity;
    }

    public int getSourceId()
    {
        return sourceId;
    }

    public void setSourceId(int sourceId)
    {
        this.sourceId = sourceId;
    }

    public int getDealerId()
    {
        return dealerId;
    }

    public void setDealerId(int dealerId)
    {
        this.dealerId = dealerId;
    }

    public int getOrginerId()
    {
        return orginerId;
    }

    public void setOrginerId(int orginerId)
    {
        this.orginerId = orginerId;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
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

    public Date getEndTime1()
    {
        return endTime1;
    }

    public void setEndTime1(Date endTime1)
    {
        this.endTime1 = endTime1;
    }

    public Date getEndTime2()
    {
        return endTime2;
    }

    public void setEndTime2(Date endTime2)
    {
        this.endTime2 = endTime2;
    }

    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

    public static String EXAMPLE_ASKFOR = "Wf_ExampleAskFor";
    public static String EXAMPLE_OWNER = "Wf_ExampleStepOwner";
    private String sourceEntity;
    private int sourceId;
    private int dealerId;
    private int orginerId;
    private int status;
    private String description;
    private String remarks;
    private Date startTime1;
    private Date startTime2;
    private Date endTime1;
    private Date endTime2;
    private String orderBy;

}
