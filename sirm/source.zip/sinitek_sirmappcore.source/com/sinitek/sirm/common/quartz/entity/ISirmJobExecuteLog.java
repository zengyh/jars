// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISirmJobExecuteLog.java

package com.sinitek.sirm.common.quartz.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface ISirmJobExecuteLog
    extends IMetaObjectImpl
{

    public abstract String getName();

    public abstract void setName(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Integer getEmpid();

    public abstract void setEmpid(Integer integer);

    public abstract Date getStarttime();

    public abstract void setStarttime(Date date);

    public abstract Date getEndtime();

    public abstract void setEndtime(Date date);

    public abstract Integer getType();

    public abstract void setType(Integer integer);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public static final String ENTITY_NAME = "SIRMJOBEXECUTELOG";
}
