// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IUserScheme.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IUserScheme
    extends IMetaObjectImpl
{

    public abstract Integer getRange();

    public abstract void setRange(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract String getTitle();

    public abstract void setTitle(String s);

    public abstract String getCode();

    public abstract void setCode(String s);

    public abstract String getDepartmentIds();

    public abstract void setDepartmentIds(String s);

    public abstract String getPostIds();

    public abstract void setPostIds(String s);

    public abstract String getRoleIds();

    public abstract void setRoleIds(String s);

    public abstract String getTeamIds();

    public abstract void setTeamIds(String s);

    public static final String ENTITY_NAME = "USERSCHEME";
}
