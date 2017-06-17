// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaDBCachePluginEventListener.java

package com.sinitek.base.metadb.cache.plugin;

import com.sinitek.base.metadb.IEntity;
import com.sinitek.base.metadb.cache.ObjectOperateType;

public interface IMetaDBCachePluginEventListener
{

    public abstract void afterTransactionCommit(IEntity aientity[], int ai[], ObjectOperateType aobjectoperatetype[]);
}
