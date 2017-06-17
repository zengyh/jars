// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessStepLink.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessStepLink
    extends IMetaObjectImpl
{

    public abstract Integer getAftStepId();

    public abstract void setAftStepId(Integer integer);

    public abstract Integer getProcessId();

    public abstract void setProcessId(Integer integer);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract Integer getPreStepId();

    public abstract void setPreStepId(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public static final String ENTITY_NAME = "WFPROCESSSTEPLINK";
}
