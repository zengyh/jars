// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnhandledException.java

package org.apache.commons.lang;

import org.apache.commons.lang.exception.NestableRuntimeException;

public class UnhandledException extends NestableRuntimeException
{

    public UnhandledException(Throwable cause)
    {
        super(cause);
    }

    public UnhandledException(String message, Throwable cause)
    {
        super(message, cause);
    }

    private static final long serialVersionUID = 0x196cee7c03f63cd8L;
}
