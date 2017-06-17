// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectPersistentClass.java

package com.sinitek.base.metadb.hibernate;

import com.sinitek.base.metadb.IEntity;
import org.hibernate.mapping.RootClass;

public class MetaObjectPersistentClass extends RootClass
{

    public MetaObjectPersistentClass()
    {
    }

    public IEntity getEntity()
    {
        return entity;
    }

    public void setEntity(IEntity entity)
    {
        this.entity = entity;
    }

    private IEntity entity;
}
