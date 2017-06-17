// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgRelationSchemeImpl.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.org.busin.entity:
//            IOrgRelationScheme

public class OrgRelationSchemeImpl extends MetaObjectImpl
    implements IOrgRelationScheme
{

    public OrgRelationSchemeImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("ORGRELATIONSCHEME"));
    }

    public OrgRelationSchemeImpl(IEntity entity)
    {
        super(entity);
    }

    public String getCode()
    {
        return (String)get("Code");
    }

    public void setCode(String value)
    {
        put("Code", value);
    }

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }

    public String getBrief()
    {
        return (String)get("Brief");
    }

    public void setBrief(String value)
    {
        put("Brief", value);
    }
}
