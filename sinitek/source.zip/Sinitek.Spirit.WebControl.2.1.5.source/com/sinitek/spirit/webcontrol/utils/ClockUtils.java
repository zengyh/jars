// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClockUtils.java

package com.sinitek.spirit.webcontrol.utils;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

public class ClockUtils
{

    public ClockUtils(Logger log)
    {
        allTime = 0L;
        this.log = log;
        if(log == null || !log.isDebugEnabled())
        {
            return;
        } else
        {
            clock = new StopWatch();
            clock.start();
            return;
        }
    }

    public void printStep(String s)
    {
        if(log == null || !log.isDebugEnabled())
            return;
        long time = clock.getTime();
        allTime += time;
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append(s).append("\u6267\u884C:").append(time).append("\u6BEB\u79D2").toString());
        clock.reset();
        clock.start();
    }

    public void printAllTime(String s)
    {
        if(log == null || !log.isDebugEnabled())
            return;
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append(s).append("\u5171\u6267\u884C:").append(allTime).append("\u6BEB\u79D2").toString());
        clock.reset();
        clock.start();
    }

    public void reset()
    {
        if(log == null || !log.isDebugEnabled())
        {
            return;
        } else
        {
            allTime += clock.getTime();
            clock.reset();
            clock.start();
            return;
        }
    }

    private StopWatch clock;
    private long allTime;
    private Logger log;
}
