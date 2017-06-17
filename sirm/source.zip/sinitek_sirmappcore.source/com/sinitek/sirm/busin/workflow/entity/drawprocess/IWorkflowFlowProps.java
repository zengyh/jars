// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowFlowProps.java

package com.sinitek.sirm.busin.workflow.entity.drawprocess;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowFlowProps
    extends IMetaObjectImpl
{

    public abstract String getKey();

    public abstract void setKey(String s);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract String getSourcename();

    public abstract void setSourcename(String s);

    public abstract Integer getSourceid();

    public abstract void setSourceid(Integer integer);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getValue();

    public abstract void setValue(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public static final String ENTITY_NAME = "WFFLOWPROPS";
}
