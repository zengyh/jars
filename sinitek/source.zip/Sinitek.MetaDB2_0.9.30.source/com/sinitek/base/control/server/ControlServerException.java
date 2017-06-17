// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ControlServerException.java

package com.sinitek.base.control.server;

import com.sinitek.base.common.PropertiesBasedException;

public class ControlServerException extends PropertiesBasedException
{

    public ControlServerException(String s)
    {
        super(s);
    }

    public ControlServerException(String s, Object objects[])
    {
        super(s, objects);
    }

    public ControlServerException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public ControlServerException(String s, Throwable throwable, Object objects[])
    {
        super(s, throwable, objects);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/control/server/controlserver_errorcode.properties";
    }

    public String getModuleCode()
    {
        return "04";
    }

    public String getSubModuleCode()
    {
        return "01";
    }
}
