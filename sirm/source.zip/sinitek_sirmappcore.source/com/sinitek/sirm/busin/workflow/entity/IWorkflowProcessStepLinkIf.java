// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessStepLinkIf.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessStepLinkIf
    extends IMetaObjectImpl
{

    public abstract Integer getLinkId();

    public abstract void setLinkId(Integer integer);

    public abstract Integer getProcessId();

    public abstract void setProcessId(Integer integer);

    public abstract Integer getIfType();

    public abstract void setIfType(Integer integer);

    public abstract Integer getStepId();

    public abstract void setStepId(Integer integer);

    public abstract Integer getIfAnd();

    public abstract void setIfAnd(Integer integer);

    public abstract String getIfAds();

    public abstract void setIfAds(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public static final String ENTITY_NAME = "WFPROCESSSTEPLINKIF";
}
