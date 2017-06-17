// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IQualifyInfo.java

package com.sinitek.sirm.org.busin.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IQualifyInfo
    extends IMetaObjectImpl
{

    public abstract String getOrgId();

    public abstract void setOrgId(String s);

    public abstract Integer getQualifyType();

    public abstract void setQualifyType(Integer integer);

    public abstract String getQualifyno();

    public abstract void setQualifyno(String s);

    public abstract Date getEndtime();

    public abstract void setEndtime(Date date);

    public abstract Date getFailureDate();

    public abstract void setFailureDate(Date date);

    public abstract String getIssuingUnit();

    public abstract void setIssuingUnit(String s);

    public abstract Date getIssuingDate();

    public abstract void setIssuingDate(Date date);

    public abstract String getBrief();

    public abstract void setBrief(String s);

    public abstract Integer getLev();

    public abstract void setLev(Integer integer);

    public abstract String getOrigid();

    public abstract void setOrigid(String s);

    public abstract String getDataSrc();

    public abstract void setDataSrc(String s);

    public static final String ENTITY_NAME = "QUALIFYINFO";
}
