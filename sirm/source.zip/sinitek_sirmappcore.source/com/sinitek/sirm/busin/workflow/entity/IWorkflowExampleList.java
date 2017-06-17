// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowExampleList.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowExampleList
    extends IMetaObjectImpl
{

    public abstract Integer getKey();

    public abstract void setKey(Integer integer);

    public abstract String getValue();

    public abstract void setValue(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract String getName();

    public abstract void setName(String s);

    public static final String ENTITY_NAME = "WFEXAMPLELIST";
}
