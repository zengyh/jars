// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceConfigException.java

package com.sinitek.ds.core.service.config;

import com.sinitek.ds.core.service.ServiceException;

public class ServiceConfigException extends ServiceException
{

    public ServiceConfigException(String s)
    {
        super(s);
    }

    public ServiceConfigException(String s, Object objects[])
    {
        super(s, objects);
    }

    public ServiceConfigException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public ServiceConfigException(String s, Throwable throwable, Object objects[])
    {
        super(s, throwable, objects);
    }

    public String getSubModuleCode()
    {
        return "00";
    }
}
