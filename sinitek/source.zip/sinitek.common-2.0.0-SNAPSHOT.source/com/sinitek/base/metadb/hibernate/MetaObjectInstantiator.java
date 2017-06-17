// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectInstantiator.java

package com.sinitek.base.metadb.hibernate;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.util.MetaObjectBuilder;
import java.io.Serializable;
import org.hibernate.tuple.Instantiator;

public class MetaObjectInstantiator
    implements Instantiator
{

    public MetaObjectInstantiator(IEntity entity)
    {
        this.entity = entity;
    }

    public Object instantiate(Serializable id)
    {
        IMetaObjectImpl mo = MetaObjectBuilder.createNewMetaObject(entity);
        mo.setId(((Integer)id).intValue());
        mo.setPersistStatus(true);
        return mo;
    }

    public Object instantiate()
    {
        return new MetaObjectImpl(entity);
    }

    public boolean isInstance(Object object)
    {
        if(object instanceof IMetaObjectImpl)
        {
            IMetaObjectImpl o = (IMetaObjectImpl)object;
            return o.getEntity().equals(entity);
        } else
        {
            return false;
        }
    }

    private IEntity entity;
}
