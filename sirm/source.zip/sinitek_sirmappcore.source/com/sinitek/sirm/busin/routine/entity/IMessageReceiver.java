// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMessageReceiver.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IMessageReceiver
    extends IMetaObjectImpl
{

    public abstract Integer getTemplateId();

    public abstract void setTemplateId(Integer integer);

    public abstract String getOrgId();

    public abstract void setOrgId(String s);

    public abstract Integer getOrgType();

    public abstract void setOrgType(Integer integer);

    public abstract Integer getUserType();

    public abstract void setUserType(Integer integer);

    public static final String ENTITY_NAME = "MESSAGERECEIVER";
}
