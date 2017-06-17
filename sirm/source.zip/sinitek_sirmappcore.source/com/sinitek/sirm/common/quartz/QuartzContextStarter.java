// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QuartzContextStarter.java

package com.sinitek.sirm.common.quartz;

import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.loader.utils.AppLoader;
import com.sinitek.sirm.common.loader.utils.IAppLoaderContext;
import com.sinitek.sirm.common.quartz.service.ISchedulerService;
import com.sinitek.sirm.common.quartz.service.ISirmJobExecuteLogService;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

// Referenced classes of package com.sinitek.sirm.common.quartz:
//            TriggerListener

public class QuartzContextStarter
    implements AppLoader
{

    public QuartzContextStarter()
    {
        _isInitialized = false;
    }

    public void initialize(IAppLoaderContext context)
    {
        load("quartz.properties");
        _isInitialized = true;
    }

    public boolean isInitialized()
    {
        return _isInitialized;
    }

    public void destroy(IAppLoaderContext context)
    {
        try
        {
            if(scheduler != null)
            {
                scheduler.shutdown();
                _isInitialized = false;
            }
        }
        catch(SchedulerException e)
        {
            LOGGER.warn("\u5173\u95EDquartz\u5931\u8D25!", e);
        }
    }

    public static void load(String resource)
    {
        CommonServiceFactory.getSchedulerService().resetTrigderStatus();
        CommonServiceFactory.getSirmJobExecuteLogService().updateJobStatus();
        LOGGER.debug((new StringBuilder()).append("\u542F\u52A8").append(resource).append("\u5B9A\u65F6\u5668\u914D\u7F6E\u6587\u4EF6").toString());
        try
        {
            TriggerListener listener = new TriggerListener();
            StdSchedulerFactory _sf = new StdSchedulerFactory(resource);
            scheduler = _sf.getScheduler();
            scheduler.addGlobalTriggerListener(listener);
            scheduler.addGlobalJobListener(listener);
            scheduler.start();
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public void destroy()
    {
    }

    public static Scheduler getScheduler()
    {
        return scheduler;
    }

    public void run()
    {
        destroy();
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/quartz/QuartzContextStarter);
    private static Scheduler scheduler = null;
    private boolean _isInitialized;

}
