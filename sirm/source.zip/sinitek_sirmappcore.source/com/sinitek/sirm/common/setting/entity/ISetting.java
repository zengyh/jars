// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISetting.java

package com.sinitek.sirm.common.setting.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface ISetting
    extends IMetaObjectImpl
{

    public abstract String getModule();

    public abstract void setModule(String s);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getValue();

    public abstract void setValue(String s);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public abstract Integer getEncryptionFlag();

    public abstract void setEncryptionFlag(Integer integer);

    public static final String ENTITY_NAME = "SETTING";
}
