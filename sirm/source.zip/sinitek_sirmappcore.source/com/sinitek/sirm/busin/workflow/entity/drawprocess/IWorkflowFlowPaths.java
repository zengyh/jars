// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowFlowPaths.java

package com.sinitek.sirm.busin.workflow.entity.drawprocess;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowFlowPaths
    extends IMetaObjectImpl
{

    public abstract String getType();

    public abstract void setType(String s);

    public abstract Integer getToid();

    public abstract void setToid(Integer integer);

    public abstract Integer getFromid();

    public abstract void setFromid(Integer integer);

    public abstract Integer getProcessid();

    public abstract void setProcessid(Integer integer);

    public static final String ENTITY_NAME = "WFFLOWPATHS";
}
