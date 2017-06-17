// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntitySettingImpl.java

package com.sinitek.sirm.common.setting.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.setting.entity:
//            IEntitySetting

public class EntitySettingImpl extends MetaObjectImpl
    implements IEntitySetting
{

    public EntitySettingImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("ENTITYSETTING"));
    }

    public EntitySettingImpl(IEntity entity)
    {
        super(entity);
    }

    public String getSourceentity()
    {
        return (String)get("sourceentity");
    }

    public void setSourceentity(String value)
    {
        put("sourceentity", value);
    }

    public Integer getSourceid()
    {
        return (Integer)get("sourceid");
    }

    public void setSourceid(Integer value)
    {
        put("sourceid", value);
    }

    public String getName()
    {
        return (String)get("name");
    }

    public void setName(String value)
    {
        put("name", value);
    }

    public String getValue()
    {
        return (String)get("value");
    }

    public void setValue(String value)
    {
        put("value", value);
    }

    public String getBrief()
    {
        return (String)get("brief");
    }

    public void setBrief(String value)
    {
        put("brief", value);
    }
}
