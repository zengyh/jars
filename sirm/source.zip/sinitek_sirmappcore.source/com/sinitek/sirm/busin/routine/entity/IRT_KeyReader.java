// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRT_KeyReader.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IRT_KeyReader
    extends IMetaObjectImpl
{

    public abstract String getAuthEntity();

    public abstract void setAuthEntity(String s);

    public abstract String getReply();

    public abstract void setReply(String s);

    public abstract String getReaderId();

    public abstract void setReaderId(String s);

    public abstract Integer getAuthId();

    public abstract void setAuthId(Integer integer);

    public abstract String getOrgid();

    public abstract void setOrgid(String s);

    public static final String ENTITY_NAME = "RTKEYREADER";
}
