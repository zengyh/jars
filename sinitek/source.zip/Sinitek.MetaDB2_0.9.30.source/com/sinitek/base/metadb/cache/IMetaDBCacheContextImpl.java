// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaDBCacheContextImpl.java

package com.sinitek.base.metadb.cache;

import java.util.List;

// Referenced classes of package com.sinitek.base.metadb.cache:
//            IMetaDBCacheContext

public interface IMetaDBCacheContextImpl
    extends IMetaDBCacheContext
{

    public abstract void notifyAfterCommit(List list);
}