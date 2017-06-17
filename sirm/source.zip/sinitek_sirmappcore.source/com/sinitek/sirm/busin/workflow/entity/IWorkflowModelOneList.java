// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowModelOneList.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowModelOneList
    extends IMetaObjectImpl
{

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getRules();

    public abstract void setRules(String s);

    public abstract String getExecutor();

    public abstract void setExecutor(String s);

    public abstract String getTaskRemind();

    public abstract void setTaskRemind(String s);

    public abstract Integer getProcessId();

    public abstract void setProcessId(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public static final String ENTITY_NAME = "WFMODELONELIST";
}
