// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocalDataSourceConfig.java

package com.sinitek.base.datasource.config.impl;

import com.sinitek.base.datasource.config.ILocalDataSourceConfig;

public class LocalDataSourceConfig
    implements ILocalDataSourceConfig
{

    public LocalDataSourceConfig()
    {
    }

    public String getDataSourceName()
    {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName)
    {
        this.dataSourceName = dataSourceName;
    }

    public String getDriverClassName()
    {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName)
    {
        this.driverClassName = driverClassName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getProvider()
    {
        return provider;
    }

    public void setProvider(String provider)
    {
        this.provider = provider;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public boolean isNeedBind()
    {
        return needBind;
    }

    public void setNeedBind(boolean needBind)
    {
        this.needBind = needBind;
    }

    private String dataSourceName;
    private String provider;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
    private boolean needBind;
}
