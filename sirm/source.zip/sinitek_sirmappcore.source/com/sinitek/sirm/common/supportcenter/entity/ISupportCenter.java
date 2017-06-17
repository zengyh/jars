// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISupportCenter.java

package com.sinitek.sirm.common.supportcenter.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface ISupportCenter
    extends IMetaObjectImpl
{

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getTel();

    public abstract void setTel(String s);

    public abstract String getRange();

    public abstract void setRange(String s);

    public abstract String getEmail();

    public abstract void setEmail(String s);

    public abstract String getMsn();

    public abstract void setMsn(String s);

    public abstract String getQQ();

    public abstract void setQQ(String s);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public static final String ENTITY_NAME = "SUPPORTCENTER";
}
