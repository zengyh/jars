// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JobQuartzServiceImpl.java

package com.sinitek.sirm.common.quartz.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.quartz.entity.*;
import com.sinitek.sirm.common.quartz.service.IJobQuartzService;
import java.util.List;

public class JobQuartzServiceImpl extends MetaDBContextSupport
    implements IJobQuartzService
{

    public JobQuartzServiceImpl()
    {
    }

    public void saveJobQuartzGroup(IJobQuartzGroup jobQuartzGroup)
    {
        save(jobQuartzGroup);
    }

    public void removeJobQuartzGroup(int groupId)
    {
        IJobQuartzGroup jobQuartzGroup = getJobQuartzGroup(groupId);
        if(jobQuartzGroup != null)
            remove(jobQuartzGroup);
    }

    public IJobQuartzGroup getJobQuartzGroup(int groupId)
    {
        return (IJobQuartzGroup)getMetaDBContext().get(com/sinitek/sirm/common/quartz/entity/IJobQuartzGroup, groupId);
    }

    public List findJobQuartzGroupList()
    {
        String _sql = " 1=1";
        return getMetaDBContext().query("JOBQUARTZGROUP", _sql, null);
    }

    public void saveJobQuartzClassReal(IJobQuartzClassReal jobQuartzClassReal)
    {
        save(jobQuartzClassReal);
    }

    public void removeJobQuartzClassReal(int classId)
    {
        IJobQuartzClassReal jobQuartzClassReal = getJobQuartzClassReal(classId);
        if(jobQuartzClassReal != null)
            remove(jobQuartzClassReal);
    }

    public IJobQuartzClassReal getJobQuartzClassReal(int classId)
    {
        return (IJobQuartzClassReal)getMetaDBContext().get(com/sinitek/sirm/common/quartz/entity/IJobQuartzClassReal, classId);
    }

    public List findJobQuartzClassReal(Integer groupId)
    {
        String _sql = " 1=1";
        return getMetaDBContext().query("JOBQUARTZCLASSREAL", _sql, null);
    }

    public void saveJobQuartzTime(IJobQuartzTime jobQuartzTime)
    {
        save(jobQuartzTime);
    }

    public void removeJobQuartzTime(int timeId)
    {
        IJobQuartzTime jobQuartzTime = getJobQuartzTime(timeId);
        if(jobQuartzTime != null)
            remove(jobQuartzTime);
    }

    public IJobQuartzTime getJobQuartzTime(int timeId)
    {
        return (IJobQuartzTime)getMetaDBContext().get(com/sinitek/sirm/common/quartz/entity/IJobQuartzTime, timeId);
    }

    public List findJobQuartzTime()
    {
        String _sql = " 1=1";
        return getMetaDBContext().query("JOBQUARTZTIME", _sql, null);
    }
}
