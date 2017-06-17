// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CachePluginContainer.java

package com.sinitek.base.metadb.cache.impl;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.cache.*;
import com.sinitek.base.metadb.cache.plugin.IMetaDBCachePlugin;
import com.sinitek.base.metadb.cache.plugin.IMetaDBCachePluginEventListener;
import java.util.*;
import org.apache.log4j.Logger;

public class CachePluginContainer
{

    public CachePluginContainer()
    {
        pluginList = new ArrayList();
        relaEntitys = new HashMap();
    }

    public void addPlugin(IMetaDBCachePlugin plugin)
    {
        getLock(false);
        try
        {
            plugin.initData();
            List entitys = new ArrayList();
            for(int i = 0; i < plugin.getCachedEntityNames().length; i++)
                entitys.add(MetaDBContextFactory.getInstance().getEntity(plugin.getCachedEntityNames()[i]));

            if(pluginList.contains(plugin))
                throw new MetaDBCacheException("0004", new Object[] {
                    plugin
                });
            pluginList.add(plugin);
            List relas;
            for(Iterator iter = entitys.iterator(); iter.hasNext(); relas.add(plugin))
            {
                IEntity entity = (IEntity)iter.next();
                relas = (List)relaEntitys.get(entity);
                if(relas == null)
                {
                    relas = new ArrayList();
                    relaEntitys.put(entity, relas);
                }
            }

        }
        catch(MetaDBCacheException e)
        {
            throw e;
        }
        catch(Exception ex)
        {
            throw new MetaDBCacheException("0005", ex);
        }
        releaseLock(false);
        break MISSING_BLOCK_LABEL_217;
        Exception exception;
        exception;
        releaseLock(false);
        throw exception;
    }

    public List getPlugins()
    {
        getLock(true);
        ArrayList ret = new ArrayList(pluginList);
        releaseLock(true);
        return ret;
    }

    public void reloadPlugin()
    {
        getLock(true);
        IMetaDBCachePlugin plugin;
        for(Iterator iter = pluginList.iterator(); iter.hasNext(); plugin.reloadAll())
            plugin = (IMetaDBCachePlugin)iter.next();

        releaseLock(true);
        break MISSING_BLOCK_LABEL_77;
        Exception ex;
        ex;
        LOGGER.warn("\u5BF9\u6240\u6709\u7F13\u5B58\u63D2\u4EF6\u8C03\u7528reload\u65B9\u6CD5\u629B\u51FA\u5F02\u5E38", ex);
        releaseLock(true);
        break MISSING_BLOCK_LABEL_77;
        Exception exception;
        exception;
        releaseLock(true);
        throw exception;
    }

    public void reloadPlugin(String entityNames[])
    {
        List plugins;
        getLock(true);
        plugins = new ArrayList();
label0:
        for(int i = 0; i < entityNames.length; i++)
        {
            IEntity entity = MetaDBContextFactory.getInstance().getEntity(entityNames[i]);
            List relaPlugins = (List)relaEntitys.get(entity);
            if(relaPlugins == null)
                continue;
            Iterator pluginIter = relaPlugins.iterator();
            do
            {
                IMetaDBCachePlugin plugin;
                do
                {
                    if(!pluginIter.hasNext())
                        continue label0;
                    plugin = (IMetaDBCachePlugin)pluginIter.next();
                } while(plugins.contains(plugin));
                plugins.add(plugin);
            } while(true);
        }

        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u5BF9[").append(plugins.size()).append("]\u4E2A\u7F13\u5B58\u63D2\u4EF6\u8FDB\u884C\u91CD\u8F7D").toString());
        IMetaDBCachePlugin plugin;
        for(Iterator iter = plugins.iterator(); iter.hasNext(); plugin.reloadAll())
            plugin = (IMetaDBCachePlugin)iter.next();

        releaseLock(true);
        break MISSING_BLOCK_LABEL_230;
        Exception ex;
        ex;
        LOGGER.warn("\u5BF9\u7F13\u5B58\u63D2\u4EF6\u8C03\u7528reload\u65B9\u6CD5\u629B\u51FA\u5F02\u5E38", ex);
        releaseLock(true);
        break MISSING_BLOCK_LABEL_230;
        Exception exception;
        exception;
        releaseLock(true);
        throw exception;
    }

