// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessPara.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessPara
    extends IMetaObjectImpl
{

    public abstract Integer getTypeId();

    public abstract void setTypeId(Integer integer);

    public abstract String getCodeName();

    public abstract void setCodeName(String s);

    public abstract String getShowName();

    public abstract void setShowName(String s);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract Integer getKind();

    public abstract void setKind(Integer integer);

    public abstract String getBaseValue();

    public abstract void setBaseValue(String s);

    public abstract Integer getColumnId();

    public abstract void setColumnId(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public static final String ENTITY_NAME = "WFPROCESSPARA";
}
