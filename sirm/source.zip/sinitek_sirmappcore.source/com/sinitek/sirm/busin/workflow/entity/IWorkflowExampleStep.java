// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowExampleStep.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IWorkflowExampleStep
    extends IMetaObjectImpl
{

    public abstract Integer getRecoverFlag();

    public abstract void setRecoverFlag(Integer integer);

    public abstract Date getEndTime();

    public abstract void setEndTime(Date date);

    public abstract Integer getProcessStepId();

    public abstract void setProcessStepId(Integer integer);

    public abstract Integer getProcessId();

    public abstract void setProcessId(Integer integer);

    public abstract Integer getExampleId();

    public abstract void setExampleId(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract String getProcessStepName();

    public abstract void setProcessStepName(String s);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public abstract String getStepBeigin();

    public abstract void setStepBeigin(String s);

    public abstract String getStepEnd();

    public abstract void setStepEnd(String s);

    public abstract Integer getStepCondition();

    public abstract void setStepCondition(Integer integer);

    public abstract Integer getStepType();

    public abstract void setStepType(Integer integer);

    public abstract String getActionUrl();

    public abstract void setActionUrl(String s);

    public abstract String getShowUrl();

    public abstract void setShowUrl(String s);

    public abstract Integer getUrlMark();

    public abstract void setUrlMark(Integer integer);

    public abstract Date getStartTime();

    public abstract void setStartTime(Date date);

    public static final String ENTITY_NAME = "WFEXAMPLESTEP";
}
