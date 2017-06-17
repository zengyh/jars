// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmEnumServiceImpl.java

package com.sinitek.sirm.common.sirmenum.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.sirmenum.EnumMap;
import com.sinitek.sirm.common.sirmenum.entity.ISirmEnum;
import com.sinitek.sirm.common.sirmenum.service.ISirmEnumService;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;

public class SirmEnumServiceImpl extends MetaDBContextSupport
    implements ISirmEnumService
{

    public SirmEnumServiceImpl()
    {
    }

    public Map findSirmEnumByCataLogAndType(String cataLog, String type)
    {
        String _sql = "upper(CATALOG) = upper(:CATALOG) AND TYPE=:TYPE ORDER BY VALUE";
        Map _mp = new HashMap();
        _mp.put("CATALOG", StringUtil.safeToString(cataLog, "").toUpperCase());
        _mp.put("TYPE", type);
        List _list = getMetaDBContext().query("SIRMENUM", _sql, _mp);
        if(_list == null || _list.size() < 1)
            return null;
        Map _map = new LinkedHashMap();
        for(int _i = 0; _i < _list.size(); _i++)
        {
            ISirmEnum _objs = (ISirmEnum)_list.get(_i);
            String _value = _objs.getName();
            _map.put(_objs.getValue() != null ? ((Object) ((new StringBuilder()).append("").append(_objs.getValue()).toString())) : ((Object) (_objs.getStrvalue())), _value);
        }

        return new EnumMap(_map);
    }

    public ISirmEnum getSirmEnumName(String cataLog, String type, Integer value)
    {
        String _sql = "upper(CATALOG) = upper(:CATALOG) AND TYPE=:TYPE AND VALUE=:VALUE ORDER BY VALUE";
        Map _mp = new HashMap();
        _mp.put("CATALOG", StringUtil.safeToString(cataLog, "").toUpperCase());
        _mp.put("TYPE", type);
        _mp.put("VALUE", value);
        List _list = getMetaDBContext().query("SIRMENUM", _sql, _mp);
        if(_list == null || _list.size() < 1)
            return null;
        else
            return (ISirmEnum)_list.get(0);
    }

    public List findSirmEnum(Map map)
    {
        StringBuffer sql = new StringBuffer();
        Map params = new HashMap();
        int i = 0;
        for(Iterator i$ = map.keySet().iterator(); i$.hasNext();)
        {
            String type = (String)i$.next();
            String catalog = StringUtil.safeToString(map.get(type), "").toUpperCase();
            String paramType = (new StringBuilder()).append("type").append(i).toString();
            String paramCatalog = (new StringBuilder()).append("catalog").append(i).toString();
            if(i > 0)
                sql.append("OR ");
            sql.append((new StringBuilder()).append("(upper(CATALOG) = upper(:").append(paramCatalog).append(") AND TYPE=:").append(paramType).append(") ").toString());
            params.put(paramType, type);
            params.put(paramCatalog, catalog);
            i++;
        }

        sql.append("ORDER BY TYPE , value");
        return getMetaDBContext().query("SIRMENUM", sql.toString(), params);
    }

    public IMetaDBQuery findSirmEnum(String catalog, String name, String type)
    {
        IMetaDBQuery _metaDBQuery = null;
        StringBuilder _buffer = new StringBuilder();
        Map _param = new HashMap();
        _buffer.append("select objid,upper(catalog) catalog,description,name,sort,value,strvalue,type,nvl2(value,0,1) valuetype from sirm_enum  t where 1=1  ");
        if(!"".equals(catalog) && null != catalog)
        {
            _buffer.append(" and upper(t.catalog) like upper(:catalog) ");
            _param.put("catalog", (new StringBuilder()).append("%").append(catalog).append("%").toString());
        }
        if(!"".equals(name) && null != name)
        {
            _buffer.append(" and upper(t.name) like upper(:name) ");
            _param.put("name", (new StringBuilder()).append("%").append(name).append("%").toString());
        }
        if(!"".equals(type) && null != type)
        {
            _buffer.append(" and upper(t.type) like upper(:type) ");
            _param.put("type", (new StringBuilder()).append("%").append(type).append("%").toString());
        }
        _metaDBQuery = getMetaDBContext().createSqlQuery(_buffer.toString());
        _metaDBQuery.setParameters(_param);
        return _metaDBQuery;
    }

    public Integer saveSirmEnum(ISirmEnum sirmEnum)
    {
        if(!"".equals(Integer.valueOf(sirmEnum.getId())) && 0 != sirmEnum.getId())
        {
            ISirmEnum obj = null;
            try
            {
                obj = (ISirmEnum)getMetaDBContext().get("SIRMENUM", sirmEnum.getId());
                if(obj != null)
                {
                    obj.setCataLog(sirmEnum.getCataLog());
                    obj.setDescription(sirmEnum.getDescription());
                    obj.setName(sirmEnum.getName());
                    obj.setType(sirmEnum.getType());
                    obj.setValue(sirmEnum.getValue());
                    obj.setUpdateTimeStamp(new Date());
                    obj.setSort(sirmEnum.getSort());
                    obj.setStrvalue(sirmEnum.getStrvalue());
                    save(obj);
                }
            }
            catch(Exception e) { }
        } else
        {
            save(sirmEnum);
        }
        return Integer.valueOf(sirmEnum.getId());
    }

    public void delSirmEnumById(String objid)
    {
        ISirmEnum obj = (ISirmEnum)getMetaDBContext().get("SIRMENUM", Integer.parseInt(objid));
        if(obj != null)
            remove(obj);
    }

    public List findSirmEnum(String catalog, String type)
    {
        String _sql = "upper(CATALOG)=upper(:CATALOG) AND TYPE=:TYPE ORDER BY VALUE";
        Map _mp = new HashMap();
        _mp.put("CATALOG", catalog);
        _mp.put("TYPE", type);
        List _list = getMetaDBContext().query("SIRMENUM", _sql, _mp);
        if(_list == null || _list.size() < 1)
            return null;
        else
            return _list;
    }

    public ISirmEnum getSirmEnumById(int objid)
    {
        return (ISirmEnum)getMetaDBContext().get(com/sinitek/sirm/common/sirmenum/entity/ISirmEnum, objid);
    }
}
