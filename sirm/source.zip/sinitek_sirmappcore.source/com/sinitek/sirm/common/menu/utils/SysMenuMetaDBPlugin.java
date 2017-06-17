// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SysMenuMetaDBPlugin.java

package com.sinitek.sirm.common.menu.utils;

import com.sinitek.base.metadb.IEntity;
import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.base.metadb.cache.ObjectOperateType;
import com.sinitek.base.metadb.cache.plugin.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.utils.CacheUtils;
import com.sinitek.spirit.web.sysmenu.ISysMenu;
import java.util.*;

public class SysMenuMetaDBPlugin
    implements IMetaDBCachePlugin, IMetaDBCachePluginEventListener
{

    public SysMenuMetaDBPlugin()
    {
        sysMenuUrlMap = new HashMap();
    }

    public void initData()
    {
        sysMenuUrlMap.clear();
        IMetaDBQuery q = (new MetaDBTemplate()).createQuery("SPRT_SYSMENU", "url is not null");
        List sysMenus = q.getResult();
        ISysMenu sysMenu;
        for(Iterator i$ = sysMenus.iterator(); i$.hasNext(); CacheUtils.cacheObjectByUrl(sysMenuUrlMap, sysMenu.getUrl().trim(), sysMenu, false))
            sysMenu = (ISysMenu)i$.next();

    }

    public void saveCacheStatus()
    {
    }

    public void reloadAll()
    {
        initData();
    }

    public void setCacheContext(IMetaDBCacheContext imetadbcachecontext)
    {
    }

    public String[] getCachedEntityNames()
    {
        return (new String[] {
            "SPRT_SYSMENU"
        });
    }

    public IMetaDBCachePluginEventListener getEventListener()
    {
        return this;
    }

    public IMetaDBCachePluginStatus getStatus()
    {
        return null;
    }

    public void afterTransactionCommit(IEntity iEntities[], int ints[], ObjectOperateType objectOperateTypes[])
    {
        initData();
    }

    public List findSysMenusByUrl(String contextpath, String uri, Map params)
    {
        return CacheUtils.findCacheObjectsByUrl(sysMenuUrlMap, contextpath, uri, params);
    }

    private Map sysMenuUrlMap;
}
