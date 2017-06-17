// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessJudgeLinkMain.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessJudgeLinkMain
    extends IMetaObjectImpl
{

    public abstract Integer getLinkId();

    public abstract void setLinkId(Integer integer);

    public abstract String getLinkMain();

    public abstract void setLinkMain(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract Integer getStepId();

    public abstract void setStepId(Integer integer);

    public static final String ENTITY_NAME = "WFPROCESSJUDGELINKMAIN";
}
