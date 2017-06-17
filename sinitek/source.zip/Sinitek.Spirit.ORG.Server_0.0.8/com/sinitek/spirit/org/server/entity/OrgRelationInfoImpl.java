// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgRelationInfoImpl.java

package com.sinitek.spirit.org.server.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.spirit.org.server.entity:
//            IOrgRelationInfo

public class OrgRelationInfoImpl extends MetaObjectImpl
    implements IOrgRelationInfo
{

    public OrgRelationInfoImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("ORGRELATIONINFO"));
    }

    public OrgRelationInfoImpl(IEntity entity)
    {
        super(entity);
    }

    public String getFromObjectId()
    {
        return (String)get("FromObjectId");
    }

    public void setFromObjectId(String value)
    {
        put("FromObjectId", value);
    }

    public String getToObjectId()
    {
        return (String)get("ToObjectId");
    }

    public void setToObjectId(String value)
    {
        put("ToObjectId", value);
    }

    public String getRelationType()
    {
        return (String)get("RelationType");
    }

    public void setRelationType(String value)
    {
        put("RelationType", value);
    }

    public Integer getOrderCode()
    {
        return (Integer)get("OrderCode");
    }

    public void setOrderCode(Integer value)
    {
        put("OrderCode", value);
    }
}
