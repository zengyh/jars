// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEmailLogon.java

package com.sinitek.sirm.common.maillogon.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IEmailLogon
    extends IMetaObjectImpl
{

    public abstract String getUUID();

    public abstract void setUUID(String s);

    public abstract String getUserName();

    public abstract void setUserName(String s);

    public abstract Date getStartDate();

    public abstract void setStartDate(Date date);

    public abstract Integer getUsedTime();

    public abstract void setUsedTime(Integer integer);

    public static final String ENTITY_NAME = "SIRMEMAILLOGON";
}
