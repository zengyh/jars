// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JobQuartzDetailsImpl.java

package com.sinitek.sirm.common.quartz.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.common.quartz.entity:
//            IJobQuartzDetails

public class JobQuartzDetailsImpl extends MetaObjectImpl
    implements IJobQuartzDetails
{

    public JobQuartzDetailsImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("JOBQUARTZDETAILS"));
    }

    public JobQuartzDetailsImpl(IEntity entity)
    {
        super(entity);
    }

    public String getJobId()
    {
        return (String)get("JobId");
    }

    public void setJobId(String value)
    {
        put("JobId", value);
    }

    public String getQuartzId()
    {
        return (String)get("quartzId");
    }

    public void setQuartzId(String value)
    {
        put("quartzId", value);
    }

    public Date getStarttime()
    {
        return (Date)get("Starttime");
    }

    public void setStarttime(Date value)
    {
        put("Starttime", value);
    }

    public Date getEndtime()
    {
        return (Date)get("endtime");
    }

    public void setEndtime(Date value)
    {
        put("endtime", value);
    }

    public Integer getExecStatus()
    {
        return (Integer)get("execStatus");
    }

    public void setExecStatus(Integer value)
    {
        put("execStatus", value);
    }

    public Integer getResultStatus()
    {
        return (Integer)get("resultStatus");
    }

    public void setResultStatus(Integer value)
    {
        put("resultStatus", value);
    }

    public Integer getAuto()
    {
        return (Integer)get("auto");
    }

    public void setAuto(Integer value)
    {
        put("auto", value);
    }

    public String getMemo()
    {
        return (String)get("memo");
    }

    public void setMemo(String value)
    {
        put("memo", value);
    }
}
