// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgObjectImpl.java

package com.sinitek.spirit.org.server.entity;

import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.spirit.org.server.entity:
//            IOrgObject

public class OrgObjectImpl extends MetaObjectImpl
    implements IOrgObject
{

    public OrgObjectImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("ORGOBJECT"));
    }

    public OrgObjectImpl(IEntity entity)
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

    public String getOrgName()
    {
        return (String)get("OrgName");
    }

    public void setOrgName(String value)
    {
        put("OrgName", value);
    }

    public IEnumItem getObjectType()
    {
        return (IEnumItem)get("ObjectType");
    }

    public void setObjectType(IEnumItem value)
    {
        put("ObjectType", value);
    }

    public void setObjectTypeEnumValue(Integer value)
    {
        setEnumValue("ObjectType", value);
    }

    public String getDescription()
    {
        return (String)get("Description");
    }

    public void setDescription(String value)
    {
        put("Description", value);
    }

    public String getUnitType()
    {
        return (String)get("UnitType");
    }

    public void setUnitType(String value)
    {
        put("UnitType", value);
    }

    public String getUserId()
    {
        return (String)get("UserId");
    }

    public void setUserId(String value)
    {
        put("UserId", value);
    }

    public Boolean getInservice()
    {
        return (Boolean)get("Inservice");
    }

    public void setInservice(Boolean value)
    {
        put("Inservice", value);
    }
}
