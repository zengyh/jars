// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityProxy.java

package com.sinitek.base.metadb.hibernate.proxy;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.base.metadb.hibernate.proxy:
//            AbstractProxyValue

public class EntityProxy extends AbstractProxyValue
{

    public EntityProxy(String entityName, String propName)
    {
        this.entityName = entityName;
        this.propName = propName;
    }

    public Object getValue()
    {
        if(value == null)
        {
            if(dbValue == null)
                return null;
            MetaDBContextFactory factory = MetaDBContextFactory.getInstance();
            IMetaDBContext ctx = factory.createContext(MetaDBContextFactory.AUTO);
            value = ctx.load(factory.getEntity(entityName), propName, dbValue);
        }
        return value;
    }

    public void setValue(Object value)
    {
        this.value = (IMetaObject)value;
    }

    public Object getDBValue()
    {
        if(value == null)
            return null;
        else
            return value.get(propName);
    }

    public void setDBValue(Object value)
    {
        dbValue = value;
    }

    public Class getValueType()
    {
        return com/sinitek/base/metadb/IMetaObject;
    }

    private String propName;
    private String entityName;
    private Object dbValue;
    private IMetaObject value;
}
