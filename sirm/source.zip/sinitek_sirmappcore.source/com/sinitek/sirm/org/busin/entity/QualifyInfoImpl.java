// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QualifyInfoImpl.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.org.busin.entity:
//            IQualifyInfo

public class QualifyInfoImpl extends MetaObjectImpl
    implements IQualifyInfo
{

    public QualifyInfoImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("QUALIFYINFO"));
    }

    public QualifyInfoImpl(IEntity entity)
    {
        super(entity);
    }

    public String getOrgId()
    {
        return (String)get("orgId");
    }

    public void setOrgId(String value)
    {
        put("orgId", value);
    }

    public Integer getQualifyType()
    {
        return (Integer)get("qualifyType");
    }

    public void setQualifyType(Integer value)
    {
        put("qualifyType", value);
    }

    public String getQualifyno()
    {
        return (String)get("qualifyno");
    }

    public void setQualifyno(String value)
    {
        put("qualifyno", value);
    }

    public Date getEndtime()
    {
        return (Date)get("endtime");
    }

    public void setEndtime(Date value)
    {
        put("endtime", value);
    }

    public Date getFailureDate()
    {
        return (Date)get("FailureDate");
    }

    public void setFailureDate(Date value)
    {
        put("FailureDate", value);
    }

    public String getIssuingUnit()
    {
        return (String)get("IssuingUnit");
    }

    public void setIssuingUnit(String value)
    {
        put("IssuingUnit", value);
    }

    public Date getIssuingDate()
    {
        return (Date)get("IssuingDate");
    }

    public void setIssuingDate(Date value)
    {
        put("IssuingDate", value);
    }

    public String getBrief()
    {
        return (String)get("Brief");
    }

    public void setBrief(String value)
    {
        put("Brief", value);
    }

    public Integer getLev()
    {
        return (Integer)get("lev");
    }

    public void setLev(Integer value)
    {
        put("lev", value);
    }

    public String getOrigid()
    {
        return (String)get("origid");
    }

    public void setOrigid(String value)
    {
        put("origid", value);
    }

    public String getDataSrc()
    {
        return (String)get("DataSrc");
    }

    public void setDataSrc(String value)
    {
        put("DataSrc", value);
    }
}
