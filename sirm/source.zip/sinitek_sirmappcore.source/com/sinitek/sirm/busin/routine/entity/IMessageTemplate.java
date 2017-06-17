// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMessageTemplate.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IMessageTemplate
    extends IMetaObjectImpl
{

    public abstract String getCode();

    public abstract void setCode(String s);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getContent();

    public abstract void setContent(String s);

    public abstract Integer getSendMode();

    public abstract void setSendMode(Integer integer);

    public abstract String getTitle();

    public abstract void setTitle(String s);

    public abstract String getSmsContent();

    public abstract void setSmsContent(String s);

    public abstract String getRemindContent();

    public abstract void setRemindContent(String s);

    public abstract Integer getForceflag();

    public abstract void setForceflag(Integer integer);

    public abstract Integer getCatagory();

    public abstract void setCatagory(Integer integer);

    public abstract String getRemark();

    public abstract void setRemark(String s);

    public abstract Integer getProcesstype();

    public abstract void setProcesstype(Integer integer);

    public abstract String getMobileContent();

    public abstract void setMobileContent(String s);

    public abstract Integer getTemplatetype();

    public abstract void setTemplatetype(Integer integer);

    public static final String ENTITY_NAME = "MESSAGETEMPLATE";
}
