// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseScheduleJob.java

package com.sinitek.sirm.common.support;

import com.sinitek.base.metadb.MetaDBTemplate;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import java.util.*;
import org.quartz.*;

// Referenced classes of package com.sinitek.sirm.common.support:
//            ScheduleJobContext

public abstract class BaseScheduleJob
    implements StatefulJob
{

    public BaseScheduleJob()
    {
        result = null;
        name = null;
        param = new HashMap();
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Map getParam()
    {
        return param;
    }

    public void setParam(Map param)
    {
        this.param = param;
    }

    public void execute(JobExecutionContext context)
        throws JobExecutionException
    {
        ScheduleJobContext scheduleJobContext = new ScheduleJobContext();
        String name = "";
        if(this.name == null)
            name = context.getJobDetail().getName();
        else
            name = this.name;
        if(context == null)
        {
            scheduleJobContext.setLasttime(returndate(name));
            scheduleJobContext.setName(this.name);
            scheduleJobContext.setParam(param);
        } else
        {
            if(context.getPreviousFireTime() == null)
                scheduleJobContext.setLasttime(returndate(name));
            else
                scheduleJobContext.setLasttime(context.getPreviousFireTime());
            scheduleJobContext.setName(context.getJobDetail().getName());
            scheduleJobContext.setParam(context.getMergedJobDataMap());
        }
        scheduleJob(scheduleJobContext);
    }

    private Date returndate(String name)
    {
        Date lasttime = null;
        MetaDBTemplate template = new MetaDBTemplate();
        String sql = "select max(endtime) as endtime from sirm_jobexecutelog where  name =:name ";
        Map _param = new HashMap();
        _param.put("name", name);
        IMetaDBQuery query = template.createSqlQuery(sql);
        query.setParameters(_param);
        List list = query.getResult();
        if(list.size() > 0)
        {
            Map iSirmJobExecuteLog = (Map)list.get(0);
            lasttime = (Date)iSirmJobExecuteLog.get("endtime");
        }
        return lasttime;
    }

    public abstract void scheduleJob(ScheduleJobContext schedulejobcontext);

    private String result;
    private String name;
    private Map param;
}
