// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ActionInfoImpl.java

package com.sinitek.sirm.common.engine.event.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.engine.event.entity:
//            IActionInfo

public class ActionInfoImpl extends MetaObjectImpl
    implements IActionInfo
{

    public ActionInfoImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("ACTIONINFO"));
    }

    public ActionInfoImpl(IEntity entity)
    {
        super(entity);
    }

    public String getCode()
    {
        return (String)get("code");
    }

    public void setCode(String value)
    {
        put("code", value);
    }

    public String getName()
    {
        return (String)get("name");
    }

    public void setName(String value)
    {
        put("name", value);
    }

    public String getBrief()
    {
        return (String)get("brief");
    }

    public void setBrief(String value)
    {
        put("brief", value);
    }

    public String getLocation()
    {
        return (String)get("location");
    }

    public void setLocation(String value)
    {
        put("location", value);
    }

    public Integer getSyncflag()
    {
        return (Integer)get("syncflag");
    }

    public void setSyncflag(Integer value)
    {
        put("syncflag", value);
    }
}
