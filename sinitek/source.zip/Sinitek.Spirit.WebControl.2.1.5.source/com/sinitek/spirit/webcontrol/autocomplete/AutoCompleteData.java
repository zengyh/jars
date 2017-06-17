// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AutoCompleteData.java

package com.sinitek.spirit.webcontrol.autocomplete;

import java.util.Map;

public class AutoCompleteData
{

    public AutoCompleteData()
    {
    }

    public AutoCompleteData(String id, String text)
    {
        this.id = id;
        this.text = text;
    }

    public AutoCompleteData(String id, String text, String textSub)
    {
        this.id = id;
        this.text = text;
        this.textSub = textSub;
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

    public String getTextSub()
    {
        return textSub;
    }

    public void setTextSub(String textSub)
    {
        this.textSub = textSub;
    }

    public Map getOrgData()
    {
        return orgData;
    }

    public void setOrgData(Map orgData)
    {
        this.orgData = orgData;
    }

    private String id;
    private String text;
    private String textSub;
    private Map orgData;
}
