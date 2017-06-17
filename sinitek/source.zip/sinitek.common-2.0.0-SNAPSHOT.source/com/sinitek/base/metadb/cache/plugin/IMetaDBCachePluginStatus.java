// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaDBCachePluginStatus.java

package com.sinitek.base.metadb.cache.plugin;


public interface IMetaDBCachePluginStatus
{

    public abstract String[] getPoolNames();

    public abstract int getPoolSize(String s);

    public abstract int getPoolMaxSize(String s);

    public abstract float getPoolHitRate(String s);

    public abstract long getTotalAccessCount(String s);
}
