// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowExampleRelation.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowExampleRelation
    extends IMetaObjectImpl
{

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract Integer getExampleStepId();

    public abstract void setExampleStepId(Integer integer);

    public abstract Integer getExampleOwnerId();

    public abstract void setExampleOwnerId(Integer integer);

    public abstract Integer getSubExampleId();

    public abstract void setSubExampleId(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Integer getMark();

    public abstract void setMark(Integer integer);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public abstract Integer getExampleId();

    public abstract void setExampleId(Integer integer);

    public static final String ENTITY_NAME = "WFEXAMPLERELATION";
}
