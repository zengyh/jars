// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRTHolidays.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IRTHolidays
    extends IMetaObjectImpl
{

    public abstract String getSender();

    public abstract void setSender(String s);

    public abstract Integer getYear();

    public abstract void setYear(Integer integer);

    public abstract Integer getDay();

    public abstract void setDay(Integer integer);

    public abstract Integer getType();

    public abstract void setType(Integer integer);

    public abstract String getRemark();

    public abstract void setRemark(String s);

    public abstract String getHolidaysTime();

    public abstract void setHolidaysTime(String s);

    public static final String ENTITY_NAME = "RTHOLIDAYS";
}
