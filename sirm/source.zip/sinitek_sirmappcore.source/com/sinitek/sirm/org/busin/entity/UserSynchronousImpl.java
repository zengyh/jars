// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserSynchronousImpl.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.org.busin.entity:
//            IUserSynchronous

public class UserSynchronousImpl extends MetaObjectImpl
    implements IUserSynchronous
{

    public UserSynchronousImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("USERSYNCHRONOUS"));
    }

    public UserSynchronousImpl(IEntity entity)
    {
        super(entity);
    }

    public String getPassword()
    {
        return (String)get("password");
    }

    public void setPassword(String value)
    {
        put("password", value);
    }

    public String getUserName()
    {
        return (String)get("userName");
    }

    public void setUserName(String value)
    {
        put("userName", value);
    }

    public String getDeptname()
    {
        return (String)get("deptname");
    }

    public void setDeptname(String value)
    {
        put("deptname", value);
    }

    public String getMobile()
    {
        return (String)get("mobile");
    }

    public void setMobile(String value)
    {
        put("mobile", value);
    }

    public String getEmail()
    {
        return (String)get("email");
    }

    public void setEmail(String value)
    {
        put("email", value);
    }

    public String getUserId()
    {
        return (String)get("userId");
    }

    public void setUserId(String value)
    {
        put("userId", value);
    }

    public String getTelephone()
    {
        return (String)get("telephone");
    }

    public void setTelephone(String value)
    {
        put("telephone", value);
    }

    public String getRemark()
    {
        return (String)get("remark");
    }

    public void setRemark(String value)
    {
        put("remark", value);
    }

    public Integer getIssuccess()
    {
        return (Integer)get("issuccess");
    }

    public void setIssuccess(Integer value)
    {
        put("issuccess", value);
    }
}
