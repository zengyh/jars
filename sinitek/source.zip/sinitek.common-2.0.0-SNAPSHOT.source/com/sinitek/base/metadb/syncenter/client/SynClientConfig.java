// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynClientConfig.java

package com.sinitek.base.metadb.syncenter.client;

import com.sinitek.base.metadb.syncenter.common.DBConnectionFactory;
import java.net.InetSocketAddress;

public class SynClientConfig
{

    public SynClientConfig()
    {
    }

    public DBConnectionFactory getConnectionFactory()
    {
        return connectionFactory;
    }

    public void setConnectionFactory(DBConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
    }

    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public int getListernPort()
    {
        return listernPort;
    }

    public void setListernPort(int listernPort)
    {
        this.listernPort = listernPort;
    }

    public InetSocketAddress[] getServerAddress()
    {
        return serverAddress;
    }

    public void setServerAddress(InetSocketAddress serverAddress[])
    {
        this.serverAddress = serverAddress;
    }

    public String getAppKey()
    {
        return appKey;
    }

    public void setAppKey(String appKey)
    {
        this.appKey = appKey;
    }

    public String getLocalAddress()
    {
        return localAddress;
    }

    public void setLocalAddress(String localAddress)
    {
        this.localAddress = localAddress;
    }

    private DBConnectionFactory connectionFactory;
    private String appName;
    private String appKey;
    private int listernPort;
    private String localAddress;
    private InetSocketAddress serverAddress[];
}
