// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectIdCache.java

package com.sinitek.base.metadb.cache.impl;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.cache.*;
import java.io.Serializable;
import java.util.*;
import org.apache.commons.collections.map.AbstractLinkedMap;
import org.apache.commons.collections.map.LRUMap;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

// Referenced classes of package com.sinitek.base.metadb.cache.impl:
//            MetaObjectProxyFactory

public class MetaObjectIdCache
{
    private static class StaticsLRUMap extends LRUMap
    {

        protected void moveToMRU(org.apache.commons.collections.map.AbstractLinkedMap.LinkEntry linkEntry)
        {
            super.moveToMRU(linkEntry);
            hitCount++;
            totalAccessCount++;
            if(MetaObjectIdCache.LOGGER.isDebugEnabled())
                MetaObjectIdCache.LOGGER.debug((new StringBuilder()).append("\u5728\u5B9E\u4F53[").append(property.getEntity().getEntityName()).append("]\u7684[").append(property.getPropertyName()).append("]\u7F13\u5B58\u4E2D\uFF0C\u952E\u503C\u4E3A[").append(linkEntry.getKey()).append("]\u7F13\u5B58\u547D\u4E2D").toString());
        }

        protected boolean removeLRU(org.apache.commons.collections.map.AbstractLinkedMap.LinkEntry linkEntry)
        {
            totalAccessCount++;
            if(MetaObjectIdCache.LOGGER.isDebugEnabled())
                MetaObjectIdCache.LOGGER.debug((new StringBuilder()).append("\u5728\u5B9E\u4F53[").append(property.getEntity().getEntityName()).append("]\u7684[").append(property.getPropertyName()).append("]\u7F13\u5B58\u4E2D\uFF0C\u952E\u503C\u4E3A[").append(linkEntry.getKey()).append("]\u88AB\u66FF\u6362\u51FA\u7F13\u5B58").toString());
            return super.removeLRU(linkEntry);
        }

        public float getHitRate()
        {
            return totalAccessCount != 0L ? hitCount / totalAccessCount : 1.0F;
        }

        long totalAccessCount;
        long hitCount;
        IProperty property;

        private StaticsLRUMap(int i, IProperty property)
        {
            super(i);
            totalAccessCount = 0L;
            hitCount = 0L;
            this.property = property;
        }

    }


    public MetaObjectIdCache(IProperty property)
    {
        lock = 0;
        entity = property.getEntity();
        this.property = property;
        dataCache = new StaticsLRUMap(entity.getIdCacheSize(), property);
    }

    public IMetaObjectImpl get(Object key)
    {
        getLock(true);
        IMetaObjectImpl imetaobjectimpl;
        IMetaObjectImpl bean = null;
        synchronized(dataCache)
        {
            bean = (IMetaObjectImpl)dataCache.get(key);
        }
        if(bean == null)
            bean = exchangeObject(key);
        if(bean == null)
            break MISSING_BLOCK_LABEL_64;
        imetaobjectimpl = MetaObjectProxyFactory.createProxy(bean);
        releaseLock(true);
        return imetaobjectimpl;
        Exception exception1;
        IMetaObjectImpl imetaobjectimpl1;
        try
        {
            imetaobjectimpl1 = null;
        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception e)
        {
            throw new MetaDBCacheException("0001", e, new Object[] {
                entity.getEntityName(), property.getPropertyName(), key
            });
        }
        finally
        {
            releaseLock(true);
        }
        releaseLock(true);
        return imetaobjectimpl1;
        throw exception1;
    }

