// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessOwner.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessOwner
    extends IMetaObjectImpl
{

    public abstract Integer getValue();

    public abstract void setValue(Integer integer);

    public abstract Integer getOwnerGoto();

    public abstract void setOwnerGoto(Integer integer);

    public abstract Integer getOwnerGotoId();

    public abstract void setOwnerGotoId(Integer integer);

    public abstract Integer getProcessId();

    public abstract void setProcessId(Integer integer);

    public abstract Integer getStepId();

    public abstract void setStepId(Integer integer);

    public abstract String getOrgId();

    public abstract void setOrgId(String s);

    public abstract Integer getOrgType();

    public abstract void setOrgType(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public static final String ENTITY_NAME = "WFPROCESSOWNER";
}
