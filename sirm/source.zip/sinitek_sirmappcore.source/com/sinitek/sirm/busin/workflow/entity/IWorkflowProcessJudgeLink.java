// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessJudgeLink.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessJudgeLink
    extends IMetaObjectImpl
{

    public abstract Integer getStepId();

    public abstract void setStepId(Integer integer);

    public abstract Integer getResult();

    public abstract void setResult(Integer integer);

    public abstract String getResultAds();

    public abstract void setResultAds(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Integer getType();

    public abstract void setType(Integer integer);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public static final String ENTITY_NAME = "WFPROCESSJUDGELINK";
}
