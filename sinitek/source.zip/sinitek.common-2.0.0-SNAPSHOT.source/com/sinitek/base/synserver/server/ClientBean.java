// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientBean.java

package com.sinitek.base.synserver.server;

import java.util.Date;

public class ClientBean
{

    public ClientBean()
    {
        clientIp = "";
    }

    public String getClientIp()
    {
        return clientIp;
    }

    public void setClientIp(String clientIp)
    {
        this.clientIp = clientIp;
        genInfo();
    }

    public int getClientPort()
    {
        return clientPort;
    }

    public void setClientPort(int clientPort)
    {
        this.clientPort = clientPort;
        genInfo();
    }

    public Date getConnectTime()
    {
        return connectTime;
    }

    public void setConnectTime(Date connectTime)
    {
        this.connectTime = connectTime;
    }

    public Date getLastCheckTime()
    {
        return lastCheckTime;
    }

    public void setLastCheckTime(Date lastCheckTime)
    {
        this.lastCheckTime = lastCheckTime;
    }

    public int getNextCheckDelay()
    {
        return nextCheckDelay;
    }

    public synchronized void setNextCheckDelay(int nextCheckDelay)
    {
        this.nextCheckDelay = nextCheckDelay;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof ClientBean)
        {
            ClientBean target = (ClientBean)obj;
            return target.clientIp.equalsIgnoreCase(clientIp) && target.clientPort == clientPort;
        } else
        {
            return false;
        }
    }

    public String getInfo()
    {
        return info;
    }

    private void genInfo()
    {
        info = (new StringBuilder()).append("\u5BA2\u6237\u7AEF[").append(clientIp).append(":").append(clientPort).append("]").toString();
    }

    private String clientIp;
    private int clientPort;
    private Date connectTime;
    private Date lastCheckTime;
    private int nextCheckDelay;
    private String info;
}
