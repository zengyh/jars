// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FunctionGroupMetaDBPlugin.java

package com.sinitek.sirm.common.function.utils;

import com.sinitek.base.metadb.IEntity;
import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.base.metadb.cache.ObjectOperateType;
import com.sinitek.base.metadb.cache.plugin.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.function.entity.IFunctionGroup;
import java.util.*;

public class FunctionGroupMetaDBPlugin
    implements IMetaDBCachePlugin, IMetaDBCachePluginEventListener
{

    public FunctionGroupMetaDBPlugin()
    {
        functionGroupMap = new HashMap();
    }

    public IFunctionGroup getFunctionGroup(int groupId)
    {
        return (IFunctionGroup)functionGroupMap.get(new Integer(groupId));
    }

    public void initData()
    {
        functionGroupMap.clear();
        IMetaDBQuery q = (new MetaDBTemplate()).createQuery("FUNCTIONGROUP", "1=1");
        List groups = q.getResult();
        IFunctionGroup group;
        for(Iterator i$ = groups.iterator(); i$.hasNext(); functionGroupMap.put(Integer.valueOf(group.getId()), group))
            group = (IFunctionGroup)i$.next();

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
            "FUNCTIONGROUP"
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

    Map functionGroupMap;
}
