// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectSetter.java

package com.sinitek.base.metadb.hibernate;

import com.sinitek.base.metadb.IMetaObjectImpl;
import com.sinitek.base.metadb.IProperty;
import com.sinitek.base.metadb.hibernate.proxy.LastIdProxy;
import java.lang.reflect.Method;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.property.Setter;

public class MetaObjectSetter
    implements Setter
{
    public static final class EntityNameSetter extends MetaObjectSetter
    {

        protected void setValue(IMetaObjectImpl imetaobjectimpl, Object obj)
        {
        }

        public EntityNameSetter(IProperty property)
        {
            super(property);
        }
    }

    public static final class VersionSetter extends MetaObjectSetter
    {

        protected void setValue(IMetaObjectImpl mo, Object value)
        {
            mo.setVersion(((Integer)value).intValue());
        }

        public VersionSetter(IProperty property)
        {
            super(property);
        }
    }

    public static final class LastIdSetter extends MetaObjectSetter
    {

        protected void setValue(IMetaObjectImpl mo, Object value)
        {
            if(value != null)
                mo.setLastProxy((LastIdProxy)value);
        }

        public LastIdSetter(IProperty property)
        {
            super(property);
        }
    }

    public static final class UpdateTimeStampSetter extends MetaObjectSetter
    {

        protected void setValue(IMetaObjectImpl mo, Object value)
        {
            mo.setUpdateTimeStamp((Date)value);
        }

        public UpdateTimeStampSetter(IProperty property)
        {
            super(property);
        }
    }

    public static final class CreateTimeStampSetter extends MetaObjectSetter
    {

        protected void setValue(IMetaObjectImpl mo, Object value)
        {
            mo.setCreateTimeStamp((Date)value);
        }

        public CreateTimeStampSetter(IProperty property)
        {
            super(property);
        }
    }

    public static final class IdSetter extends MetaObjectSetter
    {

        protected void setValue(IMetaObjectImpl mo, Object value)
        {
            mo.setId(((Integer)value).intValue());
        }

        public IdSetter(IProperty property)
        {
            super(property);
        }
    }


    public MetaObjectSetter(IProperty property)
    {
        this.property = property;
    }

    public void set(Object target, Object value, SessionFactoryImplementor factory)
        throws HibernateException
    {
        IMetaObjectImpl mo = (IMetaObjectImpl)target;
        setValue(mo, value);
    }

    public String getMethodName()
    {
        return null;
    }

    public Method getMethod()
    {
        return null;
    }

    protected void setValue(IMetaObjectImpl mo, Object value)
    {
        mo.putValue(property.getPropertyName().toLowerCase(), value);
    }

    private IProperty property;
}
