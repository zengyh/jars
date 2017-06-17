// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearchFilterConditionImpl.java

package com.sinitek.sirm.common.searchfilter.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.searchfilter.entity:
//            ISearchFilterCondition

public class SearchFilterConditionImpl extends MetaObjectImpl
    implements ISearchFilterCondition
{

    public SearchFilterConditionImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SEARCHFILTERCONDITION"));
    }

    public SearchFilterConditionImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getFilterId()
    {
        return (Integer)get("FilterId");
    }

    public void setFilterId(Integer value)
    {
        put("FilterId", value);
    }

    public String getFieldName()
    {
        return (String)get("FieldName");
    }

    public void setFieldName(String value)
    {
        put("FieldName", value);
    }

    public String getSearchType()
    {
        return (String)get("SearchType");
    }

    public void setSearchType(String value)
    {
        put("SearchType", value);
    }

    public String getCondition()
    {
        return (String)get("Condition");
    }

    public void setCondition(String value)
    {
        put("Condition", value);
    }
}
