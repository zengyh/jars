// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HomePageCfgImpl.java

package com.sinitek.sirm.common.homepagecfg.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.homepagecfg.entity:
//            IHomePageCfg

public class HomePageCfgImpl extends MetaObjectImpl
    implements IHomePageCfg
{

    public HomePageCfgImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("HOMEPAGECFG"));
    }

    public HomePageCfgImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getOrgType()
    {
        return (Integer)get("OrgType");
    }

    public void setOrgType(Integer value)
    {
        put("OrgType", value);
    }

    public Integer getOrgId()
    {
        return (Integer)get("OrgId");
    }

    public void setOrgId(Integer value)
    {
        put("OrgId", value);
    }

    public Integer getPageLayout()
    {
        return (Integer)get("PageLayout");
    }

    public void setPageLayout(Integer value)
    {
        put("PageLayout", value);
    }

    public String getModuleLayout()
    {
        return (String)get("ModuleLayout");
    }

    public void setModuleLayout(String value)
    {
        put("ModuleLayout", value);
    }

    public Integer getRighttype()
    {
        return (Integer)get("righttype");
    }

    public void setRighttype(Integer value)
    {
        put("righttype", value);
    }

    public Integer getModuleId()
    {
        return (Integer)get("ModuleId");
    }

    public void setModuleId(Integer value)
    {
        put("ModuleId", value);
    }

    public Integer getSort()
    {
        return (Integer)get("Sort");
    }

    public void setSort(Integer value)
    {
        put("Sort", value);
    }
}
