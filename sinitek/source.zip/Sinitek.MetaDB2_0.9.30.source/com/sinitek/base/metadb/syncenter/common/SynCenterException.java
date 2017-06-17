// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynCenterException.java

package com.sinitek.base.metadb.syncenter.common;

import com.sinitek.base.common.PropertiesBasedException;

public abstract class SynCenterException extends PropertiesBasedException
{

    public SynCenterException(String errorCode)
    {
        super(errorCode);
    }

    public SynCenterException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public SynCenterException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public SynCenterException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public String getModuleCode()
    {
        return "02";
    }
}
