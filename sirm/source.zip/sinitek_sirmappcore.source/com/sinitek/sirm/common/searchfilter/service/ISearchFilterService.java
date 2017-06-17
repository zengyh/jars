// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISearchFilterService.java

package com.sinitek.sirm.common.searchfilter.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.searchfilter.entity.ISearchFilter;
import java.util.List;

public interface ISearchFilterService
{

    public abstract ISearchFilter getSearchFilterById(int i);

    public abstract ISearchFilter getSearchFilterByNameCatalog(String s, String s1);

    public abstract List findListSearchFiltersByCatalogEmpId(String s, Integer integer);

    public abstract IMetaDBQuery findSearchFiltersByCatalogEmpId(String s, Integer integer);

    public abstract List findSearchFilterConditionsByFilterId(int i);

    public abstract void saveSearchFilter(ISearchFilter isearchfilter);

    public abstract void deleteSearchFilter(ISearchFilter isearchfilter);

    public abstract void saveSearchConditions(ISearchFilter isearchfilter, List list);
}
