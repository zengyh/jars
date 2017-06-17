// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTApplyDocAuthImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            IRTApplyDocAuth

public class RTApplyDocAuthImpl extends MetaObjectImpl
    implements IRTApplyDocAuth
{

    public RTApplyDocAuthImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("RTAPPLYDOCAUTH"));
    }

    public RTApplyDocAuthImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getDocumentid()
    {
        return (Integer)get("documentid");
    }

    public void setDocumentid(Integer value)
    {
        put("documentid", value);
    }

    public Integer getOrgid()
    {
        return (Integer)get("orgid");
    }

    public void setOrgid(Integer value)
    {
        put("orgid", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }

    public Date getStarttime()
    {
        return (Date)get("starttime");
    }

    public void setStarttime(Date value)
    {
        put("starttime", value);
    }

    public Date getEndtime()
    {
        return (Date)get("endtime");
    }

    public void setEndtime(Date value)
    {
        put("endtime", value);
    }

    public Date getApplytime()
    {
        return (Date)get("applytime");
    }

    public void setApplytime(Date value)
    {
        put("applytime", value);
    }

    public String getApplyreason()
    {
        return (String)get("applyreason");
    }

    public void setApplyreason(String value)
    {
        put("applyreason", value);
    }

    public Integer getExampleid()
    {
        return (Integer)get("exampleid");
    }

    public void setExampleid(Integer value)
    {
        put("exampleid", value);
    }

    public String getApplyauth()
    {
        return (String)get("applyauth");
    }

    public void setApplyauth(String value)
    {
        put("applyauth", value);
    }
}
