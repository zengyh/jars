// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RT_KeyReaderImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            IRT_KeyReader

public class RT_KeyReaderImpl extends MetaObjectImpl
    implements IRT_KeyReader
{

    public RT_KeyReaderImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("RTKEYREADER"));
    }

    public RT_KeyReaderImpl(IEntity entity)
    {
        super(entity);
    }

    public String getAuthEntity()
    {
        return (String)get("authEntity");
    }

    public void setAuthEntity(String value)
    {
        put("authEntity", value);
    }

    public String getReply()
    {
        return (String)get("reply");
    }

    public void setReply(String value)
    {
        put("reply", value);
    }

    public String getReaderId()
    {
        return (String)get("readerId");
    }

    public void setReaderId(String value)
    {
        put("readerId", value);
    }

    public Integer getAuthId()
    {
        return (Integer)get("authId");
    }

    public void setAuthId(Integer value)
    {
        put("authId", value);
    }

    public String getOrgid()
    {
        return (String)get("orgid");
    }

    public void setOrgid(String value)
    {
        put("orgid", value);
    }
}
