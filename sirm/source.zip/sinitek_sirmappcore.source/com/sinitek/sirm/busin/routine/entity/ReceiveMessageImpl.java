// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReceiveMessageImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            IReceiveMessage

public class ReceiveMessageImpl extends MetaObjectImpl
    implements IReceiveMessage
{

    public ReceiveMessageImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("RECEIVEMESSAGE"));
    }

    public ReceiveMessageImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getReceiverType()
    {
        return (Integer)get("ReceiverType");
    }

    public void setReceiverType(Integer value)
    {
        put("ReceiverType", value);
    }

    public Integer getSendMessageId()
    {
        return (Integer)get("SendMessageId");
    }

    public void setSendMessageId(Integer value)
    {
        put("SendMessageId", value);
    }

    public Integer getReceiver()
    {
        return (Integer)get("Receiver");
    }

    public void setReceiver(Integer value)
    {
        put("Receiver", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public Date getReadTime()
    {
        return (Date)get("ReadTime");
    }

    public void setReadTime(Date value)
    {
        put("ReadTime", value);
    }

    public Integer getRelapseMessageId()
    {
        return (Integer)get("RelapseMessageId");
    }

    public void setRelapseMessageId(Integer value)
    {
        put("RelapseMessageId", value);
    }

    public Integer getSendMode()
    {
        return (Integer)get("SendMode");
    }

    public void setSendMode(Integer value)
    {
        put("SendMode", value);
    }

    public Integer getDeleteFlag()
    {
        return (Integer)get("DeleteFlag");
    }

    public void setDeleteFlag(Integer value)
    {
        put("DeleteFlag", value);
    }
}
