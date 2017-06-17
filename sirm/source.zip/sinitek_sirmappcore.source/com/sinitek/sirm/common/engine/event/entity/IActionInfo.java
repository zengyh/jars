// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IActionInfo.java

package com.sinitek.sirm.common.engine.event.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IActionInfo
    extends IMetaObjectImpl
{

    public abstract String getCode();

    public abstract void setCode(String s);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public abstract String getLocation();

    public abstract void setLocation(String s);

    public abstract Integer getSyncflag();

    public abstract void setSyncflag(Integer integer);

    public static final String ENTITY_NAME = "ACTIONINFO";
}
