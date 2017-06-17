// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JobQuartzRealImpl.java

package com.sinitek.sirm.common.quartz.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.quartz.entity:
//            IJobQuartzReal

public class JobQuartzRealImpl extends MetaObjectImpl
    implements IJobQuartzReal
{

    public JobQuartzRealImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("JOBQUARTZREAL"));
    }

    public JobQuartzRealImpl(IEntity entity)
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

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }

    public Integer getType()
    {
        return (Integer)get("type");
    }

    public void setType(Integer value)
    {
        put("type", value);
    }
}
