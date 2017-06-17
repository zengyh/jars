// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HandleCoreException.java

package com.sinitek.base.control;


// Referenced classes of package com.sinitek.base.control:
//            HandleException

public final class HandleCoreException extends HandleException
{

    public HandleCoreException(String s)
    {
        super(s);
    }

    public HandleCoreException(String s, Object objects[])
    {
        super(s, objects);
    }

    public HandleCoreException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public HandleCoreException(String s, Throwable throwable, Object objects[])
    {
        super(s, throwable, objects);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/control/handlecore_errorcode.properties";
    }

    public String getModuleCode()
    {
        return "04";
    }

    public String getSubModuleCode()
    {
        return "00";
    }
}
