// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmEnumImpl.java

package com.sinitek.sirm.common.sirmenum.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.sirmenum.entity:
//            ISirmEnum

public class SirmEnumImpl extends MetaObjectImpl
    implements ISirmEnum
{

    public SirmEnumImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SIRMENUM"));
    }

    public SirmEnumImpl(IEntity entity)
    {
        super(entity);
    }

    public String getType()
    {
        return (String)get("type");
    }

    public void setType(String value)
    {
        put("type", value);
    }

    public String getName()
    {
        return (String)get("name");
    }

    public void setName(String value)
    {
        put("name", value);
    }

    public Integer getValue()
    {
        return (Integer)get("value");
    }

    public void setValue(Integer value)
    {
        put("value", value);
    }

    public String getDescription()
    {
        return (String)get("description");
    }

    public void setDescription(String value)
    {
        put("description", value);
    }

    public Integer getSort()
    {
        return (Integer)get("sort");
    }

    public void setSort(Integer value)
    {
        put("sort", value);
    }

    public String getCataLog()
    {
        return (String)get("cataLog");
    }

    public void setCataLog(String value)
    {
        put("cataLog", value);
    }

    public String getStrvalue()
    {
        return (String)get("strvalue");
    }

    public void setStrvalue(String value)
    {
        put("strvalue", value);
    }
}
