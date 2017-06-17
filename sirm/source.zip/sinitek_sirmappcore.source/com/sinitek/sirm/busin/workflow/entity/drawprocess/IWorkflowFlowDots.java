// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowFlowDots.java

package com.sinitek.sirm.busin.workflow.entity.drawprocess;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowFlowDots
    extends IMetaObjectImpl
{

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract Integer getPathid();

    public abstract void setPathid(Integer integer);

    public abstract String getPos();

    public abstract void setPos(String s);

    public static final String ENTITY_NAME = "WFFLOWDOTS";
}
