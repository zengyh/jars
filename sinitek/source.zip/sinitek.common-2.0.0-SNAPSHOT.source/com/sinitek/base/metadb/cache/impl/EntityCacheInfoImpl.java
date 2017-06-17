// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityCacheInfoImpl.java

package com.sinitek.base.metadb.cache.impl;

import com.sinitek.base.metadb.IEntity;
import com.sinitek.base.metadb.IProperty;
import com.sinitek.base.metadb.cache.IEntityCacheInfo;
import java.util.Map;

// Referenced classes of package com.sinitek.base.metadb.cache.impl:
//            MetaObjectIdCache

public class EntityCacheInfoImpl
    implements IEntityCacheInfo
{

    public EntityCacheInfoImpl(IEntity entity, Map idCacheMap)
    {
        this.entity = entity;
        this.idCacheMap = idCacheMap;
    }

    public int getIdCacheSize()
    {
        return getPropertyCacheSize(entity.getProperty("Id"));
    }

    public float getIdCacheHitRate()
    {
        return getPropertyCacheHitRate(entity.getProperty("Id"));
    }

    public long getIdCacheAccessCount()
    {
        return getPropertyCacheAccessCount(entity.getProperty("Id"));
    }

    public int getPropertyCacheSize(IProperty property)
    {
        MetaObjectIdCache cache = (MetaObjectIdCache)idCacheMap.get(property);
        if(cache == null)
            return 0;
        else
            return cache.getCacheSize();
    }

    public float getPropertyCacheHitRate(IProperty property)
    {
        MetaObjectIdCache cache = (MetaObjectIdCache)idCacheMap.get(property);
        if(cache == null)
            return 0.0F;
        else
            return cache.getCacheHitRate();
    }

    public long getPropertyCacheAccessCount(IProperty property)
    {
        MetaObjectIdCache cache = (MetaObjectIdCache)idCacheMap.get(property);
        if(cache == null)
            return 0L;
        else
            return cache.getCacheAccessCount();
    }

    private Map idCacheMap;
    private IEntity entity;
}
