// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserSchemeImpl.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.org.busin.entity:
//            IUserScheme

public class UserSchemeImpl extends MetaObjectImpl
    implements IUserScheme
{

    public UserSchemeImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("USERSCHEME"));
    }

    public UserSchemeImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getRange()
    {
        return (Integer)get("Range");
    }

    public void setRange(Integer value)
    {
        put("Range", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public String getTitle()
    {
        return (String)get("Title");
    }

    public void setTitle(String value)
    {
        put("Title", value);
    }

    public String getCode()
    {
        return (String)get("Code");
    }

    public void setCode(String value)
    {
        put("Code", value);
    }

    public String getDepartmentIds()
    {
        return (String)get("DepartmentIds");
    }

    public void setDepartmentIds(String value)
    {
        put("DepartmentIds", value);
    }

    public String getPostIds()
    {
        return (String)get("PostIds");
    }

    public void setPostIds(String value)
    {
        put("PostIds", value);
    }

    public String getRoleIds()
    {
        return (String)get("RoleIds");
    }

    public void setRoleIds(String value)
    {
        put("RoleIds", value);
    }

    public String getTeamIds()
    {
        return (String)get("TeamIds");
    }

    public void setTeamIds(String value)
    {
        put("TeamIds", value);
    }
}
