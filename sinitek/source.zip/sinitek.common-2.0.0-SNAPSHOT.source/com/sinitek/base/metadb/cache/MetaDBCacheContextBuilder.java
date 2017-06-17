// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBCacheContextBuilder.java

package com.sinitek.base.metadb.cache;

import com.sinitek.base.metadb.cache.impl.MetaDBCacheContextLocalImpl;
import com.sinitek.base.metadb.cache.impl.MetaDBCacheContextNewSyncRemoteImpl;
import com.sinitek.base.metadb.cache.impl.MetaDBCacheContextRemoteImpl;
import com.sinitek.base.metadb.config.IMetaDBSysLoader;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.base.metadb.cache:
//            MetaDBCacheException, IMetaDBCacheContextImpl

public class MetaDBCacheContextBuilder
{

    public MetaDBCacheContextBuilder()
    {
    }

    public static IMetaDBCacheContextImpl buildCacheContext(Properties cacheConfigProps, IMetaDBSysLoader metaDBLoader)
    {
        if(!"true".equalsIgnoreCase(cacheConfigProps.getProperty("sync.enable", "false")))
        {
            String synMode = cacheConfigProps.getProperty("cache.synserver.enable");
            if(StringUtils.isEmpty(synMode))
                throw new MetaDBCacheException("0003", new Object[] {
                    "cache.synserver.enable"
                });
            if("true".equalsIgnoreCase(synMode.trim()))
            {
                String serverName = cacheConfigProps.getProperty("cache.synserver.address", "").trim();
                if(StringUtils.isEmpty(serverName))
                    throw new MetaDBCacheException("0007");
                String szPort = cacheConfigProps.getProperty("cache.synserver.port", "").trim();
                if(StringUtils.isEmpty(szPort))
                    throw new MetaDBCacheException("0008");
                int port = 0;
                try
                {
                    port = Integer.parseInt(szPort);
                }
                catch(NumberFormatException e)
                {
                    throw new MetaDBCacheException("0009");
                }
                return new MetaDBCacheContextRemoteImpl(serverName, port, metaDBLoader);
            } else
            {
                return new MetaDBCacheContextLocalImpl();
            }
        } else
        {
            return new MetaDBCacheContextNewSyncRemoteImpl(cacheConfigProps, metaDBLoader);
        }
    }
}
