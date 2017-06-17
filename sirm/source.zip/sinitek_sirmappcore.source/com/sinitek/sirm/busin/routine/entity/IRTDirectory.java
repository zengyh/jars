// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRTDirectory.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IRTDirectory
    extends IMetaObjectImpl
{

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public abstract String getDirectoryer();

    public abstract void setDirectoryer(String s);

    public abstract String getDirectoryname();

    public abstract void setDirectoryname(String s);

    public abstract Integer getParentid();

    public abstract void setParentid(Integer integer);

    public abstract String getDirectorycontent();

    public abstract void setDirectorycontent(String s);

    public abstract Integer getDirectorytype();

    public abstract void setDirectorytype(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public static final String ENTITY_NAME = "RTDIRECTORY";
}
