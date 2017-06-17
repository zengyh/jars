// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceRuntimeException.java

package com.sinitek.ds.core.service;


// Referenced classes of package com.sinitek.ds.core.service:
//            ServiceException

public class ServiceRuntimeException extends ServiceException
{

    public ServiceRuntimeException(String s)
    {
        super(s);
    }

    public ServiceRuntimeException(String s, Object objects[])
    {
        super(s, objects);
    }

    public ServiceRuntimeException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public ServiceRuntimeException(String s, Throwable throwable, Object objects[])
    {
        super(s, throwable, objects);
    }

    public String getSubModuleCode()
    {
        return "01";
    }
}
