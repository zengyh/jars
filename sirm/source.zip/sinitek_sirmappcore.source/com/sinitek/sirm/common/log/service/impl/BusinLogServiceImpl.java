// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BusinLogServiceImpl.java

package com.sinitek.sirm.common.log.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.ISQLUpdater;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.log.entity.BusinLogQueryCondition;
import com.sinitek.sirm.common.log.service.IBusinLogService;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

public class BusinLogServiceImpl extends AbstractMetaDBContextSupport
    implements IBusinLogService
{

    public BusinLogServiceImpl()
    {
    }

    public IMetaDBQuery queryBusinLogs(BusinLogQueryCondition condition)
    {
        Map params = new HashMap();
        StringBuffer sb = new StringBuffer();
        sb.append("select objid,userid,operatetype,description,url,physicaladdress,ipaddress,modulename,\n");
        sb.append("cast(starttime as timestamp) starttime, cast(endtime as timestamp) endtime\n");
        sb.append(" from sprt_businlogger \n");
        sb.append("where 1=1\n");
        if(condition.getUserIds() != null && condition.getUserIds().length > 0)
            sb.append(" and userid in ('").append(StringUtils.join(condition.getUserIds(), "','")).append("') \n");
        if(StringUtils.isNotBlank(condition.getModuleName()))
        {
            sb.append(" and moduleName like :moduleName\n");
            params.put("moduleName", (new StringBuilder()).append("%").append(condition.getModuleName()).append("%").toString());
        }
        if(condition.getStartTime() != null)
        {
            sb.append(" and startTime>=:startTime\n");
            params.put("startTime", condition.getStartTime());
        }
        if(condition.getEndTime() != null)
        {
            sb.append(" and startTime<:endTime\n");
            params.put("endTime", DateUtils.addDays(condition.getEndTime(), 1));
        }
        String sql = sb.toString();
        IMetaDBQuery metadb = getMetaDBContext().createSqlQuery(sql);
        metadb.setParameters(params);
        return metadb;
    }

    public void removeBussinessLogs(Date date)
    {
        String sql = "delete from sprt_businlogger s where s.createtimestamp <:date";
        ISQLUpdater updater = getMetaDBContext().createSqlUpdater(sql);
        updater.setParameter("date", date);
        updater.executeUpdate();
    }
}
