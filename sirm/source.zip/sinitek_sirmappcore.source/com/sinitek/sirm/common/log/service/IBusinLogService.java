// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IBusinLogService.java

package com.sinitek.sirm.common.log.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.log.entity.BusinLogQueryCondition;
import java.util.Date;

public interface IBusinLogService
{

    public abstract IMetaDBQuery queryBusinLogs(BusinLogQueryCondition businlogquerycondition);

    public abstract void removeBussinessLogs(Date date);
}
