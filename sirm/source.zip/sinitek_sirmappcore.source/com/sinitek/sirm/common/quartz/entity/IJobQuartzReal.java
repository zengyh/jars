// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IJobQuartzReal.java

package com.sinitek.sirm.common.quartz.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IJobQuartzReal
    extends IMetaObjectImpl
{

    public abstract String getJobId();

    public abstract void setJobId(String s);

    public abstract String getQuartzId();

    public abstract void setQuartzId(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Integer getType();

    public abstract void setType(Integer integer);

    public static final String ENTITY_NAME = "JOBQUARTZREAL";
}
