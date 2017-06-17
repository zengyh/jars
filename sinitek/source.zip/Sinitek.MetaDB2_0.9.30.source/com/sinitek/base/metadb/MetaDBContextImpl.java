// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBContextImpl.java

package com.sinitek.base.metadb;

import com.sinitek.base.common.util.DBUtils;
import com.sinitek.base.metadb.cache.IMetaDBCacheContextImpl;
import com.sinitek.base.metadb.cache.MetaObjectUpdateBean;
import com.sinitek.base.metadb.cache.ObjectOperateType;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.query.MetaDBHQLQueryImpl;
import com.sinitek.base.metadb.query.MetaDBSQLQueryImpl;
import com.sinitek.base.metadb.util.MetaObjectBuilder;
import java.lang.reflect.Field;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Expression;

// Referenced classes of package com.sinitek.base.metadb:
//            ISQLUpdaterImpl, MetaDBCoreException, IMetaObject, IProperty, 
//            MetaDBException, IMetaDBContext, IEntity, MetaDBContextFactory, 
//            MetaDBContextUtil, MetaDBLoggerFactory, ISQLUpdater, IMetaObjectImpl

public class MetaDBContextImpl
    implements IMetaDBContext
{

    public MetaDBContextImpl(int id, Session session, IMetaDBCacheContextImpl cacheContext, boolean isAutoCommit)
    {
        isBegin = false;
        isClosed = false;
        this.isAutoCommit = false;
        isCommitCalled = false;
        isRollbackCalled = false;
        isFlushOnOperation = true;
        operationObjects = new ArrayList();
        this.id = Integer.valueOf(id);
        this.session = session;
        this.isAutoCommit = isAutoCommit;
        this.cacheContext = cacheContext;
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("\u521B\u5EFA\u5168\u65B0\u7684MetaDB\u4F1A\u8BDD\uFF0Cid=[").append(id).append("]").toString());
    }

    public int getId()
    {
        return id.intValue();
    }

    public Session getSession()
    {
        checkClose();
        return session;
    }

    public IMetaObject createNewMetaObject(IEntity entity)
    {
        if(entity == null)
            throw new NullPointerException("\u8F93\u5165\u7684\u5B9E\u4F53\u4E3A\u7A7A");
        else
            return MetaObjectBuilder.createNewMetaObject(entity);
    }

    public IMetaObject createNewMetaObject(String entityName)
    {
        IEntity entity = MetaDBContextFactory.getInstance().getEntity(entityName);
        return createNewMetaObject(entity);
    }

    public IMetaObject createNewMetaObject(Class clz)
    {
        return createNewMetaObject(getEntityName(clz));
    }

    public List query(IEntity entity, String where, Map params)
        throws MetaDBException
    {
        checkClose();
        IMetaDBQuery query = createQuery(entity, where);
        query.setParameters(params != null ? params : Collections.EMPTY_MAP);
        return query.getResult();
    }

    public List query(String entityName, String where, Map params)
        throws MetaDBException
    {
        IEntity entity = MetaDBContextFactory.getInstance().getEntity(entityName);
        return query(entity, where, params);
    }

    public List query(Class clz, String where, Map params)
        throws MetaDBException
    {
        return query(getEntityName(clz), where, params);
    }

    public List query(String hql, Map params)
        throws MetaDBException
    {
        checkClose();
        Query query = session.createQuery(hql);
        if(params != null)
        {
            java.util.Map.Entry entry;
            for(Iterator i$ = params.entrySet().iterator(); i$.hasNext(); query.setParameter((String)entry.getKey(), entry.getValue()))
            {
                Object o = i$.next();
                entry = (java.util.Map.Entry)o;
            }

        }
        return query.list();
    }

    public IMetaDBQuery createQuery(IEntity entity, String queryString)
        throws MetaDBException
    {
        return new MetaDBHQLQueryImpl(entity, queryString, defaultIteratorFatchSize);
    }

    public IMetaDBQuery createQuery(String entityName, String queryString)
        throws MetaDBException
    {
        IEntity entity = MetaDBContextFactory.getInstance().getEntity(entityName);
        return createQuery(entity, queryString);
    }

    public IMetaDBQuery createQuery(Class clz, String queryString)
        throws MetaDBException
    {
        return createQuery(getEntityName(clz), queryString);
    }

    public IMetaDBQuery createSqlQuery(String sql)
        throws MetaDBException
    {
        return new MetaDBSQLQueryImpl(null, sql, defaultIteratorFatchSize);
    }

    public ISQLUpdater createSqlUpdater(String updateSql)
    {
        return new ISQLUpdaterImpl(updateSql);
    }

    public void begin()
        throws MetaDBException
    {
        checkClose();
        if(!isBegin)
        {
            transaction = session.beginTransaction();
            transaction.begin();
            isBegin = true;
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("MetaDB\u4F1A\u8BDD[").append(id).append("]\u5F00\u542F\u4E8B\u52A1").toString());
        }
    }

    public void commit()
        throws MetaDBException
    {
        Exception exception;
        checkClose();
        if(!isBegin)
            return;
        try
        {
            transaction.commit();
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("MetaDB\u4F1A\u8BDD[").append(id).append("]\u4E8B\u52A1\u88AB\u63D0\u4EA4").toString());
            cacheContext.notifyAfterCommit(operationObjects);
        }
        catch(HibernateException e)
        {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("MetaDB\u4F1A\u8BDD[").append(id).append("]\u4E8B\u52A1\u63D0\u4EA4\u5931\u8D25").toString(), e);
            throw new MetaDBCoreException("0023", e, new Object[] {
                id
            });
        }
        finally
        {
            isBegin = false;
        }
        isBegin = false;
        isCommitCalled = true;
        break MISSING_BLOCK_LABEL_168;
        isCommitCalled = true;
        throw exception;
    }

    public void rollback()
        throws MetaDBException
    {
        checkClose();
        if(transaction != null)
            transaction.rollback();
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("MetaDB\u4F1A\u8BDD[").append(id).append("]\u4E8B\u52A1\u88AB\u56DE\u6EDA").toString());
        isBegin = false;
        isRollbackCalled = true;
        break MISSING_BLOCK_LABEL_136;
        HibernateException e;
        e;
        LOGGER.error((new StringBuilder()).append("MetaDB\u4F1A\u8BDD[").append(id).append("]\u4E8B\u52A1\u56DE\u6EDA\u5931\u8D25").toString(), e);
        isBegin = false;
        isRollbackCalled = true;
        break MISSING_BLOCK_LABEL_136;
        Exception exception;
        exception;
        isBegin = false;
        isRollbackCalled = true;
        throw exception;
    }

    public IMetaObject load(IEntity entity, int id)
        throws MetaDBException
    {
        return load(entity, id, true);
    }

    public IMetaObject load(IEntity entity, int id, boolean isFromCache)
        throws MetaDBException
    {
        IMetaObject ret;
        checkClose();
        if(isFromCache && cacheContext != null && entity.isIdCacheSupport())
        {
            ret = cacheContext.loadFromCache(entity, id);
            if(ret == null)
                throw new MetaDBCoreException("0032", new Object[] {
                    entity.getEntityName(), Integer.valueOf(id)
                });
            else
                return ret;
        }
        ret = (IMetaObject)session.get(entity.getEntityName(), Integer.valueOf(id));
        if(ret == null)
            throw new MetaDBCoreException("0032", new Object[] {
                entity.getEntityName(), Integer.valueOf(id)
            });
        return ret;
        HibernateException e;
        e;
        throw new MetaDBCoreException("0018", e, new Object[] {
            this.id
        });
    }

    public IMetaObject load(String entityName, int id)
        throws MetaDBException
    {
        return load(entityName, id, true);
    }

    public IMetaObject load(Class clz, int id)
    {
        return load(getEntityName(clz), id);
    }

    public IMetaObject load(String entityName, int id, boolean isFromCache)
        throws MetaDBException
    {
        IEntity entity = MetaDBContextFactory.getInstance().getEntity(entityName);
        return load(entity, id, isFromCache);
    }

    public IMetaObject load(Class clz, int id, boolean isFromCache)
        throws MetaDBException
    {
        return load(getEntityName(clz), id, isFromCache);
    }

    public IMetaObject load(IEntity entity, String uniquePropertyName, Object propValue)
        throws MetaDBException
    {
        return load(entity, uniquePropertyName, propValue, true);
    }

    public IMetaObject load(IEntity entity, String uniquePropertyName, Object propValue, boolean isFromCache)
        throws MetaDBException
    {
        IProperty property;
        checkClose();
        property = checkUniqueProperty(entity, uniquePropertyName);
        if(isFromCache && cacheContext != null && entity.isIdCacheSupport())
        {
            IMetaObject ret = cacheContext.loadFromCache(property, propValue);
            if(ret == null)
                throw new MetaDBCoreException("0031", new Object[] {
                    entity.getEntityName(), uniquePropertyName, propValue
                });
            else
                return ret;
        }
        List ret;
        Criteria criteria = session.createCriteria(entity.getEntityName());
        criteria.add(Expression.eq(property.getPropertyName(), propValue));
        ret = criteria.list();
        if(ret.size() == 0)
            throw new MetaDBCoreException("0031", new Object[] {
                entity.getEntityName(), uniquePropertyName, propValue
            });
        return (IMetaObject)ret.get(0);
        HibernateException e;
        e;
        throw new MetaDBCoreException("0018", e, new Object[] {
            id
        });
    }

    public IMetaObject load(Class clz, String uniquePropertyName, Object propValue, boolean isFromCache)
        throws MetaDBException
    {
        return load(getEntityName(clz), uniquePropertyName, propValue, isFromCache);
    }

    public IMetaObject load(String entityName, String uniquePropertyName, Object propValue)
        throws MetaDBException
    {
        return load(entityName, uniquePropertyName, propValue, true);
    }

    public IMetaObject load(Class clz, String uniquePropertyName, Object propValue)
        throws MetaDBException
    {
        return load(getEntityName(clz), uniquePropertyName, propValue);
    }

    public IMetaObject load(String entityName, String uniquePropertyName, Object propValue, boolean isFormCache)
        throws MetaDBException
    {
        IEntity entity = MetaDBContextFactory.getInstance().getEntity(entityName);
        return load(entity, uniquePropertyName, propValue, isFormCache);
    }

    public IMetaObject get(IEntity entity, int id)
        throws MetaDBException
    {
        return get(entity, id, true);
    }

    public IMetaObject get(String entityName, int id)
        throws MetaDBException
    {
        IEntity entity = MetaDBContextFactory.getInstance().getEntity(entityName);
        return get(entity, id);
    }

    public IMetaObject get(Class clz, int id)
        throws MetaDBException
    {
        return get(getEntityName(clz), id);
    }

    public IMetaObject get(IEntity entity, int id, boolean isFormCache)
        throws MetaDBException
    {
        checkClose();
        if(isFormCache && cacheContext != null && entity.isIdCacheSupport())
            return cacheContext.loadFromCache(entity, id);
        return (IMetaObject)session.get(entity.getEntityName(), Integer.valueOf(id));
        HibernateException e;
        e;
        throw new MetaDBCoreException("0018", e, new Object[] {
            this.id
        });
    }

    public IMetaObject get(String entityName, int id, boolean isFromCache)
        throws MetaDBException
    {
        IEntity entity = MetaDBContextFactory.getInstance().getEntity(entityName);
        return get(entity, id, isFromCache);
    }

    public IMetaObject get(Class clz, int id, boolean isFromCache)
        throws MetaDBException
    {
        return get(getEntityName(clz), id, isFromCache);
    }

    public IMetaObject get(IEntity entity, String uniquePropertyName, Object propValue, boolean isFromCache)
        throws MetaDBException
    {
        IProperty property;
        checkClose();
        property = checkUniqueProperty(entity, uniquePropertyName);
        if(isFromCache && cacheContext != null && entity.isIdCacheSupport())
            return cacheContext.loadFromCache(property, propValue);
        List ret;
        Criteria criteria = session.createCriteria(entity.getEntityName());
        criteria.add(Expression.eq(property.getPropertyName(), propValue));
        ret = criteria.list();
        if(ret.size() == 0)
            return null;
        return (IMetaObject)ret.get(0);
        HibernateException e;
        e;
        throw new MetaDBCoreException("0018", e, new Object[] {
            id
        });
    }

    public IMetaObject get(IEntity entity, String uniquePropertyName, Object propValue)
        throws MetaDBException
    {
        return get(entity, uniquePropertyName, propValue, true);
    }

    public IMetaObject get(String entityName, String uniquePropertyName, Object propValue)
        throws MetaDBException
    {
        return get(entityName, uniquePropertyName, propValue, true);
    }

    public IMetaObject get(Class clz, String uniquePropertyName, Object propValue)
        throws MetaDBException
    {
        return get(clz, uniquePropertyName, propValue, true);
    }

    public IMetaObject get(String entityName, String uniquePropertyName, Object propValue, boolean isFromCache)
        throws MetaDBException
    {
        IEntity entity = MetaDBContextFactory.getInstance().getEntity(entityName);
        return get(entity, uniquePropertyName, propValue, isFromCache);
    }

    public IMetaObject get(Class clz, String uniquePropertyName, Object propValue, boolean isFromCache)
        throws MetaDBException
    {
        return get(getEntityName(clz), uniquePropertyName, propValue, isFromCache);
    }

    public IMetaObject get(String entityName, Map valueMap)
        throws MetaDBException
    {
        String propertyNames[] = new String[valueMap.size()];
        Object propertyValues[] = new Object[valueMap.size()];
        int i = 0;
        for(Iterator i$ = valueMap.entrySet().iterator(); i$.hasNext();)
        {
            Object o = i$.next();
            java.util.Map.Entry entry = (java.util.Map.Entry)o;
            Object key = entry.getKey();
            if(key instanceof String)
                propertyNames[i] = (String)key;
            else
            if(key instanceof IProperty)
                propertyNames[i] = ((IProperty)key).getPropertyName();
            else
                throw new IllegalArgumentException((new StringBuilder()).append("\u975E\u6CD5\u7684\u5C5E\u6027\u540D[").append(key).append("]").toString());
            propertyValues[i] = entry.getValue();
            i++;
        }

        return get(entityName, propertyNames, propertyValues);
    }

    public IMetaObject get(Class clz, Map valueMap)
        throws MetaDBException
    {
        return get(getEntityName(clz), valueMap);
    }

    public IMetaObject get(String entityName, String propertyNames[], Object propertyValues[])
        throws MetaDBException
    {
        if(propertyNames.length != propertyValues.length)
            throw new IllegalArgumentException("\u67E5\u8BE2\u6761\u4EF6\u5C5E\u6027\u4E0E\u6570\u636E\u503C\u4E0D\u5339\u914D");
        StringBuffer sb = new StringBuffer(" 1=1 ");
        String arr$[] = propertyNames;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String propertyName = arr$[i$];
            sb.append(" and ").append(propertyName).append("=:").append(propertyName);
        }

        IMetaDBQuery query = createQuery(entityName, sb.toString());
        for(int i = 0; i < propertyValues.length; i++)
            query.setParameter(propertyNames[i], propertyValues[i]);

        List list = query.getResult();
        if(null == list || 0 == list.size())
            return null;
        if(1 == list.size())
            return (IMetaObject)list.get(0);
        else
            throw new MetaDBCoreException("0057", new Object[] {
                entityName, sb.toString()
            });
    }

    public IMetaObject get(Class clz, String propertyNames[], Object propertyValues[])
        throws MetaDBException
    {
        return get(getEntityName(clz), propertyNames, propertyValues);
    }

    public transient IMetaObject get(Class clz, String propertyNames, Object propertyValues[])
        throws MetaDBException
    {
        return get(getEntityName(clz), StringUtils.split(propertyNames, ","), propertyValues);
    }

    public IMetaObject loadHistoryObject(IEntity entity, int lastId)
        throws MetaDBException
    {
        checkClose();
        return (IMetaObject)session.get((new StringBuilder()).append(entity.getEntityName()).append("_his").toString(), Integer.valueOf(lastId));
    }

    public IMetaObject loadHistoryObject(String entityName, int lastId)
        throws MetaDBException
    {
        IEntity entity = MetaDBContextFactory.getInstance().getEntity(entityName);
        return loadHistoryObject(entity, lastId);
    }

    public IMetaObject loadHistoryObject(Class clz, int lastId)
        throws MetaDBException
    {
        return loadHistoryObject(getEntityName(clz), lastId);
    }

    public void close()
        throws MetaDBException
    {
        Exception exception;
        MetaDBContextFactory factory = MetaDBContextFactory.getInstance();
        if(!isClosed)
        {
            try
            {
                if(isBegin && factory.isNeedCloseCall(this))
                    onClose();
            }
            catch(MetaDBException me)
            {
                LOGGER.warn((new StringBuilder()).append("MetaDB\u4F1A\u8BDD").append(id).append("\u5728\u5173\u95ED\u524D\u56DE\u8C03\u629B\u51FA\u5F02\u5E38").toString(), me);
                throw me;
            }
            finally
            {
                isClosed = factory.closeContext(this);
            }
            isClosed = factory.closeContext(this);
        }
        break MISSING_BLOCK_LABEL_91;
        throw exception;
    }

    public void saveBatch(List data)
    {
        MetaDBContextUtil.saveBatch(data);
    }

    public void removeBatch(List data)
    {
        MetaDBContextUtil.removeBatch(data);
    }

    public void deleteBatch(List data)
    {
        MetaDBContextUtil.deleteBatch(data);
    }

    public int nextId(String entityName)
    {
        return DBUtils.nextId(entityName, MetaDBContextFactory.getInstance().getDataSourceProvider());
    }

    void addObjectUpdate(IMetaObjectImpl mo, ObjectOperateType operType, IEntity entity, int objId)
    {
        operationObjects.add(new MetaObjectUpdateBean(mo, operType, entity, objId));
    }

    private void checkClose()
        throws MetaDBCoreException
    {
        if(isClosed)
            throw new MetaDBCoreException("0024", new Object[] {
                id
            });
        else
            return;
    }

    private IProperty checkUniqueProperty(IEntity entity, String propName)
        throws MetaDBCoreException
    {
        IProperty property = entity.getProperty(propName);
        if(!property.isUnique())
            throw new MetaDBCoreException("0030", new Object[] {
                entity.getEntityName(), propName
            });
        else
            return property;
    }

    private void onClose()
        throws MetaDBException
    {
        if(isAutoCommit && !isCommitCalled && !isRollbackCalled)
        {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("MetaDB\u4F1A\u8BDD[").append(id).append("]\u5728\u5173\u95ED\u524D\u81EA\u52A8\u63D0\u4EA4").toString());
            commit();
        } else
        if(!isAutoCommit && !isRollbackCalled && !isRollbackCalled)
        {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("MetaDB\u4F1A\u8BDD[").append(id).append("]\u5728\u5173\u95ED\u524D\u81EA\u52A8\u56DE\u6EDA").toString());
            rollback();
        }
    }

    private String getEntityName(Class clz)
    {
        return (String)clz.getField("ENTITY_NAME").get(null);
        Exception e;
        e;
        throw new MetaDBCoreException("0045", new Object[] {
            clz.getName()
        });
    }

    public int getDefaultIteratorFatchSize()
    {
        return defaultIteratorFatchSize;
    }

    public void setDefaultIteratorFatchSize(int defaultIteratorFatchSize)
    {
        this.defaultIteratorFatchSize = defaultIteratorFatchSize;
    }

    public boolean isFlushOnOperation()
    {
        return isFlushOnOperation;
    }

    public void setFlushOnOperation(boolean flushOnOperation)
    {
        isFlushOnOperation = flushOnOperation;
    }

    private Integer id;
    private int defaultIteratorFatchSize;
    IMetaDBCacheContextImpl cacheContext;
    private Session session;
    private Transaction transaction;
    private boolean isBegin;
    private boolean isClosed;
    private boolean isAutoCommit;
    private boolean isCommitCalled;
    private boolean isRollbackCalled;
    private boolean isFlushOnOperation;
    private List operationObjects;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CORE;
    }
}
