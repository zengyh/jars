// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TriggerListener.java

package com.sinitek.sirm.common.quartz;

import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.quartz.entity.ISirmJobExecuteLog;
import com.sinitek.sirm.common.quartz.entity.SirmJobExecuteLogImpl;
import com.sinitek.sirm.common.quartz.service.ISirmJobExecuteLogService;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.Trigger;

public class TriggerListener
    implements org.quartz.TriggerListener, JobListener
{

    public TriggerListener()
    {
        threadMap = new HashMap();
    }

    public String getName()
    {
        return "TriggerListener";
    }

    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext)
    {
        LOGGER.info((new StringBuilder()).append("TriggerListener.triggerFired.[").append(trigger.getName()).append("]").toString());
        try
        {
            JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
            ISirmJobExecuteLog sirmJobExecuteLog = new SirmJobExecuteLogImpl();
            sirmJobExecuteLog.setName(trigger.getJobName());
            sirmJobExecuteLog.setStarttime(new Date());
            if(jobDataMap != null && jobDataMap.get("executeType") != null && jobDataMap.get("executeType").equals("hand") && jobDataMap.get("empid") != null)
            {
                sirmJobExecuteLog.setType(Integer.valueOf(2));
                sirmJobExecuteLog.setEmpid(Integer.valueOf(Integer.parseInt(jobDataMap.get("empid").toString())));
            } else
            {
                sirmJobExecuteLog.setType(Integer.valueOf(1));
                sirmJobExecuteLog.setEmpid(Integer.valueOf(0));
            }
            sirmJobExecuteLog.setStatus(Integer.valueOf(1));
            CommonServiceFactory.getSirmJobExecuteLogService().saveJobExecuteLog(sirmJobExecuteLog);
            threadMap.put(Thread.currentThread(), sirmJobExecuteLog);
        }
        catch(Exception e)
        {
            LOGGER.error("TriggerListener.triggerFired\u51FA\u9519", e);
        }
    }

    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext)
    {
        LOGGER.info((new StringBuilder()).append("TriggerListener.vetoJobExecution.[").append(trigger.getName()).append("]").toString());
        return false;
    }

    public void triggerMisfired(Trigger trigger)
    {
        LOGGER.info((new StringBuilder()).append("TriggerListener.triggerMisfired.[").append(trigger.getName()).append("]").toString());
    }

    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, int i)
    {
        LOGGER.info((new StringBuilder()).append("TriggerListener.triggerComplete.[").append(trigger.getName()).append("]").toString());
        try
        {
            int error_state = jobExecutionContext.getScheduler().getTriggerState(trigger.getName(), trigger.getGroup());
            if(error_state == 3)
                LOGGER.info("TriggerListener.triggerCompleteERROR");
        }
        catch(Exception e)
        {
            LOGGER.error("\u83B7\u53D6\u5B9A\u65F6\u4EFB\u52A1\u6267\u884C\u72B6\u6001\u9519\u8BEF", e);
        }
    }

    public void jobToBeExecuted(JobExecutionContext context)
    {
        LOGGER.info((new StringBuilder()).append("TriggerListener.jobToBeExecuted.[").append(context.getTrigger().getName()).append("]").toString());
    }

    public void jobExecutionVetoed(JobExecutionContext context)
    {
        LOGGER.info((new StringBuilder()).append("TriggerListener.jobExecutionVetoed.[").append(context.getTrigger().getName()).append("]").toString());
    }

    public void jobWasExecuted(JobExecutionContext context, JobExecutionException e)
    {
        LOGGER.info((new StringBuilder()).append("TriggerListener.jobWasExecuted.[").append(context.getTrigger().getName()).append("]").toString());
        try
        {
            if(threadMap != null && threadMap.get(Thread.currentThread()) != null)
                if(e != null)
                {
                    String str = e.getMessage();
                    LOGGER.error((new StringBuilder()).append("\u6267\u884C\u5B9A\u65F6\u4EFB\u52A1\u51FA\u9519\uFF01\u5931\u8D25\u539F\u56E0\uFF1A<br/>").append(str.replaceAll("\r\n", "<br/>")).toString(), e);
                    ISirmJobExecuteLog jobExecuteLog = (ISirmJobExecuteLog)threadMap.get(Thread.currentThread());
                    try
                    {
                        jobExecuteLog.setEndtime(new Date());
                        jobExecuteLog.setStatus(Integer.valueOf(3));
                        CommonServiceFactory.getSirmJobExecuteLogService().saveJobExecuteLog(jobExecuteLog);
                    }
                    catch(Exception ex)
                    {
                        LOGGER.error("TriggerListener.jobWasExecuted\u51FA\u9519", ex);
                    }
                } else
                {
                    ISirmJobExecuteLog jobExecuteLog = (ISirmJobExecuteLog)threadMap.get(Thread.currentThread());
                    try
                    {
                        jobExecuteLog.setEndtime(new Date());
                        jobExecuteLog.setStatus(Integer.valueOf(2));
                        CommonServiceFactory.getSirmJobExecuteLogService().saveJobExecuteLog(jobExecuteLog);
                    }
                    catch(Exception ex)
                    {
                        LOGGER.error("TriggerListener.jobWasExecuted\u51FA\u9519", ex);
                    }
                }
        }
        catch(Exception ex)
        {
            LOGGER.error("TriggerListener.jobWasExecuted\u51FA\u9519", e);
        }
    }

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/quartz/TriggerListener);
    private Map threadMap;

}