    public void reloadAll()
    {
        getLock(false);
        IMetaDBContext ctx = null;
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("\u5F00\u59CB\u5237\u65B0\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u7684[").append(property.getPropertyName()).append("]\u5C5E\u6027\u7684\u7F13\u5B58").toString());
        try
        {
            ctx = MetaDBContextFactory.getInstance().createContext(MetaDBContextFactory.CREATE_ONLY);
            List deleteKeys = new ArrayList();
            Map updateMos = new HashMap();
            synchronized(dataCache)
            {
                for(Iterator i$ = dataCache.entrySet().iterator(); i$.hasNext();)
                {
                    Object o = i$.next();
                    java.util.Map.Entry entry = (java.util.Map.Entry)o;
                    IMetaObject mo = (IMetaObject)entry.getValue();
                    if(mo == null)
                    {
                        deleteKeys.add(entry.getKey());
                    } else
                    {
                        IMetaObject newMo;
                        Object key;
                        if(property.getPropertyType() == PropertyType.ID)
                        {
                            newMo = ctx.get(entity, mo.getId(), false);
                            key = new Integer(mo.getId());
                        } else
                        {
                            key = mo.get(property);
                            newMo = ctx.get(entity, property.getPropertyName(), key, false);
                        }
                        if(newMo == null)
                            deleteKeys.add(key);
                        else
                            updateMos.put(key, newMo);
                    }
                }

            }
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("\u5C06\u4ECE\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u7684[").append(property.getPropertyName()).append("]\u5C5E\u6027\u7684\u7F13\u5B58\u4E2D\u79FB\u9664[").append(deleteKeys.size()).append("]\u4E2A\u5BF9\u8C61").toString());
            for(Iterator i$ = deleteKeys.iterator(); i$.hasNext();)
            {
                Object deleteKey = i$.next();
                synchronized(dataCache)
                {
                    dataCache.remove(deleteKey);
                }
            }

            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("\u5C06\u4ECE\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u7684[").append(property.getPropertyName()).append("]\u5C5E\u6027\u7684\u7F13\u5B58\u66F4\u65B0[").append(updateMos.size()).append("]\u4E2A\u5BF9\u8C61").toString());
            for(Iterator i$ = updateMos.entrySet().iterator(); i$.hasNext();)
            {
                Object o = i$.next();
                java.util.Map.Entry entry = (java.util.Map.Entry)o;
                synchronized(dataCache)
                {
                    dataCache.put(entry.getKey(), entry.getValue());
                }
            }

            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("\u5237\u65B0\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u7684[").append(property.getPropertyName()).append("]\u5C5E\u6027\u7684\u6210\u529F").toString());
        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception ex)
        {
            throw new MetaDBCacheException("0002", ex, new Object[] {
                entity.getEntityName(), property.getPropertyName()
            });
        }
        finally
        {
            releaseLock(false);
            if(ctx != null)
                ctx.close();
            throw exception3;
        }
        releaseLock(false);
        if(ctx != null)
            ctx.close();
    }

    public void updateObject(List updateDatas)
    {
        Exception exception1;
        getLock(false);
        try
        {
            for(Iterator i$ = updateDatas.iterator(); i$.hasNext();)
            {
                Object updateData = i$.next();
                MetaObjectUpdateBean updateBean = (MetaObjectUpdateBean)updateData;
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u5BF9\u8C61[").append(updateBean.getUpdateObject().getId()).append("]\u88AB[").append(updateBean.getUpdateType().getEnumItemInfo()).append("]").toString());
                IMetaObjectImpl targetObject = updateBean.getUpdateObject();
                Object origPropValue = updateBean.getUpdateObject().getOrigValue(property);
                synchronized(dataCache)
                {
                    if(updateBean.getUpdateType().equals(ObjectOperateType.INSERT))
                    {
                        if(!entity.hasStreamProperty() && !entity.hasRelaProperty() && !dataCache.isFull())
                        {
                            if(LOGGER.isDebugEnabled())
                                LOGGER.debug("\u7F13\u5B58\u6C60\u672A\u6EE1\uFF0C\u5BF9\u8C61\u653E\u5165\u7F13\u5B58\u6C60");
                            dataCache.put(targetObject.get(property), targetObject);
                        }
                    } else
                    if(updateBean.getUpdateType().equals(ObjectOperateType.UPDATE))
                    {
                        if(dataCache.containsKey(origPropValue))
                            if(!entity.hasStreamProperty() && !entity.hasRelaProperty())
                            {
                                if(LOGGER.isDebugEnabled())
                                    LOGGER.debug("\u5BF9\u8C61\u5728\u7F13\u5B58\u6C60\u4E2D\uFF0C\u66F4\u65B0\u5BF9\u8C61");
                                if(!property.getPropertyType().equals(PropertyType.ID))
                                    dataCache.put(targetObject.get(property), targetObject);
                                else
                                    dataCache.put(new Integer(targetObject.getId()), targetObject);
                            } else
                            {
                                dataCache.remove(origPropValue);
                            }
                    } else
                    if(dataCache.containsKey(origPropValue))
                    {
                        if(LOGGER.isDebugEnabled())
                            LOGGER.debug("\u5BF9\u8C61\u5728\u7F13\u5B58\u6C60\u4E2D\uFF0C\u5220\u9664\u5BF9\u8C61");
                        dataCache.remove(origPropValue);
                    }
                }
            }

        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception e)
        {
            throw new MetaDBCacheException("0002", e, new Object[] {
                entity.getEntityName(), property.getPropertyName()
            });
        }
        finally
        {
            releaseLock(false);
        }
        releaseLock(false);
        break MISSING_BLOCK_LABEL_484;
        throw exception1;
    }

    public void ensureInCache(IMetaObject mo)
    {
        getLock(false);
        if("Id".equals(property.getPropertyName()))
        {
            Integer id = new Integer(mo.getId());
            synchronized(dataCache)
            {
                if(!dataCache.containsKey(id))
                    dataCache.put(id, mo);
            }
        }
        releaseLock(false);
        break MISSING_BLOCK_LABEL_94;
        Exception exception1;
        exception1;
        releaseLock(false);
        throw exception1;
    }

    public IMetaObjectImpl getCurretObject(Object key)
    {
        StaticsLRUMap staticslrumap = dataCache;
        JVM INSTR monitorenter ;
        return (IMetaObjectImpl)dataCache.get(key);
        Exception exception;
        exception;
        throw exception;
    }

    public int getCacheSize()
    {
        StaticsLRUMap staticslrumap = dataCache;
        JVM INSTR monitorenter ;
        return dataCache.size();
        Exception exception;
        exception;
        throw exception;
    }

    public float getCacheHitRate()
    {
        StaticsLRUMap staticslrumap = dataCache;
        JVM INSTR monitorenter ;
        return dataCache.getHitRate();
        Exception exception;
        exception;
        throw exception;
    }

    public long getCacheAccessCount()
    {
        StaticsLRUMap staticslrumap = dataCache;
        JVM INSTR monitorenter ;
        return dataCache.totalAccessCount;
        Exception exception;
        exception;
        throw exception;
    }

    public IEntity getEntity()
    {
        return entity;
    }

    public synchronized IMetaObjectImpl doExchangeObject(Object key)
    {
        getLock(false);
        IMetaObjectImpl imetaobjectimpl = exchangeObject(key);
        releaseLock(false);
        return imetaobjectimpl;
        Exception exception;
        exception;
        releaseLock(false);
        throw exception;
    }

    public IProperty getProperty()
    {
        return property;
    }

    private synchronized void getLock(boolean isReadLock)
    {
        try
        {
            if(isReadLock)
            {
                while(lock < 0) 
                    wait();
                lock++;
            } else
            {
                while(lock != 0) 
                    wait();
                lock = -1;
            }
        }
        catch(InterruptedException e)
        {
            LOGGER.warn("\u9501\u88AB\u6253\u65AD", e);
        }
    }

    private synchronized void releaseLock(boolean isReadLock)
    {
        if(isReadLock)
            lock--;
        else
            lock = 0;
        notifyAll();
    }

    private synchronized IMetaObjectImpl exchangeObject(Object key)
    {
        IMetaDBContext ctx = null;
        Session session;
        List ret;
        ctx = MetaDBContextFactory.getInstance().createContext(MetaDBContextFactory.CREATE_ONLY);
        session = ctx.getSession();
        if(!property.getPropertyType().equals(PropertyType.ID))
            break MISSING_BLOCK_LABEL_116;
        IMetaObjectImpl mo = (IMetaObjectImpl)session.get(entity.getEntityName(), (Serializable)key);
        if(mo != null)
            synchronized(dataCache)
            {
                dataCache.put(key, mo);
            }
        ret = mo;
        if(ctx != null)
            ctx.close();
        return ret;
        IMetaObjectImpl imetaobjectimpl;
        Criteria criteria = session.createCriteria(entity.getEntityName());
        criteria.add(Expression.eq(property.getPropertyName(), key));
        ret = criteria.list();
        if(ret.size() != 0)
            break MISSING_BLOCK_LABEL_189;
        imetaobjectimpl = null;
        if(ctx != null)
            ctx.close();
        return imetaobjectimpl;
        Exception exception2;
        IMetaObjectImpl imetaobjectimpl1;
        try
        {
            IMetaObjectImpl mo = (IMetaObjectImpl)ret.get(0);
            synchronized(dataCache)
            {
                dataCache.put(key, mo);
            }
            imetaobjectimpl1 = mo;
        }
        catch(Exception ex)
        {
            throw new MetaDBCacheException("0001", ex, new Object[] {
                entity.getEntityName(), property.getPropertyName(), key
            });
        }
        finally
        {
            if(ctx == null) goto _L0; else goto _L0
        }
        if(ctx != null)
            ctx.close();
        return imetaobjectimpl1;
        ctx.close();
        throw exception2;
    }

    private IEntity entity;
    private IProperty property;
    private StaticsLRUMap dataCache;
    private int lock;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CACHE;
    }

}
