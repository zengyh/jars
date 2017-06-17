// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SupportCenterImpl.java

package com.sinitek.sirm.common.supportcenter.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.supportcenter.entity:
//            ISupportCenter

public class SupportCenterImpl extends MetaObjectImpl
    implements ISupportCenter
{

    public SupportCenterImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("SUPPORTCENTER"));
    }

    public SupportCenterImpl(IEntity entity)
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

    public String getTel()
    {
        return (String)get("Tel");
    }

    public void setTel(String value)
    {
        put("Tel", value);
    }

    public String getRange()
    {
        return (String)get("range");
    }

    public void setRange(String value)
    {
        put("range", value);
    }

    public String getEmail()
    {
        return (String)get("Email");
    }

    public void setEmail(String value)
    {
        put("Email", value);
    }

    public String getMsn()
    {
        return (String)get("msn");
    }

    public void setMsn(String value)
    {
        put("msn", value);
    }

    public String getQQ()
    {
        return (String)get("QQ");
    }

    public void setQQ(String value)
    {
        put("QQ", value);
    }

    public Integer getSort()
    {
        return (Integer)get("Sort");
    }

    public void setSort(Integer value)
    {
        put("Sort", value);
    }
}
