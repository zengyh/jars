// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBConfigException.java

package com.sinitek.base.metadb.config;

import com.sinitek.base.metadb.MetaDBException;

public class MetaDBConfigException extends MetaDBException
{

    public MetaDBConfigException(String errorCode)
    {
        super(errorCode);
    }

    public MetaDBConfigException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public MetaDBConfigException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public MetaDBConfigException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public String getSubModuleCode()
    {
        return "00";
    }
}
