// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IIdGenerator.java

package com.sinitek.base.metadb;

import com.sinitek.base.metadb.config.IDataSourceProvider;
import java.io.Serializable;

// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBException, IEntity

public interface IIdGenerator
    extends Serializable
{

    public abstract int generateId(IEntity ientity, IDataSourceProvider idatasourceprovider)
        throws MetaDBException;
}
