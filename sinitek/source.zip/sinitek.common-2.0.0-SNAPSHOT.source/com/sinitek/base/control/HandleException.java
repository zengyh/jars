// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HandleException.java

package com.sinitek.base.control;

import com.sinitek.base.common.PropertiesBasedException;

public abstract class HandleException extends PropertiesBasedException
{

    public HandleException(String s)
    {
        super(s);
    }

    public HandleException(String s, Object objects[])
    {
        super(s, objects);
    }

    public HandleException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public HandleException(String s, Throwable throwable, Object objects[])
    {
        super(s, throwable, objects);
    }
}
