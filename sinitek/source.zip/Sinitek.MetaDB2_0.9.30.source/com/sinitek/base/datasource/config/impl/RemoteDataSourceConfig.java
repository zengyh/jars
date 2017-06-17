// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemoteDataSourceConfig.java

package com.sinitek.base.datasource.config.impl;

import com.sinitek.base.datasource.config.IRemoteDataSourceConfig;
import java.util.Map;

public class RemoteDataSourceConfig
    implements IRemoteDataSourceConfig
{

    public RemoteDataSourceConfig()
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

    public String getJndi()
    {
        return jndi;
    }

    public void setJndi(String jndi)
    {
        this.jndi = jndi;
    }

    public Map getJndiProp()
    {
        return jndiProp;
    }

    public void setJndiProp(Map jndiProp)
    {
        this.jndiProp = jndiProp;
    }

    public boolean isNeedBind()
    {
        return needBind;
    }

    public void setNeedBind(boolean needBind)
    {
        this.needBind = needBind;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String dataSourceName;
    public String jndi;
    public Map jndiProp;
    private boolean needBind;
    private String userName;
    private String password;
}
