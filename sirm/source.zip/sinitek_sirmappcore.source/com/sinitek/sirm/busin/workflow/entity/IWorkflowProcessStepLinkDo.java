// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessStepLinkDo.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessStepLinkDo
    extends IMetaObjectImpl
{

    public abstract Integer getLinkId();

    public abstract void setLinkId(Integer integer);

    public abstract Integer getProcessId();

    public abstract void setProcessId(Integer integer);

    public abstract Integer getDoType();

    public abstract void setDoType(Integer integer);

    public abstract String getDoAds();

    public abstract void setDoAds(String s);

    public abstract Integer getDoMark();

    public abstract void setDoMark(Integer integer);

    public abstract Integer getStepId();

    public abstract void setStepId(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public static final String ENTITY_NAME = "WFPROCESSSTEPLINKDO";
}
