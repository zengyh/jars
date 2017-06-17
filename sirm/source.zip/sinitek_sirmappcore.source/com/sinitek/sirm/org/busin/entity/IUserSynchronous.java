// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IUserSynchronous.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IUserSynchronous
    extends IMetaObjectImpl
{

    public abstract String getPassword();

    public abstract void setPassword(String s);

    public abstract String getUserName();

    public abstract void setUserName(String s);

    public abstract String getDeptname();

    public abstract void setDeptname(String s);

    public abstract String getMobile();

    public abstract void setMobile(String s);

    public abstract String getEmail();

    public abstract void setEmail(String s);

    public abstract String getUserId();

    public abstract void setUserId(String s);

    public abstract String getTelephone();

    public abstract void setTelephone(String s);

    public abstract String getRemark();

    public abstract void setRemark(String s);

    public abstract Integer getIssuccess();

    public abstract void setIssuccess(Integer integer);

    public static final String ENTITY_NAME = "USERSYNCHRONOUS";
}
