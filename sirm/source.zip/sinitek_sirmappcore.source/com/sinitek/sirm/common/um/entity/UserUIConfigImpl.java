// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserUIConfigImpl.java

package com.sinitek.sirm.common.um.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.um.entity:
//            IUserUIConfig

public class UserUIConfigImpl extends MetaObjectImpl
    implements IUserUIConfig
{

    public UserUIConfigImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("USERUICONFIG"));
    }

    public UserUIConfigImpl(IEntity entity)
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

    public String getCatalog()
    {
        return (String)get("Catalog");
    }

    public void setCatalog(String value)
    {
        put("Catalog", value);
    }

    public String getActionClass()
    {
        return (String)get("ActionClass");
    }

    public void setActionClass(String value)
    {
        put("ActionClass", value);
    }

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }

    public String getValue()
    {
        return (String)get("value");
    }

    public void setValue(String value)
    {
        put("value", value);
    }
}
