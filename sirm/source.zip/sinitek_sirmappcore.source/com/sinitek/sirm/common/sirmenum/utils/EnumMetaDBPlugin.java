// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnumMetaDBPlugin.java

package com.sinitek.sirm.common.sirmenum.utils;

import com.sinitek.base.metadb.IEntity;
import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.base.metadb.cache.ObjectOperateType;
import com.sinitek.base.metadb.cache.plugin.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.sirmenum.entity.ISirmEnum;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class EnumMetaDBPlugin
    implements IMetaDBCachePlugin, IMetaDBCachePluginEventListener
{

    public EnumMetaDBPlugin()
    {
        enumMap = new HashMap();
    }

    public void initData()
    {
        enumMap.clear();
        IMetaDBQuery q = (new MetaDBTemplate()).createQuery("SIRMENUM", "1=1");
        q.setOrderBy("sort");
        List enumlist = q.getResult();
        ISirmEnum e;
        List itemlist;
        for(Iterator i$ = enumlist.iterator(); i$.hasNext(); itemlist.add(e))
        {
            e = (ISirmEnum)i$.next();
            Map catalogMap = (Map)enumMap.get(e.getCataLog().toUpperCase());
            if(catalogMap == null)
            {
                catalogMap = new HashMap();
                enumMap.put(e.getCataLog().toUpperCase(), catalogMap);
            }
            itemlist = (List)catalogMap.get(e.getType());
            if(itemlist == null)
            {
                itemlist = new ArrayList();
                catalogMap.put(e.getType(), itemlist);
            }
        }

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
            "SIRMENUM"
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

    public List findSirmEnumByCataLogAndType(String cataLog, String type)
    {
        Map catalogMap = (Map)enumMap.get(cataLog.toUpperCase());
        if(catalogMap != null)
        {
            List _list = (List)catalogMap.get(type);
            if(_list != null)
                return _list;
        }
        return new ArrayList();
    }

    public ISirmEnum getSirmEnumName(String cataLog, String type, Integer value)
    {
        return getSirmEnumName(cataLog, type, (new StringBuilder()).append("").append(value).toString());
    }

    public ISirmEnum getSirmEnumName(String cataLog, String type, String value)
    {
        ISirmEnum _result;
label0:
        {
            _result = null;
            Map catalogMap = (Map)enumMap.get(cataLog.toUpperCase());
            if(catalogMap == null)
                break label0;
            List _list = (List)catalogMap.get(type);
            if(_list == null)
                break label0;
            Iterator i$ = _list.iterator();
            ISirmEnum _objs;
label1:
            do
            {
                do
                {
                    if(!i$.hasNext())
                        break label0;
                    _objs = (ISirmEnum)i$.next();
                    if(_objs.getValue() != null)
                        continue label1;
                } while(!StringUtils.equals(_objs.getStrvalue(), value));
                _result = _objs;
                break label0;
            } while(!_objs.getValue().equals(NumberTool.safeToInteger(value, Integer.valueOf(_objs.getValue().intValue() - 1))));
            _result = _objs;
        }
        return _result;
    }

    public List findSirmEnum(Map map)
    {
        List result = new ArrayList();
        List data;
        for(Iterator i$ = map.keySet().iterator(); i$.hasNext(); result.addAll(data))
        {
            String type = (String)i$.next();
            String catalog = (String)map.get(type);
            data = findSirmEnumByCataLogAndType(catalog.toUpperCase(), type);
        }

        return result;
    }

    private Map enumMap;
}
