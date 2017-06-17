// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessRule.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessRule
    extends IMetaObjectImpl
{

    public abstract String getTargetType();

    public abstract void setTargetType(String s);

    public abstract Integer getTargetId();

    public abstract void setTargetId(Integer integer);

    public abstract String getTargetAdd();

    public abstract void setTargetAdd(String s);

    public abstract Integer getRuleId();

    public abstract void setRuleId(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public static final String ENTITY_NAME = "WFPROCESSRULE";
}
