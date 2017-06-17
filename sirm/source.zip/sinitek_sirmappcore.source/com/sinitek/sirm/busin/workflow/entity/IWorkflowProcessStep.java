// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessStep.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessStep
    extends IMetaObjectImpl
{

    public abstract Integer getPhoneshow();

    public abstract void setPhoneshow(Integer integer);

    public abstract Integer getProcessId();

    public abstract void setProcessId(Integer integer);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract Integer getStepTypeId();

    public abstract void setStepTypeId(Integer integer);

    public abstract String getCondition();

    public abstract void setCondition(String s);

    public abstract String getActionUrl();

    public abstract void setActionUrl(String s);

    public abstract String getShowUrl();

    public abstract void setShowUrl(String s);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract Integer getUrlMark();

    public abstract void setUrlMark(Integer integer);

    public abstract Integer getStepCode();

    public abstract void setStepCode(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract String getStepTypeAds();

    public abstract void setStepTypeAds(String s);

    public abstract Integer getPointTypeId();

    public abstract void setPointTypeId(Integer integer);

    public abstract Integer getAgentType();

    public abstract void setAgentType(Integer integer);

    public abstract String getStepSpecial();

    public abstract void setStepSpecial(String s);

    public static final String ENTITY_NAME = "WFPROCESSSTEP";
}
