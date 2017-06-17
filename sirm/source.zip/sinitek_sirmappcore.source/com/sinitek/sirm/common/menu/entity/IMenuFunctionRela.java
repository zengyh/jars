// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMenuFunctionRela.java

package com.sinitek.sirm.common.menu.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IMenuFunctionRela
    extends IMetaObjectImpl
{

    public abstract Integer getFunctionId();

    public abstract void setFunctionId(Integer integer);

    public abstract Integer getMenuId();

    public abstract void setMenuId(Integer integer);

    public static final String ENTITY_NAME = "MENUFUNCTIONRELA";
}
