// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowExampleAskFor.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IWorkflowExampleAskFor
    extends IMetaObjectImpl
{

    public abstract Integer getAskerId();

    public abstract void setAskerId(Integer integer);

    public abstract Integer getParentId();

    public abstract void setParentId(Integer integer);

    public abstract Integer getExampleOwnerId();

    public abstract void setExampleOwnerId(Integer integer);

    public abstract Integer getOwnerId();

    public abstract void setOwnerId(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Date getStartTime();

    public abstract void setStartTime(Date date);

    public abstract Date getApproveTime();

    public abstract void setApproveTime(Date date);

    public abstract Integer getApproveStatus();

    public abstract void setApproveStatus(Integer integer);

    public abstract String getOpinion();

    public abstract void setOpinion(String s);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public static final String ENTITY_NAME = "WFEXAMPLEASKFOR";
}
