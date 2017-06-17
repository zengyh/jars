// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBCacheContextLocalImpl.java

package com.sinitek.base.metadb.cache.impl;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.cache.*;
import com.sinitek.base.metadb.cache.plugin.IMetaDBCachePlugin;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.cache.impl:
//            EntityCacheContainer, CachePluginContainer, ReloadTimerTask, MetaObjectIdCache

public class MetaDBCacheContextLocalImpl
    implements IMetaDBCacheContextImpl
{

    public MetaDBCacheContextLocalImpl()
    {
        entityCacheContainer = new EntityCacheContainer();
        pluginContainer = new CachePluginContainer();
        timer = new Timer(true);
    }

    public IMetaObject loadFromCache(IEntity entity, int id)
        throws MetaDBException
    {
        return entityCacheContainer.getIdCache(entity).get(new Integer(id));
    }

    public IMetaObject loadFromCache(IProperty property, Object value)
        throws MetaDBException
    {
        IMetaObjectImpl ret = entityCacheContainer.getPropertyCache(property).get(value);
        if(ret != null)
            entityCacheContainer.getIdCache(property.getEntity()).ensureInCache(ret);
        return ret;
    }

    public IEntityCacheInfo getEntityCacheInfo(IEntity entity)
        throws MetaDBException
    {
        return entityCacheContainer.getEntityCacheInfo(entity);
    }

    public void reloadEntityCache(IEntity entity)
        throws MetaDBException
    {
        entityCacheContainer.reloadEntityCache(entity);
    }

    public void reloadAllEntityCache()
        throws MetaDBException
    {
        entityCacheContainer.reloadAllEntityCache();
    }

    public List getCachePlugins()
    {
        return pluginContainer.getPlugins();
    }

    public void registCachePlugin(IMetaDBCachePlugin plugin)
        throws MetaDBException
    {
        plugin.setCacheContext(this);
        pluginContainer.addPlugin(plugin);
    }

    public void registReloadEvent(String targetEntityNames[], Date firstTime, long period, ILocalReloadEventListener listener)
    {
        if(firstTime == null)
        {
            Calendar c = Calendar.getInstance();
            c.add(14, (int)period);
            firstTime = c.getTime();
        }
        timer.scheduleAtFixedRate(new ReloadTimerTask(listener, targetEntityNames), firstTime, period);
        if(LOGGER.isDebugEnabled())
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LOGGER.debug((new StringBuilder()).append("\u6210\u529F\u6CE8\u518C\u5237\u65B0\u4E8B\u4EF6\u5904\u7406\u5668[").append(listener.getClass().getName()).append("]\uFF0C\u8C03\u7528\u95F4\u9694[").append(period).append("]ms\uFF0C\u9996\u6B21\u8C03\u7528\u65F6\u95F4[").append(sdf.format(firstTime)).append("]").toString());
        }
    }

    public void notifyReload()
    {
        LOGGER.debug("\u5F00\u59CB\u5237\u65B0\u6240\u6709\u672C\u5730\u7F13\u5B58");
        entityCacheContainer.reloadAllEntityCache();
        pluginContainer.reloadPlugin();
        LOGGER.debug("\u6240\u6709\u672C\u5730\u7F13\u5B58\u5237\u65B0\u6210\u529F");
    }

    public void notifyLocalReload()
    {
        notifyReload();
    }

    public void notifyReload(String entityNames[])
    {
        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("\u5F00\u59CB\u5237\u65B0\u90E8\u5206\u672C\u5730\u7F13\u5B58");
            StringBuffer sb = new StringBuffer("\u6D89\u53CA\u5B9E\u4F53\uFF1A");
            for(int i = 0; i < entityNames.length; i++)
            {
                if(i > 0)
                    sb.append(",");
                sb.append("[").append(entityNames[i]).append("]");
            }

            LOGGER.debug(sb.toString());
        }
        entityCacheContainer.readEntityCache(entityNames);
        pluginContainer.reloadPlugin(entityNames);
        LOGGER.debug("\u90E8\u5206\u672C\u5730\u7F13\u5B58\u5237\u65B0\u6210\u529F");
    }

    public void notifyAfterCommit(List commitDatas)
    {
        entityCacheContainer.notifiyAfterCommit(commitDatas);
        pluginContainer.afterCommit(commitDatas);
    }

    protected EntityCacheContainer entityCacheContainer;
    protected CachePluginContainer pluginContainer;
    protected Timer timer;
    protected static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CACHE;
    }
}
