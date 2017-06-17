// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBInstanceInfo.java

package com.sinitek.base.metadb.syncenter.common;

import java.util.Date;

public class MetaDBInstanceInfo
{

    public MetaDBInstanceInfo()
    {
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getInstanceIpAddress()
    {
        return instanceIpAddress;
    }

    public void setInstanceIpAddress(String instanceIpAddress)
    {
        this.instanceIpAddress = instanceIpAddress;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    private String key;
    private String appName;
    private String instanceIpAddress;
    private int port;
    private Date lastUpdateTime;
}
