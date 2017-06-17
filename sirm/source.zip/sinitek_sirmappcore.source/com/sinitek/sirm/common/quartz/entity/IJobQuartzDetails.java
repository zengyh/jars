// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IJobQuartzDetails.java

package com.sinitek.sirm.common.quartz.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IJobQuartzDetails
    extends IMetaObjectImpl
{

    public abstract String getJobId();

    public abstract void setJobId(String s);

    public abstract String getQuartzId();

    public abstract void setQuartzId(String s);

    public abstract Date getStarttime();

    public abstract void setStarttime(Date date);

    public abstract Date getEndtime();

    public abstract void setEndtime(Date date);

    public abstract Integer getExecStatus();

    public abstract void setExecStatus(Integer integer);

    public abstract Integer getResultStatus();

    public abstract void setResultStatus(Integer integer);

    public abstract Integer getAuto();

    public abstract void setAuto(Integer integer);

    public abstract String getMemo();

    public abstract void setMemo(String s);

    public static final String ENTITY_NAME = "JOBQUARTZDETAILS";
}
