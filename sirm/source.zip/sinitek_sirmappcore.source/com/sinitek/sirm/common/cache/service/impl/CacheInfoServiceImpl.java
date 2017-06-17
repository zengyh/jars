// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CacheInfoServiceImpl.java

package com.sinitek.sirm.common.cache.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.cache.entity.ICacheInfo;
import com.sinitek.sirm.common.cache.service.ICacheInfoService;
import java.util.HashMap;
import java.util.List;

public class CacheInfoServiceImpl extends AbstractMetaDBContextSupport
    implements ICacheInfoService
{

    public CacheInfoServiceImpl()
    {
    }

    public ICacheInfo getCacheInfoById(int id)
    {
        return (ICacheInfo)super.getMetaDBContext().get(com/sinitek/sirm/common/cache/entity/ICacheInfo, id);
    }

    public List findAllCacheInfos()
    {
        List list = super.getMetaDBContext().query("CACHEINFO", "1=1", new HashMap());
        return list;
    }
}
