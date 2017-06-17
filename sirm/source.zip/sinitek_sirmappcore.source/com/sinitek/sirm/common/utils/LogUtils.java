// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LogUtils.java

package com.sinitek.sirm.common.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * @deprecated Class LogUtils is deprecated
 */

public class LogUtils
{

    public LogUtils()
    {
    }

    public static void debug(Class clazz, String message)
    {
        getLog(clazz.getName()).debug(formatMsg(clazz, message));
    }

    public static void debug(Class clazz, Throwable ex)
    {
        getLog(clazz.getName()).debug(formatMsg(clazz, ex), ex);
    }

    public static void debug(Class clazz, String msg, Throwable ex)
    {
        getLog(clazz.getName()).debug(formatMsg(clazz, msg), ex);
    }

    public static void info(Class clazz, String message)
    {
        getLog(clazz.getName()).info(formatMsg(clazz, message));
    }

    public static void info(Class clazz, Throwable ex)
    {
        getLog(clazz.getName()).info(formatMsg(clazz, ex), ex);
    }

    public static void info(Class clazz, String msg, Throwable ex)
    {
        getLog(clazz.getName()).info(formatMsg(clazz, msg), ex);
    }

    public static void warn(Class clazz, String message)
    {
        getLog(clazz.getName()).warn(formatMsg(clazz, message));
    }

    public static void warn(Class clazz, Throwable ex)
    {
        getLog(clazz.getName()).warn(formatMsg(clazz, ex), ex);
    }

    public static void warn(Class clazz, String msg, Throwable ex)
    {
        getLog(clazz.getName()).warn(formatMsg(clazz, msg), ex);
    }

    public static void error(Class clazz, String message)
    {
        getLog(clazz.getName()).error(formatMsg(clazz, message));
    }

    public static void error(Class clazz, Throwable ex)
    {
        getLog(clazz.getName()).error(formatMsg(clazz, ex), ex);
    }

    public static void error(Class clazz, String msg, Throwable ex)
    {
        getLog(clazz.getName()).error(formatMsg(clazz, msg), ex);
    }

    private static Logger getLog(String name)
    {
        Logger log = (Logger)logMap.get(name);
        if(log == null)
            synchronized(lock)
            {
                if(log == null)
                {
                    log = Logger.getLogger(name);
                    logMap.put(name, log);
                }
            }
        return log;
    }

    public static String formatMsg(Class clazz, String message)
    {
        return (new StringBuilder()).append("[").append(clazz.getName()).append("]").append(message).toString();
    }

    public static String formatMsg(Class clazz, Throwable ex)
    {
        return (new StringBuilder()).append("[").append(clazz.getName()).append("]").append(ex.toString()).toString();
    }

    private static final Object lock = new Object();
    private static Map logMap = new HashMap();

}
