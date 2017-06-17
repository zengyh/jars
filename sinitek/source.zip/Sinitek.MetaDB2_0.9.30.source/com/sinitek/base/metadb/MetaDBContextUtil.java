// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBContextUtil.java

package com.sinitek.base.metadb;

import com.sinitek.base.metadb.cache.IMetaDBCacheContextImpl;
import com.sinitek.base.metadb.cache.ObjectOperateType;
import com.sinitek.base.metadb.hibernate.proxy.LastIdProxy;
import java.util.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;

// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBContextImpl, IMetaObjectImpl, MetaObjectImpl, IProperty, 
//            MetaDBCoreException, MetaDBContextFactory, IEntity

class MetaDBContextUtil
{

    MetaDBContextUtil()
    {
    }

    public static void saveBatch(List data)
    {
        MetaDBContextImpl ctx;
        Set reloadEntitynames;
        ctx = (MetaDBContextImpl)MetaDBContextFactory.getInstance().createContext();
        reloadEntitynames = new HashSet();
        ctx.begin();
        Session session = ctx.getSession();
        for(Iterator i$ = data.iterator(); i$.hasNext();)
        {
            IMetaObjectImpl mo = (IMetaObjectImpl)i$.next();
            reloadEntitynames.add(mo.getEntityName());
            Date now = new Date();
            if(mo.getId() == 0)
            {
                mo.setCreateTimeStamp(now);
                mo.setUpdateTimeStamp(now);
                session.save(mo.getEntityName(), mo);
                ctx.addObjectUpdate(mo, ObjectOperateType.INSERT, mo.getEntity(), mo.getId());
            } else
            {
                mo.setUpdateTimeStamp(now);
                if(mo.getEntity().isHistorySupport())
                {
                    IMetaObjectImpl origObj;
                    if(mo.getEntity().hasStreamProperty())
                    {
                        origObj = (IMetaObjectImpl)ctx.load(mo.getEntity(), mo.getId());
                        origObj = (IMetaObjectImpl)origObj.clone();
                    } else
                    {
                        origObj = (IMetaObjectImpl)mo.clone();
                        if(mo instanceof MetaObjectImpl)
                        {
                            IProperty prop;
                            for(Iterator iter = ((MetaObjectImpl)mo).getDirtyProperties().entrySet().iterator(); iter.hasNext(); origObj.put(prop, mo.getOrigValue(prop)))
                            {
                                java.util.Map.Entry dirtyEntry = (java.util.Map.Entry)iter.next();
                                prop = (IProperty)dirtyEntry.getValue();
                            }

                        }
                    }
                    origObj.setCreateTimeStamp(mo.getCreateTimestamp());
                    origObj.setUpdateTimeStamp(mo.getUpdateTimestamp());
                    LastIdProxy lastIdProxy = new LastIdProxy(mo.getEntity());
                    lastIdProxy.setDBValue(Integer.valueOf(mo.getLastId()));
                    origObj.setLastProxy(lastIdProxy);
                    session.save((new StringBuilder()).append(mo.getEntityName()).append("_his").toString(), origObj);
                    lastIdProxy = new LastIdProxy(mo.getEntity());
                    lastIdProxy.setDBValue(new Integer(origObj.getId()));
                    mo.setLastProxy(lastIdProxy);
                }
                session.update(mo.getEntityName(), mo);
                ctx.addObjectUpdate(mo, ObjectOperateType.UPDATE, mo.getEntity(), mo.getId());
            }
        }

        if(ctx.isFlushOnOperation())
            ctx.getSession().flush();
        IMetaObjectImpl mo;
        for(Iterator i$ = data.iterator(); i$.hasNext(); mo.setPersistStatus(true))
            mo = (IMetaObjectImpl)i$.next();

        ctx.close();
        break MISSING_BLOCK_LABEL_681;
        HibernateException he;
        he;
        String e;
        Iterator i$ = reloadEntitynames.iterator();
        if(!i$.hasNext())
            break MISSING_BLOCK_LABEL_665;
        e = (String)i$.next();
        ctx.cacheContext.notifyReload(new String[] {
            e
        });
        throw new MetaDBCoreException("0026", he, new Object[] {
            e
        });
        Exception exception;
        exception;
        throw new MetaDBCoreException("0026", he, new Object[] {
            e
        });
        ctx.close();
        break MISSING_BLOCK_LABEL_681;
        Exception exception1;
        exception1;
        ctx.close();
        throw exception1;
    }

    public static void removeBatch(List data)
    {
        MetaDBContextImpl ctx;
        String currentEntityname;
        ctx = (MetaDBContextImpl)MetaDBContextFactory.getInstance().createContext();
        currentEntityname = null;
        ctx.begin();
        Session session = ctx.getSession();
        for(Iterator i$ = data.iterator(); i$.hasNext();)
        {
            IMetaObjectImpl mo = (IMetaObjectImpl)i$.next();
            currentEntityname = mo.getEntityName();
            if(mo.getEntity().isRemoveSupport())
            {
                IMetaObjectImpl origObj = (IMetaObjectImpl)mo.clone();
                origObj.setCreateTimeStamp(mo.getCreateTimestamp());
                origObj.setUpdateTimeStamp(mo.getUpdateTimestamp());
                LastIdProxy lastIdProxy = new LastIdProxy(mo.getEntity());
                lastIdProxy.setDBValue(Integer.valueOf(mo.getLastId()));
                origObj.setLastProxy(lastIdProxy);
                origObj.putValue("origobjid", Integer.valueOf(mo.getId()));
                origObj.putValue("baktime", new Date());
                session.save((new StringBuilder()).append(mo.getEntityName()).append("_bak").toString(), origObj);
                session.delete(mo.getEntityName(), mo);
                ctx.addObjectUpdate(mo, ObjectOperateType.DELETE, mo.getEntity(), mo.getId());
            } else
            {
                session.delete(mo.getEntityName(), mo);
                ctx.addObjectUpdate(mo, ObjectOperateType.DELETE, mo.getEntity(), mo.getId());
            }
        }

        if(ctx.isFlushOnOperation())
            session.flush();
        ctx.close();
        break MISSING_BLOCK_LABEL_367;
        HibernateException he;
        he;
        if(currentEntityname != null)
            throw new MetaDBCoreException("0028", he, new Object[] {
                currentEntityname
            });
        ctx.close();
        break MISSING_BLOCK_LABEL_367;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }

    public static void deleteBatch(List data)
    {
        MetaDBContextImpl ctx;
        String currentEntityname;
        ctx = (MetaDBContextImpl)MetaDBContextFactory.getInstance().createContext();
        currentEntityname = null;
        ctx.begin();
        Session session = ctx.getSession();
        Iterator i$ = data.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IMetaObjectImpl mo = (IMetaObjectImpl)i$.next();
            currentEntityname = mo.getEntityName();
            session.delete(mo.getEntityName(), mo);
            ctx.addObjectUpdate(mo, ObjectOperateType.DELETE, mo.getEntity(), mo.getId());
            if(ctx.isFlushOnOperation())
                ctx.getSession().flush();
        } while(true);
        ctx.close();
        break MISSING_BLOCK_LABEL_163;
        HibernateException he;
        he;
        if(currentEntityname != null)
            throw new MetaDBCoreException("0028", he, new Object[] {
                currentEntityname
            });
        ctx.close();
        break MISSING_BLOCK_LABEL_163;
        Exception exception;
        exception;
        ctx.close();
        throw exception;
    }
}
