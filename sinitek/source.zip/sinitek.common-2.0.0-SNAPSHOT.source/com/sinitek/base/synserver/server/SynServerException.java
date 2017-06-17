// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynServerException.java

package com.sinitek.base.synserver.server;

import com.sinitek.base.synserver.SynException;

public class SynServerException extends SynException
{

    public SynServerException(String errorCode)
    {
        super(errorCode);
    }

    public SynServerException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public SynServerException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public SynServerException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public String getSubModuleCode()
    {
        return "01";
    }
}
