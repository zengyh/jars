// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISirmJobExecuteLogService.java

package com.sinitek.sirm.common.quartz.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.quartz.entity.ISirmJobExecuteLog;
import java.util.List;
import java.util.Map;

public interface ISirmJobExecuteLogService
{

    public abstract void saveJobExecuteLog(ISirmJobExecuteLog isirmjobexecutelog);

    public abstract IMetaDBQuery searchJobExecuteLog(Map map);

    public abstract List getSirmJobExecuteLogs(String s, Integer integer);

    public abstract void updateJobStatus();

    public abstract List getSirmJobExecuteLogs(Integer integer);
}
