// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScheduleTaskJob.java

package com.sinitek.sirm.common.support.schedule;

import org.quartz.*;

// Referenced classes of package com.sinitek.sirm.common.support.schedule:
//            IScheduleTask

public abstract class ScheduleTaskJob
    implements Job, IScheduleTask
{

    public ScheduleTaskJob()
    {
    }

    public void execute(JobExecutionContext context)
        throws JobExecutionException
    {
        init();
        execute();
        destroy();
    }
}
