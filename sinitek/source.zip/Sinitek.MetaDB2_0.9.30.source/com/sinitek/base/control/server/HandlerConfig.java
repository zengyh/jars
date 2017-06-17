// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HandlerConfig.java

package com.sinitek.base.control.server;

import java.util.Properties;

public class HandlerConfig
{

    public HandlerConfig()
    {
    }

    public String getHandlerClass()
    {
        return handlerClass;
    }

    public String getLogLevel()
    {
        return logLevel;
    }

    public String getName()
    {
        return name;
    }

    public void setHandlerClass(String handlerClass)
    {
        this.handlerClass = handlerClass;
    }

    public void setLogLevel(String logLevel)
    {
        this.logLevel = logLevel;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Properties getHandlerProperties()
    {
        return handlerProperties;
    }

    public void setHandlerProperties(Properties handlerProperties)
    {
        this.handlerProperties = handlerProperties;
    }

    private String handlerClass;
    private String logLevel;
    private Properties handlerProperties;
    private String name;
}
