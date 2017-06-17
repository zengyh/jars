// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOrgProperty.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IOrgProperty
    extends IMetaObjectImpl
{

    public abstract Boolean getF1();

    public abstract void setF1(Boolean boolean1);

    public abstract String getOrgId();

    public abstract void setOrgId(String s);

    public static final String ENTITY_NAME = "ORGPROPERTY";
}
