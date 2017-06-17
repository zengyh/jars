// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISearchFilter.java

package com.sinitek.sirm.common.searchfilter.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface ISearchFilter
    extends IMetaObjectImpl
{

    public abstract String getName();

    public abstract void setName(String s);

    public abstract Integer getEmpId();

    public abstract void setEmpId(Integer integer);

    public abstract String getCatalog();

    public abstract void setCatalog(String s);

    public static final String ENTITY_NAME = "SEARCHFILTER";
}
