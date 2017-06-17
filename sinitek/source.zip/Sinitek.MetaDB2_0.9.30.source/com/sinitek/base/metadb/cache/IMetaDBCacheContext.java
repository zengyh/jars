// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaDBCacheContext.java

package com.sinitek.base.metadb.cache;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.cache.plugin.IMetaDBCachePlugin;
import java.util.Date;
import java.util.List;

// Referenced classes of package com.sinitek.base.metadb.cache:
//            IEntityCacheInfo, ILocalReloadEventListener

public interface IMetaDBCacheContext
{

    public abstract IMetaObject loadFromCache(IEntity ientity, int i)
        throws MetaDBException;

    public abstract IMetaObject loadFromCache(IProperty iproperty, Object obj)
        throws MetaDBException;

    public abstract IEntityCacheInfo getEntityCacheInfo(IEntity ientity)
        throws MetaDBException;

    public abstract void reloadEntityCache(IEntity ientity)
        throws MetaDBException;

    public abstract List getCachePlugins();

    public abstract void registCachePlugin(IMetaDBCachePlugin imetadbcacheplugin)
        throws MetaDBException;

    public abstract void registReloadEvent(String as[], Date date, long l, ILocalReloadEventListener ilocalreloadeventlistener);

    public abstract void notifyReload();

    public abstract void notifyLocalReload();

    public abstract void notifyReload(String as[]);
}
