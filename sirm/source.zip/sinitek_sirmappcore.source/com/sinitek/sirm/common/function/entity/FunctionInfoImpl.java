// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FunctionInfoImpl.java

package com.sinitek.sirm.common.function.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.function.entity:
//            IFunctionInfo

public class FunctionInfoImpl extends MetaObjectImpl
    implements IFunctionInfo
{

    public FunctionInfoImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("FUNCTIONINFO"));
    }

    public FunctionInfoImpl(IEntity entity)
    {
        super(entity);
    }

    public String getCode()
    {
        return (String)get("Code");
    }

    public void setCode(String value)
    {
        put("Code", value);
    }

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }

    public Integer getSort()
    {
        return (Integer)get("Sort");
    }

    public void setSort(Integer value)
    {
        put("Sort", value);
    }

    public String getUrl()
    {
        return (String)get("Url");
    }

    public void setUrl(String value)
    {
        put("Url", value);
    }

    public String getAction()
    {
        return (String)get("Action");
    }

    public void setAction(String value)
    {
        put("Action", value);
    }

    public String getMethod()
    {
        return (String)get("Method");
    }

    public void setMethod(String value)
    {
        put("Method", value);
    }

    public String getBrief()
    {
        return (String)get("Brief");
    }

    public void setBrief(String value)
    {
        put("Brief", value);
    }

    public Integer getGroupId()
    {
        return (Integer)get("GroupId");
    }

    public void setGroupId(Integer value)
    {
        put("GroupId", value);
    }

    public Integer getType()
    {
        return (Integer)get("Type");
    }

    public void setType(Integer value)
    {
        put("Type", value);
    }
}
