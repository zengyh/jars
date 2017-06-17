// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceWebServiceClientException.java

package com.sinitek.ds.core.service.impl.webservice;

import com.sinitek.ds.core.service.ServiceException;

public class ServiceWebServiceClientException extends ServiceException
{

    public ServiceWebServiceClientException(String s)
    {
        super(s);
    }

    public ServiceWebServiceClientException(String s, Object objects[])
    {
        super(s, objects);
    }

    public ServiceWebServiceClientException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public ServiceWebServiceClientException(String s, Throwable throwable, Object objects[])
    {
        super(s, throwable, objects);
    }

    public String getSubModuleCode()
    {
        return "03";
    }
}
