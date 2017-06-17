// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FunctionGroupImpl.java

package com.sinitek.sirm.common.function.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.function.entity:
//            IFunctionGroup

public class FunctionGroupImpl extends MetaObjectImpl
    implements IFunctionGroup
{

    public FunctionGroupImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("FUNCTIONGROUP"));
    }

    public FunctionGroupImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getParentId()
    {
        return (Integer)get("ParentId");
    }

    public void setParentId(Integer value)
    {
        put("ParentId", value);
    }

    public Integer getSort()
    {
        return (Integer)get("Sort");
    }

    public void setSort(Integer value)
    {
        put("Sort", value);
    }

    public String getBrief()
    {
        return (String)get("Brief");
    }

    public void setBrief(String value)
    {
        put("Brief", value);
    }

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }
}
