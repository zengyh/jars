// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmAppException.java

package com.sinitek.sirm.common.exception;


public class SirmAppException extends RuntimeException
{

    public SirmAppException(String msg, String code)
    {
        super(msg);
        this.code = null;
        params = null;
        this.code = code;
    }

    public SirmAppException(String code)
    {
        this.code = null;
        params = null;
        this.code = code;
    }

    public SirmAppException(String code, String params[])
    {
        this.code = null;
        this.params = null;
        this.code = code;
        this.params = params;
    }

    public SirmAppException()
    {
        code = null;
        params = null;
    }

    public String toString()
    {
        return (new StringBuilder()).append("[").append(code).append("]").append(super.toString()).toString();
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String[] getParams()
    {
        return params;
    }

    public void setParams(String params[])
    {
        this.params = params;
    }

    public static final String IO = "0001";
    public static final String MAIL = "0002";
    public static final String ENCODING = "0003";
    public static final String NEWINSTANCE = "0004";
    private String code;
    private String params[];
}
