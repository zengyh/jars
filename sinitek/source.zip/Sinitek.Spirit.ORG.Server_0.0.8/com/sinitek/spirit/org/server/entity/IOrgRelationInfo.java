// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOrgRelationInfo.java

package com.sinitek.spirit.org.server.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IOrgRelationInfo
    extends IMetaObjectImpl
{

    public abstract String getFromObjectId();

    public abstract void setFromObjectId(String s);

    public abstract String getToObjectId();

    public abstract void setToObjectId(String s);

    public abstract String getRelationType();

    public abstract void setRelationType(String s);

    public abstract Integer getOrderCode();

    public abstract void setOrderCode(Integer integer);

    public static final String ENTITY_NAME = "ORGRELATIONINFO";
}
