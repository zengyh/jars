// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IProperty.java

package com.sinitek.base.metadb;

import java.io.Serializable;

// Referenced classes of package com.sinitek.base.metadb:
//            IEntity, PropertyType, PropertyDBType

public interface IProperty
    extends Serializable
{

    public abstract IEntity getEntity();

    public abstract String getPropertyName();

    public abstract String getPropertyInfo();

    public abstract PropertyType getPropertyType();

    public abstract String getEnumName();

    public abstract String getPropertyColumnName();

    public abstract int getPropertyLength();

    public abstract PropertyDBType getPropertyDBType();

    public abstract boolean isNotNull();

    public abstract Object getDefaultValue();

    public abstract IProperty getRelaProperty();

    public abstract boolean isUnique();

    public static final String ID_PROPERTYNAME = "Id";
    public static final String CREATETIMESTAMP_PROPERTYNAME = "CreateTimeStamp";
    public static final String UPDATETIMESTAMP_PROPERTYNAME = "UpdateTimeStamp";
    public static final String ENTITYNAME_PROPERTYNAME = "EntityName";
    public static final String VERSION_PROPERTYNAME = "Version";
    public static final String LASTID_PROPERTYNAME = "LastId";
    public static final String SYSTEM_PROPERTY_NAMES[] = {
        "Id", "CreateTimeStamp", "UpdateTimeStamp", "EntityName", "Version", "LastId"
    };

}
