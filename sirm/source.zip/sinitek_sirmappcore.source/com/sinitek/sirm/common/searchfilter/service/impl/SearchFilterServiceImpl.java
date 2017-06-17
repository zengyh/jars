// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearchFilterServiceImpl.java

package com.sinitek.sirm.common.searchfilter.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.searchfilter.entity.ISearchFilter;
import com.sinitek.sirm.common.searchfilter.entity.ISearchFilterCondition;
import com.sinitek.sirm.common.searchfilter.service.ISearchFilterService;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.util.*;
import org.apache.log4j.Logger;

public class SearchFilterServiceImpl extends AbstractMetaDBContextSupport
    implements ISearchFilterService
{

    public SearchFilterServiceImpl()
    {
    }

    public ISearchFilter getSearchFilterById(int id)
    {
        return (ISearchFilter)getMetaDBContext().get("SEARCHFILTER", id);
    }

    public ISearchFilter getSearchFilterByNameCatalog(String name, String catalog)
    {
        String sql = "name=:name and catalog=:catalog";
        Map param = new HashMap();
        param.put("name", name);
        param.put("catalog", catalog);
        List list = getMetaDBContext().query("SEARCHFILTER", sql, param);
        return list.size() <= 0 ? null : (ISearchFilter)list.get(0);
    }

    public List findListSearchFiltersByCatalogEmpId(String catalog, Integer empId)
    {
        String sql = "catalog=:catalog and empId=:empId";
        Map param = new HashMap();
        param.put("catalog", catalog);
        param.put("empId", empId);
        return getMetaDBContext().query("SEARCHFILTER", sql, param);
    }

    public IMetaDBQuery findSearchFiltersByCatalogEmpId(String catalog, Integer empId)
    {
        StringBuilder _sql = new StringBuilder("SELECT * FROM SIRM_SEARCHFILTER WHERE 1=1 ");
        Map _paras = new HashMap();
        QueryUtils.build("=", "catalog", catalog, _sql, _paras);
        QueryUtils.build("=", "empId", empId, _sql, _paras);
        LOGGER.debug((new StringBuilder()).append(_sql).append("\n").append("parameters are:").append(_paras.toString()).toString());
        IMetaDBQuery _query = (new MetaDBTemplate()).createSqlQuery(_sql.toString());
        if(!_paras.isEmpty())
            _query.setParameters(_paras);
        return _query;
    }

    public List findSearchFilterConditionsByFilterId(int filterId)
    {
        String sql = "filterId=:filterId";
        Map param = new HashMap();
        param.put("filterId", Integer.valueOf(filterId));
        return getMetaDBContext().query("SEARCHFILTERCONDITION", sql, param);
    }

    public void saveSearchFilter(ISearchFilter filter)
    {
        filter.save();
    }

    public void deleteSearchFilter(ISearchFilter filter)
    {
        String sql = "filterId=:filterId";
        Map param = new HashMap();
        param.put("filterId", Integer.valueOf(filter.getId()));
        List list = getMetaDBContext().query("SEARCHFILTERCONDITION", sql, param);
        ISearchFilterCondition c;
        for(Iterator i$ = list.iterator(); i$.hasNext(); c.delete())
            c = (ISearchFilterCondition)i$.next();

        filter.delete();
    }

    public void saveSearchConditions(ISearchFilter filter, List conditions)
    {
        String sql = "filterId=:filterId";
        Map param = new HashMap();
        param.put("filterId", Integer.valueOf(filter.getId()));
        List list = getMetaDBContext().query("SEARCHFILTERCONDITION", sql, param);
        ISearchFilterCondition c;
        for(Iterator i$ = list.iterator(); i$.hasNext(); c.delete())
            c = (ISearchFilterCondition)i$.next();

        ISearchFilterCondition c;
        for(Iterator i$ = conditions.iterator(); i$.hasNext(); c.save())
        {
            c = (ISearchFilterCondition)i$.next();
            c.setFilterId(Integer.valueOf(filter.getId()));
        }

    }

    final Logger LOGGER = Logger.getLogger(getClass());
}
