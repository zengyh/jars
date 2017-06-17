// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowExampleTask.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IWorkflowExampleTask
    extends IMetaObjectImpl
{

    public abstract String getSourceEntity();

    public abstract void setSourceEntity(String s);

    public abstract Integer getSourceId();

    public abstract void setSourceId(Integer integer);

    public abstract Integer getDealerId();

    public abstract void setDealerId(Integer integer);

    public abstract Integer getOrginerId();

    public abstract void setOrginerId(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Date getStartTime();

    public abstract void setStartTime(Date date);

    public abstract Date getEndTime();

    public abstract void setEndTime(Date date);

    public abstract String getDescription();

    public abstract void setDescription(String s);

    public abstract String getRemarks();

    public abstract void setRemarks(String s);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public static final String ENTITY_NAME = "WFEXAMPLETASK";
}
