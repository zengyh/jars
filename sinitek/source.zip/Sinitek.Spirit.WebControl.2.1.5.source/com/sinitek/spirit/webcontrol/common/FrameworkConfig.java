// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FrameworkConfig.java

package com.sinitek.spirit.webcontrol.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import org.apache.log4j.Logger;

public class FrameworkConfig
{

    private FrameworkConfig()
    {
        config = new HashMap();
        init();
    }

    public static synchronized FrameworkConfig getInstance()
    {
        if(instance == null)
            instance = new FrameworkConfig();
        return instance;
    }

    void init()
    {
        InputStream inputFile;
        Properties propertie;
        inputFile = null;
        propertie = new Properties();
        config = new HashMap();
        try
        {
            URL configUrl = Thread.currentThread().getContextClassLoader().getResource("spirit-webframework.properties");
            inputFile = configUrl.openStream();
            propertie.load(inputFile);
            String key;
            for(Enumeration em = propertie.propertyNames(); em.hasMoreElements(); config.put(key, propertie.getProperty(key)))
                key = (String)em.nextElement();

        }
        catch(Exception e)
        {
            LOGGER.error("\u521D\u59CB\u5316spirit-webframework.properties\u5931\u8D25", e);
            throw new RuntimeException("\u521D\u59CB\u5316spirit-webframework.properties\u5931\u8D25", e);
        }
        try
        {
            inputFile.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        break MISSING_BLOCK_LABEL_147;
        Exception exception;
        exception;
        try
        {
            inputFile.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        throw exception;
    }

    public String getParam(String s)
    {
        return (String)config.get(s);
    }

    public String getTheme()
    {
        return (String)config.get("default-theme");
    }

    public Map getConfig()
    {
        return config;
    }

    public void setConfig(Map config)
    {
        this.config = config;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/spirit/webcontrol/common/FrameworkConfig);
    private static FrameworkConfig instance;
    private Map config;

}
