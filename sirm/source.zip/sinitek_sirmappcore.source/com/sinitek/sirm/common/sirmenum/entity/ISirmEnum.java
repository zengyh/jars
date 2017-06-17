// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISirmEnum.java

package com.sinitek.sirm.common.sirmenum.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface ISirmEnum
    extends IMetaObjectImpl
{

    public abstract String getType();

    public abstract void setType(String s);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract Integer getValue();

    public abstract void setValue(Integer integer);

    public abstract String getDescription();

    public abstract void setDescription(String s);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract String getCataLog();

    public abstract void setCataLog(String s);

    public abstract String getStrvalue();

    public abstract void setStrvalue(String s);

    public static final String ENTITY_NAME = "SIRMENUM";
}
