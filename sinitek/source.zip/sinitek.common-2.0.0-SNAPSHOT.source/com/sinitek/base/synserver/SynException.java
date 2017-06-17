// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynException.java

package com.sinitek.base.synserver;

import com.sinitek.base.common.PropertiesBasedException;

public abstract class SynException extends PropertiesBasedException
{

    public SynException(String errorCode)
    {
        super(errorCode);
    }

    public SynException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public SynException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public SynException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/synserver/synserver_exceptioncode.properties";
    }

    public String getModuleCode()
    {
        return "02";
    }
}
