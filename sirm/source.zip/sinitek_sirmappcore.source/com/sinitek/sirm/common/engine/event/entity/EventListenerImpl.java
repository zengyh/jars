// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventListenerImpl.java

package com.sinitek.sirm.common.engine.event.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.engine.event.entity:
//            IEventListener

public class EventListenerImpl extends MetaObjectImpl
    implements IEventListener
{

    public EventListenerImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("EVENTLISTENER"));
    }

    public EventListenerImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getEventInfoId()
    {
        return (Integer)get("eventInfoId");
    }

    public void setEventInfoId(Integer value)
    {
        put("eventInfoId", value);
    }

    public Integer getActionInfoId()
    {
        return (Integer)get("actionInfoId");
    }

    public void setActionInfoId(Integer value)
    {
        put("actionInfoId", value);
    }
}
