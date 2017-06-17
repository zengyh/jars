// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaDBCachePlugin.java

package com.sinitek.base.metadb.cache.plugin;

import com.sinitek.base.metadb.cache.IMetaDBCacheContext;

// Referenced classes of package com.sinitek.base.metadb.cache.plugin:
//            IMetaDBCachePluginEventListener, IMetaDBCachePluginStatus

public interface IMetaDBCachePlugin
{

    public abstract void initData();

    public abstract void saveCacheStatus();

    public abstract void reloadAll();

    public abstract void setCacheContext(IMetaDBCacheContext imetadbcachecontext);

    public abstract String[] getCachedEntityNames();

    public abstract IMetaDBCachePluginEventListener getEventListener();

    public abstract IMetaDBCachePluginStatus getStatus();
}
