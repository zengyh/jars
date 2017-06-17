// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BusinLogQueryCondition.java

package com.sinitek.sirm.common.log.entity;

import java.util.Date;

public class BusinLogQueryCondition
{

    public BusinLogQueryCondition()
    {
        userIds = null;
        startTime = null;
        endTime = null;
        moduleName = null;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public String[] getUserIds()
    {
        return userIds;
    }

    public void setUserIds(String userIds[])
    {
        this.userIds = userIds;
    }

    private String userIds[];
    private Date startTime;
    private Date endTime;
    private String moduleName;
}
