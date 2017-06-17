// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FunctionInfoMetaDBPlugin.java

package com.sinitek.sirm.common.function.utils;

import com.sinitek.base.metadb.IEntity;
import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.base.metadb.cache.ObjectOperateType;
import com.sinitek.base.metadb.cache.plugin.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.function.entity.IFunctionInfo;
import com.sinitek.sirm.common.utils.CacheUtils;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class FunctionInfoMetaDBPlugin
    implements IMetaDBCachePlugin, IMetaDBCachePluginEventListener
{

    public FunctionInfoMetaDBPlugin()
    {
        funcActionMap = new HashMap();
        funcUrlMap = new HashMap();
        functionMap = new HashMap();
    }

    public IFunctionInfo getFunctionInfoByActionMethod(String action, String method)
    {
        IFunctionInfo result = null;
        Map map = (Map)funcActionMap.get(action);
        if(map != null)
        {
            String code = (String)map.get(method);
            if(code != null)
                result = (IFunctionInfo)functionMap.get(code);
        }
        return result;
    }

    public IFunctionInfo getFunctionInfoByUrl(String contextpath, String uri, Map params)
    {
        IFunctionInfo result = (IFunctionInfo)CacheUtils.getCacheObjectByUrl(funcUrlMap, contextpath, uri, params);
        return result;
    }

    public IFunctionInfo getFunctionInfoByCode(String code)
    {
        return (IFunctionInfo)functionMap.get(code);
    }

    public void initData()
    {
        functionMap.clear();
        funcActionMap.clear();
        funcUrlMap.clear();
        IMetaDBQuery q = (new MetaDBTemplate()).createQuery("FUNCTIONINFO", "1=1");
        List functionInfos = q.getResult();
        Iterator i$ = functionInfos.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IFunctionInfo info = (IFunctionInfo)i$.next();
            functionMap.put(info.getCode(), info);
            if(StringUtils.isNotBlank(info.getUrl()))
                CacheUtils.cacheObjectByUrl(funcUrlMap, info.getUrl().trim(), info);
            if(StringUtils.isNotBlank(info.getAction()) && StringUtils.isNotBlank(info.getMethod()))
            {
                Map map = (Map)funcActionMap.get(info.getAction());
                if(map == null)
                {
                    map = new HashMap();
                    funcActionMap.put(info.getAction(), map);
                }
                map.put(info.getMethod(), info.getCode());
            }
        } while(true);
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
            "FUNCTIONINFO"
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

    private static Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/function/utils/FunctionInfoMetaDBPlugin);
    Map funcActionMap;
    Map funcUrlMap;
    Map functionMap;

}
