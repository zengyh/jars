// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessStepHistory.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessStepHistory
    extends IMetaObjectImpl
{

    public abstract Integer getPreStepId();

    public abstract void setPreStepId(Integer integer);

    public abstract Integer getPreProcessId();

    public abstract void setPreProcessId(Integer integer);

    public abstract Integer getNewProcessId();

    public abstract void setNewProcessId(Integer integer);

    public abstract Integer getNewStepId();

    public abstract void setNewStepId(Integer integer);

    public static final String ENTITY_NAME = "WFPROCESSSTEPHISTORY";
}
