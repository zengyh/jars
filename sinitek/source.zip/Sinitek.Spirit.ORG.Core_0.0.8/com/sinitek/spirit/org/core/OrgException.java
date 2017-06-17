// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgException.java

package com.sinitek.spirit.org.core;

import com.sinitek.base.common.PropertiesBasedException;

public abstract class OrgException extends PropertiesBasedException
{

    public OrgException(String errorCode)
    {
        super(errorCode);
    }

    public OrgException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public OrgException(String errorCode, Object errorMsgParams[])
    {
        super(errorCode, errorMsgParams);
    }

    public OrgException(String errorCode, Throwable cause, Object errorMsgParams[])
    {
        super(errorCode, cause, errorMsgParams);
    }

    public final String getModuleCode()
    {
        return "SP";
    }

    public final String getSubModuleCode()
    {
        return "OG";
    }
}
