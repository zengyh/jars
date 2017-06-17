// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowModelOneUrl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowModelOneUrl
    extends IMetaObjectImpl
{

    public abstract Integer getTypeId();

    public abstract void setTypeId(Integer integer);

    public abstract String getApproveUrl();

    public abstract void setApproveUrl(String s);

    public abstract String getEditUrl();

    public abstract void setEditUrl(String s);

    public abstract String getWatchUrl();

    public abstract void setWatchUrl(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public static final String ENTITY_NAME = "WFMODELONEURL";
}
