// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GroupSettingImpl.java

package com.sinitek.sirm.common.setting.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.setting.entity:
//            IGroupSetting

public class GroupSettingImpl extends MetaObjectImpl
    implements IGroupSetting
{

    public GroupSettingImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SIRMGROUPSETTING"));
    }

    public GroupSettingImpl(IEntity entity)
    {
        super(entity);
    }

    public String getName()
    {
        return (String)get("name");
    }

    public void setName(String value)
    {
        put("name", value);
    }

    public String getCatalogCode()
    {
        return (String)get("catalogCode");
    }

    public void setCatalogCode(String value)
    {
        put("catalogCode", value);
    }

    public String getUrl()
    {
        return (String)get("url");
    }

    public void setUrl(String value)
    {
        put("url", value);
    }

    public Integer getSort()
    {
        return (Integer)get("sort");
    }

    public void setSort(Integer value)
    {
        put("sort", value);
    }
}
