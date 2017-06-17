// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IFunctionGroup.java

package com.sinitek.sirm.common.function.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IFunctionGroup
    extends IMetaObjectImpl
{

    public abstract Integer getParentId();

    public abstract void setParentId(Integer integer);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public abstract String getName();

    public abstract void setName(String s);

    public static final String ENTITY_NAME = "FUNCTIONGROUP";
}
