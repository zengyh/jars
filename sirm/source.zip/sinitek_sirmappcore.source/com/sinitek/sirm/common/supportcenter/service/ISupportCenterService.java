// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISupportCenterService.java

package com.sinitek.sirm.common.supportcenter.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.supportcenter.entity.ISupportCenter;

public interface ISupportCenterService
{

    public abstract ISupportCenter getSupportCenterById(int i);

    public abstract IMetaDBQuery findSupportCenter();

    public abstract void saveSupportCenter(ISupportCenter isupportcenter);

    public abstract void delSupportCenter(int i);
}
