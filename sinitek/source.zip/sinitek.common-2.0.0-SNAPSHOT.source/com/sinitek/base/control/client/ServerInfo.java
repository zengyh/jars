// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerInfo.java

package com.sinitek.base.control.client;


public class ServerInfo
{

    public ServerInfo()
    {
    }

    public String getServerAddress()
    {
        return serverAddress;
    }

    public int getServerPort()
    {
        return serverPort;
    }

    public void setServerAddress(String serverAddress)
    {
        this.serverAddress = serverAddress;
    }

    public void setServerPort(int serverPort)
    {
        this.serverPort = serverPort;
    }

    private String serverAddress;
    private int serverPort;
}
