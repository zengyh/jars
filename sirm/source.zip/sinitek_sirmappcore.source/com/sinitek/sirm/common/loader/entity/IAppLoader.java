// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IAppLoader.java

package com.sinitek.sirm.common.loader.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IAppLoader
    extends IMetaObjectImpl
{

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getCode();

    public abstract void setCode(String s);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public abstract String getClassFullName();

    public abstract void setClassFullName(String s);

    public abstract Integer getPriority();

    public abstract void setPriority(Integer integer);

    public abstract Boolean getEnabled();

    public abstract void setEnabled(Boolean boolean1);

    public static final String ENTITY_NAME = "APPLOADER";
}
