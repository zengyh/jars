// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessTimeOut.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessTimeOut
    extends IMetaObjectImpl
{

    public abstract Integer getPollType();

    public abstract void setPollType(Integer integer);

    public abstract String getPollTime();

    public abstract void setPollTime(String s);

    public abstract Integer getAccuracy();

    public abstract void setAccuracy(Integer integer);

    public abstract Integer getDealType();

    public abstract void setDealType(Integer integer);

    public abstract Integer getDealKey();

    public abstract void setDealKey(Integer integer);

    public abstract String getDealValue();

    public abstract void setDealValue(String s);

    public abstract String getTimeOut();

    public abstract void setTimeOut(String s);

    public abstract String getDealValueAds();

    public abstract void setDealValueAds(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract Integer getProcessId();

    public abstract void setProcessId(Integer integer);

    public abstract Integer getStepId();

    public abstract void setStepId(Integer integer);

    public static final String ENTITY_NAME = "WFPROCESSTIMEOUT";
}
