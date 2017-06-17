// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StarterConfigInfo.java

package com.sinitek.base.starter.config;

import java.util.Properties;

public class StarterConfigInfo
{

    public StarterConfigInfo()
    {
    }

    public boolean isIgnoreOnError()
    {
        return ignoreOnError;
    }

    public void setIgnoreOnError(boolean ignoreOnError)
    {
        this.ignoreOnError = ignoreOnError;
    }

    public Properties getProps()
    {
        return props;
    }

    public void setProps(Properties props)
    {
        this.props = props;
    }

    public String getStarterClass()
    {
        return starterClass;
    }

    public void setStarterClass(String starterClass)
    {
        this.starterClass = starterClass;
    }

    private String starterClass;
    private boolean ignoreOnError;
    private Properties props;
}
