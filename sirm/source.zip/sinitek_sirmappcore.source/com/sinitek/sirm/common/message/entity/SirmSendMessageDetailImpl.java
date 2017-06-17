// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmSendMessageDetailImpl.java

package com.sinitek.sirm.common.message.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.common.message.entity:
//            ISirmSendMessageDetail

public class SirmSendMessageDetailImpl extends MetaObjectImpl
    implements ISirmSendMessageDetail
{

    public SirmSendMessageDetailImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SIRMSENDMESSAGEDETAIL"));
    }

    public SirmSendMessageDetailImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getSendMessageId()
    {
        return (Integer)get("sendMessageId");
    }

    public void setSendMessageId(Integer value)
    {
        put("sendMessageId", value);
    }

    public String getAddress()
    {
        return (String)get("address");
    }

    public void setAddress(String value)
    {
        put("address", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }

    public Date getSendTime()
    {
        return (Date)get("sendTime");
    }

    public void setSendTime(Date value)
    {
        put("sendTime", value);
    }

    public String getEmpid()
    {
        return (String)get("empid");
    }

    public void setEmpid(String value)
    {
        put("empid", value);
    }
}
