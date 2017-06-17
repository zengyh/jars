// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOrgTeamIndustryRela.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IOrgTeamIndustryRela
    extends IMetaObjectImpl
{

    public abstract String getTeamId();

    public abstract void setTeamId(String s);

    public abstract String getIndustryCode();

    public abstract void setIndustryCode(String s);

    public static final String ENTITY_NAME = "ORGTEAMINDUSTRYRELA";
}
