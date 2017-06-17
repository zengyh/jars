// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectImpl.java

package com.sinitek.base.metadb;

import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.metadb.cache.IMetaDBCacheContextImpl;
import com.sinitek.base.metadb.cache.ObjectOperateType;
import com.sinitek.base.metadb.hibernate.proxy.IProxyValue;
import com.sinitek.base.metadb.hibernate.proxy.LastIdProxy;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import java.lang.reflect.Method;
import java.util.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;

// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBCoreException, IMetaObject, MetaDBContextImpl, IMetaObjectImpl, 
//            IProperty, MetaDBTemplate, IStreamValue, IMetaRelaEntityObject, 
//            MetaDBException, IEntity, IMetaDBContext, MetaDBContextFactory, 
//            PropertyType, PropertyDBType, IMetaObjectPropertyUpdateInfo

public class MetaObjectImpl extends HashMap
    implements IMetaObjectImpl
{
    private static class OrigValueNotPersistBeanImpl extends OrigValueBeanImpl
    {

        public Object getOrigValue()
        {
            return null;
        }

        private OrigValueNotPersistBeanImpl(MetaObjectImpl impl, IProperty property)
        {
            super(impl, property);
        }

    }

    private static class OrigValueBeanImpl
        implements IMetaObjectPropertyUpdateInfo.IOrigValueBean
    {

        public Object getOrigValue()
        {
            return impl.origValues.get(property);
        }

        public Object getCurrentValue()
        {
            return impl.get(property);
        }

        public IProperty getProperty()
        {
            return property;
        }

        protected IProperty property;
        protected MetaObjectImpl impl;

        private OrigValueBeanImpl(MetaObjectImpl impl, IProperty property)
        {
            this.impl = impl;
            this.property = property;
        }

    }

    private static class MetaObjectPropertyUpdateInfoImpl
        implements IMetaObjectPropertyUpdateInfo
    {

        public boolean isPersistStatus()
        {
            return impl.isPersistStatus();
        }

        public IEntity getEntity()
        {
            return impl.entity;
        }

        public IProperty[] getUpdatedProperties()
        {
            if(!isPersistStatus())
            {
                List _tmp = new ArrayList();
                Iterator iter = impl.entity.getProperties().iterator();
                do
                {
                    if(!iter.hasNext())
                        break;
                    Object prop = iter.next();
                    if(impl.get(prop) != null)
                        _tmp.add(prop);
                } while(true);
                return (IProperty[])(IProperty[])_tmp.toArray(new IProperty[_tmp.size()]);
            } else
            {
                Set _tmp = impl.origValues.keySet();
                return (IProperty[])(IProperty[])_tmp.toArray(new IProperty[_tmp.size()]);
            }
        }

        public boolean isPropertyUpdated(IProperty property)
        {
            if(!isPersistStatus())
                return impl.get(property) != null;
            else
                return impl.origValues.containsKey(property);
        }

        public boolean isPropertyUpdated(String propertyName)
        {
            return isPropertyUpdated(impl.entity.getProperty(propertyName));
        }

        public IMetaObjectPropertyUpdateInfo.IOrigValueBean getOrigValueBean(IProperty property)
            throws MetaDBException
        {
            if(isPropertyUpdated(property))
            {
                if(isPersistStatus())
                    return new OrigValueBeanImpl(impl, property);
                else
                    return new OrigValueNotPersistBeanImpl(impl, property);
            } else
            {
                throw new MetaDBCoreException("0044");
            }
        }

        public IMetaObjectPropertyUpdateInfo.IOrigValueBean getOrigValueBean(String propertyName)
            throws MetaDBException
        {
            return getOrigValueBean(impl.entity.getProperty(propertyName));
        }

        public IMetaObjectPropertyUpdateInfo.IOrigValueBean[] getOrigValueBeans()
        {
            IProperty updatedProperties[] = getUpdatedProperties();
            IMetaObjectPropertyUpdateInfo.IOrigValueBean ret[] = new IMetaObjectPropertyUpdateInfo.IOrigValueBean[updatedProperties.length];
            for(int i = 0; i < updatedProperties.length; i++)
                ret[i] = getOrigValueBean(updatedProperties[i]);

            return ret;
        }

        private MetaObjectImpl impl;

        private MetaObjectPropertyUpdateInfoImpl(MetaObjectImpl impl)
        {
            this.impl = impl;
        }

    }

    private static class ProxyEntry
        implements java.util.Map.Entry
    {

        public Object getKey()
        {
            return key;
        }

        public Object getValue()
        {
            return impl.get(key);
        }

        public Object setValue(Object value)
        {
            return impl.put(key, value);
        }

        private MetaObjectImpl impl;
        private Object key;

        private ProxyEntry(MetaObjectImpl impl, Object key)
        {
            this.impl = impl;
            this.key = key;
        }

    }


    public MetaObjectImpl(IEntity entity)
    {
        persistStatus = false;
        dirtyProperties = new HashMap();
        origValues = new HashMap();
        origEntityDBValue = new CaseInsensitiveMap();
        this.entity = entity;
    }

    public int getId()
    {
        return id;
    }

    public Date getUpdateTimestamp()
    {
        return updateTimeStamp;
    }

    public Date getCreateTimestamp()
    {
        return createTimeStamp;
    }

    public void setCreateTimeStamp(Date createTimeStamp)
    {
        this.createTimeStamp = createTimeStamp;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setUpdateTimeStamp(Date updateTimeStamp)
    {
        this.updateTimeStamp = updateTimeStamp;
    }

    public void setLastProxy(LastIdProxy proxy)
    {
        lastId = proxy;
    }

    public int getLastId()
    {
        if(entity.isHistorySupport())
        {
            if(lastId != null)
                return ((Integer)lastId.getDBValue()).intValue();
            else
                return 0;
        } else
        {
            throw new MetaDBCoreException("0037", new Object[] {
                getEntityName()
            });
        }
    }

    public IMetaObject getLastObject()
        throws MetaDBException
    {
        if(lastId != null)
            return (IMetaObject)lastId.getValue();
        else
            return null;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public void save()
    {
        MetaDBContextImpl ctx = (MetaDBContextImpl)MetaDBContextFactory.getInstance().createContext();
        ctx.begin();
        Date now = new Date();
        if(getId() == 0)
        {
            setCreateTimeStamp(now);
            setUpdateTimeStamp(now);
            ctx.getSession().save(getEntityName(), this);
            ctx.addObjectUpdate(this, ObjectOperateType.INSERT, entity, id);
        } else
        {
            setUpdateTimeStamp(now);
            if(getEntity().isHistorySupport())
            {
                IMetaObjectImpl origObj;
                if(entity.hasStreamProperty())
                {
                    origObj = (IMetaObjectImpl)ctx.load(entity, id);
                    origObj = (IMetaObjectImpl)origObj.clone();
                } else
                {
                    origObj = (IMetaObjectImpl)clone();
                    IProperty prop;
                    for(Iterator iter = dirtyProperties.entrySet().iterator(); iter.hasNext(); origObj.put(prop, getOrigValue(prop)))
                    {
                        java.util.Map.Entry dirtyEntry = (java.util.Map.Entry)iter.next();
                        prop = (IProperty)dirtyEntry.getValue();
                    }

                }
                origObj.setCreateTimeStamp(getCreateTimestamp());
                origObj.setUpdateTimeStamp(getUpdateTimestamp());
                LastIdProxy lastIdProxy = new LastIdProxy(getEntity());
                if(lastId != null)
                    lastIdProxy.setDBValue(lastId.getDBValue() != null ? lastId.getDBValue() : ((Object) (new Integer(0))));
                else
                    lastIdProxy.setDBValue(new Integer(0));
                origObj.setLastProxy(lastIdProxy);
                ctx.getSession().save((new StringBuilder()).append(getEntityName()).append("_his").toString(), origObj);
                lastId = new LastIdProxy(getEntity());
                lastId.setDBValue(new Integer(origObj.getId()));
            }
            ctx.getSession().update(getEntityName(), this);
            ctx.addObjectUpdate(this, ObjectOperateType.UPDATE, entity, id);
        }
        if(ctx.isFlushOnOperation())
            ctx.getSession().flush();
        setPersistStatus(true);
        ctx.close();
        break MISSING_BLOCK_LABEL_511;
        HibernateException he;
        he;
        ctx.cacheContext.notifyReload(new String[] {
            entity.getEntityName()
        });
        throw new MetaDBCoreException("0026", he, new Object[] {
            getEntityName()
        });
        Exception exception;
        exception;
        throw new MetaDBCoreException("0026", he, new Object[] {
            getEntityName()
        });
        Exception exception1;
        exception1;
        ctx.close();
        throw exception1;
    }

    public void remove()
    {
        checkPersisted();
        Exception exception;
        if(entity.isRemoveSupport())
        {
            MetaDBContextImpl ctx = (MetaDBContextImpl)MetaDBContextFactory.getInstance().createContext();
            try
            {
                ctx.begin();
                IMetaObjectImpl origObj = (IMetaObjectImpl)clone();
                origObj.setCreateTimeStamp(getCreateTimestamp());
                origObj.setUpdateTimeStamp(getUpdateTimestamp());
                origObj.setLastProxy(lastId);
                origObj.putValue("origobjid", new Integer(id));
                origObj.putValue("baktime", new Date());
                ctx.getSession().save((new StringBuilder()).append(getEntityName()).append("_bak").toString(), origObj);
                ctx.getSession().delete(getEntityName(), this);
                ctx.addObjectUpdate(this, ObjectOperateType.DELETE, entity, id);
                if(ctx.isFlushOnOperation())
                    ctx.getSession().flush();
            }
            catch(HibernateException he)
            {
                throw new MetaDBCoreException("0028", he, new Object[] {
                    getEntityName()
                });
            }
            finally
            {
                ctx.close();
            }
            ctx.close();
        } else
        {
            delete();
        }
        break MISSING_BLOCK_LABEL_227;
        throw exception;
    }

    public void delete()
    {
        Exception exception;
        checkPersisted();
        MetaDBContextImpl ctx = (MetaDBContextImpl)MetaDBContextFactory.getInstance().createContext();
        try
        {
            ctx.begin();
            ctx.getSession().delete(getEntityName(), this);
            ctx.addObjectUpdate(this, ObjectOperateType.DELETE, entity, id);
            if(ctx.isFlushOnOperation())
                ctx.getSession().flush();
        }
        catch(HibernateException he)
        {
            throw new MetaDBCoreException("0028", he, new Object[] {
                getEntityName()
            });
        }
        finally
        {
            ctx.close();
        }
        ctx.close();
        break MISSING_BLOCK_LABEL_101;
        throw exception;
    }

    public Object clone()
    {
        MetaObjectImpl cln = (MetaObjectImpl)(new MetaDBTemplate()).createNewMetaObject(entity);
        java.util.Map.Entry entry;
        Object origValue;
        for(Iterator iter = entrySet().iterator(); iter.hasNext(); cln.putValue(entry.getKey(), origValue))
        {
            entry = (java.util.Map.Entry)iter.next();
            origValue = entry.getValue();
            if(origValue instanceof IStreamValue)
                origValue = ((IStreamValue)origValue).deepCopy();
        }

        cln.id = 0;
        cln.version = 0;
        cln.lastId = null;
        cln.createTimeStamp = new Date();
        cln.updateTimeStamp = new Date();
        return cln;
    }

    public IEntity getEntity()
    {
        return entity;
    }

    public String getEntityName()
    {
        return entity.getEntityName();
    }

    public List getRelaObjects(IEntity relaEntity)
        throws MetaDBException
    {
        Exception exception;
        checkPersisted();
        IMetaDBContext ctx = MetaDBContextFactory.getInstance().createContext();
        List list;
        try
        {
            String queryStr = (new StringBuilder()).append(entity.getRelaProperty(relaEntity).getPropertyName()).append("=:value").toString();
            IMetaDBQuery query = ctx.createQuery(relaEntity, queryStr);
            query.setParameter("value", this);
            list = query.getResult();
        }
        catch(HibernateException he)
        {
            throw new MetaDBCoreException("0029", he, new Object[] {
                entity.getEntityName(), relaEntity.getEntityName()
            });
        }
        finally
        {
            ctx.close();
        }
        ctx.close();
        return list;
        throw exception;
    }

    public IMetaObjectPropertyUpdateInfo getUpdateInfo()
    {
        return new MetaObjectPropertyUpdateInfoImpl(this);
    }

    public Object get(Object key)
    {
        if(!(key instanceof String))
            break MISSING_BLOCK_LABEL_249;
        if(entity.containsProperty((String)key))
        {
            IProperty prop = entity.getProperty(key.toString().trim());
            if(prop.getPropertyType().equals(PropertyType.ID))
                return new Integer(getId());
            if(prop.getPropertyType().equals(PropertyType.CREATETIMESTAMP))
                return getCreateTimestamp();
            if(prop.getPropertyType().equals(PropertyType.UPDATETIMESTAMP))
                return getUpdateTimestamp();
            if(prop.getPropertyType().equals(PropertyType.LASTID))
                return getLastObject();
            if(prop.getPropertyType().equals(PropertyType.VERSION))
                return new Integer(getVersion());
            else
                return get(prop);
        }
        if(!PropertyUtils.isReadable(this, key.toString()))
            break MISSING_BLOCK_LABEL_231;
        Method method = getClass().getMethod((new StringBuilder()).append("get").append(StringUtils.capitalize((String)key)).toString(), null);
        return method.invoke(this, null);
        Exception e;
        e;
        throw new MetaDBCoreException("0040", new Object[] {
            key
        });
        throw new MetaDBCoreException("0041", new Object[] {
            key
        });
        if(key instanceof IProperty)
        {
            IProperty prop = (IProperty)key;
            Object origObject = super.get(prop.getPropertyName().toLowerCase());
            if(origObject instanceof IProxyValue)
                return ((IProxyValue)origObject).getValue();
            if((origObject instanceof IMetaRelaEntityObject) && entity.isIdCacheSupport())
            {
                IMetaRelaEntityObject entityObj = (IMetaRelaEntityObject)origObject;
                entityObj.resetRelaObject();
                return entityObj;
            } else
            {
                return origObject;
            }
        } else
        {
            throw new MetaDBCoreException("0015", new Object[] {
                key
            });
        }
    }

    public Object getValue(Object key)
    {
        return super.get(key);
    }

    public Object put(Object key, Object value)
    {
        if(key instanceof IProperty)
        {
            IProperty prop = (IProperty)key;
            if(prop.isNotNull() && value == null)
                throw new MetaDBCoreException("0035", new Object[] {
                    getEntityName(), prop.getPropertyName()
                });
            if(id > 0 && !origValues.containsKey(prop))
                origValues.put(prop, get(prop));
            if(persistStatus)
                dirtyProperties.put(prop.getPropertyName(), prop);
            String strPropName = prop.getPropertyName().toLowerCase();
            Object current = super.get(strPropName);
            if(current instanceof IProxyValue)
            {
                IProxyValue proxy = (IProxyValue)current;
                proxy.setValue(value);
                return null;
            } else
            {
                return super.put(strPropName, value);
            }
        }
        if(key instanceof String)
        {
            String propName = key.toString().trim();
            return put(entity.getProperty(propName), value);
        } else
        {
            throw new MetaDBCoreException("0019", new Object[] {
                key
            });
        }
    }

    public void clear()
    {
        for(Iterator iter = keySet().iterator(); iter.hasNext(); remove(iter.next()));
    }

    public boolean isEmpty()
    {
        return false;
    }

    public Object remove(Object key)
    {
        return put(key, null);
    }

    public void putAll(Map m)
    {
        java.util.Map.Entry entry;
        for(Iterator iter = m.entrySet().iterator(); iter.hasNext(); put(entry.getKey(), entry.getValue()))
            entry = (java.util.Map.Entry)iter.next();

    }

    public Object putValue(Object key, Object value)
    {
        return super.put(key, value);
    }

    public boolean isDirtyProperty(String propName)
    {
        return persistStatus && dirtyProperties.containsKey(propName);
    }

    public int getDirtyPropertyCount()
    {
        return persistStatus ? dirtyProperties.size() : 0;
    }

    public Object getOrigValue(IProperty prop)
    {
        if(prop.getPropertyType().equals(PropertyType.ID))
            return new Integer(id);
        if(origValues.containsKey(prop))
            return origValues.get(prop);
        else
            return get(prop);
    }

    public void setEnumValue(String propName, Object enumValue)
    {
        IProperty prop = entity.getProperty(propName);
        if(!prop.getPropertyType().equals(PropertyType.ENUM))
            throw new MetaDBCoreException("0042", new Object[] {
                getEntityName(), prop.getPropertyName()
            });
        if(enumValue == null)
        {
            put(prop, null);
            return;
        }
        if(prop.getPropertyDBType().equals(PropertyDBType.INTEGER))
        {
            Number number = (Number)enumValue;
            put(prop, EnumItemContext.getInstance().getEnumItem(prop.getEnumName(), number.intValue()));
        } else
        {
            put(prop, EnumItemContext.getInstance().getEnumItem(prop.getEnumName(), (String)enumValue));
        }
    }

    public void setEntityValue(String propName, Object entityPropValue)
    {
        IProperty prop = entity.getProperty(propName);
        if(!prop.getPropertyType().equals(PropertyType.ENTITY))
            throw new MetaDBCoreException("0043", new Object[] {
                getEntityName(), prop.getPropertyName()
            });
        if(entityPropValue == null)
        {
            put(prop, null);
        } else
        {
            MetaDBTemplate template = new MetaDBTemplate(MetaDBContextFactory.AUTO);
            put(prop, template.get(prop.getRelaProperty().getEntity(), prop.getRelaProperty().getPropertyName(), entityPropValue));
        }
    }

    public void setEntityOrigDBValue(String propName, Object origDBValue)
    {
        origEntityDBValue.put(propName, origDBValue);
    }

    public Map getEntityOrigValues()
    {
        return Collections.unmodifiableMap(origEntityDBValue);
    }

    public boolean isPersistStatus()
    {
        return persistStatus;
    }

    public void setPersistStatus(boolean persistStatus)
    {
        this.persistStatus = persistStatus;
    }

    public boolean equals(Object o)
    {
        if(o == null)
            return false;
        if(o instanceof IMetaObjectImpl)
        {
            IMetaObjectImpl mo = (IMetaObjectImpl)o;
            return entity.equals(mo.getEntity()) && id == mo.getId() && createTimeStamp.equals(mo.getCreateTimestamp()) && updateTimeStamp.equals(mo.getUpdateTimestamp()) && version == mo.getVersion() && super.equals(mo);
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    public Set entrySet()
    {
        Set ret = new HashSet();
        for(Iterator iter = keySet().iterator(); iter.hasNext(); ret.add(new ProxyEntry(this, iter.next())));
        return ret;
    }

    public Map getDirtyProperties()
    {
        return dirtyProperties;
    }

    private void checkPersisted()
    {
        if(id == 0)
            throw new MetaDBCoreException("0027");
        else
            return;
    }

    private IEntity entity;
    private boolean persistStatus;
    private int id;
    private Date createTimeStamp;
    private Date updateTimeStamp;
    private LastIdProxy lastId;
    private int version;
    private Map dirtyProperties;
    private Map origValues;
    private Map origEntityDBValue;


}
