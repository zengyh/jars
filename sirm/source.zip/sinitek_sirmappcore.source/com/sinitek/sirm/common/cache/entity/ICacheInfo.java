// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ICacheInfo.java

package com.sinitek.sirm.common.cache.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface ICacheInfo
    extends IMetaObjectImpl
{

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getEntityNames();

    public abstract void setEntityNames(String s);

    public abstract String getLocation();

    public abstract void setLocation(String s);

    public static final String ENTITY_NAME = "CACHEINFO";
}
