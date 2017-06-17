// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ICacheInfoService.java

package com.sinitek.sirm.common.cache.service;

import com.sinitek.sirm.common.cache.entity.ICacheInfo;
import java.util.List;

public interface ICacheInfoService
{

    public abstract ICacheInfo getCacheInfoById(int i);

    public abstract List findAllCacheInfos();
}
