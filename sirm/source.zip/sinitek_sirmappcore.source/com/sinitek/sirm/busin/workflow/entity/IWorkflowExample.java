// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowExample.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IWorkflowExample
    extends IMetaObjectImpl
{

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Date getEndTime();

    public abstract void setEndTime(Date date);

    public abstract String getStarterAds();

    public abstract void setStarterAds(String s);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public abstract Integer getStarterId();

    public abstract void setStarterId(Integer integer);

    public abstract Date getStartTime();

    public abstract void setStartTime(Date date);

    public abstract Integer getProcessId();

    public abstract void setProcessId(Integer integer);

    public abstract String getProcessName();

    public abstract void setProcessName(String s);

    public abstract String getDataFrom();

    public abstract void setDataFrom(String s);

    public static final String ENTITY_NAME = "WFEXAMPLE";
}
