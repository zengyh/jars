// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEventActionProperty.java

package com.sinitek.sirm.common.engine.event.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IEventActionProperty
    extends IMetaObjectImpl
{

    public abstract Integer getEventActionId();

    public abstract void setEventActionId(Integer integer);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getValue();

    public abstract void setValue(String s);

    public static final String ENTITY_NAME = "EVENTACTIONPROPERTY";
}
