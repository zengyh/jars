// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocaleLiteral.java

package com.sinitek.sirm.common.i18n;


public class LocaleLiteral
{

    public LocaleLiteral(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public LocaleLiteral(String key, String value, int level)
    {
        this.key = key;
        this.value = value;
        this.level = level;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    private String key;
    private String value;
    protected int level;
}
