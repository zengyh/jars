// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynClientException.java

package com.sinitek.base.synserver.client;

import com.sinitek.base.synserver.SynException;

public class SynClientException extends SynException
{

    public SynClientException(String errorCode)
    {
        super(errorCode);
    }

    public SynClientException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public SynClientException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public SynClientException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public String getSubModuleCode()
    {
        return "02";
    }
}
