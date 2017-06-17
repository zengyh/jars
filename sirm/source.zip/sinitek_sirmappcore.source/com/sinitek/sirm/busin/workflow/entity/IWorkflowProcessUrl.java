// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessUrl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessUrl
    extends IMetaObjectImpl
{

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getActionUrl();

    public abstract void setActionUrl(String s);

    public abstract String getShowUrl();

    public abstract void setShowUrl(String s);

    public abstract Integer getType();

    public abstract void setType(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract String getViewUrl();

    public abstract void setViewUrl(String s);

    public static final String ENTITY_NAME = "WFPROCESSURL";
}
