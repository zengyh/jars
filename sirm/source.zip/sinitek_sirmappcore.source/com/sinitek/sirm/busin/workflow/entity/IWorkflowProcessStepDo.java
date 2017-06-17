// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessStepDo.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessStepDo
    extends IMetaObjectImpl
{

    public abstract Integer getTaskid();

    public abstract void setTaskid(Integer integer);

    public abstract Integer getType();

    public abstract void setType(Integer integer);

    public abstract Integer getProcessid();

    public abstract void setProcessid(Integer integer);

    public abstract Integer getProcessstepid();

    public abstract void setProcessstepid(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public static final String ENTITY_NAME = "WFPROCESSSTEPDO";
}
