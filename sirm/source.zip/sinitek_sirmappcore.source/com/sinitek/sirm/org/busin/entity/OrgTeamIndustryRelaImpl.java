// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgTeamIndustryRelaImpl.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.org.busin.entity:
//            IOrgTeamIndustryRela

public class OrgTeamIndustryRelaImpl extends MetaObjectImpl
    implements IOrgTeamIndustryRela
{

    public OrgTeamIndustryRelaImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("ORGTEAMINDUSTRYRELA"));
    }

    public OrgTeamIndustryRelaImpl(IEntity entity)
    {
        super(entity);
    }

    public String getTeamId()
    {
        return (String)get("TeamId");
    }

    public void setTeamId(String value)
    {
        put("TeamId", value);
    }

    public String getIndustryCode()
    {
        return (String)get("IndustryCode");
    }

    public void setIndustryCode(String value)
    {
        put("IndustryCode", value);
    }
}
