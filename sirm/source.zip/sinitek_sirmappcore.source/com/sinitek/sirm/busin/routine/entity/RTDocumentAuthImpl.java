// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTDocumentAuthImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            IRTDocumentAuth

public class RTDocumentAuthImpl extends MetaObjectImpl
    implements IRTDocumentAuth
{

    public RTDocumentAuthImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("RTDOCUMENTAUTH"));
    }

    public RTDocumentAuthImpl(IEntity entity)
    {
        super(entity);
    }

    public String getSharinger()
    {
        return (String)get("Sharinger");
    }

    public void setSharinger(String value)
    {
        put("Sharinger", value);
    }

    public Integer getAuthid()
    {
        return (Integer)get("Authid");
    }

    public void setAuthid(Integer value)
    {
        put("Authid", value);
    }

    public Date getBegintime()
    {
        return (Date)get("Begintime");
    }

    public void setBegintime(Date value)
    {
        put("Begintime", value);
    }

    public Date getEndtime()
    {
        return (Date)get("endtime");
    }

    public void setEndtime(Date value)
    {
        put("endtime", value);
    }

    public String getAuthEntity()
    {
        return (String)get("authEntity");
    }

    public void setAuthEntity(String value)
    {
        put("authEntity", value);
    }

    public String getOrgid()
    {
        return (String)get("Orgid");
    }

    public void setOrgid(String value)
    {
        put("Orgid", value);
    }

    public String getFromEntity()
    {
        return (String)get("FromEntity");
    }

    public void setFromEntity(String value)
    {
        put("FromEntity", value);
    }

    public Integer getFromObjid()
    {
        return (Integer)get("FromObjid");
    }

    public void setFromObjid(Integer value)
    {
        put("FromObjid", value);
    }
}
