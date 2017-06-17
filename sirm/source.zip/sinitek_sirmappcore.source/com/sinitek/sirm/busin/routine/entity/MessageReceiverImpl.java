// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageReceiverImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            IMessageReceiver

public class MessageReceiverImpl extends MetaObjectImpl
    implements IMessageReceiver
{

    public MessageReceiverImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("MESSAGERECEIVER"));
    }

    public MessageReceiverImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getTemplateId()
    {
        return (Integer)get("TemplateId");
    }

    public void setTemplateId(Integer value)
    {
        put("TemplateId", value);
    }

    public String getOrgId()
    {
        return (String)get("OrgId");
    }

    public void setOrgId(String value)
    {
        put("OrgId", value);
    }

    public Integer getOrgType()
    {
        return (Integer)get("OrgType");
    }

    public void setOrgType(Integer value)
    {
        put("OrgType", value);
    }

    public Integer getUserType()
    {
        return (Integer)get("UserType");
    }

    public void setUserType(Integer value)
    {
        put("UserType", value);
    }
}
