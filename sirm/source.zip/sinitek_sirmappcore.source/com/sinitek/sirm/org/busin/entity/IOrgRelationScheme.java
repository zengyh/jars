// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOrgRelationScheme.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IOrgRelationScheme
    extends IMetaObjectImpl
{

    public abstract String getCode();

    public abstract void setCode(String s);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public static final String ENTITY_NAME = "ORGRELATIONSCHEME";
}