// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailLogonImpl.java

package com.sinitek.sirm.common.maillogon.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.common.maillogon.entity:
//            IEmailLogon

public class EmailLogonImpl extends MetaObjectImpl
    implements IEmailLogon
{

    public EmailLogonImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SIRMEMAILLOGON"));
    }

    public EmailLogonImpl(IEntity entity)
    {
        super(entity);
    }

    public String getUUID()
    {
        return (String)get("UUID");
    }

    public void setUUID(String value)
    {
        put("UUID", value);
    }

    public String getUserName()
    {
        return (String)get("UserName");
    }

    public void setUserName(String value)
    {
        put("UserName", value);
    }

    public Date getStartDate()
    {
        return (Date)get("StartDate");
    }

    public void setStartDate(Date value)
    {
        put("StartDate", value);
    }

    public Integer getUsedTime()
    {
        return (Integer)get("UsedTime");
    }

    public void setUsedTime(Integer value)
    {
        put("UsedTime", value);
    }
}
