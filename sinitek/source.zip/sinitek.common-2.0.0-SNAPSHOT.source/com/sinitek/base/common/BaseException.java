// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseException.java

package com.sinitek.base.common;


public abstract class BaseException extends RuntimeException
{

    public BaseException(String errorCode, Throwable cause)
    {
        super(cause);
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public abstract String getErrorMsg();

    public String getErrorCode()
    {
        return errorCode;
    }

    public abstract String getModuleCode();

    public abstract String getSubModuleCode();

    public String getCode()
    {
        return (new StringBuilder()).append(getModuleCode()).append(getSubModuleCode()).append(getErrorCode()).toString();
    }

    public final String toString()
    {
        return (new StringBuilder()).append("[").append(getCode()).append("]-").append(getErrorMsg()).toString();
    }

    protected String errorCode;
}
