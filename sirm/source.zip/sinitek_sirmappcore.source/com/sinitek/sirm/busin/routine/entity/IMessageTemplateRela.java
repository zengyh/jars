// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMessageTemplateRela.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface IMessageTemplateRela
    extends IMetaObjectImpl
{

    public abstract String getCode();

    public abstract void setCode(String s);

    public abstract String getSourceEntity();

    public abstract void setSourceEntity(String s);

    public abstract Integer getSourceId();

    public abstract void setSourceId(Integer integer);

    public abstract Integer getRelaType();

    public abstract void setRelaType(Integer integer);

    public abstract String getDescription();

    public abstract void setDescription(String s);

    public static final String ENTITY_NAME = "MESSAGETEMPLATERELA";
}
