// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOrgPswHis.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IOrgPswHis
    extends IMetaObjectImpl
{

    public abstract String getPassword();

    public abstract void setPassword(String s);

    public abstract Date getLogDate();

    public abstract void setLogDate(Date date);

    public abstract String getOrgid();

    public abstract void setOrgid(String s);

    public static final String ENTITY_NAME = "ORGPSWHIS";
}
