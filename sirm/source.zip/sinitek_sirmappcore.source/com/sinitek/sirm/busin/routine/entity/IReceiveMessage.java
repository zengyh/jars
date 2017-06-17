// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IReceiveMessage.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IReceiveMessage
    extends IMetaObjectImpl
{

    public abstract Integer getReceiverType();

    public abstract void setReceiverType(Integer integer);

    public abstract Integer getSendMessageId();

    public abstract void setSendMessageId(Integer integer);

    public abstract Integer getReceiver();

    public abstract void setReceiver(Integer integer);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Date getReadTime();

    public abstract void setReadTime(Date date);

    public abstract Integer getRelapseMessageId();

    public abstract void setRelapseMessageId(Integer integer);

    public abstract Integer getSendMode();

    public abstract void setSendMode(Integer integer);

    public abstract Integer getDeleteFlag();

    public abstract void setDeleteFlag(Integer integer);

    public static final String ENTITY_NAME = "RECEIVEMESSAGE";
}
