// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageAttachment.java

package com.sinitek.sirm.busin.routine.utils;


public class MessageAttachment
{

    public MessageAttachment()
    {
        fileName = null;
        displayName = null;
    }

    public MessageAttachment(String fileName, String displayName)
    {
        this.fileName = null;
        this.displayName = null;
        this.fileName = fileName;
        this.displayName = displayName;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    private String fileName;
    private String displayName;
}
