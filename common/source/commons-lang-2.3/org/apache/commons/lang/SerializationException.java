// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SerializationException.java

package org.apache.commons.lang;

import org.apache.commons.lang.exception.NestableRuntimeException;

public class SerializationException extends NestableRuntimeException
{

    public SerializationException()
    {
    }

    public SerializationException(String msg)
    {
        super(msg);
    }

    public SerializationException(Throwable cause)
    {
        super(cause);
    }

    public SerializationException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    private static final long serialVersionUID = 0x37e9f9395ae00706L;
}
