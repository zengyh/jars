// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaObject.java

package com.sinitek.base.metadb;

import java.io.Serializable;
import java.util.*;

// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBException, IEntity, IMetaObjectPropertyUpdateInfo

public interface IMetaObject
    extends Cloneable, Map, Serializable
{

    public abstract int getId();

    public abstract Date getUpdateTimestamp();

    public abstract Date getCreateTimestamp();

    public abstract int getLastId()
        throws MetaDBException;

    public abstract IMetaObject getLastObject()
        throws MetaDBException;

    public abstract Map getEntityOrigValues();

    public abstract void save()
        throws MetaDBException;

    public abstract void remove()
        throws MetaDBException;

    public abstract void delete()
        throws MetaDBException;

    public abstract int getVersion();

    public abstract Object clone();

    public abstract IEntity getEntity();

    public abstract String getEntityName();

    public abstract List getRelaObjects(IEntity ientity)
        throws MetaDBException;

    public abstract IMetaObjectPropertyUpdateInfo getUpdateInfo();
}
