// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JobQuartzGroupImpl.java

package com.sinitek.sirm.common.quartz.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.quartz.entity:
//            IJobQuartzGroup

public class JobQuartzGroupImpl extends MetaObjectImpl
    implements IJobQuartzGroup
{

    public JobQuartzGroupImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("JOBQUARTZGROUP"));
    }

    public JobQuartzGroupImpl(IEntity entity)
    {
        super(entity);
    }

    public String getGroupName()
    {
        return (String)get("GroupName");
    }

    public void setGroupName(String value)
    {
        put("GroupName", value);
    }

    public String getMemo()
    {
        return (String)get("Memo");
    }

    public void setMemo(String value)
    {
        put("Memo", value);
    }

    public String getCode()
    {
        return (String)get("code");
    }

    public void setCode(String value)
    {
        put("code", value);
    }
}
