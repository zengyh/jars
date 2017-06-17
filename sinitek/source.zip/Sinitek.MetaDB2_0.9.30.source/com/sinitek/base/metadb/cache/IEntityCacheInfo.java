// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEntityCacheInfo.java

package com.sinitek.base.metadb.cache;

import com.sinitek.base.metadb.IProperty;

public interface IEntityCacheInfo
{

    public abstract int getIdCacheSize();

    public abstract float getIdCacheHitRate();

    public abstract long getIdCacheAccessCount();

    public abstract int getPropertyCacheSize(IProperty iproperty);

    public abstract float getPropertyCacheHitRate(IProperty iproperty);

    public abstract long getPropertyCacheAccessCount(IProperty iproperty);
}
