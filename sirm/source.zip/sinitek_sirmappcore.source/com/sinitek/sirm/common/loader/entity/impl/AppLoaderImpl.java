// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AppLoaderImpl.java

package com.sinitek.sirm.common.loader.entity.impl;

import com.sinitek.base.metadb.*;
import com.sinitek.sirm.common.loader.entity.IAppLoader;

public class AppLoaderImpl extends MetaObjectImpl
    implements IAppLoader
{

    public AppLoaderImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("APPLOADER"));
    }

    public AppLoaderImpl(IEntity entity)
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

    public String getCode()
    {
        return (String)get("code");
    }

    public void setCode(String value)
    {
        put("code", value);
    }

    public String getBrief()
    {
        return (String)get("brief");
    }

    public void setBrief(String value)
    {
        put("brief", value);
    }

    public String getClassFullName()
    {
        return (String)get("classFullName");
    }

    public void setClassFullName(String value)
    {
        put("classFullName", value);
    }

    public Integer getPriority()
    {
        return (Integer)get("priority");
    }

    public void setPriority(Integer value)
    {
        put("priority", value);
    }

    public Boolean getEnabled()
    {
        return (Boolean)get("enabled");
    }

    public void setEnabled(Boolean value)
    {
        put("enabled", value);
    }
}
