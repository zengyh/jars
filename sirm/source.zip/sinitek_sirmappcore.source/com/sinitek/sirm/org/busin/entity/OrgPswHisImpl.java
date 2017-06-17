// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgPswHisImpl.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.org.busin.entity:
//            IOrgPswHis

public class OrgPswHisImpl extends MetaObjectImpl
    implements IOrgPswHis
{

    public OrgPswHisImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("ORGPSWHIS"));
    }

    public OrgPswHisImpl(IEntity entity)
    {
        super(entity);
    }

    public String getPassword()
    {
        return (String)get("Password");
    }

    public void setPassword(String value)
    {
        put("Password", value);
    }

    public Date getLogDate()
    {
        return (Date)get("LogDate");
    }

    public void setLogDate(Date value)
    {
        put("LogDate", value);
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
