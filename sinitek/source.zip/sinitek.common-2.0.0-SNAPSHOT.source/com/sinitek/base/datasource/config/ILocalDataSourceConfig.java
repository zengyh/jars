// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ILocalDataSourceConfig.java

package com.sinitek.base.datasource.config;


public interface ILocalDataSourceConfig
{

    public abstract String getDataSourceName();

    public abstract String getProvider();

    public abstract String getDriverClassName();

    public abstract String getUrl();

    public abstract String getUserName();

    public abstract String getPassword();

    public abstract boolean isNeedBind();
}
