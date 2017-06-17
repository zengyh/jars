// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEventListener.java

package com.sinitek.sirm.common.engine.event.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IEventListener
    extends IMetaObjectImpl
{

    public abstract Integer getEventInfoId();

    public abstract void setEventInfoId(Integer integer);

    public abstract Integer getActionInfoId();

    public abstract void setActionInfoId(Integer integer);

    public static final String ENTITY_NAME = "EVENTLISTENER";
}
