// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QueryBatchIterator.java

package com.sinitek.base.metadb.query;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import java.util.*;
import org.hibernate.*;

public class QueryBatchIterator
    implements Iterator
{

    public QueryBatchIterator(int batchSize, boolean cacheUse, IEntity entity, int firstResult, int maxResult, String queryStr, Map params)
    {
        this.batchSize = batchSize;
        this.cacheUse = cacheUse;
        this.entity = entity;
        this.firstResult = firstResult;
        this.maxResult = maxResult;
        this.params = params;
        this.queryStr = queryStr;
        readCount = firstResult;
    }

    public void remove()
    {
        throw new UnsupportedOperationException("\u67E5\u8BE2\u7ED3\u679C\u8FED\u4EE3\u5668\uFF0C\u4E0D\u652F\u6301\u5220\u9664\u64CD\u4F5C");
    }

    public boolean hasNext()
    {
        IMetaDBContext context;
        if(batchResult != null && currentPos != batchSize)
            break MISSING_BLOCK_LABEL_159;
        context = null;
        try
        {
            context = MetaDBContextFactory.getInstance().createContext();
            Query query = createQuery(context.getSession());
            query.setFirstResult(readCount);
            query.setMaxResults(Math.min(readCount + batchSize, maxResult));
            batchResult = getQueryList(query);
            currentPos = 0;
        }
        catch(HibernateException e)
        {
            throw new MetaDBCoreException("0034", e, new Object[] {
                entity != null ? entity.getEntityName() : "SQL", queryStr
            });
        }
        if(context != null)
            context.close();
        break MISSING_BLOCK_LABEL_159;
        Exception exception;
        exception;
        if(context != null)
            context.close();
        throw exception;
        return currentPos < batchResult.size();
    }

    public Object next()
    {
        if(!hasNext())
        {
            throw new NoSuchElementException("\u8FED\u4EE3\u5668\u5DF2\u7ECF\u5230\u8FBE\u5E95\u90E8");
        } else
        {
            readCount++;
            return batchResult.get(currentPos++);
        }
    }

    protected Query createQuery(Session session)
    {
        String queryString;
        if(cacheUse && entity.isIdCacheSupport())
            queryString = (new StringBuilder()).append("select id from ").append(entity.getEntityName()).append(" where ").append(queryStr).toString();
        else
            queryString = (new StringBuilder()).append("from ").append(entity.getEntityName()).append(" where ").append(queryStr).toString();
        Query query = session.createQuery(queryString);
        query.setProperties(params);
        return query;
    }

    protected List getQueryList(Query query)
    {
        if(cacheUse && entity.isIdCacheSupport())
        {
            List _tmp = query.list();
            List ret = new ArrayList(_tmp.size());
            int objId;
            for(Iterator iter = _tmp.iterator(); iter.hasNext(); ret.add(MetaDBContextFactory.getInstance().getCacheContext().loadFromCache(entity, objId)))
                objId = ((Integer)iter.next()).intValue();

            return ret;
        } else
        {
            return query.list();
        }
    }

    protected String queryStr;
    protected IEntity entity;
    protected boolean cacheUse;
    protected Map params;
    protected int firstResult;
    protected int maxResult;
    protected int currentPos;
    protected int readCount;
    protected int batchSize;
    protected List batchResult;
}
