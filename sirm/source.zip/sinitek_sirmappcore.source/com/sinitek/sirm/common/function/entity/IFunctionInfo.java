// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IFunctionInfo.java

package com.sinitek.sirm.common.function.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IFunctionInfo
    extends IMetaObjectImpl
{

    public abstract String getCode();

    public abstract void setCode(String s);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract String getUrl();

    public abstract void setUrl(String s);

    public abstract String getAction();

    public abstract void setAction(String s);

    public abstract String getMethod();

    public abstract void setMethod(String s);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public abstract Integer getGroupId();

    public abstract void setGroupId(Integer integer);

    public abstract Integer getType();

    public abstract void setType(Integer integer);

    public static final String ENTITY_NAME = "FUNCTIONINFO";
}
