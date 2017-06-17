// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowModelOne.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowModelOne
    extends IMetaObjectImpl
{

    public abstract Integer getTypeId();

    public abstract void setTypeId(Integer integer);

    public abstract Integer getOperationId();

    public abstract void setOperationId(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract String getDescription();

    public abstract void setDescription(String s);

    public abstract String getName();

    public abstract void setName(String s);

    public static final String ENTITY_NAME = "WFMODELONE";
}
