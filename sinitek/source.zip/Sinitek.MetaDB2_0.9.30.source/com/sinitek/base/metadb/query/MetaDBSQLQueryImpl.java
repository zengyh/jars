// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBSQLQueryImpl.java

package com.sinitek.base.metadb.query;

import com.sinitek.base.metadb.IEntity;
import java.util.*;
import org.hibernate.*;
import org.hibernate.transform.ResultTransformer;

// Referenced classes of package com.sinitek.base.metadb.query:
//            MetaDBHQLQueryImpl, SQLQueryBatchIterator

public class MetaDBSQLQueryImpl extends MetaDBHQLQueryImpl
{
    protected static class IgnoreCaseMap extends Hashtable
    {

        public synchronized Object get(Object key)
        {
            return super.get(key.toString().toLowerCase());
        }

        public synchronized Object put(Object key, Object value)
        {
            return super.put(key.toString().toLowerCase(), value);
        }

        public IgnoreCaseMap(int initialCapacity)
        {
            super(initialCapacity);
        }
    }

    protected static class MapResultTransformer
        implements ResultTransformer
    {

        public Object transformTuple(Object tuple[], String aliases[])
        {
            Map result = new IgnoreCaseMap(tuple.length);
            for(int i = 0; i < tuple.length; i++)
            {
                String alias = aliases[i];
                if(alias != null && tuple[i] != null)
                    result.put(alias, tuple[i]);
            }

            return result;
        }

        public List transformList(List collection)
        {
            return collection;
        }

        protected MapResultTransformer()
        {
        }
    }


    public MetaDBSQLQueryImpl(IEntity entity, String queryStr, int defaultIterateSize)
    {
        super(entity, queryStr, defaultIterateSize);
        cacheUse = false;
    }

    protected Query createCountQuery(Session session)
    {
        return session.createSQLQuery((new StringBuilder()).append("select count(*) from (").append(queryStr).append(") a_").toString());
    }

    protected Query createDataQuery(Session session)
    {
        SQLQuery query = session.createSQLQuery((new StringBuilder()).append(queryStr).append(genOrderByStr()).toString());
        query.setResultTransformer(new MapResultTransformer());
        return query;
    }

    public void setCacheUse(boolean cacheUse)
    {
        if(cacheUse)
        {
            throw new IllegalArgumentException("SQL\u8BED\u53E5\u67E5\u8BE2\u4E0D\u652F\u6301\u7F13\u5B58");
        } else
        {
            super.setCacheUse(cacheUse);
            return;
        }
    }

    public Iterator getIterator(int fatchSize)
    {
        return new SQLQueryBatchIterator(fatchSize, cacheUse, entity, firstResult, maxResult, queryStr, paramsMap);
    }
}
