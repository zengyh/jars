// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowAgents.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IWorkflowAgents
    extends IMetaObjectImpl
{

    public abstract Date getEndTime();

    public abstract void setEndTime(Date date);

    public abstract String getTypeSource();

    public abstract void setTypeSource(String s);

    public abstract Integer getTypeSourceId();

    public abstract void setTypeSourceId(Integer integer);

    public abstract String getTypeAds();

    public abstract void setTypeAds(String s);

    public abstract Integer getOwnerId();

    public abstract void setOwnerId(Integer integer);

    public abstract Integer getAgentsId();

    public abstract void setAgentsId(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Date getStartTime();

    public abstract void setStartTime(Date date);

    public abstract Integer getProcessType();

    public abstract void setProcessType(Integer integer);

    public abstract Integer getProcessId();

    public abstract void setProcessId(Integer integer);

    public abstract Integer getProcessStepId();

    public abstract void setProcessStepId(Integer integer);

    public static final String ENTITY_NAME = "WFAGENTS";
}
