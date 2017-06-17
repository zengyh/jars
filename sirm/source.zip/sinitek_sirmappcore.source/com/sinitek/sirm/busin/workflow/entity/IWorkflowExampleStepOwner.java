// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowExampleStepOwner.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IWorkflowExampleStepOwner
    extends IMetaObjectImpl
{

    public abstract Integer getExampleId();

    public abstract void setExampleId(Integer integer);

    public abstract Integer getExampleStepId();

    public abstract void setExampleStepId(Integer integer);

    public abstract Integer getOwnerId();

    public abstract void setOwnerId(Integer integer);

    public abstract Integer getPreOwnerId();

    public abstract void setPreOwnerId(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Date getApproveTime();

    public abstract void setApproveTime(Date date);

    public abstract String getApproveOpinion();

    public abstract void setApproveOpinion(String s);

    public abstract Integer getApproveStatus();

    public abstract void setApproveStatus(Integer integer);

    public abstract String getApproveBrief();

    public abstract void setApproveBrief(String s);

    public abstract Date getStartTime();

    public abstract void setStartTime(Date date);

    public abstract Integer getValue();

    public abstract void setValue(Integer integer);

    public static final String ENTITY_NAME = "WFEXAMPLESTEPOWNER";
}
