// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectGetter.java

package com.sinitek.base.metadb.hibernate;

import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.hibernate.proxy.BytesProxy;
import com.sinitek.base.metadb.hibernate.proxy.ClassProxy;
import com.sinitek.base.metadb.hibernate.proxy.ClobProxy;
import com.sinitek.base.metadb.hibernate.proxy.EntityProxy;
import com.sinitek.base.metadb.hibernate.proxy.LastIdProxy;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.property.Getter;

public class MetaObjectGetter
    implements Getter
{
    public static final class VersionGetter extends MetaObjectGetter
    {

        protected Object getPropValue(IMetaObjectImpl mo)
        {
            return new Integer(mo.getVersion());
        }

        public VersionGetter(IProperty property)
        {
            super(property);
        }
    }

    public static final class UpdateTimeStampGetter extends MetaObjectGetter
    {

        protected Object getPropValue(IMetaObjectImpl mo)
        {
            return mo.getUpdateTimestamp();
        }

        public UpdateTimeStampGetter(IProperty property)
        {
            super(property);
        }
    }

    public static final class CreateTimeStampGetter extends MetaObjectGetter
    {

        protected Object getPropValue(IMetaObjectImpl mo)
        {
            return mo.getCreateTimestamp();
        }

        public CreateTimeStampGetter(IProperty property)
        {
            super(property);
        }
    }

    public static final class EntityNameGetter extends MetaObjectGetter
    {

        protected Object getPropValue(IMetaObjectImpl mo)
        {
            return mo.getEntityName();
        }

        public EntityNameGetter(IProperty property)
        {
            super(property);
        }
    }

    public static final class LastIdGetter extends MetaObjectGetter
    {

        protected Object getPropValue(IMetaObjectImpl mo)
        {
            return new Integer(mo.getLastId());
        }

        public LastIdGetter(IProperty property)
        {
            super(property);
        }
    }

    public static final class IdGetter extends MetaObjectGetter
    {

        protected Object getPropValue(IMetaObjectImpl mo)
        {
            return new Integer(mo.getId());
        }

        public IdGetter(IProperty property)
        {
            super(property);
        }
    }


    public MetaObjectGetter(IProperty property)
    {
        this.property = property;
    }

    public Object get(Object owner)
        throws HibernateException
    {
        IMetaObjectImpl mo = (IMetaObjectImpl)owner;
        return getPropValue(mo);
    }

    public Object getForInsert(Object owner, Map mergeMap, SessionImplementor session)
        throws HibernateException
    {
        return get(owner);
    }

    public Class getReturnType()
    {
        return getReturnClass(property.getPropertyType());
    }

    public String getMethodName()
    {
        return null;
    }

    public Method getMethod()
    {
        return null;
    }

    protected Object getPropValue(IMetaObjectImpl mo)
    {
        return mo.getValue(property.getPropertyName().toLowerCase());
    }

    protected Class getReturnClass(PropertyType propType)
    {
        if(propType.equals(PropertyType.BOOLEAN))
            return java/lang/Boolean;
        if(propType.equals(PropertyType.BYTES))
            return com/sinitek/base/metadb/hibernate/proxy/BytesProxy;
        if(propType.equals(PropertyType.CLASS))
            return com/sinitek/base/metadb/hibernate/proxy/ClassProxy;
        if(propType.equals(PropertyType.CLOB))
            return com/sinitek/base/metadb/hibernate/proxy/ClobProxy;
        if(propType.equals(PropertyType.CREATETIMESTAMP))
            return java/util/Date;
        if(propType.equals(PropertyType.DATE))
            return java/util/Date;
        if(propType.equals(PropertyType.DOUBLE))
            return java/lang/Double;
        if(propType.equals(PropertyType.ENTITY))
            return com/sinitek/base/metadb/hibernate/proxy/EntityProxy;
        if(propType.equals(PropertyType.ENTITYNAME))
            return java/lang/String;
        if(propType.equals(PropertyType.ENUM))
            return com/sinitek/base/enumsupport/IEnumItem;
        if(propType.equals(PropertyType.FLOAT))
            return java/lang/Float;
        if(propType.equals(PropertyType.ID))
            return java/lang/Integer;
        if(propType.equals(PropertyType.INTEGER))
            return java/lang/Integer;
        if(propType.equals(PropertyType.LASTID))
            return com/sinitek/base/metadb/hibernate/proxy/LastIdProxy;
        if(propType.equals(PropertyType.STREAM))
            return com/sinitek/base/metadb/IStreamValue;
        if(propType.equals(PropertyType.STRING))
            return java/lang/String;
        if(propType.equals(PropertyType.UID))
            return java/lang/String;
        if(propType.equals(PropertyType.UPDATETIMESTAMP))
            return java/util/Date;
        if(propType.equals(PropertyType.VERSION))
            return java/lang/Integer;
        else
            throw new UnsupportedOperationException((new StringBuilder()).append("\u4E0D\u652F\u6301\u7684\u5C5E\u6027\u7C7B\u578B[").append(propType).append("]").toString());
    }

    private IProperty property;
}
