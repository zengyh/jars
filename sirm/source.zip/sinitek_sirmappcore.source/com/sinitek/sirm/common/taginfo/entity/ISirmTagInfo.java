// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISirmTagInfo.java

package com.sinitek.sirm.common.taginfo.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface ISirmTagInfo
    extends IMetaObjectImpl
{

    public abstract String getTagname();

    public abstract void setTagname(String s);

    public abstract String getPinyin();

    public abstract void setPinyin(String s);

    public abstract Integer getTagtype();

    public abstract void setTagtype(Integer integer);

    public abstract Integer getHeat();

    public abstract void setHeat(Integer integer);

    public abstract Integer getType();

    public abstract void setType(Integer integer);

    public abstract String getEmpid();

    public abstract void setEmpid(String s);

    public abstract Date getCreatetime();

    public abstract void setCreatetime(Date date);

    public static final String ENTITY_NAME = "SIRMTAGINFO";
}
