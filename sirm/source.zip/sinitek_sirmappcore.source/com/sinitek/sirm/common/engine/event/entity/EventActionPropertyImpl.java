// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventActionPropertyImpl.java

package com.sinitek.sirm.common.engine.event.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.engine.event.entity:
//            IEventActionProperty

public class EventActionPropertyImpl extends MetaObjectImpl
    implements IEventActionProperty
{

    public EventActionPropertyImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("EVENTACTIONPROPERTY"));
    }

    public EventActionPropertyImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getEventActionId()
    {
        return (Integer)get("eventActionId");
    }

    public void setEventActionId(Integer value)
    {
        put("eventActionId", value);
    }

    public String getName()
    {
        return (String)get("name");
    }

    public void setName(String value)
    {
        put("name", value);
    }

    public String getValue()
    {
        return (String)get("value");
    }

    public void setValue(String value)
    {
        put("value", value);
    }
}
