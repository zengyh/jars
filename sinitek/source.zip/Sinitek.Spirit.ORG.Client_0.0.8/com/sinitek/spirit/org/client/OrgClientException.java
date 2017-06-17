// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgClientException.java

package com.sinitek.spirit.org.client;

import com.sinitek.spirit.org.core.OrgException;

public class OrgClientException extends OrgException
{

    public OrgClientException(String errorCode)
    {
        super(errorCode);
    }

    public OrgClientException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public OrgClientException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public OrgClientException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public String getPropertieFile()
    {
        return "com/sinitek/spirit/org/client/client.errorcode.properties";
    }
}
