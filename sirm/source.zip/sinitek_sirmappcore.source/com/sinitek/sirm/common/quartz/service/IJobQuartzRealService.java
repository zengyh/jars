// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IJobQuartzRealService.java

package com.sinitek.sirm.common.quartz.service;

import com.sinitek.sirm.common.quartz.entity.IJobQuartzDetails;
import com.sinitek.sirm.common.quartz.entity.IJobQuartzReal;
import java.util.List;

public interface IJobQuartzRealService
{

    public abstract List findJobQuartzRealByType(Integer integer, Integer integer1);

    public abstract List findJobQuartzRealByQuartzId(String s, Integer integer);

    public abstract IJobQuartzReal getJobQuartzRealById(int i);

    public abstract void saveList(List list);

    public abstract void saveJobQuartzReal(IJobQuartzReal ijobquartzreal);

    public abstract IJobQuartzDetails saveJobQuartzDetails(IJobQuartzDetails ijobquartzdetails);

    public abstract void pastJobQuartzDetails();

    public abstract List findJobQuartzDetailsList(Integer integer);
}
