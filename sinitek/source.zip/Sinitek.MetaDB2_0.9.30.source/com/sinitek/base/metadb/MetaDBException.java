// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBException.java

package com.sinitek.base.metadb;

import com.sinitek.base.common.PropertiesBasedException;

public abstract class MetaDBException extends PropertiesBasedException
{

    public MetaDBException(String errorCode)
    {
        super(errorCode);
    }

    public MetaDBException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public MetaDBException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public MetaDBException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/metadb/metadb_exceptioncode.properties";
    }

    public String getModuleCode()
    {
        return "01";
    }
}
