// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmTagRelaImpl.java

package com.sinitek.sirm.common.taginfo.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.taginfo.entity:
//            ISirmTagRela

public class SirmTagRelaImpl extends MetaObjectImpl
    implements ISirmTagRela
{

    public SirmTagRelaImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SIRMTAGRELA"));
    }

    public SirmTagRelaImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getTagid()
    {
        return (Integer)get("tagid");
    }

    public void setTagid(Integer value)
    {
        put("tagid", value);
    }

    public String getSourceentity()
    {
        return (String)get("sourceentity");
    }

    public void setSourceentity(String value)
    {
        put("sourceentity", value);
    }

    public Integer getSourceid()
    {
        return (Integer)get("sourceid");
    }

    public void setSourceid(Integer value)
    {
        put("sourceid", value);
    }
}
