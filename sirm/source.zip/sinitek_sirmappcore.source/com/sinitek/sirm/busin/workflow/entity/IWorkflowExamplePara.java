// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowExamplePara.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowExamplePara
    extends IMetaObjectImpl
{

    public abstract Integer getType();

    public abstract void setType(Integer integer);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getValue();

    public abstract void setValue(String s);

    public abstract Integer getExampleId();

    public abstract void setExampleId(Integer integer);

    public abstract Integer getExampleStepId();

    public abstract void setExampleStepId(Integer integer);

    public abstract Integer getExampleOwnerId();

    public abstract void setExampleOwnerId(Integer integer);

    public static final String ENTITY_NAME = "WFEXAMPLEPARA";
}
