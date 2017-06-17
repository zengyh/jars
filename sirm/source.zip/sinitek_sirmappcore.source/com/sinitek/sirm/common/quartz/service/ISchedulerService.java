// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISchedulerService.java

package com.sinitek.sirm.common.quartz.service;

import java.util.*;
import org.quartz.CronExpression;
import org.quartz.JobDetail;

public interface ISchedulerService
{

    public abstract void schedule(String s, JobDetail jobdetail);

    public abstract void schedule(String s, String s1, JobDetail jobdetail);

    public abstract void schedule(String s, String s1, String s2, JobDetail jobdetail);

    public abstract void schedule(CronExpression cronexpression, JobDetail jobdetail);

    public abstract void schedule(String s, CronExpression cronexpression, JobDetail jobdetail);

    public abstract void schedule(String s, CronExpression cronexpression, String s1, JobDetail jobdetail);

    public abstract void schedule(Date date, JobDetail jobdetail);

    public abstract void schedule(Date date, String s, JobDetail jobdetail);

    public abstract void schedule(String s, Date date, JobDetail jobdetail);

    public abstract void schedule(String s, Date date, String s1, JobDetail jobdetail);

    public abstract void schedule(Date date, Date date1, JobDetail jobdetail);

    public abstract void schedule(Date date, Date date1, String s, JobDetail jobdetail);

    public abstract void schedule(String s, Date date, Date date1, JobDetail jobdetail);

    public abstract void schedule(String s, Date date, Date date1, String s1, JobDetail jobdetail);

    public abstract void schedule(Date date, Date date1, int i, JobDetail jobdetail);

    public abstract void schedule(Date date, Date date1, int i, String s, JobDetail jobdetail);

    public abstract void schedule(String s, Date date, Date date1, int i, JobDetail jobdetail);

    public abstract void schedule(String s, Date date, Date date1, int i, String s1, JobDetail jobdetail);

    public abstract void schedule(Date date, Date date1, int i, long l, JobDetail jobdetail);

    public abstract void schedule(Date date, Date date1, int i, long l, String s, JobDetail jobdetail);

    public abstract void schedule(String s, Date date, Date date1, int i, long l, JobDetail jobdetail);

    public abstract void schedule(String s, Date date, Date date1, int i, long l, String s1, 
            JobDetail jobdetail);

    public abstract void schedule(Map map, JobDetail jobdetail);

    public abstract void pauseTrigger(String s, String s1);

    public abstract void resumeTrigger(String s, String s1);

    public abstract boolean removeTrigdger(String s, String s1);

    public abstract List findTrigderList(String s);

    public abstract List exportTrigderList(String s);

    public abstract void resetTrigderStatus();

    public abstract void modifyTrigger(String s, String s1, String s2, JobDetail jobdetail);

    public abstract boolean validatejobName(String s, String s1, String s2);
}
