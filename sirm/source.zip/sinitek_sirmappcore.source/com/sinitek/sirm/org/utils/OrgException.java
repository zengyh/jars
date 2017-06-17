// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgException.java

package com.sinitek.sirm.org.utils;


public class OrgException extends RuntimeException
{

    public OrgException(String msg, String code)
    {
        super(msg);
        this.code = null;
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    private String code;
    public static final String NoSuchUserException = "0102";
}
