// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISearchFilterCondition.java

package com.sinitek.sirm.common.searchfilter.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface ISearchFilterCondition
    extends IMetaObjectImpl
{

    public abstract Integer getFilterId();

    public abstract void setFilterId(Integer integer);

    public abstract String getFieldName();

    public abstract void setFieldName(String s);

    public abstract String getSearchType();

    public abstract void setSearchType(String s);

    public abstract String getCondition();

    public abstract void setCondition(String s);

    public static final String ENTITY_NAME = "SEARCHFILTERCONDITION";
}
