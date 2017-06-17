// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IOrgObject.java

package com.sinitek.spirit.org.server.entity;

import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IOrgObject
    extends IMetaObjectImpl
{

    public abstract String getOrgId();

    public abstract void setOrgId(String s);

    public abstract String getOrgName();

    public abstract void setOrgName(String s);

    public abstract IEnumItem getObjectType();

    public abstract void setObjectType(IEnumItem ienumitem);

    public abstract void setObjectTypeEnumValue(Integer integer);

    public abstract String getDescription();

    public abstract void setDescription(String s);

    public abstract String getUnitType();

    public abstract void setUnitType(String s);

    public abstract String getUserId();

    public abstract void setUserId(String s);

    public abstract Boolean getInservice();

    public abstract void setInservice(Boolean boolean1);

    public static final String ENTITY_NAME = "ORGOBJECT";
}
