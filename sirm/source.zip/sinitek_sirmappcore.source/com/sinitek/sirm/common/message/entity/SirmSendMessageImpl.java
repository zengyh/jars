// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmSendMessageImpl.java

package com.sinitek.sirm.common.message.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.common.message.entity:
//            ISirmSendMessage

public class SirmSendMessageImpl extends MetaObjectImpl
    implements ISirmSendMessage
{

    public SirmSendMessageImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SirmSendMessage"));
    }

    public SirmSendMessageImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }

    public String getTitle()
    {
        return (String)get("title");
    }

    public void setTitle(String value)
    {
        put("title", value);
    }

    public Integer getType()
    {
        return (Integer)get("type");
    }

    public void setType(Integer value)
    {
        put("type", value);
    }

    public Integer getIsTime()
    {
        return (Integer)get("isTime");
    }

    public void setIsTime(Integer value)
    {
        put("isTime", value);
    }

    public Date getSendTime()
    {
        return (Date)get("sendTime");
    }

    public void setSendTime(Date value)
    {
        put("sendTime", value);
    }

    public String getContent()
    {
        return (String)get("content");
    }

    public void setContent(String value)
    {
        put("content", value);
    }

    public String getSenderId()
    {
        return (String)get("senderId");
    }

    public void setSenderId(String value)
    {
        put("senderId", value);
    }

    public Integer getExampleownerid()
    {
        return (Integer)get("exampleownerid");
    }

    public void setExampleownerid(Integer value)
    {
        put("exampleownerid", value);
    }

    public Integer getTemplatetypeid()
    {
        return (Integer)get("templatetypeid");
    }

    public void setTemplatetypeid(Integer value)
    {
        put("templatetypeid", value);
    }
}
