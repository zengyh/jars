// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageTemplateRelaImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            IMessageTemplateRela

public class MessageTemplateRelaImpl extends MetaObjectImpl
    implements IMessageTemplateRela
{

    public MessageTemplateRelaImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("MESSAGETEMPLATERELA"));
    }

    public MessageTemplateRelaImpl(IEntity entity)
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

    public String getSourceEntity()
    {
        return (String)get("SourceEntity");
    }

    public void setSourceEntity(String value)
    {
        put("SourceEntity", value);
    }

    public Integer getSourceId()
    {
        return (Integer)get("SourceId");
    }

    public void setSourceId(Integer value)
    {
        put("SourceId", value);
    }

    public Integer getRelaType()
    {
        return (Integer)get("RelaType");
    }

    public void setRelaType(Integer value)
    {
        put("RelaType", value);
    }

    public String getDescription()
    {
        return (String)get("Description");
    }

    public void setDescription(String value)
    {
        put("Description", value);
    }
}
