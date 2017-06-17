// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBCacheException.java

package com.sinitek.base.metadb.cache;

import com.sinitek.base.metadb.MetaDBException;

public class MetaDBCacheException extends MetaDBException
{

    public MetaDBCacheException(String errorCode)
    {
        super(errorCode);
    }

    public MetaDBCacheException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public MetaDBCacheException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public MetaDBCacheException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public String getSubModuleCode()
    {
        return "02";
    }
}
