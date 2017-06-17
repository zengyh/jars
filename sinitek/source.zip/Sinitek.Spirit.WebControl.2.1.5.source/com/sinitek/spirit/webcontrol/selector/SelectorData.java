// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SelectorData.java

package com.sinitek.spirit.webcontrol.selector;


public class SelectorData
{

    public SelectorData()
    {
    }

    public SelectorData(String id, String text)
    {
        this.id = id;
        this.text = text;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    private String id;
    private String text;
}
