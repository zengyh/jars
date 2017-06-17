// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SQLQueryBatchIterator.java

package com.sinitek.base.metadb.query;

import com.sinitek.base.metadb.IEntity;
import java.util.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;

// Referenced classes of package com.sinitek.base.metadb.query:
//            QueryBatchIterator

public class SQLQueryBatchIterator extends QueryBatchIterator
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
                if(alias != null && tuple[i] != null && !aliases[i].equalsIgnoreCase("rownum_"))
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


    public SQLQueryBatchIterator(int batchSize, boolean cacheUse, IEntity entity, int firstResult, int maxResult, String queryStr, Map params)
    {
        super(batchSize, cacheUse, entity, firstResult, maxResult, queryStr, params);
    }

    protected Query createQuery(Session session)
    {
        Query query = session.createSQLQuery(queryStr);
        query.setResultTransformer(new MapResultTransformer());
        query.setProperties(params);
        return query;
    }

    protected List getQueryList(Query query)
    {
        return query.list();
    }
}