    public void afterCommit(List commitData)
    {
        getLock(true);
        try
        {
            Map updateMap = new HashMap();
            Iterator iter = commitData.iterator();
            do
            {
                if(!iter.hasNext())
                    break;
                MetaObjectUpdateBean updateBean = (MetaObjectUpdateBean)iter.next();
                IEntity entity = updateBean.getEntity();
                if(relaEntitys.containsKey(entity))
                {
                    List beans = (List)updateMap.get(entity);
                    if(beans == null)
                    {
                        beans = new ArrayList();
                        updateMap.put(entity, beans);
                    }
                    beans.add(updateBean);
                }
            } while(true);
            List plugins = new ArrayList();
            Iterator iter;
            for(iter = updateMap.entrySet().iterator(); iter.hasNext();)
            {
                java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
                IEntity entity = (IEntity)entry.getKey();
                List relaPlugins = (List)relaEntitys.get(entity);
                if(relaPlugins != null)
                {
                    Iterator pluginIter = relaPlugins.iterator();
                    while(pluginIter.hasNext()) 
                    {
                        IMetaDBCachePlugin plugin = (IMetaDBCachePlugin)pluginIter.next();
                        if(!plugins.contains(plugin))
                            plugins.add(plugin);
                    }
                }
            }

            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u901A\u77E5[").append(plugins.size()).append("]\u4E2A\u7F13\u5B58\u63D2\u4EF6").toString());
            iter = plugins.iterator();
            do
            {
                if(!iter.hasNext())
                    break;
                IMetaDBCachePlugin plugin = (IMetaDBCachePlugin)iter.next();
                List entityList = new ArrayList();
                List objIdList = new ArrayList();
                List opTypeList = new ArrayList();
                for(int i = 0; i < plugin.getCachedEntityNames().length; i++)
                {
                    IEntity entity = MetaDBContextFactory.getInstance().getEntity(plugin.getCachedEntityNames()[i]);
                    List updateList = (List)updateMap.get(entity);
                    if(updateList == null)
                        continue;
                    MetaObjectUpdateBean updateBean;
                    for(Iterator beanIter = updateList.iterator(); beanIter.hasNext(); opTypeList.add(updateBean.getUpdateType()))
                    {
                        entityList.add(entity);
                        updateBean = (MetaObjectUpdateBean)beanIter.next();
                        objIdList.add(new Integer(updateBean.getObjId()));
                    }

                }

                IEntity entities[] = (IEntity[])(IEntity[])entityList.toArray(new IEntity[entityList.size()]);
                ObjectOperateType types[] = (ObjectOperateType[])(ObjectOperateType[])opTypeList.toArray(new ObjectOperateType[opTypeList.size()]);
                int objIds[] = new int[objIdList.size()];
                int i = 0;
                for(Iterator idIterator = objIdList.iterator(); idIterator.hasNext();)
                {
                    Integer objId = (Integer)idIterator.next();
                    objIds[i++] = objId.intValue();
                }

                try
                {
                    IMetaDBCachePluginEventListener listener = plugin.getEventListener();
                    if(listener != null)
                    {
                        if(LOGGER.isDebugEnabled())
                            LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u901A\u77E5\u63D2\u4EF6[").append(plugin).append("]\u4E8B\u52A1\u63D0\u4EA4\u4E8B\u4EF6").toString());
                        listener.afterTransactionCommit(entities, objIds, types);
                        if(LOGGER.isDebugEnabled())
                            LOGGER.debug((new StringBuilder()).append("\u63D2\u4EF6[").append(plugin).append("]\u7684\u4E8B\u52A1\u63D0\u4EA4\u4E8B\u4EF6\u6210\u529F\u54CD\u5E94").toString());
                    }
                }
                catch(Exception ex)
                {
                    LOGGER.warn((new StringBuilder()).append("\u63D2\u4EF6[").append(plugin).append("]\u7684\u4E8B\u52A1\u63D0\u4EA4\u4E8B\u4EF6\u629B\u51FA\u5F02\u5E38").toString(), ex);
                }
            } while(true);
        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception e)
        {
            throw new MetaDBCacheException("0006", e);
        }
        releaseLock(true);
        break MISSING_BLOCK_LABEL_773;
        Exception exception;
        exception;
        releaseLock(true);
        throw exception;
    }

    private synchronized void getLock(boolean isReadLock)
    {
        try
        {
            if(isReadLock)
            {
                while(lock < 0) 
                    wait();
                lock++;
            } else
            {
                while(lock > 0) 
                    wait();
                lock = -1;
            }
        }
        catch(InterruptedException e)
        {
            LOGGER.warn("\u9501\u88AB\u6253\u65AD", e);
        }
    }

    private synchronized void releaseLock(boolean isReadLock)
    {
        if(isReadLock)
            lock--;
        else
            lock = 0;
        notifyAll();
    }

    private List pluginList;
    private Map relaEntitys;
    private int lock;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CACHE;
    }
}
