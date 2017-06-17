// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IUserSchemeRela.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IUserSchemeRela
    extends IMetaObjectImpl
{

    public abstract String getPath();

    public abstract void setPath(String s);

    public abstract Integer getSchemeId();

    public abstract void setSchemeId(Integer integer);

    public static final String ENTITY_NAME = "USERSCHEMERELA";
}
