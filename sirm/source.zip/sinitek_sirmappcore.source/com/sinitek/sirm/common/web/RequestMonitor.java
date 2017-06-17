// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RequestMonitor.java

package com.sinitek.sirm.common.web;

import com.sinitek.base.starter.IStarter;
import com.sinitek.sirm.common.utils.DateUtil;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.*;
import org.apache.log4j.Logger;

public class RequestMonitor
    implements IStarter, Runnable
{

    public RequestMonitor()
    {
        timeout = 0;
        enabled = false;
    }

    public static void putRequest(Map info)
    {
        Thread thread = Thread.currentThread();
        if(requests.get(thread) == null)
            synchronized(lock)
            {
                if(requests.get(thread) == null)
                    requests.put(thread, info);
            }
    }

    public static void removeRequest()
    {
        Thread thread = Thread.currentThread();
        if(requests.containsKey(thread))
            synchronized(lock)
            {
                if(requests.containsKey(thread))
                    requests.remove(thread);
            }
    }

    public static void killRequest(long rid)
    {
        synchronized(lock)
        {
            Iterator i$ = requests.keySet().iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Thread t = (Thread)i$.next();
                if(t == null || t.getId() != rid)
                    continue;
                try
                {
                    t.interrupt();
                    LOGGER.error((new StringBuilder()).append("interrupt request thread[").append(rid).append("] OK.").toString());
                    requests.remove(t);
                    break;
                }
                catch(Exception ex)
                {
                    LOGGER.error((new StringBuilder()).append("interrupt request thread[").append(rid).append("] failed.").toString(), ex);
                }
            } while(true);
        }
    }

    public static Map getRequests()
    {
        Thread thread = Thread.currentThread();
        Map result = new LinkedHashMap();
        synchronized(lock)
        {
            Iterator i$ = result.keySet().iterator();
            do
            {
                if(!i$.hasNext())
                    break;
                Thread t = (Thread)i$.next();
                if(t != null && t.getId() != thread.getId())
                    result.put(t, result.get(t));
            } while(true);
        }
        return result;
    }

    public void start(Properties properties)
        throws Exception
    {
        timeout = NumberTool.safeToInteger(properties.getProperty("timeout"), Integer.valueOf(0)).intValue();
        if(timeout > 10)
        {
            Thread t = new Thread(this);
            t.setDaemon(true);
            t.start();
        }
        try
        {
            enabled = (new Boolean(properties.getProperty("enabled"))).booleanValue();
        }
        catch(Exception ex)
        {
            LOGGER.warn((new StringBuilder()).append("paser enabled param failed.[").append(properties.getProperty("enabled")).append("]").toString());
        }
    }

    public void run()
    {
        if(timeout <= 10)
            break MISSING_BLOCK_LABEL_175;
_L3:
        Iterator i$ = requests.keySet().iterator();
_L1:
        Thread thread;
        Date endtime;
        do
        {
            if(!i$.hasNext())
                break MISSING_BLOCK_LABEL_162;
            thread = (Thread)i$.next();
            Map m = (Map)requests.get(thread);
            Date starttime = (Date)m.get("starttime");
            endtime = DateUtil.addMinutes(starttime, timeout);
        } while((new Date()).compareTo(endtime) <= 0);
        LOGGER.debug("request too long,need to kill.");
        thread.interrupt();
        requests.remove(thread);
          goto _L1
        Exception ex;
        ex;
        LOGGER.warn("kill request failed.", ex);
        requests.remove(thread);
          goto _L1
        Exception exception;
        exception;
        requests.remove(thread);
        throw exception;
        Thread.sleep(60000L);
        if(true) goto _L3; else goto _L2
_L2:
        InterruptedException ex;
        ex;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/web/RequestMonitor);
    private int timeout;
    private boolean enabled;
    private static Map requests = new LinkedHashMap();
    private static Object lock = new Object();

}
