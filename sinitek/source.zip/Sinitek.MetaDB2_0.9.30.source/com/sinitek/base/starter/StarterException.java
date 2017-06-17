// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StarterException.java

package com.sinitek.base.starter;

import com.sinitek.base.common.PropertiesBasedException;

public class StarterException extends PropertiesBasedException
{

    public StarterException(String errorCode)
    {
        super(errorCode);
    }

    public StarterException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public StarterException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public StarterException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/starter/starter_exceptioncode.properties";
    }

    public String getModuleCode()
    {
        return "00";
    }

    public String getSubModuleCode()
    {
        return "04";
    }
}
