// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IWorkflowProcessOwnerLink.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IWorkflowProcessOwnerLink
    extends IMetaObjectImpl
{

    public abstract String getOwnerStarter();

    public abstract void setOwnerStarter(String s);

    public abstract String getOwnerEnder();

    public abstract void setOwnerEnder(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract Integer getLinkRoot();

    public abstract void setLinkRoot(Integer integer);

    public abstract Integer getLinkLeaf();

    public abstract void setLinkLeaf(Integer integer);

    public abstract Integer getStepId();

    public abstract void setStepId(Integer integer);

    public abstract Integer getProcessId();

    public abstract void setProcessId(Integer integer);

    public static final String ENTITY_NAME = "WFPROCESSOWNERLINK";
}
