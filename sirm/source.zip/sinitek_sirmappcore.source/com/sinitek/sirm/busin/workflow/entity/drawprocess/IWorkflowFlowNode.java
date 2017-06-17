// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowFlowNode.java

package com.sinitek.sirm.busin.workflow.entity.drawprocess;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowFlowNode
    extends IMetaObjectImpl
{

    public abstract String getAttr();

    public abstract void setAttr(String s);

    public abstract String getType();

    public abstract void setType(String s);

    public abstract Integer getProcessid();

    public abstract void setProcessid(Integer integer);

    public abstract String getText();

    public abstract void setText(String s);

    public static final String ENTITY_NAME = "WFFLOWNODE";
}
