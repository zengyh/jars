// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JobQuartzRealServiceImpl.java

package com.sinitek.sirm.common.quartz.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.quartz.entity.IJobQuartzDetails;
import com.sinitek.sirm.common.quartz.entity.IJobQuartzReal;
import com.sinitek.sirm.common.quartz.service.IJobQuartzRealService;
import java.util.*;

public class JobQuartzRealServiceImpl extends MetaDBContextSupport
    implements IJobQuartzRealService
{

    public JobQuartzRealServiceImpl()
    {
    }

    public IJobQuartzReal getJobQuartzRealById(int id)
    {
        return (IJobQuartzReal)getMetaDBContext().get("JOBQUARTZREAL", id);
    }

    public List findJobQuartzRealByQuartzId(String quartzId, Integer statue)
    {
        String _sql = "1=1";
        Map _map = new HashMap();
        if(quartzId != null)
        {
            _sql = (new StringBuilder()).append(_sql).append(" AND QUARTZID=:QUARTZID").toString();
            _map.put("QUARTZID", quartzId);
        }
        if(statue != null)
        {
            _sql = (new StringBuilder()).append(_sql).append(" AND STATUS=:STATUS").toString();
            _map.put("STATUS", Integer.valueOf(statue.intValue()));
        }
        return getMetaDBContext().query(com/sinitek/sirm/common/quartz/entity/IJobQuartzReal, _sql, _map);
    }

    public List findJobQuartzRealByType(Integer type, Integer statue)
    {
        String _sql = "1=1";
        Map _map = new HashMap();
        if(type != null)
        {
            _sql = (new StringBuilder()).append(_sql).append(" AND TYPE=:TYPE").toString();
            _map.put("TYPE", Integer.valueOf(type.intValue()));
        }
        if(statue != null)
        {
            _sql = (new StringBuilder()).append(_sql).append(" AND STATUS=:STATUS").toString();
            _map.put("STATUS", Integer.valueOf(statue.intValue()));
        }
        return getMetaDBContext().query(com/sinitek/sirm/common/quartz/entity/IJobQuartzReal, _sql, _map);
    }

    public void saveList(List list)
    {
        if(list != null)
        {
            for(int i = 0; i < list.size(); i++)
                saveJobQuartzReal((IJobQuartzReal)list.get(i));

        }
    }

    public void saveJobQuartzReal(IJobQuartzReal jobQuartzReal)
    {
        if(jobQuartzReal != null)
            save(jobQuartzReal);
    }

    public IJobQuartzDetails saveJobQuartzDetails(IJobQuartzDetails jobQuartzDetails)
    {
        jobQuartzDetails.save();
        return jobQuartzDetails;
    }

    public List findJobQuartzDetailsList(Integer resultStatus)
    {
        String _sql = "1=1";
        Map _map = new HashMap();
        if(resultStatus != null)
        {
            _sql = (new StringBuilder()).append(_sql).append(" AND RESULTSTATUS=:RESULTSTATUS").toString();
            _map.put("RESULTSTATUS", resultStatus);
        }
        return getMetaDBContext().query(com/sinitek/sirm/common/quartz/entity/IJobQuartzDetails, _sql, _map);
    }

    public void pastJobQuartzDetails()
    {
        List list = findJobQuartzDetailsList(Integer.valueOf(-1));
        if(list != null)
        {
            IJobQuartzDetails details;
            for(Iterator i$ = list.iterator(); i$.hasNext(); saveJobQuartzDetails(details))
            {
                details = (IJobQuartzDetails)i$.next();
                details.setResultStatus(Integer.valueOf(0));
                details.setExecStatus(Integer.valueOf(1));
            }

        }
    }
}
