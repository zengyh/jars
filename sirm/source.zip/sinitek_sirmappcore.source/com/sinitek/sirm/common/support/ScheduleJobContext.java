// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScheduleJobContext.java

package com.sinitek.sirm.common.support;

import java.util.Date;
import java.util.Map;

public class ScheduleJobContext
{

    public ScheduleJobContext()
    {
        name = null;
        lasttime = null;
        param = null;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Map getParam()
    {
        return param;
    }

    public void setParam(Map param)
    {
        this.param = param;
    }

    public Date getLasttime()
    {
        return lasttime;
    }

    public void setLasttime(Date lasttime)
    {
        this.lasttime = lasttime;
    }

    private String name;
    private Date lasttime;
    private Map param;
}
