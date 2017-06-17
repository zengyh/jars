// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynCenterClientException.java

package com.sinitek.base.metadb.syncenter.client;

import com.sinitek.base.metadb.syncenter.common.SynCenterException;

public class SynCenterClientException extends SynCenterException
{

    public SynCenterClientException(String errorCode)
    {
        super(errorCode);
    }

    public SynCenterClientException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public SynCenterClientException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public SynCenterClientException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/base/metadb/syncenter/client/client_errorcode.properties";
    }

    public String getSubModuleCode()
    {
        return "04";
    }
}
