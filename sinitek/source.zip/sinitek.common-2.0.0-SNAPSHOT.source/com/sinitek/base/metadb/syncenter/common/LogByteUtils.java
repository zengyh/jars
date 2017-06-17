// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LogByteUtils.java

package com.sinitek.base.metadb.syncenter.common;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LogByteUtils
{

    public LogByteUtils()
    {
    }

    public static void logByte(Logger logger, byte bytesToLog[], Level logLevel)
    {
        if(logLevel.isGreaterOrEqual(logger.getEffectiveLevel()) && bytesToLog != null && bytesToLog.length > 0)
        {
            String logStr = convertBytesToStr(bytesToLog);
            if(logStr == null)
                if(logger.isTraceEnabled())
                    logStr = logIntoFile(bytesToLog);
                else
                    logStr = "\u6570\u636E\u65E0\u6CD5\u8F6C\u6362\u4E3A\u53EF\u89C1\u5B57\u7B26";
            logger.log(com/sinitek/base/metadb/syncenter/common/LogByteUtils.getName(), Level.DEBUG, logStr, null);
        }
    }

    private static String convertBytesToStr(byte bytesToLog[])
    {
        try
        {
            return new String(bytesToLog);
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    private static String logIntoFile(byte bytesToLog[])
    {
        return "";
    }
}
