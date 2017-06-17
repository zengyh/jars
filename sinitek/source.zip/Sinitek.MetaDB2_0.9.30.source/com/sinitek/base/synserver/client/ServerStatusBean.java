// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerStatusBean.java

package com.sinitek.base.synserver.client;

import java.util.Date;
import java.util.List;

public class ServerStatusBean
{

    public ServerStatusBean()
    {
    }

    public Date getCheckDate()
    {
        return checkDate;
    }

    public void setCheckDate(Date checkDate)
    {
        this.checkDate = checkDate;
    }

    public List getClients()
    {
        return clients;
    }

    public void setClients(List clients)
    {
        this.clients = clients;
    }

    private Date checkDate;
    private List clients;
}
