// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmTagInfoImpl.java

package com.sinitek.sirm.common.taginfo.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.common.taginfo.entity:
//            ISirmTagInfo

public class SirmTagInfoImpl extends MetaObjectImpl
    implements ISirmTagInfo
{

    public SirmTagInfoImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SIRMTAGINFO"));
    }

    public SirmTagInfoImpl(IEntity entity)
    {
        super(entity);
    }

    public String getTagname()
    {
        return (String)get("tagname");
    }

    public void setTagname(String value)
    {
        put("tagname", value);
    }

    public String getPinyin()
    {
        return (String)get("pinyin");
    }

    public void setPinyin(String value)
    {
        put("pinyin", value);
    }

    public Integer getTagtype()
    {
        return (Integer)get("tagtype");
    }

    public void setTagtype(Integer value)
    {
        put("tagtype", value);
    }

    public Integer getHeat()
    {
        return (Integer)get("heat");
    }

    public void setHeat(Integer value)
    {
        put("heat", value);
    }

    public Integer getType()
    {
        return (Integer)get("type");
    }

    public void setType(Integer value)
    {
        put("type", value);
    }

    public String getEmpid()
    {
        return (String)get("empid");
    }

    public void setEmpid(String value)
    {
        put("empid", value);
    }

    public Date getCreatetime()
    {
        return (Date)get("createtime");
    }

    public void setCreatetime(Date value)
    {
        put("createtime", value);
    }
}
