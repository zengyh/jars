// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TriggerActionImpl.java

package com.sinitek.sirm.common.engine.event.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.common.engine.event.entity:
//            ITriggerAction

public class TriggerActionImpl extends MetaObjectImpl
    implements ITriggerAction
{

    public TriggerActionImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("TRIGGERACTION"));
    }

    public TriggerActionImpl(IEntity entity)
    {
        super(entity);
    }

    public String getEventCode()
    {
        return (String)get("EventCode");
    }

    public void setEventCode(String value)
    {
        put("EventCode", value);
    }

    public String getActionCode()
    {
        return (String)get("ActionCode");
    }

    public void setActionCode(String value)
    {
        put("ActionCode", value);
    }

    public String getLocation()
    {
        return (String)get("Location");
    }

    public void setLocation(String value)
    {
        put("Location", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }

    public Date getDealtime()
    {
        return (Date)get("dealtime");
    }

    public void setDealtime(Date value)
    {
        put("dealtime", value);
    }

    public IStreamValue getActionParam()
    {
        return (IStreamValue)get("actionParam");
    }

    public void setActionParam(IStreamValue value)
    {
        put("actionParam", value);
    }
}
