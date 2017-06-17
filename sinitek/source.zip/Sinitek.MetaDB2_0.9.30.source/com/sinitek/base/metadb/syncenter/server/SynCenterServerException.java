// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynCenterServerException.java

package com.sinitek.base.metadb.syncenter.server;

import com.sinitek.base.metadb.syncenter.common.SynCenterException;

public final class SynCenterServerException extends SynCenterException
{

    public SynCenterServerException(String errorCode)
    {
        super(errorCode);
    }

    public SynCenterServerException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public SynCenterServerException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public SynCenterServerException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/metadb/syncenter/server/server_errorcode.properties";
    }

    public String getSubModuleCode()
    {
        return "03";
    }
}
