// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRTDocumentAuth.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IRTDocumentAuth
    extends IMetaObjectImpl
{

    public abstract String getSharinger();

    public abstract void setSharinger(String s);

    public abstract Integer getAuthid();

    public abstract void setAuthid(Integer integer);

    public abstract Date getBegintime();

    public abstract void setBegintime(Date date);

    public abstract Date getEndtime();

    public abstract void setEndtime(Date date);

    public abstract String getAuthEntity();

    public abstract void setAuthEntity(String s);

    public abstract String getOrgid();

    public abstract void setOrgid(String s);

    public abstract String getFromEntity();

    public abstract void setFromEntity(String s);

    public abstract Integer getFromObjid();

    public abstract void setFromObjid(Integer integer);

    public static final String ENTITY_NAME = "RTDOCUMENTAUTH";
}
