// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgPropertyImpl.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.org.busin.entity:
//            IOrgProperty

public class OrgPropertyImpl extends MetaObjectImpl
    implements IOrgProperty
{

    public OrgPropertyImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("ORGPROPERTY"));
    }

    public OrgPropertyImpl(IEntity entity)
    {
        super(entity);
    }

    public Boolean getF1()
    {
        return (Boolean)get("F1");
    }

    public void setF1(Boolean value)
    {
        put("F1", value);
    }

    public String getOrgId()
    {
        return (String)get("OrgId");
    }

    public void setOrgId(String value)
    {
        put("OrgId", value);
    }
}
