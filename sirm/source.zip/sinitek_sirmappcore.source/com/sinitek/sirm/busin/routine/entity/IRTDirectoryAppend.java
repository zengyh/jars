// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRTDirectoryAppend.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IRTDirectoryAppend
    extends IMetaObjectImpl
{

    public abstract Integer getIssearch();

    public abstract void setIssearch(Integer integer);

    public abstract Integer getAllowapplyautor();

    public abstract void setAllowapplyautor(Integer integer);

    public abstract Integer getAllowsubapplyauthor();

    public abstract void setAllowsubapplyauthor(Integer integer);

    public abstract Integer getWorkflowid();

    public abstract void setWorkflowid(Integer integer);

    public abstract Integer getDirectoryid();

    public abstract void setDirectoryid(Integer integer);

    public static final String ENTITY_NAME = "RTDIRECTORYAPPEND";
}
