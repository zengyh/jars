// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JobQuartzClassRealImpl.java

package com.sinitek.sirm.common.quartz.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.quartz.entity:
//            IJobQuartzClassReal

public class JobQuartzClassRealImpl extends MetaObjectImpl
    implements IJobQuartzClassReal
{

    public JobQuartzClassRealImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("JOBQUARTZCLASSREAL"));
    }

    public JobQuartzClassRealImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getTIMERID()
    {
        return (Integer)get("TIMERID");
    }

    public void setTIMERID(Integer value)
    {
        put("TIMERID", value);
    }

    public String getCLASSNAME()
    {
        return (String)get("CLASSNAME");
    }

    public void setCLASSNAME(String value)
    {
        put("CLASSNAME", value);
    }

    public String getCLASSPATH()
    {
        return (String)get("CLASSPATH");
    }

    public void setCLASSPATH(String value)
    {
        put("CLASSPATH", value);
    }

    public String getMEMO()
    {
        return (String)get("MEMO");
    }

    public void setMEMO(String value)
    {
        put("MEMO", value);
    }

    public Integer getGROUPID()
    {
        return (Integer)get("GROUPID");
    }

    public void setGROUPID(Integer value)
    {
        put("GROUPID", value);
    }
}
