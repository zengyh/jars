// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessMail.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessMail
    extends IMetaObjectImpl
{

    public abstract String getMailContext();

    public abstract void setMailContext(String s);

    public abstract Integer getMailGoto();

    public abstract void setMailGoto(Integer integer);

    public abstract Integer getMailGotoId();

    public abstract void setMailGotoId(Integer integer);

    public abstract Integer getProcessId();

    public abstract void setProcessId(Integer integer);

    public abstract Integer getStepId();

    public abstract void setStepId(Integer integer);

    public abstract String getMailTitle();

    public abstract void setMailTitle(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public static final String ENTITY_NAME = "WFPROCESSMAIL";
}
