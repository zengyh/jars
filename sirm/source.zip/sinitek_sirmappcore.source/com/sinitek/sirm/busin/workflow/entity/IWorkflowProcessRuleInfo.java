// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessRuleInfo.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessRuleInfo
    extends IMetaObjectImpl
{

    public abstract Integer getRuleType();

    public abstract void setRuleType(Integer integer);

    public abstract String getInfo();

    public abstract void setInfo(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public abstract String getRuleName();

    public abstract void setRuleName(String s);

    public static final String ENTITY_NAME = "WFPROCESSRULEINFO";
}
