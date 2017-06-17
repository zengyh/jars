// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBContextSupportException.java

package com.sinitek.base.metadb.support;

import com.sinitek.base.metadb.MetaDBException;

public class MetaDBContextSupportException extends MetaDBException
{

    public MetaDBContextSupportException(String errorCode)
    {
        super(errorCode);
    }

    public MetaDBContextSupportException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public MetaDBContextSupportException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public MetaDBContextSupportException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public String getSubModuleCode()
    {
        return "03";
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/metadb/support/metadbcontextsupport_errorcode.properties";
    }
}
