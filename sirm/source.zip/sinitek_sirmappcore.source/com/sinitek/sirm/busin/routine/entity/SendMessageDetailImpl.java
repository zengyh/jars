// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SendMessageDetailImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            ISendMessageDetail

public class SendMessageDetailImpl extends MetaObjectImpl
    implements ISendMessageDetail
{

    public SendMessageDetailImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SENDMESSAGEDETAIL"));
    }

    public SendMessageDetailImpl(IEntity entity)
    {
        super(entity);
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

    public Date getSendTime()
    {
        return (Date)get("SendTime");
    }

    public void setSendTime(Date value)
    {
        put("SendTime", value);
    }

    public Integer getSendStatus()
    {
        return (Integer)get("SendStatus");
    }

    public void setSendStatus(Integer value)
    {
        put("SendStatus", value);
    }

    public String getReason()
    {
        return (String)get("Reason");
    }

    public void setReason(String value)
    {
        put("Reason", value);
    }

    public String getReceiverid()
    {
        return (String)get("receiverid");
    }

    public void setReceiverid(String value)
    {
        put("receiverid", value);
    }

    public Integer getReceiverType()
    {
        return (Integer)get("ReceiverType");
    }

    public void setReceiverType(Integer value)
    {
        put("ReceiverType", value);
    }
}
