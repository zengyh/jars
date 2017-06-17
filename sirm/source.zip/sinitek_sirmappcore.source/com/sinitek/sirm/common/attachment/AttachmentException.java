// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttachmentException.java

package com.sinitek.sirm.common.attachment;


public class AttachmentException extends RuntimeException
{

    public AttachmentException(String msg, String code)
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
    public static final String IOERROR = "01";
    public static final String DIRNOTEXIST = "0101";
}
