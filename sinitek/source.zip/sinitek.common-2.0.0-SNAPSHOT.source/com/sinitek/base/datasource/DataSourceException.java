// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSourceException.java

package com.sinitek.base.datasource;

import com.sinitek.base.common.PropertiesBasedException;

public class DataSourceException extends PropertiesBasedException
{

    public DataSourceException(String errorCode)
    {
        super(errorCode);
    }

    public DataSourceException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public DataSourceException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public DataSourceException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public String getModuleCode()
    {
        return "03";
    }

    public String getSubModuleCode()
    {
        return "00";
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/datasource/datasource_exceptioncode.properties";
    }
}
