// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgRelationImpl.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.org.busin.entity:
//            IOrgRelation

public class OrgRelationImpl extends MetaObjectImpl
    implements IOrgRelation
{

    public OrgRelationImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("ORGRELATION"));
    }

    public OrgRelationImpl(IEntity entity)
    {
        super(entity);
    }

    public String getOrgId()
    {
        return (String)get("OrgId");
    }

    public void setOrgId(String value)
    {
        put("OrgId", value);
    }

    public Integer getSchemeId()
    {
        return (Integer)get("SchemeId");
    }

    public void setSchemeId(Integer value)
    {
        put("SchemeId", value);
    }

    public Integer getParentId()
    {
        return (Integer)get("ParentId");
    }

    public void setParentId(Integer value)
    {
        put("ParentId", value);
    }
}
