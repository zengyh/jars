// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectTuplizer.java

package com.sinitek.base.metadb.hibernate;

import com.sinitek.base.metadb.*;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.property.Getter;
import org.hibernate.property.Setter;
import org.hibernate.proxy.ProxyFactory;
import org.hibernate.proxy.map.MapProxyFactory;
import org.hibernate.tuple.Instantiator;
import org.hibernate.tuple.entity.AbstractEntityTuplizer;
import org.hibernate.tuple.entity.EntityMetamodel;

// Referenced classes of package com.sinitek.base.metadb.hibernate:
//            MetaObjectPersistentClass, MetaObjectInstantiator, MetaObjectGetterFactory, MetaObjectSetterFactory

public class MetaObjectTuplizer extends AbstractEntityTuplizer
{

    public MetaObjectTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappingInfo)
    {
        super(entityMetamodel, mappingInfo);
    }

    protected EntityMode getEntityMode()
    {
        return EntityMode.MAP;
    }

    protected Getter buildPropertyGetter(Property mappedProperty, PersistentClass mappedEntity)
    {
        MetaObjectPersistentClass pc = (MetaObjectPersistentClass)mappedEntity;
        final String name = mappedProperty.getName();
        if(name.equals("OrigObjId") || name.equals("BakTime"))
            return new Getter() {

                public Object get(Object owner)
                    throws HibernateException
                {
                    IMetaObjectImpl mo = (IMetaObjectImpl)owner;
                    return mo.getValue(name.toLowerCase());
                }

                public Object getForInsert(Object owner, Map mergeMap, SessionImplementor session)
                    throws HibernateException
                {
                    return get(owner);
                }

                public Class getReturnType()
                {
                    return ((Class) ("OrigObjId".equals(name) ? java/lang/Integer : java/util/Date));
                }

                public String getMethodName()
                {
                    return null;
                }

                public Method getMethod()
                {
                    return null;
                }

                final String val$name;
                final MetaObjectTuplizer this$0;

            
            {
                this$0 = MetaObjectTuplizer.this;
                name = s;
                super();
            }
            }
;
        else
            return MetaObjectGetterFactory.createGetter(pc.getEntity().getProperty(mappedProperty.getName()));
    }

    protected Setter buildPropertySetter(Property mappedProperty, PersistentClass mappedEntity)
    {
        MetaObjectPersistentClass pc = (MetaObjectPersistentClass)mappedEntity;
        final String name = mappedProperty.getName();
        if(name.equals("OrigObjId") || name.equals("BakTime"))
            return new Setter() {

                public void set(Object target, Object value, SessionFactoryImplementor factory)
                    throws HibernateException
                {
                    IMetaObjectImpl mo = (IMetaObjectImpl)target;
                    mo.putValue(name.toLowerCase(), value);
                }

                public String getMethodName()
                {
                    return null;
                }

                public Method getMethod()
                {
                    return null;
                }

                final String val$name;
                final MetaObjectTuplizer this$0;

            
            {
                this$0 = MetaObjectTuplizer.this;
                name = s;
                super();
            }
            }
;
        else
            return MetaObjectSetterFactory.createSetter(pc.getEntity().getProperty(mappedProperty.getName()));
    }

    protected Instantiator buildInstantiator(PersistentClass mappingInfo)
    {
        MetaObjectPersistentClass pc = (MetaObjectPersistentClass)mappingInfo;
        return new MetaObjectInstantiator(pc.getEntity());
    }

    protected ProxyFactory buildProxyFactory(PersistentClass mappingInfo, Getter idGetter, Setter idSetter)
    {
        ProxyFactory pf = new MapProxyFactory();
        try
        {
            pf.postInstantiate(getEntityName(), null, null, null, null, null);
        }
        catch(HibernateException he)
        {
            LOGGER.warn((new StringBuilder()).append("could not create proxy factory for:").append(getEntityName()).toString(), he);
            pf = null;
        }
        return pf;
    }

    public Class getConcreteProxyClass()
    {
        return com/sinitek/base/metadb/IMetaObject;
    }

    public boolean isInstrumented()
    {
        return false;
    }

    public Class getMappedClass()
    {
        return com/sinitek/base/metadb/IMetaObject;
    }

    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CONFIG;
    }
}
