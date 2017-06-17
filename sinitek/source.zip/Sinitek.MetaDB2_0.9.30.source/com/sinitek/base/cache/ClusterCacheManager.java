// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClusterCacheManager.java

package com.sinitek.base.cache;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import java.util.*;

// Referenced classes of package com.sinitek.base.cache:
//            ICacheManager

public class ClusterCacheManager
    implements ICacheManager
{

    public ClusterCacheManager(String servers[])
    {
        mcc = null;
        pool = null;
        pool = SockIOPool.getInstance();
        pool.setServers(servers);
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(1000);
        pool.initialize();
        mcc = new MemCachedClient();
    }

    public boolean set(String name, Object value)
    {
        return mcc.set(name, value);
    }

    public Object get(String name)
    {
        return mcc.get(name);
    }

    public boolean delete(String name)
    {
        return mcc.delete(name);
    }

    public boolean delete(String names[])
    {
        return mcc.flushAll(names);
    }

    public List listKeys()
    {
        List keylist = new ArrayList();
        Map statsItems = mcc.statsItems();
        int items_number = 0;
        for(Iterator iterator = statsItems.keySet().iterator(); iterator.hasNext();)
        {
            String server = (String)iterator.next();
            Map statsItems_sub = (Map)statsItems.get(server);
            Iterator iterator_item = statsItems_sub.keySet().iterator();
            while(iterator_item.hasNext()) 
            {
                String statsItems_sub_key = (String)iterator_item.next();
                if(statsItems_sub_key.toUpperCase().startsWith("items:".toUpperCase()) && statsItems_sub_key.toUpperCase().endsWith(":number".toUpperCase()))
                {
                    items_number = Integer.parseInt(((String)statsItems_sub.get(statsItems_sub_key)).trim());
                    Map statsCacheDump = mcc.statsCacheDump(new String[] {
                        server
                    }, Integer.parseInt(statsItems_sub_key.split(":")[1].trim()), items_number);
                    Iterator statsCacheDump_iterator = statsCacheDump.keySet().iterator();
                    while(statsCacheDump_iterator.hasNext()) 
                    {
                        Map statsCacheDump_sub = (Map)statsCacheDump.get(statsCacheDump_iterator.next());
                        Iterator iterator_keys = statsCacheDump_sub.keySet().iterator();
                        while(iterator_keys.hasNext()) 
                        {
                            String statsCacheDumpsub_key = (String)iterator_keys.next();
                            keylist.add(statsCacheDumpsub_key);
                        }
                    }
                }
            }
        }

        return keylist;
    }

    private MemCachedClient mcc;
    private SockIOPool pool;
}
