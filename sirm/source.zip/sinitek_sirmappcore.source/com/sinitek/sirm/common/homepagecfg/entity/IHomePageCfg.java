// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IHomePageCfg.java

package com.sinitek.sirm.common.homepagecfg.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IHomePageCfg
    extends IMetaObjectImpl
{

    public abstract Integer getOrgType();

    public abstract void setOrgType(Integer integer);

    public abstract Integer getOrgId();

    public abstract void setOrgId(Integer integer);

    public abstract Integer getPageLayout();

    public abstract void setPageLayout(Integer integer);

    public abstract String getModuleLayout();

    public abstract void setModuleLayout(String s);

    public abstract Integer getRighttype();

    public abstract void setRighttype(Integer integer);

    public abstract Integer getModuleId();

    public abstract void setModuleId(Integer integer);

    public abstract Integer getSort();

    public abstract void setSort(Integer integer);

    public static final String ENTITY_NAME = "HOMEPAGECFG";
}
