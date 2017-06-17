// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRTApplyDocAuth.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IRTApplyDocAuth
    extends IMetaObjectImpl
{

    public abstract Integer getDocumentid();

    public abstract void setDocumentid(Integer integer);

    public abstract Integer getOrgid();

    public abstract void setOrgid(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Date getStarttime();

    public abstract void setStarttime(Date date);

    public abstract Date getEndtime();

    public abstract void setEndtime(Date date);

    public abstract Date getApplytime();

    public abstract void setApplytime(Date date);

    public abstract String getApplyreason();

    public abstract void setApplyreason(String s);

    public abstract Integer getExampleid();

    public abstract void setExampleid(Integer integer);

    public abstract String getApplyauth();

    public abstract void setApplyauth(String s);

    public static final String ENTITY_NAME = "RTAPPLYDOCAUTH";
}
