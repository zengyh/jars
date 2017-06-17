// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JobQuartzTimeImpl.java

package com.sinitek.sirm.common.quartz.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.quartz.entity:
//            IJobQuartzTime

public class JobQuartzTimeImpl extends MetaObjectImpl
    implements IJobQuartzTime
{

    public JobQuartzTimeImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("JOBQUARTZTIME"));
    }

    public JobQuartzTimeImpl(IEntity entity)
    {
        super(entity);
    }

    public String getTimeExpression()
    {
        return (String)get("timeExpression");
    }

    public void setTimeExpression(String value)
    {
        put("timeExpression", value);
    }

    public String getTimeName()
    {
        return (String)get("timeName");
    }

    public void setTimeName(String value)
    {
        put("timeName", value);
    }
}
