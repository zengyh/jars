// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearchFilterImpl.java

package com.sinitek.sirm.common.searchfilter.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.searchfilter.entity:
//            ISearchFilter

public class SearchFilterImpl extends MetaObjectImpl
    implements ISearchFilter
{

    public SearchFilterImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SEARCHFILTER"));
    }

    public SearchFilterImpl(IEntity entity)
    {
        super(entity);
    }

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }

    public Integer getEmpId()
    {
        return (Integer)get("EmpId");
    }

    public void setEmpId(Integer value)
    {
        put("EmpId", value);
    }

    public String getCatalog()
    {
        return (String)get("Catalog");
    }

    public void setCatalog(String value)
    {
        put("Catalog", value);
    }
}
