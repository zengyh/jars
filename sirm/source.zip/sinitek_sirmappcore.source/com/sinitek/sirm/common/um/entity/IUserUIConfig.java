// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IUserUIConfig.java

package com.sinitek.sirm.common.um.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IUserUIConfig
    extends IMetaObjectImpl
{

    public abstract String getOrgId();

    public abstract void setOrgId(String s);

    public abstract String getCatalog();

    public abstract void setCatalog(String s);

    public abstract String getActionClass();

    public abstract void setActionClass(String s);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getValue();

    public abstract void setValue(String s);

    public static final String ENTITY_NAME = "USERUICONFIG";
}
