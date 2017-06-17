// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractJob.java

package com.sinitek.sirm.common.quartz.service;

import java.util.Map;
import org.quartz.Job;

public abstract class AbstractJob
    implements Job
{

    public AbstractJob()
    {
        data = null;
    }

    public Map getData()
    {
        return data;
    }

    public void setData(Map data)
    {
        this.data = data;
    }

    private Map data;
}
