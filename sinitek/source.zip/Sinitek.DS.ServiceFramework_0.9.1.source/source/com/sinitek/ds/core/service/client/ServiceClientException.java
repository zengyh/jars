// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceClientException.java

package com.sinitek.ds.core.service.client;

import com.sinitek.ds.core.service.ServiceException;

public class ServiceClientException extends ServiceException
{

    public ServiceClientException(String s)
    {
        super(s);
    }

    public ServiceClientException(String s, Object objects[])
    {
        super(s, objects);
    }

    public ServiceClientException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public ServiceClientException(String s, Throwable throwable, Object objects[])
    {
        super(s, throwable, objects);
    }

    public String getSubModuleCode()
    {
        return "02";
    }
}
