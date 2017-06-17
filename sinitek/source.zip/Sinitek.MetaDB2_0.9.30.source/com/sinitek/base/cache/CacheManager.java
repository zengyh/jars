// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CacheManager.java

package com.sinitek.base.cache;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.cache:
//            ClusterCacheManager, ICacheManager

public class CacheManager
{

    private CacheManager()
    {
    }

    public static ICacheManager getClusterCacheManager()
    {
        if(clusterCacheManager == null)
            synchronized(LOCK)
            {
                if(clusterCacheManager == null)
                    loadCusterManager();
            }
        return clusterCacheManager;
    }

    private static void loadCusterManager()
    {
        InputStream fin = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/memcached.properties");
        if(fin != null)
        {
            Properties properties = new Properties();
            try
            {
                properties.load(fin);
                String servers = properties.getProperty("socketpool.servers");
                if(StringUtils.isNotBlank(servers))
                {
                    List _servers = new ArrayList();
                    String list[] = servers.split(",");
                    String arr$[] = list;
                    int len$ = arr$.length;
                    for(int i$ = 0; i$ < len$; i$++)
                    {
                        String s = arr$[i$];
                        if(StringUtils.isNotBlank(s))
                            _servers.add(StringUtils.trimToEmpty(s));
                    }

                    clusterCacheManager = new ClusterCacheManager((String[])_servers.toArray(new String[0]));
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        } else
        {
            LOGGER.warn("cannot find [conf/memcached.properties]");
        }
    }

    private static final Logger LOGGER;
    private static final Object LOCK = new Object();
    private static ICacheManager clusterCacheManager = null;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CACHE;
    }
}
