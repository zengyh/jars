// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IGroupSetting.java

package com.sinitek.sirm.common.setting.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IGroupSetting
    extends IMetaObjectImpl
{

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getCatalogCode();

    public abstract void setCatalogCode(String s);

    public abstract String getUrl();

    public abstract void setUrl(String s);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public static final String ENTITY_NAME = "SIRMGROUPSETTING";
}
