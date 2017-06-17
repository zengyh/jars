// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgCacheManager.java

package com.sinitek.spirit.org.server.cache;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.base.metadb.cache.ObjectOperateType;
import com.sinitek.base.metadb.cache.plugin.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.spirit.org.server.entity.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// Referenced classes of package com.sinitek.spirit.org.server.cache:
//            OrgQueryCache, OrgQuery, PathExpression

public final class OrgCacheManager
{
    private class OrgCacheMetaDBPlugin
        implements IMetaDBCachePlugin, IMetaDBCachePluginEventListener
    {

        public void initData()
        {
        }

        public void saveCacheStatus()
        {
        }

        public void reloadAll()
        {
            cachesLock.readLock().lock();
            Collection cacheCollection = caches.values();
            OrgQueryCache cache;
            for(Iterator i$ = cacheCollection.iterator(); i$.hasNext(); cache.markDirty())
                cache = (OrgQueryCache)i$.next();

            cachesLock.readLock().unlock();
            break MISSING_BLOCK_LABEL_93;
            Exception exception;
            exception;
            cachesLock.readLock().unlock();
            throw exception;
        }

        public void setCacheContext(IMetaDBCacheContext imetadbcachecontext)
        {
        }

        public String[] getCachedEntityNames()
        {
            return (new String[] {
                "ORGRELATIONINFO"
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
            IMetaDBContext context = MetaDBContextFactory.getInstance().createContext();
            for(int i = 0; i < iEntities.length; i++)
            {
                if(objectOperateTypes[i] == ObjectOperateType.INSERT)
                {
                    IOrgRelationInfo relationInfo = (IOrgRelationInfo)context.get(com/sinitek/spirit/org/server/entity/IOrgRelationInfo, ints[i], false);
                    if(relationInfo != null)
                        relationCreated(relationInfo.getFromObjectId(), relationInfo.getToObjectId());
                    continue;
                }
                if(objectOperateTypes[i] == ObjectOperateType.DELETE)
                {
                    IOrgRelationInfo relationInfo = loadBackupOrgRelation(context, ints[i]);
                    relationDeleted(relationInfo.getFromObjectId(), relationInfo.getToObjectId());
                }
            }

            context.close();
            break MISSING_BLOCK_LABEL_151;
            Exception exception;
            exception;
            context.close();
            throw exception;
        }

        private IOrgRelationInfo loadBackupOrgRelation(IMetaDBContext context, int objId)
        {
            String hql = "SELECT * FROM SPRT_ORGRELA_BAK WHERE ORIGOBJID = :objId";
            Map param = new HashMap();
            param.put("objId", Integer.valueOf(objId));
            IMetaDBQuery query = context.createSqlQuery(hql);
            query.setParameters(param);
            Map row = (Map)query.getResult().get(0);
            IOrgRelationInfo orgRelation = new OrgRelationInfoImpl();
            orgRelation.setFromObjectId((String)row.get("fromobjectid"));
            orgRelation.setToObjectId((String)row.get("toobjectid"));
            orgRelation.setRelationType((String)row.get("relationtype"));
            return orgRelation;
        }

        final OrgCacheManager this$0;

        private OrgCacheMetaDBPlugin()
        {
            this$0 = OrgCacheManager.this;
            super();
        }

    }

    private class OrgQueryCacheImpl
        implements OrgQueryCache
    {

        public void markDirty()
        {
            if(dirty)
                break MISSING_BLOCK_LABEL_39;
            lock.lock();
            dirty = true;
            lock.unlock();
            break MISSING_BLOCK_LABEL_39;
            Exception exception;
            exception;
            lock.unlock();
            throw exception;
        }

        public OrgQuery getOrgQuery()
        {
            return orgQuery;
        }

        public List getResult()
        {
            if(!dirty)
                break MISSING_BLOCK_LABEL_112;
            lock.lock();
            if(dirty)
            {
                cachedResult.clear();
                List result = orgQuery.getResult();
                IOrgObject orgObject;
                for(Iterator i$ = result.iterator(); i$.hasNext(); cachedResult.add(orgObject.getOrgId()))
                    orgObject = (IOrgObject)i$.next();

                dirty = false;
            }
            lock.unlock();
            break MISSING_BLOCK_LABEL_112;
            Exception exception;
            exception;
            lock.unlock();
            throw exception;
            return getAllOrgObjects(cachedResult);
        }

        private volatile boolean dirty;
        private final ReentrantLock lock = new ReentrantLock();
        private final OrgQuery orgQuery;
        private final Set cachedResult = new LinkedHashSet();
        final OrgCacheManager this$0;

        public OrgQueryCacheImpl(OrgQuery orgQuery)
        {
            this$0 = OrgCacheManager.this;
            super();
            dirty = true;
            this.orgQuery = orgQuery;
        }
    }

    private class OrgQueryCollection
    {

        Set upQueries;
        Set downQueries;
        final OrgCacheManager this$0;

        private OrgQueryCollection()
        {
            this$0 = OrgCacheManager.this;
            super();
            upQueries = new HashSet();
            downQueries = new HashSet();
        }

    }


    private OrgCacheManager()
    {
        caches = new HashMap();
        attributionMap = new HashMap();
        cachesLock = new ReentrantReadWriteLock();
        MetaDBContextFactory.getInstance().getCacheContext().registCachePlugin(new OrgCacheMetaDBPlugin());
    }

    public static OrgCacheManager getInstance()
    {
        return INSTANCE;
    }

    public void relationCreated(String fromObjectId, String toObjectId)
    {
        cachesLock.readLock().lock();
        analyzeAttribution(fromObjectId, toObjectId);
        cachesLock.readLock().unlock();
        break MISSING_BLOCK_LABEL_42;
        Exception exception;
        exception;
        cachesLock.readLock().unlock();
        throw exception;
    }

    public void relationDeleted(String fromObjectId, String toObjectId)
    {
        cachesLock.readLock().lock();
        analyzeAttribution(fromObjectId, toObjectId);
        cachesLock.readLock().unlock();
        break MISSING_BLOCK_LABEL_42;
        Exception exception;
        exception;
        cachesLock.readLock().unlock();
        throw exception;
    }

    public List getAllOrgObjects(Collection orgIds)
    {
        IMetaDBContext context = MetaDBContextFactory.getInstance().createContext();
        List list;
        List orgObjects = new LinkedList();
        String orgId;
        for(Iterator i$ = orgIds.iterator(); i$.hasNext(); orgObjects.add(context.load(com/sinitek/spirit/org/server/entity/IOrgObject, "orgid", orgId)))
            orgId = (String)i$.next();

        list = orgObjects;
        context.close();
        return list;
        Exception exception;
        exception;
        context.close();
        throw exception;
    }

    public OrgQueryCache getCache(OrgQuery query)
    {
        cachesLock.readLock().lock();
        if(caches.containsKey(query))
            break MISSING_BLOCK_LABEL_121;
        cachesLock.readLock().unlock();
        cachesLock.writeLock().lock();
        if(!caches.containsKey(query))
        {
            caches.put(query, new OrgQueryCacheImpl(query));
            setAttribution(query);
        }
        cachesLock.readLock().lock();
        cachesLock.writeLock().unlock();
        break MISSING_BLOCK_LABEL_121;
        Exception exception;
        exception;
        cachesLock.readLock().lock();
        cachesLock.writeLock().unlock();
        throw exception;
        OrgQueryCache orgquerycache = (OrgQueryCache)caches.get(query);
        cachesLock.readLock().unlock();
        return orgquerycache;
        Exception exception1;
        exception1;
        cachesLock.readLock().unlock();
        throw exception1;
    }

    private void setAttribution(OrgQuery query)
    {
        String fromObjectId = query.getFromObjectId();
        OrgQueryCollection queryCollection = (OrgQueryCollection)attributionMap.get(fromObjectId);
        if(queryCollection == null)
        {
            queryCollection = new OrgQueryCollection();
            attributionMap.put(fromObjectId, queryCollection);
        }
        String direction = query.getPathExpression().getDirection();
        if("UP".equals(direction))
            queryCollection.upQueries.add(query);
        else
            queryCollection.downQueries.add(query);
    }

    private void analyzeAttribution(String fromObjectId, String toObjectId)
    {
        if(attributionMap.get(fromObjectId) != null)
            markDirty(((OrgQueryCollection)attributionMap.get(fromObjectId)).downQueries);
        if(attributionMap.get(toObjectId) != null)
            markDirty(((OrgQueryCollection)attributionMap.get(toObjectId)).upQueries);
    }

    private void markDirty(Set queries)
    {
        OrgQuery query;
        for(Iterator i$ = queries.iterator(); i$.hasNext(); ((OrgQueryCache)caches.get(query)).markDirty())
            query = (OrgQuery)i$.next();

    }

    private static final OrgCacheManager INSTANCE = new OrgCacheManager();
    private HashMap caches;
    private HashMap attributionMap;
    private ReentrantReadWriteLock cachesLock;



}
