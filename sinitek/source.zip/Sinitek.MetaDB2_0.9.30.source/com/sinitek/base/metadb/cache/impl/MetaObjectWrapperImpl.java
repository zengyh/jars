// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectWrapperImpl.java

package com.sinitek.base.metadb.cache.impl;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.hibernate.proxy.LastIdProxy;
import java.util.*;

public class MetaObjectWrapperImpl
    implements IMetaObjectImpl
{

    public MetaObjectWrapperImpl(IMetaObjectImpl impl)
    {
        this.impl = impl;
    }

    public Object putValue(Object key, Object value)
    {
        return impl.putValue(key, value);
    }

    public Object getValue(Object key)
    {
        return impl.getValue(key);
    }

    public boolean isDirtyProperty(String propName)
    {
        return impl.isDirtyProperty(propName);
    }

    public int getDirtyPropertyCount()
    {
        return impl.getDirtyPropertyCount();
    }

    public boolean isPersistStatus()
    {
        return impl.isPersistStatus();
    }

    public void setPersistStatus(boolean persistStatus)
    {
        impl.setPersistStatus(true);
    }

    public Object getOrigValue(IProperty prop)
    {
        return impl.getOrigValue(prop);
    }

    public void setEnumValue(String propName, Object enumValue)
    {
        impl.setEnumValue(propName, enumValue);
    }

    public void setEntityValue(String propName, Object entityPropValue)
    {
        impl.setEntityValue(propName, entityPropValue);
    }

    public void setCreateTimeStamp(Date createTimeStamp)
    {
        impl.setCreateTimeStamp(createTimeStamp);
    }

    public void setId(int id)
    {
        impl.setId(id);
    }

    public void setUpdateTimeStamp(Date updateTimeStamp)
    {
        impl.setUpdateTimeStamp(updateTimeStamp);
    }

    public void setLastProxy(LastIdProxy proxy)
    {
        impl.setLastProxy(proxy);
    }

    public IMetaObject getLastObject()
        throws MetaDBException
    {
        return impl.getLastObject();
    }

    public void setVersion(int version)
    {
        impl.setVersion(version);
    }

    public int getId()
    {
        return 0;
    }

    public Date getUpdateTimestamp()
    {
        return impl.getUpdateTimestamp();
    }

    public Date getCreateTimestamp()
    {
        return impl.getCreateTimestamp();
    }

    public int getLastId()
        throws MetaDBException
    {
        return impl.getLastId();
    }

    public void save()
        throws MetaDBException
    {
        impl.save();
    }

    public void remove()
        throws MetaDBException
    {
        impl.remove();
    }

    public void delete()
        throws MetaDBException
    {
        impl.delete();
    }

    public int getVersion()
    {
        return impl.getVersion();
    }

    public Object clone()
    {
        return new MetaObjectWrapperImpl(impl);
    }

    public IEntity getEntity()
    {
        return impl.getEntity();
    }

    public String getEntityName()
    {
        return impl.getEntityName();
    }

    public List getRelaObjects(IEntity relaEntity)
        throws MetaDBException
    {
        return impl.getRelaObjects(relaEntity);
    }

    public void setEntityOrigDBValue(String propName, Object origDBValue)
    {
        impl.setEntityOrigDBValue(propName, origDBValue);
    }

    public Map getEntityOrigValues()
    {
        return impl.getEntityOrigValues();
    }

    public int size()
    {
        return impl.size();
    }

    public void clear()
    {
        impl.clear();
    }

    public boolean isEmpty()
    {
        return impl.isEmpty();
    }

    public boolean containsKey(Object key)
    {
        return impl.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        return impl.containsKey(value);
    }

    public Collection values()
    {
        return impl.values();
    }

    public void putAll(Map t)
    {
        impl.putAll(t);
    }

    public Set entrySet()
    {
        return impl.entrySet();
    }

    public Set keySet()
    {
        return impl.keySet();
    }

    public Object get(Object key)
    {
        return impl.get(key);
    }

    public Object remove(Object key)
    {
        return impl.remove(key);
    }

    public Object put(Object key, Object value)
    {
        ensureBackup();
        return impl.put(key, value);
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof MetaObjectWrapperImpl)
            return impl.equals(((MetaObjectWrapperImpl)obj).impl);
        else
            return (obj instanceof IMetaObjectImpl) && impl.equals(obj);
    }

    public IMetaObjectPropertyUpdateInfo getUpdateInfo()
    {
        return impl.getUpdateInfo();
    }

    private synchronized void ensureBackup()
    {
        if(!isBacked)
        {
            IMetaObjectImpl _clone = (IMetaObjectImpl)impl.clone();
            _clone.setId(impl.getId());
            _clone.setVersion(impl.getVersion());
            _clone.setCreateTimeStamp(impl.getCreateTimestamp());
            _clone.setUpdateTimeStamp(impl.getUpdateTimestamp());
            _clone.setLastProxy(null);
            _clone.setPersistStatus(true);
            impl = _clone;
            isBacked = true;
        }
    }

    private IMetaObjectImpl impl;
    private boolean isBacked;
}
