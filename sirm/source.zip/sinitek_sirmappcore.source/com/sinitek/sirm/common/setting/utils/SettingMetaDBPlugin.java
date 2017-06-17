// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SettingMetaDBPlugin.java

package com.sinitek.sirm.common.setting.utils;

import com.sinitek.base.metadb.IEntity;
import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.base.metadb.cache.ObjectOperateType;
import com.sinitek.base.metadb.cache.plugin.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.setting.entity.ISetting;
import java.io.PrintStream;
import java.util.*;

public class SettingMetaDBPlugin
    implements IMetaDBCachePlugin, IMetaDBCachePluginEventListener
{

    public SettingMetaDBPlugin()
    {
        settingMap = new HashMap();
    }

    public Map getSettingMap()
    {
        return settingMap;
    }

    public void initData()
    {
        settingMap.clear();
        IMetaDBQuery q = (new MetaDBTemplate()).createQuery("SETTING", "1=1");
        List settings = q.getResult();
        ISetting setting;
        Map map;
        for(Iterator i$ = settings.iterator(); i$.hasNext(); map.put(setting.getName(), setting))
        {
            setting = (ISetting)i$.next();
            map = (Map)settingMap.get(setting.getModule());
            if(map == null)
            {
                map = new HashMap();
                settingMap.put(setting.getModule(), map);
            }
        }

    }

    public void saveCacheStatus()
    {
        System.out.println("saveCacheStatus");
    }

    public void reloadAll()
    {
        initData();
    }

    public void setCacheContext(IMetaDBCacheContext iMetaDBCacheContext)
    {
        System.out.println("setCacheContext");
    }

    public String[] getCachedEntityNames()
    {
        return (new String[] {
            "SETTING"
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

    Map settingMap;
}
