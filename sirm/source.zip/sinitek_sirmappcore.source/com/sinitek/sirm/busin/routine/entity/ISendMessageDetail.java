// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISendMessageDetail.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface ISendMessageDetail
    extends IMetaObjectImpl
{

    public abstract Integer getSendMessageId();

    public abstract void setSendMessageId(Integer integer);

    public abstract Integer getReceiver();

    public abstract void setReceiver(Integer integer);

    public abstract Date getSendTime();

    public abstract void setSendTime(Date date);

    public abstract Integer getSendStatus();

    public abstract void setSendStatus(Integer integer);

    public abstract String getReason();

    public abstract void setReason(String s);

    public abstract String getReceiverid();

    public abstract void setReceiverid(String s);

    public abstract Integer getReceiverType();

    public abstract void setReceiverType(Integer integer);

    public static final String ENTITY_NAME = "SENDMESSAGEDETAIL";
}
