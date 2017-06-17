// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISirmTagRela.java

package com.sinitek.sirm.common.taginfo.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface ISirmTagRela
    extends IMetaObjectImpl
{

    public abstract Integer getTagid();

    public abstract void setTagid(Integer integer);

    public abstract String getSourceentity();

    public abstract void setSourceentity(String s);

    public abstract Integer getSourceid();

    public abstract void setSourceid(Integer integer);

    public static final String ENTITY_NAME = "SIRMTAGRELA";
}
