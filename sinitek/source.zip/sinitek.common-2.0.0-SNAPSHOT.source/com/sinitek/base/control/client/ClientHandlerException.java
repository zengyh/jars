// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientHandlerException.java

package com.sinitek.base.control.client;

import com.sinitek.base.control.HandleException;

public class ClientHandlerException extends HandleException
{

    public ClientHandlerException(String s)
    {
        super(s);
    }

    public ClientHandlerException(String s, Object objects[])
    {
        super(s, objects);
    }

    public ClientHandlerException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public ClientHandlerException(String s, Throwable throwable, Object objects[])
    {
        super(s, throwable, objects);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/control/client/controlclient_errorcode.properties";
    }

    public String getModuleCode()
    {
        return "04";
    }

    public String getSubModuleCode()
    {
        return "02";
    }
}
