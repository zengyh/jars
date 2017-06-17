// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgServiceException.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.spirit.org.core.OrgException;

public class OrgServiceException extends OrgException
{

    public OrgServiceException(String errorCode)
    {
        super(errorCode);
    }

    public OrgServiceException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public OrgServiceException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public OrgServiceException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/spirit/org/server/service/server.errorcode.properties";
    }
}
