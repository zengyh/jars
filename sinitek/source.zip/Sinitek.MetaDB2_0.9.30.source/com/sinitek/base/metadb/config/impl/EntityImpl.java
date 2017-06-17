// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityImpl.java

package com.sinitek.base.metadb.config.impl;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.config.MetaDBConfigException;
import java.util.*;

public class EntityImpl
    implements IEntity
{

    public EntityImpl()
    {
        propMap = new HashMap();
        targetEntityMap = new HashMap();
        propertyList = new ArrayList();
    }

    public IEntityCatalog getEntityCatalog()
    {
        return entityCatalog;
    }

    public void setEntityCatalog(IEntityCatalog entityCatalog)
    {
        this.entityCatalog = entityCatalog;
    }

    public String getEntityInfo()
    {
        return entityInfo;
    }

    public void setEntityInfo(String entityInfo)
    {
        this.entityInfo = entityInfo;
    }

    public String getEntityName()
    {
        return entityName;
    }

    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }

    public boolean isHistorySupport()
    {
        return historySupport;
    }

    public void setHistorySupport(boolean historySupport)
    {
        this.historySupport = historySupport;
    }

    public int getIdCacheSize()
    {
        return idCacheSize;
    }

    public Class getInterface()
    {
        return interfaceClass;
    }

    public Class getImplClass()
    {
        return implClass;
    }

    public void setImplClass(Class implClass)
    {
        this.implClass = implClass;
    }

    public void setInterface(Class interfaceClass)
    {
        this.interfaceClass = interfaceClass;
    }

    public void setIdCacheSize(int idCacheSize)
    {
        this.idCacheSize = idCacheSize;
    }

    public boolean isIdCacheSupport()
    {
        return idCacheSupport;
    }

    public void setIdCacheSupport(boolean idCacheSupport)
    {
        this.idCacheSupport = idCacheSupport;
    }

    public IIdGenerator getIdGenerator()
    {
        return idGenerator;
    }

    public void setIdGenerator(IIdGenerator idGenerator)
    {
        this.idGenerator = idGenerator;
    }

    public boolean isRemoveSupport()
    {
        return removeSupport;
    }

    public void setRemoveSupport(boolean removeSupport)
    {
        this.removeSupport = removeSupport;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getEntityCatalogKey()
    {
        return entityCatalogKey;
    }

    public void setEntityCatalogKey(String entityCatalogKey)
    {
        this.entityCatalogKey = entityCatalogKey;
    }

    public List getProperties()
    {
        return propertyList;
    }

    public boolean hasStreamProperty()
    {
        return hasStreamProperty;
    }

    public void setHasStreamProperty(boolean hasStreamProperty)
    {
        this.hasStreamProperty = hasStreamProperty;
    }

    public boolean hasRelaProperty()
    {
        return hasRelaProperty;
    }

    public void setHasRelaProperty(boolean hasRelaProperty)
    {
        this.hasRelaProperty = hasRelaProperty;
    }

    public IProperty getProperty(String propertyName)
        throws MetaDBException
    {
        IProperty prop = (IProperty)propMap.get(propertyName.toLowerCase());
        if(prop == null)
            throw new MetaDBCoreException("0002", new Object[] {
                entityName, propertyName
            });
        else
            return prop;
    }

    public boolean containsProperty(String propertyName)
    {
        return propMap.get(propertyName.toLowerCase()) != null;
    }

    public IProperty getRelaProperty(IEntity entity)
        throws MetaDBException
    {
        IProperty prop = (IProperty)targetEntityMap.get(entity.getEntityName());
        if(prop == null)
            throw new MetaDBCoreException("0005", new Object[] {
                entityName, entity.getEntityName()
            });
        else
            return prop;
    }

    public IProperty getRelaLocalProperty(IEntity entity)
        throws MetaDBException
    {
        return getRelaProperty(entity).getRelaProperty();
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(obj instanceof IEntity)
        {
            IEntity target = (IEntity)obj;
            return target.getEntityName().equalsIgnoreCase(entityName);
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return entityName.hashCode();
    }

    public String toString()
    {
        return (new StringBuilder()).append("\u5B9E\u4F53[").append(entityName).append("]").toString();
    }

    public void addProperty(IProperty property)
        throws MetaDBException
    {
        if(propMap.put(property.getPropertyName().toLowerCase(), property) != null)
        {
            throw new MetaDBConfigException("0024", new Object[] {
                entityName, property.getPropertyName()
            });
        } else
        {
            propertyList.add(property);
            return;
        }
    }

    public void registRelaEntity(IProperty targetProperty)
        throws MetaDBException
    {
        targetEntityMap.put(targetProperty.getEntity().getEntityName(), targetProperty);
    }

    protected String entityName;
    protected String tableName;
    protected IEntityCatalog entityCatalog;
    protected String entityCatalogKey;
    protected String entityInfo;
    protected IIdGenerator idGenerator;
    protected boolean historySupport;
    protected boolean removeSupport;
    protected boolean idCacheSupport;
    protected int idCacheSize;
    protected Class interfaceClass;
    protected Class implClass;
    protected Map propMap;
    protected boolean hasStreamProperty;
    protected boolean hasRelaProperty;
    protected Map targetEntityMap;
    protected List propertyList;
}
