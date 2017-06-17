// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ICalendar.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface ICalendar
    extends IMetaObjectImpl
{

    public abstract Integer getRemindFlag();

    public abstract void setRemindFlag(Integer integer);

    public abstract String getRemindType();

    public abstract void setRemindType(String s);

    public abstract Integer getType();

    public abstract void setType(Integer integer);

    public abstract Integer getRemindMethod();

    public abstract void setRemindMethod(Integer integer);

    public abstract String getRemindValue();

    public abstract void setRemindValue(String s);

    public abstract Date getBeginDate();

    public abstract void setBeginDate(Date date);

    public abstract Date getEndDate();

    public abstract void setEndDate(Date date);

    public abstract Integer getInputId();

    public abstract void setInputId(Integer integer);

    public abstract String getSubject();

    public abstract void setSubject(String s);

    public abstract String getSourceEntity();

    public abstract void setSourceEntity(String s);

    public abstract Integer getSourceId();

    public abstract void setSourceId(Integer integer);

    public abstract Integer getEmpId();

    public abstract void setEmpId(Integer integer);

    public abstract String getContent();

    public abstract void setContent(String s);

    public abstract Integer getColumnType();

    public abstract void setColumnType(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Integer getCompleteStatus();

    public abstract void setCompleteStatus(Integer integer);

    public abstract String getUrl();

    public abstract void setUrl(String s);

    public static final String ENTITY_NAME = "CALENDAR";
}
