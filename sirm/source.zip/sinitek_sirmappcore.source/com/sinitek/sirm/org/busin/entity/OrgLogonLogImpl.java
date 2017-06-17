// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgLogonLogImpl.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.org.busin.entity:
//            IOrgLogonLog

public class OrgLogonLogImpl extends MetaObjectImpl
    implements IOrgLogonLog
{

    public OrgLogonLogImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("ORGlOGONLOG"));
    }

    public OrgLogonLogImpl(IEntity entity)
    {
        super(entity);
    }

    public Date getLogDate()
    {
        return (Date)get("LogDate");
    }

    public void setLogDate(Date value)
    {
        put("LogDate", value);
    }

    public Integer getFailedCount()
    {
        return (Integer)get("FailedCount");
    }

    public void setFailedCount(Integer value)
    {
        put("FailedCount", value);
    }

    public String getOrgid()
    {
        return (String)get("Orgid");
    }

    public void setOrgid(String value)
    {
        put("Orgid", value);
    }
}
