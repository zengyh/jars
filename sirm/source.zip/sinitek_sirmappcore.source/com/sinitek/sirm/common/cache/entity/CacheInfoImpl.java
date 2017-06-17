// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CacheInfoImpl.java

package com.sinitek.sirm.common.cache.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.cache.entity:
//            ICacheInfo

public class CacheInfoImpl extends MetaObjectImpl
    implements ICacheInfo
{

    public CacheInfoImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("CACHEINFO"));
    }

    public CacheInfoImpl(IEntity entity)
    {
        super(entity);
    }

    public String getName()
    {
        return (String)get("name");
    }

    public void setName(String value)
    {
        put("name", value);
    }

    public String getEntityNames()
    {
        return (String)get("entityNames");
    }

    public void setEntityNames(String value)
    {
        put("entityNames", value);
    }

    public String getLocation()
    {
        return (String)get("location");
    }

    public void setLocation(String value)
    {
        put("location", value);
    }
}
