// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceException.java

package com.sinitek.ds.core.service;

import com.sinitek.base.common.PropertiesBasedException;

public abstract class ServiceException extends PropertiesBasedException
{

    public String getPropertieFile()
    {
        return "com/sinitek/ds/core/service/service_errorcode.properties";
    }

    public String getModuleCode()
    {
        return "69";
    }

    public ServiceException(String s)
    {
        super(s);
    }

    public ServiceException(String s, Object objects[])
    {
        super(s, objects);
    }

    public ServiceException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public ServiceException(String s, Throwable throwable, Object objects[])
    {
        super(s, throwable, objects);
    }
}
