// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowExampleEntry.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IWorkflowExampleEntry
    extends IMetaObjectImpl
{

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract String getSourceName();

    public abstract void setSourceName(String s);

    public abstract Integer getSourceId();

    public abstract void setSourceId(Integer integer);

    public abstract Date getChangeTime();

    public abstract void setChangeTime(Date date);

    public abstract Integer getExampleId();

    public abstract void setExampleId(Integer integer);

    public static final String ENTITY_NAME = "WFEXAMPLEENTRY";
}
