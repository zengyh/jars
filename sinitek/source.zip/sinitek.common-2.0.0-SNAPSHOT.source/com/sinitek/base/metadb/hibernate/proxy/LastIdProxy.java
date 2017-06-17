// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LastIdProxy.java

package com.sinitek.base.metadb.hibernate.proxy;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.base.metadb.hibernate.proxy:
//            AbstractProxyValue

public class LastIdProxy extends AbstractProxyValue
{

    public LastIdProxy(IEntity entity)
    {
        this.entity = entity;
    }

    public Object getValue()
    {
        IMetaDBContext ctx;
        if(value != null)
            break MISSING_BLOCK_LABEL_93;
        if(dbValue == null || (new Integer(0)).equals(dbValue))
            return null;
        ctx = null;
        ctx = MetaDBContextFactory.getInstance().createContext(MetaDBContextFactory.AUTO);
        value = ctx.loadHistoryObject(entity, dbValue.intValue());
        if(ctx != null)
            ctx.close();
        break MISSING_BLOCK_LABEL_93;
        Exception exception;
        exception;
        if(ctx != null)
            ctx.close();
        throw exception;
        return value;
    }

    public void setValue(Object value)
    {
        this.value = (IMetaObject)value;
    }

    public Object getDBValue()
    {
        return value != null ? new Integer(value.getId()) : dbValue;
    }

    public void setDBValue(Object value)
    {
        dbValue = value != null ? (Integer)value : new Integer(0);
    }

    public Class getValueType()
    {
        return com/sinitek/base/metadb/IMetaObject;
    }

    private Integer dbValue;
    private IMetaObject value;
    private IEntity entity;
}
