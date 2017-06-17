// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmJobExecuteLogImpl.java

package com.sinitek.sirm.common.quartz.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.common.quartz.entity:
//            ISirmJobExecuteLog

public class SirmJobExecuteLogImpl extends MetaObjectImpl
    implements ISirmJobExecuteLog
{

    public SirmJobExecuteLogImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SIRMJOBEXECUTELOG"));
    }

    public SirmJobExecuteLogImpl(IEntity entity)
    {
        super(entity);
    }

    public String getName()
    {
        return (String)get("name");
    }

    public void setName(String value)
    {
        put("name", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }

    public Integer getEmpid()
    {
        return (Integer)get("empid");
    }

    public void setEmpid(Integer value)
    {
        put("empid", value);
    }

    public Date getStarttime()
    {
        return (Date)get("starttime");
    }

    public void setStarttime(Date value)
    {
        put("starttime", value);
    }

    public Date getEndtime()
    {
        return (Date)get("endtime");
    }

    public void setEndtime(Date value)
    {
        put("endtime", value);
    }

    public Integer getType()
    {
        return (Integer)get("type");
    }

    public void setType(Integer value)
    {
        put("type", value);
    }

    public String getBrief()
    {
        return (String)get("brief");
    }

    public void setBrief(String value)
    {
        put("brief", value);
    }
}
