// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MethodInvocationStarter.java

package com.sinitek.base.starter.helper;

import com.sinitek.base.starter.IStarter;
import java.lang.reflect.Method;
import java.util.Properties;
import org.apache.log4j.Logger;

public class MethodInvocationStarter
    implements IStarter
{

    public MethodInvocationStarter()
    {
    }

    public void start(Properties prop)
        throws Exception
    {
        String className = prop.getProperty("class");
        String method = prop.getProperty("method");
        LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u8C03\u7528[").append(className).append("].[").append(method).append("]\u65B9\u6CD5").toString());
        Class.forName(className).getMethod(method, new Class[0]).invoke(null, new Object[0]);
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/starter/helper/MethodInvocationStarter);

}
