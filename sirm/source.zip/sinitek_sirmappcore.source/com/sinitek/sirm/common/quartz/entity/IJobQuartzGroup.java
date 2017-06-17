// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IJobQuartzGroup.java

package com.sinitek.sirm.common.quartz.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IJobQuartzGroup
    extends IMetaObjectImpl
{

    public abstract String getGroupName();

    public abstract void setGroupName(String s);

    public abstract String getMemo();

    public abstract void setMemo(String s);

    public abstract String getCode();

    public abstract void setCode(String s);

    public static final String ENTITY_NAME = "JOBQUARTZGROUP";
}
