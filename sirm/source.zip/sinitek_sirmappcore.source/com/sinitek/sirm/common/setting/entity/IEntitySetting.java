// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEntitySetting.java

package com.sinitek.sirm.common.setting.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IEntitySetting
    extends IMetaObjectImpl
{

    public abstract String getSourceentity();

    public abstract void setSourceentity(String s);

    public abstract Integer getSourceid();

    public abstract void setSourceid(Integer integer);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getValue();

    public abstract void setValue(String s);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public static final String ENTITY_NAME = "ENTITYSETTING";
}
