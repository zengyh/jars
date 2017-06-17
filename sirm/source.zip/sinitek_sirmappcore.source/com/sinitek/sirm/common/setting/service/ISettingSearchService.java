// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISettingSearchService.java

package com.sinitek.sirm.common.setting.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import java.util.Map;

public interface ISettingSearchService
{

    public abstract IMetaDBQuery searchSettings(Map map);
}
