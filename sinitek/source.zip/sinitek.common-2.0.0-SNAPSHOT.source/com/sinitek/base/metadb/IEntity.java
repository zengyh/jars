// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEntity.java

package com.sinitek.base.metadb;

import java.io.Serializable;
import java.util.List;

// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBException, IEntityCatalog, IIdGenerator, IProperty

public interface IEntity
    extends Serializable
{

    public abstract String getEntityName();

    public abstract String getTableName();

    public abstract IEntityCatalog getEntityCatalog();

    public abstract String getEntityInfo();

    public abstract IIdGenerator getIdGenerator();

    public abstract boolean isHistorySupport();

    public abstract boolean isRemoveSupport();

    public abstract boolean isIdCacheSupport();

    public abstract int getIdCacheSize();

    public abstract Class getInterface();

    public abstract Class getImplClass();

    public abstract List getProperties();

    public abstract IProperty getProperty(String s)
        throws MetaDBException;

    public abstract boolean containsProperty(String s);

    public abstract boolean hasStreamProperty();

    public abstract boolean hasRelaProperty();

    public abstract IProperty getRelaProperty(IEntity ientity)
        throws MetaDBException;

    public abstract IProperty getRelaLocalProperty(IEntity ientity)
        throws MetaDBException;
}
