// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SchedulerServiceImpl.java

package com.sinitek.sirm.common.quartz.service.impl;

import com.sinitek.base.datasource.DataSourceFactory;
import com.sinitek.base.metadb.MetaDBContextFactory;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.quartz.QuartzContextStarter;
import com.sinitek.sirm.common.quartz.entity.IJobQuartzReal;
import com.sinitek.sirm.common.quartz.service.IJobQuartzRealService;
import com.sinitek.sirm.common.quartz.service.ISchedulerService;
import com.sinitek.sirm.common.utils.ListComparator;
import com.sinitek.sirm.common.utils.TimeUtil;
import java.text.ParseException;
import java.util.*;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class SchedulerServiceImpl extends AbstractMetaDBContextSupport
    implements ISchedulerService
{

    private Scheduler getScheduler()
    {
        return QuartzContextStarter.getScheduler();
    }

    private JdbcTemplate getTemplate()
    {
        return new JdbcTemplate(MetaDBContextFactory.getInstance().getDataSource());
    }

    public SchedulerServiceImpl()
    {
    }

    public void schedule(String cronExpression, JobDetail jobDetail)
    {
        schedule("", cronExpression, jobDetail);
    }

    public void schedule(String name, String cronExpression, JobDetail jobDetail)
    {
        schedule(name, cronExpression, jobDetail.getGroup(), jobDetail);
    }

    public void schedule(String name, String cronExpression, String group, JobDetail jobDetail)
    {
        try
        {
            schedule(name, new CronExpression(cronExpression), group, jobDetail);
        }
        catch(ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void schedule(CronExpression cronExpression, JobDetail jobDetail)
    {
        schedule(((String) (null)), cronExpression, jobDetail);
    }

    public void schedule(String name, CronExpression cronExpression, JobDetail jobDetail)
    {
        schedule(name, cronExpression, "DEFAULT", jobDetail);
    }

    public void schedule(String name, CronExpression cronExpression, String group, JobDetail jobDetail)
    {
        try
        {
            QuartzContextStarter.getScheduler().addJob(jobDetail, true);
            CronTrigger cronTrigger = new CronTrigger(name, group, jobDetail.getName(), group);
            cronTrigger.setDescription(jobDetail.getDescription());
            cronTrigger.setCronExpression(cronExpression);
            QuartzContextStarter.getScheduler().scheduleJob(cronTrigger);
            QuartzContextStarter.getScheduler().rescheduleJob(cronTrigger.getName(), cronTrigger.getGroup(), cronTrigger);
        }
        catch(SchedulerException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void schedule(Date startTime, JobDetail jobDetail)
    {
        schedule(startTime, "DEFAULT", jobDetail);
    }

    public void schedule(Date startTime, String group, JobDetail jobDetail)
    {
        schedule(startTime, ((Date) (null)), group, jobDetail);
    }

    public void schedule(String name, Date startTime, JobDetail jobDetail)
    {
        schedule(name, startTime, "DEFAULT", jobDetail);
    }

    public void schedule(String name, Date startTime, String group, JobDetail jobDetail)
    {
        schedule(name, startTime, ((Date) (null)), group, jobDetail);
    }

    public void schedule(Date startTime, Date endTime, JobDetail jobDetail)
    {
        schedule(startTime, endTime, "DEFAULT", jobDetail);
    }

    public void schedule(Date startTime, Date endTime, String group, JobDetail jobDetail)
    {
        schedule(startTime, endTime, 0, group, jobDetail);
    }

    public void schedule(String name, Date startTime, Date endTime, JobDetail jobDetail)
    {
        schedule(name, startTime, endTime, "DEFAULT", jobDetail);
    }

    public void schedule(String name, Date startTime, Date endTime, String group, JobDetail jobDetail)
    {
        schedule(name, startTime, endTime, 0, group, jobDetail);
    }

    public void schedule(Date startTime, Date endTime, int repeatCount, JobDetail jobDetail)
    {
        schedule(startTime, endTime, 0, "DEFAULT", jobDetail);
    }

    public void schedule(Date startTime, Date endTime, int repeatCount, String group, JobDetail jobDetail)
    {
        schedule(((String) (null)), startTime, endTime, 0, group, jobDetail);
    }

    public void schedule(String name, Date startTime, Date endTime, int repeatCount, JobDetail jobDetail)
    {
        schedule(name, startTime, endTime, 0, "DEFAULT", jobDetail);
    }

    public void schedule(String name, Date startTime, Date endTime, int repeatCount, String group, JobDetail jobDetail)
    {
        schedule(name, startTime, endTime, 0, 1L, group, jobDetail);
    }

    public void schedule(Date startTime, Date endTime, int repeatCount, long repeatInterval, JobDetail jobDetail)
    {
        schedule(startTime, endTime, repeatCount, repeatInterval, "DEFAULT", jobDetail);
    }

    public void schedule(Date startTime, Date endTime, int repeatCount, long repeatInterval, String group, JobDetail jobDetail)
    {
        schedule(jobDetail.getName(), startTime, endTime, repeatCount, repeatInterval, group, jobDetail);
    }

    public void schedule(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval, JobDetail jobDetail)
    {
        schedule(name, startTime, endTime, repeatCount, repeatInterval, "DEFAULT", jobDetail);
    }

    public void schedule(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval, String group, 
            JobDetail jobDetail)
    {
        try
        {
            QuartzContextStarter.getScheduler().addJob(jobDetail, true);
            SimpleTrigger SimpleTrigger = new SimpleTrigger(name, group, jobDetail.getName(), "MANUAL_TRIGGER", startTime, endTime, repeatCount, repeatInterval);
            QuartzContextStarter.getScheduler().scheduleJob(SimpleTrigger);
            QuartzContextStarter.getScheduler().rescheduleJob(SimpleTrigger.getName(), SimpleTrigger.getGroup(), SimpleTrigger);
        }
        catch(SchedulerException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void schedule(Map map, JobDetail jobDetail)
    {
        String temp = null;
        SimpleTrigger SimpleTrigger = new SimpleTrigger();
        SimpleTrigger.setJobName(jobDetail.getName());
        SimpleTrigger.setJobGroup("DEFAULT");
        SimpleTrigger.setRepeatInterval(1000L);
        temp = (String)map.get("triggerName");
        SimpleTrigger.setName(temp);
        temp = (String)map.get("triggerGroup");
        if(StringUtils.isEmpty(temp))
            temp = "DEFAULT";
        SimpleTrigger.setGroup(temp);
        temp = (String)map.get("startTime");
        if(StringUtils.isNotEmpty(temp))
            SimpleTrigger.setStartTime(parseDate(temp));
        temp = (String)map.get("endTime");
        if(StringUtils.isNotEmpty(temp))
            SimpleTrigger.setEndTime(parseDate(temp));
        temp = (String)map.get("repeatCount");
        if(StringUtils.isNotEmpty(temp) && NumberUtils.toInt(temp) > 0)
            SimpleTrigger.setRepeatCount(NumberUtils.toInt(temp));
        temp = (String)map.get("repeatInterval");
        if(StringUtils.isNotEmpty(temp) && NumberUtils.toLong(temp) > 0L)
            SimpleTrigger.setRepeatInterval(NumberUtils.toLong(temp) * 1000L);
        try
        {
            QuartzContextStarter.getScheduler().addJob(jobDetail, true);
            QuartzContextStarter.getScheduler().scheduleJob(SimpleTrigger);
            QuartzContextStarter.getScheduler().rescheduleJob(SimpleTrigger.getName(), SimpleTrigger.getGroup(), SimpleTrigger);
        }
        catch(SchedulerException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void pauseTrigger(String triggerName, String group)
    {
        try
        {
            QuartzContextStarter.getScheduler().pauseTrigger(triggerName, group);
        }
        catch(SchedulerException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void resumeTrigger(String triggerName, String group)
    {
        try
        {
            QuartzContextStarter.getScheduler().resumeTrigger(triggerName, group);
        }
        catch(SchedulerException e)
        {
            throw new RuntimeException(e);
        }
    }

    public boolean removeTrigdger(String triggerName, String group)
    {
        boolean isok = false;
        try
        {
            QuartzContextStarter.getScheduler().pauseTrigger(triggerName, group);
            QuartzContextStarter.getScheduler().unscheduleJob(triggerName, group);
            isok = QuartzContextStarter.getScheduler().deleteJob(triggerName, group);
        }
        catch(SchedulerException e)
        {
            JdbcTemplate jt = new JdbcTemplate(DataSourceFactory.getInstance().getDataSouce("siniteksirm"));
            jt.update("delete qrtz_cron_triggers where trigger_name=? and trigger_group=?", new Object[] {
                triggerName, group
            });
            jt.update("delete qrtz_triggers where trigger_name=? and trigger_group=?", new Object[] {
                triggerName, group
            });
            jt.update("delete QRTZ_JOB_DETAILS where job_name=? and job_group=?", new Object[] {
                triggerName, group
            });
        }
        return isok;
    }

    private Date parseDate(String time)
    {
        try
        {
            return DateUtils.parseDate(time, new String[] {
                "yyyy-MM-dd HH:mm"
            });
        }
        catch(ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    public List findTrigderList(String group)
    {
        String _sql = "SELECT T.TRIGGER_NAME,T.TRIGGER_GROUP,T.JOB_NAME,T.DESCRIPTION,T.START_TIME,T.NEXT_FIRE_TIME,T.PREV_FIRE_TIME,T.TRIGGER_STATE,T.TRIGGER_TYPE,  C.CRON_EXPRESSION,D.JOB_CLASS_NAME,D.JOB_CLASS_NAME CLASSNAME,T.JOB_GROUP FROM QRTZ_TRIGGERS T  LEFT JOIN QRTZ_CRON_TRIGGERS C ON T.TRIGGER_NAME = C.TRIGGER_NAME  LEFT JOIN QRTZ_JOB_DETAILS D ON D.JOB_NAME = T.JOB_NAME where 1 = 1";
        Object _params[] = null;
        if(group != null)
        {
            _sql = (new StringBuilder()).append(_sql).append(" AND T.TRIGGER_GROUP=?").toString();
            _params = new Object[1];
            _params[0] = group;
        }
        List result = new ArrayList();
        List list = getTemplate().queryForList(_sql, _params);
        if(list != null)
        {
            for(int i = 0; i < list.size(); i++)
            {
                ListOrderedMap map = (ListOrderedMap)list.get(i);
                map.put("objid", Integer.valueOf(i));
                String start = map.get("start_time").toString();
                map.put("start_time", TimeUtil.formatDate(new Date(Long.parseLong(start)), "yyyy-MM-dd HH:mm:ss"));
                String nexttime = map.get("next_fire_time").toString();
                map.put("next_fire_time", TimeUtil.formatDate(new Date(Long.parseLong(nexttime)), "yyyy-MM-dd HH:mm:ss"));
                String prevtime = map.get("prev_fire_time").toString();
                map.put("prev_fire_time", TimeUtil.formatDate(new Date(Long.parseLong(prevtime)), "yyyy-MM-dd HH:mm:ss"));
                result.add(map);
            }

        }
        ListComparator comparator = new ListComparator("JOB_NAME", "asc");
        Collections.sort(result, comparator);
        return result;
    }

    public void resetTrigderStatus()
    {
        String _sql = "update qrtz_triggers t set t.trigger_state = 'WAITING' where t.trigger_state = 'BLOCKED' or t.trigger_state = 'ACQUIRED'";
        getTemplate().execute(_sql);
    }

    public List exportTrigderList(String names)
    {
        String _sql = (new StringBuilder()).append("SELECT * FROM QRTZ_TRIGGERS T  LEFT JOIN QRTZ_CRON_TRIGGERS C ON T.TRIGGER_NAME = C.TRIGGER_NAME  LEFT JOIN QRTZ_JOB_DETAILS D ON D.JOB_NAME = T.JOB_NAME where 1 = 1  and T.TRIGGER_NAME in (").append(names).append(") ").toString();
        Object _params[] = null;
        List result = new ArrayList();
        List list = getTemplate().queryForList(_sql, _params);
        if(list != null)
        {
            for(int i = 0; i < list.size(); i++)
            {
                ListOrderedMap map = (ListOrderedMap)list.get(i);
                String start = map.get("start_time").toString();
                map.put("start_time", TimeUtil.formatDate(new Date(Long.parseLong(start)), "yyyy-MM-dd HH:mm:ss"));
                String nexttime = map.get("next_fire_time").toString();
                map.put("next_fire_time", TimeUtil.formatDate(new Date(Long.parseLong(nexttime)), "yyyy-MM-dd HH:mm:ss"));
                String prevtime = map.get("prev_fire_time").toString();
                map.put("prev_fire_time", TimeUtil.formatDate(new Date(Long.parseLong(prevtime)), "yyyy-MM-dd HH:mm:ss"));
                result.add(map);
            }

        }
        return result;
    }

    public boolean validatejobName(String oldTriggerName, String jobName, String group)
    {
        ListOrderedMap _quartzMap = getQuartz(jobName, group);
        if(_quartzMap != null && _quartzMap.size() > 0)
        {
            String trigger_name = _quartzMap.get("TRIGGER_NAME").toString();
            if(oldTriggerName.equals(""))
            {
                if(trigger_name.equals(jobName))
                    return true;
            } else
            if(trigger_name.equals(jobName) && !trigger_name.equals(oldTriggerName))
                return true;
        }
        return false;
    }

    public void modifyTrigger(String oldTriggerName, String oldGroup, String cronExpression, JobDetail jobDetail)
    {
        removeTrigdger(oldTriggerName, oldGroup);
        schedule(jobDetail.getName(), cronExpression, jobDetail);
        ListOrderedMap _quartzMap = getQuartz(jobDetail.getName(), jobDetail.getGroup());
        String _trigger_name = null;
        if(_quartzMap != null && _quartzMap.size() > 0)
            _trigger_name = _quartzMap.get("TRIGGER_NAME").toString();
        if(_trigger_name == null)
            return;
        List _jobQuartzRealList = CommonServiceFactory.getJobQuartzRealService().findJobQuartzRealByQuartzId(oldTriggerName, null);
        if(_jobQuartzRealList != null && _jobQuartzRealList.size() > 0)
        {
            for(int i = 0; i < _jobQuartzRealList.size(); i++)
            {
                IJobQuartzReal _jobQuartzReal = (IJobQuartzReal)_jobQuartzRealList.get(i);
                _jobQuartzReal.setQuartzId(_trigger_name);
            }

            CommonServiceFactory.getJobQuartzRealService().saveList(_jobQuartzRealList);
        }
    }

    public ListOrderedMap getQuartz(String jobname, String group)
    {
        String _sql = "SELECT T.* FROM QRTZ_TRIGGERS T WHERE T.JOB_NAME=? AND T.TRIGGER_GROUP=?";
        Object _params[] = new Object[2];
        _params[0] = jobname;
        _params[1] = group;
        List _result = getTemplate().queryForList(_sql, _params);
        if(_result == null || _result.size() < 1)
            return null;
        else
            return (ListOrderedMap)_result.get(0);
    }

    private static final Logger logger = LoggerFactory.getLogger(com/sinitek/sirm/common/quartz/service/impl/SchedulerServiceImpl);

}
