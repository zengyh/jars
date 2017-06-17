// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISendMessageContent.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;

public interface ISendMessageContent
    extends IMetaObjectImpl
{

    public abstract Integer getSendMessageId();

    public abstract void setSendMessageId(Integer integer);

    public abstract Integer getSendMode();

    public abstract void setSendMode(Integer integer);

    public abstract String getTitle();

    public abstract void setTitle(String s);

    public abstract String getContent();

    public abstract void setContent(String s);

    public static final String ENTITY_NAME = "SENDMESSAGECONTENT";
}
