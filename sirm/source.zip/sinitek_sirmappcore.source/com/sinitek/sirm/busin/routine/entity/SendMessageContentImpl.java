// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SendMessageContentImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            ISendMessageContent

public class SendMessageContentImpl extends MetaObjectImpl
    implements ISendMessageContent
{

    public SendMessageContentImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SENDMESSAGECONTENT"));
    }

    public SendMessageContentImpl(IEntity entity)
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

    public Integer getSendMode()
    {
        return (Integer)get("SendMode");
    }

    public void setSendMode(Integer value)
    {
        put("SendMode", value);
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
}
