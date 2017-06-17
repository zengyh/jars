// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBTemplate.java

package com.sinitek.base.metadb;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.query.OtherDBSQLQueryImpl;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBContextImpl, MetaDBCoreException, ISQLUpdaterImpl, IEntity, 
//            IMetaDBContext, MetaDBContextFactory, IMetaDBTemplateExecutor, MetaDBContextUtil, 
//            IMetaObject, ISQLUpdater

public class MetaDBTemplate
{

    public MetaDBTemplate()
    {
        this(MetaDBContextFactory.AUTO);
    }

    public MetaDBTemplate(MetaDBContextFactory.CreateType createType)
    {
        this.createType = createType;
    }

    public IEntity getEntity(String entityName)
    {
        return MetaDBContextFactory.getInstance().getEntity(entityName);
    }

    public IMetaObject createNewMetaObject(IEntity entity)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.createNewMetaObject(entity);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject createNewMetaObject(Class clz)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.createNewMetaObject(clz);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject createNewMetaObject(String entityName)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.createNewMetaObject(entityName);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject load(IEntity entity, int id)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.load(entity, id);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject load(IEntity entity, int id, boolean isFromCache)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.load(entity, id, isFromCache);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject load(String entityName, int id)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.load(entityName, id);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject load(Class clz, int id)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.load(clz, id);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject load(String entityName, int id, boolean isFromCache)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.load(entityName, id, isFromCache);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject load(Class clz, int id, boolean isFromCache)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.load(clz, id, isFromCache);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject load(IEntity entity, String uniquePropertyName, Object propValue)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.load(entity, uniquePropertyName, propValue);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject load(IEntity entity, String uniquePropertyName, Object propValue, boolean isFromCache)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.load(entity, uniquePropertyName, propValue, isFromCache);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject load(String entityName, String uniquePropertyName, Object propValue)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.load(entityName, uniquePropertyName, propValue);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject load(Class clz, String uniquePropertyName, Object propValue)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.load(clz, uniquePropertyName, propValue);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject load(String entityName, String uniquePropertyName, Object propValue, boolean isFromCache)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.load(entityName, uniquePropertyName, propValue, isFromCache);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject load(Class clz, String uniquePropertyName, Object propValue, boolean isFromCache)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.load(clz, uniquePropertyName, propValue, isFromCache);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject loadHistoryObject(IEntity entity, int lastId)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.loadHistoryObject(entity, lastId);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject loadHistoryObject(String entityName, int lastId)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.loadHistoryObject(entityName, lastId);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject loadHistoryObject(Class clz, int lastId)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.loadHistoryObject(clz, lastId);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(IEntity entity, int id)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(entity, id);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(IEntity entity, int id, boolean isFromCache)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(entity, id, isFromCache);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(String entityName, int id)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(entityName, id);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(Class clz, int id)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(clz, id);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(String entityName, int id, boolean isFromCache)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(entityName, id, isFromCache);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(Class clz, int id, boolean isFromCache)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(clz, id, isFromCache);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(IEntity entity, String uniquePropertyName, Object propValue)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(entity, uniquePropertyName, propValue);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(IEntity entity, String uniquePropertyName, Object propValue, boolean isFromCache)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(entity, uniquePropertyName, propValue, isFromCache);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(String entityName, String uniquePropertyName, Object propValue)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(entityName, uniquePropertyName, propValue);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(Class clz, String uniquePropertyName, Object propValue)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(clz, uniquePropertyName, propValue);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(String entityName, String uniquePropertyName, Object propValue, boolean isFromCache)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(entityName, uniquePropertyName, propValue, isFromCache);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(Class clz, String uniquePropertyName, Object propValue, boolean isFromCache)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(clz, uniquePropertyName, propValue, isFromCache);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(String entityName, Map valueMap)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(entityName, valueMap);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(Class clz, Map valueMap)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(clz, valueMap);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(String entityName, String propertyNames[], Object propertyValues[])
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(entityName, propertyNames, propertyValues);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaObject get(Class clz, String propertyNames[], Object propertyValues[])
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(clz, propertyNames, propertyValues);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public transient IMetaObject get(Class clz, String propertyNames, Object propertyValues[])
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaObject imetaobject = ctx.get(clz, propertyNames, propertyValues);
        ctx.close();
        return imetaobject;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public List query(IEntity entity, String where, Map params)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        List list = ctx.query(entity, where, params);
        ctx.close();
        return list;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public List query(String entityName, String where, Map params)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        List list = ctx.query(entityName, where, params);
        ctx.close();
        return list;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public List query(Class clz, String where, Map params)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        List list = ctx.query(clz, where, params);
        ctx.close();
        return list;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public List query(String hql, Map params)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        List list = ctx.query(hql, params);
        ctx.close();
        return list;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaDBQuery createQuery(IEntity entity, String queryString)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaDBQuery imetadbquery = ctx.createQuery(entity, queryString);
        ctx.close();
        return imetadbquery;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaDBQuery createQuery(Class clz, String queryString)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaDBQuery imetadbquery = ctx.createQuery(clz, queryString);
        ctx.close();
        return imetadbquery;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaDBQuery createQuery(String entityName, String queryString)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaDBQuery imetadbquery = ctx.createQuery(entityName, queryString);
        ctx.close();
        return imetadbquery;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaDBQuery createSqlQuery(String sql)
    {
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext(createType);
        IMetaDBQuery imetadbquery = ctx.createSqlQuery(sql);
        ctx.close();
        return imetadbquery;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public IMetaDBQuery createSqlQuery(DataSource ds, String sql)
    {
        return new OtherDBSQLQueryImpl(ds, sql);
    }

    public void callExecutor(IMetaDBTemplateExecutor executor)
    {
        Exception exception;
        MetaDBContextFactory factory = MetaDBContextFactory.getInstance();
        IMetaDBContext ctx = factory.createContext(createType);
        try
        {
            ctx.begin();
            executor.doInTransaction(ctx);
            if(factory.isNeedCloseCall((MetaDBContextImpl)ctx))
                ctx.commit();
        }
        catch(BaseException be)
        {
            ctx.rollback();
            throw be;
        }
        catch(Exception ex)
        {
            ctx.rollback();
            throw new MetaDBCoreException("0038", ex);
        }
        finally
        {
            ctx.close();
        }
        ctx.close();
        break MISSING_BLOCK_LABEL_94;
        throw exception;
    }

    public ISQLUpdater createSqlUpdater(String updateSql)
    {
        return new ISQLUpdaterImpl(updateSql);
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

    private MetaDBContextFactory.CreateType createType;
}
