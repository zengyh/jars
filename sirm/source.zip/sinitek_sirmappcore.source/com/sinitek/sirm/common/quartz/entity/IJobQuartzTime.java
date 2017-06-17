// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IJobQuartzTime.java

package com.sinitek.sirm.common.quartz.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IJobQuartzTime
    extends IMetaObjectImpl
{

    public abstract String getTimeExpression();

    public abstract void setTimeExpression(String s);

    public abstract String getTimeName();

    public abstract void setTimeName(String s);

    public static final String ENTITY_NAME = "JOBQUARTZTIME";
}
