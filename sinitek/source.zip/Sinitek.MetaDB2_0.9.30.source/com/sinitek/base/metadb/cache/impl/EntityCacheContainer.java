// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityCacheContainer.java

package com.sinitek.base.metadb.cache.impl;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.cache.IEntityCacheInfo;
import com.sinitek.base.metadb.cache.MetaObjectUpdateBean;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.cache.impl:
//            MetaObjectIdCache, EntityCacheInfoImpl

public class EntityCacheContainer
{

    public EntityCacheContainer()
    {
        idCacheMap = new Hashtable();
        entityCacheMap = new Hashtable();
    }

    public synchronized MetaObjectIdCache getIdCache(IEntity entity)
    {
        return getPropertyCache(entity.getProperty("Id"));
    }

    public synchronized MetaObjectIdCache getPropertyCache(IProperty property)
    {
        MetaObjectIdCache cache = (MetaObjectIdCache)idCacheMap.get(property);
        if(cache == null)
        {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("\u521B\u5EFA\u5B9E\u4F53[").append(property.getEntity().getEntityName()).append("]\u7684[").append(property.getPropertyName()).append("]\u7684\u7F13\u5B58\u6C60").toString());
            cache = new MetaObjectIdCache(property);
            idCacheMap.put(property, cache);
            List list = (List)entityCacheMap.get(property.getEntity());
            if(list == null)
            {
                list = new ArrayList();
                entityCacheMap.put(property.getEntity(), list);
            }
            list.add(cache);
        }
        return cache;
    }

    public IEntityCacheInfo getEntityCacheInfo(IEntity entity)
    {
        Map idCacheMap = new HashMap();
        List caches = (List)entityCacheMap.get(entity);
        MetaObjectIdCache cache;
        for(Iterator iter = caches.iterator(); iter.hasNext(); idCacheMap.put(cache.getProperty(), cache))
            cache = (MetaObjectIdCache)iter.next();

        return new EntityCacheInfoImpl(entity, idCacheMap);
    }

    public void reloadEntityCache(IEntity entity)
    {
        List caches = (List)entityCacheMap.get(entity);
        if(caches != null)
        {
            MetaObjectIdCache cache;
            for(Iterator iter = caches.iterator(); iter.hasNext(); cache.reloadAll())
                cache = (MetaObjectIdCache)iter.next();

        }
    }

    public void reloadAllEntityCache()
    {
        Iterator iterator = entityCacheMap.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            List caches = (List)entry.getValue();
            if(caches != null)
            {
                Iterator iter = caches.iterator();
                while(iter.hasNext()) 
                {
                    MetaObjectIdCache cache = (MetaObjectIdCache)iter.next();
                    cache.reloadAll();
                }
            }
        } while(true);
    }

    public void readEntityCache(String entityNames[])
    {
        for(int i = 0; i < entityNames.length; i++)
            reloadEntityCache(MetaDBContextFactory.getInstance().getEntity(entityNames[i]));

    }

    public void notifiyAfterCommit(List commitDatas)
    {
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("\u5728\u4E8B\u52A1\u63D0\u4EA4\u540E\u901A\u77E5\u5B9E\u4F53\u5BF9\u8C61\u7F13\u5B58\uFF0C\u63D0\u4EA4\u4E86[").append(commitDatas.size()).append("]\u4E2A\u5BF9\u8C61").toString());
        Map dataMap = new HashMap();
        Iterator iter = commitDatas.iterator();
        do
        {
            if(!iter.hasNext())
                break;
            MetaObjectUpdateBean updateBean = (MetaObjectUpdateBean)iter.next();
            IMetaObject mo = updateBean.getUpdateObject();
            if(mo != null)
            {
                List list = (List)dataMap.get(mo.getEntity());
                if(list == null)
                {
                    list = new ArrayList();
                    dataMap.put(updateBean.getUpdateObject().getEntity(), list);
                }
                list.add(updateBean);
            }
        } while(true);
        for(iter = dataMap.entrySet().iterator(); iter.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
            IEntity entity = (IEntity)entry.getKey();
            List targets = (List)entityCacheMap.get(entity);
            List updateDatas = (List)entry.getValue();
            if(targets != null)
            {
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u6709[").append(updateDatas.size()).append("]\u4E2A\u5BF9\u8C61\u88AB\u63D0\u4EA4").toString());
                Iterator cacheIter = targets.iterator();
                while(cacheIter.hasNext()) 
                {
                    MetaObjectIdCache cache = (MetaObjectIdCache)cacheIter.next();
                    if(LOGGER.isDebugEnabled())
                        LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u66F4\u65B0\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u7684\u5C5E\u6027[").append(cache.getProperty().getPropertyName()).append("]\u7684\u5BF9\u8C61\u7F13\u5B58").toString());
                    cache.updateObject(updateDatas);
                    if(LOGGER.isDebugEnabled())
                        LOGGER.debug((new StringBuilder()).append("\u66F4\u65B0\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u7684\u5C5E\u6027[").append(cache.getProperty().getPropertyName()).append("]\u7684\u5BF9\u8C61\u7F13\u5B58\u6210\u529F").toString());
                }
            }
        }

    }

    private Map idCacheMap;
    private Map entityCacheMap;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CACHE;
    }
}
