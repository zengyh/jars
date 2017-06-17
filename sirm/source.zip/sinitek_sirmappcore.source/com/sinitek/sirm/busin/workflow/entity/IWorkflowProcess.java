// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcess.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcess
    extends IMetaObjectImpl
{

    public abstract Integer getPhoneshow();

    public abstract void setPhoneshow(Integer integer);

    public abstract Integer getClaimType();

    public abstract void setClaimType(Integer integer);

    public abstract String getSysCode();

    public abstract void setSysCode(String s);

    public abstract Integer getSysVersion();

    public abstract void setSysVersion(Integer integer);

    public abstract Integer getProcessType();

    public abstract void setProcessType(Integer integer);

    public abstract String getProcessBrief();

    public abstract void setProcessBrief(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract Integer getProcessVersion();

    public abstract void setProcessVersion(Integer integer);

    public abstract String getProcessCode();

    public abstract void setProcessCode(String s);

    public abstract String getSpecialMark();

    public abstract void setSpecialMark(String s);

    public static final String ENTITY_NAME = "WFPROCESS";
}
