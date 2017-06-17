// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBHQLQueryImpl.java

package com.sinitek.base.metadb.query;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

// Referenced classes of package com.sinitek.base.metadb.query:
//            QueryBatchIterator, IMetaDBQuery, IMetaDBGroupByQuery

public class MetaDBHQLQueryImpl
    implements IMetaDBQuery
{

    public MetaDBHQLQueryImpl(IEntity entity, String queryStr, int defaultIterateSize)
    {
        firstResult = 0;
        maxResult = 0x7fffffff;
        cacheUse = true;
        paramsMap = new HashMap();
        this.entity = entity;
        this.queryStr = queryStr;
        this.defaultIterateSize = defaultIterateSize;
    }

    protected Query createDataQuery(Session session)
    {
        if(cacheUse && entity.isIdCacheSupport())
            return session.createQuery((new StringBuilder()).append("select id from ").append(entity.getEntityName()).append(" where ").append(queryStr).append(genOrderByStr()).toString());
        else
            return session.createQuery((new StringBuilder()).append("from ").append(entity.getEntityName()).append(" where ").append(queryStr).append(genOrderByStr()).toString());
    }

    protected Query createCountQuery(Session session)
    {
        return session.createQuery((new StringBuilder()).append("select count(*) from ").append(entity.getEntityName()).append(" where ").append(queryStr).toString());
    }

    protected String genOrderByStr()
    {
        if(StringUtils.isBlank(orderBy))
        {
            if(StringUtils.isBlank(defaultOrderBy))
                return "";
            else
                return (new StringBuilder()).append(" order by ").append(defaultOrderBy).toString();
        } else
        {
            return (new StringBuilder()).append(" order by ").append(orderBy).toString();
        }
    }

    public IEntity getEntity()
    {
        return entity;
    }

    public String getQueryString()
    {
        return queryStr;
    }

    public void setFirstResult(int firstResult)
    {
        this.firstResult = firstResult;
    }

    public void setMaxResult(int maxResult)
    {
        this.maxResult = maxResult;
    }

    public boolean isCacheUse()
    {
        return cacheUse;
    }

    public void setCacheUse(boolean cacheUse)
    {
        this.cacheUse = cacheUse;
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

    public void setDefaultOrderBy(String defaultOrderBy)
    {
        this.defaultOrderBy = defaultOrderBy;
    }

    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

    public int getResultCount()
    {
        IMetaDBContext ctx = null;
        int i;
        try
        {
            ctx = MetaDBContextFactory.getInstance().createContext();
            cacheUse = cacheUse && MetaDBContextFactory.getInstance().getCacheContext() != null;
            Query query = createCountQuery(ctx.getSession());
            Map _temp = new HashMap();
            for(Iterator iter = paramsMap.entrySet().iterator(); iter.hasNext();)
            {
                java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
                if(entry.getValue() == null)
                    query.setParameter((String)entry.getKey(), entry.getValue());
                else
                    _temp.put(entry.getKey(), entry.getValue());
            }

            query.setProperties(_temp);
            List ret = query.list();
            i = ((Number)ret.get(0)).intValue();
        }
        catch(Exception e)
        {
            throw new MetaDBCoreException("0033", e, new Object[] {
                entity != null ? entity.getEntityName() : "SQL", queryStr
            });
        }
        if(ctx != null)
            ctx.close();
        return i;
        Exception exception;
        exception;
        if(ctx != null)
            ctx.close();
        throw exception;
    }

    public List getResult()
    {
        IMetaDBContext ctx = null;
        Query query;
        List list1;
        ctx = MetaDBContextFactory.getInstance().createContext();
        cacheUse = cacheUse && MetaDBContextFactory.getInstance().getCacheContext() != null;
        query = createDataQuery(ctx.getSession());
        Map _temp = new HashMap();
        for(Iterator iter = paramsMap.entrySet().iterator(); iter.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
            if(entry.getValue() == null)
                query.setParameter((String)entry.getKey(), entry.getValue());
            else
                _temp.put(entry.getKey(), entry.getValue());
        }

        query.setProperties(_temp);
        if(firstResult > 0)
            query.setFirstResult(firstResult);
        if(maxResult < 0x7fffffff)
            query.setMaxResults(maxResult);
        if(!cacheUse || !entity.isIdCacheSupport())
            break MISSING_BLOCK_LABEL_320;
        List result = query.list();
        List ret = new ArrayList(result.size());
        int objId;
        for(Iterator iter = result.iterator(); iter.hasNext(); ret.add(MetaDBContextFactory.getInstance().getCacheContext().loadFromCache(entity, objId)))
            objId = ((Number)iter.next()).intValue();

        list1 = ret;
        if(ctx != null)
            ctx.close();
        return list1;
        List list;
        try
        {
            list = query.list();
        }
        catch(Exception e)
        {
            throw new MetaDBCoreException("0034", e, new Object[] {
                entity != null ? entity.getEntityName() : "SQL", queryStr
            });
        }
        if(ctx != null)
            ctx.close();
        return list;
        Exception exception;
        exception;
        if(ctx != null)
            ctx.close();
        throw exception;
    }

    public Iterator getIterator(int fatchSize)
    {
        return new QueryBatchIterator(fatchSize, cacheUse, entity, firstResult, maxResult, queryStr, paramsMap);
    }

    public Iterator getIterator()
    {
        return getIterator(defaultIterateSize);
    }

    public IMetaDBGroupByQuery getGroupByQuery()
    {
        return null;
    }

    protected int defaultIterateSize;
    protected int firstResult;
    protected int maxResult;
    protected boolean cacheUse;
    protected String queryStr;
    protected IEntity entity;
    protected String orderBy;
    protected String defaultOrderBy;
    protected Map paramsMap;
}
