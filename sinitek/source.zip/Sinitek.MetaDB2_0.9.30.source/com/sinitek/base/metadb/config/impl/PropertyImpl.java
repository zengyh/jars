// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropertyImpl.java

package com.sinitek.base.metadb.config.impl;

import com.sinitek.base.metadb.*;

public class PropertyImpl
    implements IProperty
{

    public PropertyImpl()
    {
        hashCode = -1;
    }

    public Object getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public IEntity getEntity()
    {
        return entity;
    }

    public void setEntity(IEntity entity)
    {
        this.entity = entity;
    }

    public boolean isNotNull()
    {
        return notNull;
    }

    public void setNotNull(boolean notNull)
    {
        this.notNull = notNull;
    }

    public String getPropertyColumnName()
    {
        return propertyColumnName;
    }

    public void setPropertyColumnName(String propertyColumnName)
    {
        this.propertyColumnName = propertyColumnName;
    }

    public PropertyDBType getPropertyDBType()
    {
        return propertyDBType;
    }

    public void setPropertyDBType(PropertyDBType propertyDBType)
    {
        this.propertyDBType = propertyDBType;
    }

    public String getPropertyInfo()
    {
        return propertyInfo;
    }

    public void setPropertyInfo(String propertyInfo)
    {
        this.propertyInfo = propertyInfo;
    }

    public int getPropertyLength()
    {
        return propertyLength;
    }

    public void setPropertyLength(int propertyLength)
    {
        this.propertyLength = propertyLength;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public PropertyType getPropertyType()
    {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType)
    {
        this.propertyType = propertyType;
    }

    public IProperty getRelaProperty()
    {
        return relaProperty;
    }

    public void setRelaProperty(IProperty relaProperty)
    {
        this.relaProperty = relaProperty;
    }

    public String getEnumName()
    {
        return enumName;
    }

    public void setEnumName(String enumName)
    {
        this.enumName = enumName;
    }

    public String getRelaPropertyName()
    {
        return relaPropertyName;
    }

    public void setRelaPropertyName(String relaPropertyName)
    {
        this.relaPropertyName = relaPropertyName;
    }

    public String getRelaEntityName()
    {
        return relaEntityName;
    }

    public void setRelaEntityName(String relaEntityName)
    {
        this.relaEntityName = relaEntityName;
    }

    public boolean isUnique()
    {
        return unique;
    }

    public void setUnique(boolean unique)
    {
        this.unique = unique;
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(obj instanceof IProperty)
        {
            IProperty target = (IProperty)obj;
            return target.getEntity().equals(getEntity()) && target.getPropertyName().equalsIgnoreCase(getPropertyName());
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        if(hashCode == -1)
            hashCode = (new StringBuilder()).append(entity.getEntityName()).append(propertyName).toString().hashCode();
        return hashCode;
    }

    public String toString()
    {
        if(toString == null)
            toString = (new StringBuilder()).append("\u5B9E\u4F53[").append(entity.getEntityName()).append("]\u6240\u5C5E\u5C5E\u6027[").append(propertyName).append("]\uFF0C\u5C5E\u6027\u7C7B\u578B[").append(propertyType.getEnumItemName()).append("]\uFF0C\u6570\u636E\u5E93\u7C7B\u578B[").append(propertyDBType.getEnumItemName()).append("]").toString();
        return toString;
    }

    public static PropertyImpl createIdProperty(IEntity entity)
    {
        PropertyImpl prop = new PropertyImpl();
        prop.setEntity(entity);
        prop.setNotNull(true);
        prop.setPropertyColumnName("OBJID");
        prop.setPropertyName("Id");
        prop.setPropertyLength(0);
        prop.setPropertyDBType(PropertyDBType.INTEGER);
        prop.setPropertyInfo("\u5B9E\u4F53\u4E3B\u952E");
        prop.setPropertyType(PropertyType.ID);
        prop.setUnique(true);
        return prop;
    }

    public static PropertyImpl createCreateTimeStampProperty(IEntity entity)
    {
        PropertyImpl prop = new PropertyImpl();
        prop.setEntity(entity);
        prop.setNotNull(true);
        prop.setPropertyColumnName("CREATETIMESTAMP");
        prop.setPropertyName("CreateTimeStamp");
        prop.setPropertyLength(0);
        prop.setPropertyDBType(PropertyDBType.DATE);
        prop.setPropertyInfo("\u521B\u5EFA\u65F6\u95F4\u6233");
        prop.setPropertyType(PropertyType.CREATETIMESTAMP);
        prop.setUnique(false);
        return prop;
    }

    public static PropertyImpl createUpdateTimeStampProperty(IEntity entity)
    {
        PropertyImpl prop = new PropertyImpl();
        prop.setEntity(entity);
        prop.setNotNull(true);
        prop.setPropertyColumnName("UPDATETIMESTAMP");
        prop.setPropertyName("UpdateTimeStamp");
        prop.setPropertyLength(0);
        prop.setPropertyDBType(PropertyDBType.DATE);
        prop.setPropertyInfo("\u66F4\u65B0\u65F6\u95F4\u6233");
        prop.setPropertyType(PropertyType.UPDATETIMESTAMP);
        prop.setUnique(false);
        return prop;
    }

    public static PropertyImpl createEntityNameProperty(IEntity entity)
    {
        PropertyImpl prop = new PropertyImpl();
        prop.setEntity(entity);
        prop.setNotNull(true);
        prop.setPropertyColumnName("ENTITYNAME");
        prop.setPropertyName("EntityName");
        prop.setPropertyLength(24);
        prop.setPropertyDBType(PropertyDBType.VARCHAR);
        prop.setPropertyInfo("\u5B9E\u4F53\u540D\u79F0");
        prop.setPropertyType(PropertyType.ENTITYNAME);
        prop.setUnique(false);
        return prop;
    }

    public static PropertyImpl createVersionProperty(IEntity entity)
    {
        PropertyImpl prop = new PropertyImpl();
        prop.setEntity(entity);
        prop.setNotNull(true);
        prop.setPropertyColumnName("VERSION");
        prop.setPropertyName("Version");
        prop.setPropertyLength(0);
        prop.setPropertyDBType(PropertyDBType.INTEGER);
        prop.setPropertyInfo("\u4E50\u89C2\u9501");
        prop.setPropertyType(PropertyType.VERSION);
        prop.setUnique(false);
        return prop;
    }

    public static PropertyImpl createLastIdProperty(IEntity entity)
    {
        PropertyImpl prop = new PropertyImpl();
        prop.setEntity(entity);
        prop.setNotNull(false);
        prop.setPropertyColumnName("LASTOBJID");
        prop.setPropertyName("LastId");
        prop.setPropertyLength(0);
        prop.setPropertyDBType(PropertyDBType.INTEGER);
        prop.setPropertyInfo("\u7EBF\u7D22\u5316\u4E3B\u952E");
        prop.setPropertyType(PropertyType.LASTID);
        prop.setUnique(false);
        return prop;
    }

    private IEntity entity;
    private String propertyName;
    private String propertyInfo;
    private PropertyType propertyType;
    private String enumName;
    private String propertyColumnName;
    private int propertyLength;
    private PropertyDBType propertyDBType;
    private boolean notNull;
    private Object defaultValue;
    private IProperty relaProperty;
    private String relaEntityName;
    private String relaPropertyName;
    private boolean unique;
    private int hashCode;
    private String toString;
}
