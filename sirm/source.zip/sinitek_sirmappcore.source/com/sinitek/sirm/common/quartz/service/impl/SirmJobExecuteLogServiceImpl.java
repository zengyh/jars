// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmJobExecuteLogServiceImpl.java

package com.sinitek.sirm.common.quartz.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.quartz.entity.ISirmJobExecuteLog;
import com.sinitek.sirm.common.quartz.service.ISirmJobExecuteLogService;
import java.util.*;

public class SirmJobExecuteLogServiceImpl extends AbstractMetaDBContextSupport
    implements ISirmJobExecuteLogService
{

    public SirmJobExecuteLogServiceImpl()
    {
    }

    public void saveJobExecuteLog(ISirmJobExecuteLog jobExecuteLog)
    {
        jobExecuteLog.save();
    }

    public IMetaDBQuery searchJobExecuteLog(Map map)
    {
        Map _param = new HashMap();
        StringBuilder sql = new StringBuilder();
        sql.append("select t.name,decode(t.empid, 0,'\u7CFB\u7EDF\u6267\u884C', a.orgname) as empname, status,\nto_char(t.starttime, 'yyyy-mm-dd hh24:mi:ss') as starttime ,\nto_char(t.endtime, 'yyyy-mm-dd hh24:mi:ss') as endtime,\ndecode(t.type,1,'\u81EA\u52A8\u6267\u884C',2,'\u624B\u52A8\u6267\u884C') as statustype \nfrom sirm_jobexecutelog t \nleft join sprt_orgobject a on to_char(t.empid) = a.orgid\nwhere t.name=:name");
        _param.put("name", map.get("queryname"));
        IMetaDBQuery metaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        metaDBQuery.setParameters(_param);
        return metaDBQuery;
    }

    public List getSirmJobExecuteLogs(String name, Integer status)
    {
        IMetaDBQuery query = getMetaDBContext().createQuery("SIRMJOBEXECUTELOG", " name = :name and status = :status");
        query.setParameter("name", name);
        query.setParameter("status", status);
        return query.getResult();
    }

    public List getSirmJobExecuteLogs(Integer status)
    {
        IMetaDBQuery query = getMetaDBContext().createQuery("SIRMJOBEXECUTELOG", "status = :status");
        query.setParameter("status", status);
        return query.getResult();
    }

    public void updateJobStatus()
    {
        List logs = getSirmJobExecuteLogs(Integer.valueOf(1));
        if(logs != null && logs.size() > 0)
        {
            ISirmJobExecuteLog log;
            for(Iterator i$ = logs.iterator(); i$.hasNext(); saveJobExecuteLog(log))
            {
                log = (ISirmJobExecuteLog)i$.next();
                log.setStatus(Integer.valueOf(3));
                log.setEndtime(new Date());
            }

        }
    }
}
