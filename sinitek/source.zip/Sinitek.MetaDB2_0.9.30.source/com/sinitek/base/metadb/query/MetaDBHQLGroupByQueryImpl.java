// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBHQLGroupByQueryImpl.java

package com.sinitek.base.metadb.query;

import com.sinitek.base.metadb.IEntity;
import java.util.*;

// Referenced classes of package com.sinitek.base.metadb.query:
//            IMetaDBGroupByQuery

public class MetaDBHQLGroupByQueryImpl
    implements IMetaDBGroupByQuery
{

    public MetaDBHQLGroupByQueryImpl(IEntity entity, String queryString, Map paramsMap)
    {
        this.entity = entity;
        this.paramsMap = paramsMap;
        this.queryString = queryString;
    }

    public void setGroupBy(String propertyName)
    {
        setGroupBy(new String[] {
            propertyName
        });
    }

    public void setGroupBy(String propertyNames[])
    {
        groupByProperties = propertyNames;
    }

    public void addCountResult(String s, String s1)
    {
    }

    public void addSumResult(String s, String s1)
    {
    }

    public void addAvgResult(String s, String s1)
    {
    }

    public IEntity getEntity()
    {
        return entity;
    }

    public String getQueryString()
    {
        return queryString;
    }

    public void setFirstResult(int i)
    {
    }

    public void setMaxResult(int i)
    {
    }

    public void setCacheUse(boolean flag)
    {
    }

    public boolean isCacheUse()
    {
        return false;
    }

    public void setParameter(String parameter, Object value)
    {
        paramsMap.put(parameter, value);
    }

    public void setParameters(Map parameters)
    {
        paramsMap.putAll(parameters);
    }

    public Map getParameters()
    {
        return paramsMap;
    }

    public int getResultCount()
    {
        return 0;
    }

    public List getResult()
    {
        return null;
    }

    public void setOrderBy(String s)
    {
    }

    public void setDefaultOrderBy(String s)
    {
    }

    public Iterator getIterator(int fatchSize)
    {
        return null;
    }

    public Iterator getIterator()
    {
        return null;
    }

    public IMetaDBGroupByQuery getGroupByQuery()
    {
        return null;
    }

    private IEntity entity;
    private String queryString;
    private Map paramsMap;
    private String groupByProperties[];
}
