// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IJobQuartzService.java

package com.sinitek.sirm.common.quartz.service;

import com.sinitek.sirm.common.quartz.entity.*;
import java.util.List;

public interface IJobQuartzService
{

    public abstract void saveJobQuartzGroup(IJobQuartzGroup ijobquartzgroup);

    public abstract void removeJobQuartzGroup(int i);

    public abstract IJobQuartzGroup getJobQuartzGroup(int i);

    public abstract List findJobQuartzGroupList();

    public abstract void saveJobQuartzClassReal(IJobQuartzClassReal ijobquartzclassreal);

    public abstract void removeJobQuartzClassReal(int i);

    public abstract IJobQuartzClassReal getJobQuartzClassReal(int i);

    public abstract List findJobQuartzClassReal(Integer integer);

    public abstract void saveJobQuartzTime(IJobQuartzTime ijobquartztime);

    public abstract void removeJobQuartzTime(int i);

    public abstract IJobQuartzTime getJobQuartzTime(int i);

    public abstract List findJobQuartzTime();
}
