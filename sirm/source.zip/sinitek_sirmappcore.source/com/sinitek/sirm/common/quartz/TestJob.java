// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TestJob.java

package com.sinitek.sirm.common.quartz;

import com.sinitek.sirm.common.quartz.service.AbstractJob;
import com.sinitek.sirm.common.utils.TimeUtil;
import java.io.PrintStream;
import java.util.Date;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob extends AbstractJob
{

    public TestJob()
    {
    }

    public void execute(JobExecutionContext context)
        throws JobExecutionException
    {
        System.out.println((new StringBuilder()).append("start TestJob[").append(TimeUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")).append("]").toString());
        System.out.println("\tsleep 10s");
        try
        {
            Thread.sleep(10000L);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println((new StringBuilder()).append("end TestJob[").append(TimeUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")).append("]").toString());
    }
}
