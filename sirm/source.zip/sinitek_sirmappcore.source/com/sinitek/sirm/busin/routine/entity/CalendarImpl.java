// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CalendarImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            ICalendar

public class CalendarImpl extends MetaObjectImpl
    implements ICalendar
{

    public CalendarImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("CALENDAR"));
    }

    public CalendarImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getRemindFlag()
    {
        return (Integer)get("remindFlag");
    }

    public void setRemindFlag(Integer value)
    {
        put("remindFlag", value);
    }

    public String getRemindType()
    {
        return (String)get("remindType");
    }

    public void setRemindType(String value)
    {
        put("remindType", value);
    }

    public Integer getType()
    {
        return (Integer)get("type");
    }

    public void setType(Integer value)
    {
        put("type", value);
    }

    public Integer getRemindMethod()
    {
        return (Integer)get("remindMethod");
    }

    public void setRemindMethod(Integer value)
    {
        put("remindMethod", value);
    }

    public String getRemindValue()
    {
        return (String)get("remindValue");
    }

    public void setRemindValue(String value)
    {
        put("remindValue", value);
    }

    public Date getBeginDate()
    {
        return (Date)get("BeginDate");
    }

    public void setBeginDate(Date value)
    {
        put("BeginDate", value);
    }

    public Date getEndDate()
    {
        return (Date)get("EndDate");
    }

    public void setEndDate(Date value)
    {
        put("EndDate", value);
    }

    public Integer getInputId()
    {
        return (Integer)get("InputId");
    }

    public void setInputId(Integer value)
    {
        put("InputId", value);
    }

    public String getSubject()
    {
        return (String)get("Subject");
    }

    public void setSubject(String value)
    {
        put("Subject", value);
    }

    public String getSourceEntity()
    {
        return (String)get("SourceEntity");
    }

    public void setSourceEntity(String value)
    {
        put("SourceEntity", value);
    }

    public Integer getSourceId()
    {
        return (Integer)get("SourceId");
    }

    public void setSourceId(Integer value)
    {
        put("SourceId", value);
    }

    public Integer getEmpId()
    {
        return (Integer)get("empId");
    }

    public void setEmpId(Integer value)
    {
        put("empId", value);
    }

    public String getContent()
    {
        return (String)get("content");
    }

    public void setContent(String value)
    {
        put("content", value);
    }

    public Integer getColumnType()
    {
        return (Integer)get("columnType");
    }

    public void setColumnType(Integer value)
    {
        put("columnType", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }

    public Integer getCompleteStatus()
    {
        return (Integer)get("completeStatus");
    }

    public void setCompleteStatus(Integer value)
    {
        put("completeStatus", value);
    }

    public String getUrl()
    {
        return (String)get("url");
    }

    public void setUrl(String value)
    {
        put("url", value);
    }
}
