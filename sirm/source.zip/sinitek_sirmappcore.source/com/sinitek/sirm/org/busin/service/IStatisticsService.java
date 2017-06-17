// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IStatisticsService.java

package com.sinitek.sirm.org.busin.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import java.util.Map;

public interface IStatisticsService
{

    public abstract IMetaDBQuery userAccessStatistics(Map map);

    public abstract IMetaDBQuery menuAccessStatistics(Map map);

    public abstract IMetaDBQuery userAccessDetail(Map map);

    public abstract IMetaDBQuery menuAccessDetail(Map map);

    public abstract IMetaDBQuery searchUserBussLog(Map map);

    public abstract IMetaDBQuery searchMenuBussLog(Map map);
}
