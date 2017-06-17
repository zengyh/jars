// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTHolidaysImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            IRTHolidays

public class RTHolidaysImpl extends MetaObjectImpl
    implements IRTHolidays
{

    public RTHolidaysImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("RTHOLIDAYS"));
    }

    public RTHolidaysImpl(IEntity entity)
    {
        super(entity);
    }

    public String getSender()
    {
        return (String)get("Sender");
    }

    public void setSender(String value)
    {
        put("Sender", value);
    }

    public Integer getYear()
    {
        return (Integer)get("Year");
    }

    public void setYear(Integer value)
    {
        put("Year", value);
    }

    public Integer getDay()
    {
        return (Integer)get("Day");
    }

    public void setDay(Integer value)
    {
        put("Day", value);
    }

    public Integer getType()
    {
        return (Integer)get("Type");
    }

    public void setType(Integer value)
    {
        put("Type", value);
    }

    public String getRemark()
    {
        return (String)get("remark");
    }

    public void setRemark(String value)
    {
        put("remark", value);
    }

    public String getHolidaysTime()
    {
        return (String)get("HolidaysTime");
    }

    public void setHolidaysTime(String value)
    {
        put("HolidaysTime", value);
    }
}
