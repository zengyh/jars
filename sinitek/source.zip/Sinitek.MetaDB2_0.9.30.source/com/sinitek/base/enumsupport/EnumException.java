// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnumException.java

package com.sinitek.base.enumsupport;

import com.sinitek.base.common.PropertiesBasedException;

public class EnumException extends PropertiesBasedException
{

    public EnumException(String errorCode)
    {
        super(errorCode);
    }

    public EnumException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public EnumException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public EnumException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/enumsupport/enum_exceptioncode.properties";
    }

    public String getModuleCode()
    {
        return "00";
    }

    public String getSubModuleCode()
    {
        return "02";
    }
}
