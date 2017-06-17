// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IdGeneratorWrapper.java

package com.sinitek.base.metadb.hibernate;

import com.sinitek.base.metadb.*;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class IdGeneratorWrapper
    implements IdentifierGenerator
{

    public IdGeneratorWrapper()
    {
    }

    public Serializable generate(SessionImplementor sessionImplementor, Object o)
        throws HibernateException
    {
        IMetaObjectImpl mo = (IMetaObjectImpl)o;
        IEntity entity = mo.getEntity();
        int idValue = entity.getIdGenerator().generateId(entity, MetaDBContextFactory.getInstance().getDataSourceProvider());
        mo.setId(idValue);
        return new Integer(idValue);
    }
}
