// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SendMessageImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            ISendMessage

public class SendMessageImpl extends MetaObjectImpl
    implements ISendMessage
{

    public SendMessageImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SENDMESSAGE"));
    }

    public SendMessageImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getEditFlag()
    {
        return (Integer)get("EditFlag");
    }

    public void setEditFlag(Integer value)
    {
        put("EditFlag", value);
    }

    public Integer getTimingFlag()
    {
        return (Integer)get("TimingFlag");
    }

    public void setTimingFlag(Integer value)
    {
        put("TimingFlag", value);
    }

    public Integer getSender()
    {
        return (Integer)get("Sender");
    }

    public void setSender(Integer value)
    {
        put("Sender", value);
    }

    public Date getDefineTime()
    {
        return (Date)get("DefineTime");
    }

    public void setDefineTime(Date value)
    {
        put("DefineTime", value);
    }

    public String getTitle()
    {
        return (String)get("Title");
    }

    public void setTitle(String value)
    {
        put("Title", value);
    }

    public String getContent()
    {
        return (String)get("Content");
    }

    public void setContent(String value)
    {
        put("Content", value);
    }

    public String getReceiver()
    {
        return (String)get("Receiver");
    }

    public void setReceiver(String value)
    {
        put("Receiver", value);
    }

    public String getSendMode()
    {
        return (String)get("SendMode");
    }

    public void setSendMode(String value)
    {
        put("SendMode", value);
    }

    public Integer getRelapseFlag()
    {
        return (Integer)get("RelapseFlag");
    }

    public void setRelapseFlag(Integer value)
    {
        put("RelapseFlag", value);
    }

    public Integer getSendStatus()
    {
        return (Integer)get("SendStatus");
    }

    public void setSendStatus(Integer value)
    {
        put("SendStatus", value);
    }

    public Integer getDeleteFlag()
    {
        return (Integer)get("DeleteFlag");
    }

    public void setDeleteFlag(Integer value)
    {
        put("DeleteFlag", value);
    }

    public Date getSendTime()
    {
        return (Date)get("SendTime");
    }

    public void setSendTime(Date value)
    {
        put("SendTime", value);
    }

    public Integer getReplyMessageId()
    {
        return (Integer)get("ReplyMessageId");
    }

    public void setReplyMessageId(Integer value)
    {
        put("ReplyMessageId", value);
    }
}
