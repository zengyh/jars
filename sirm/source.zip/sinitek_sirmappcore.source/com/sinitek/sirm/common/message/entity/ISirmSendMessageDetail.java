// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISirmSendMessageDetail.java

package com.sinitek.sirm.common.message.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface ISirmSendMessageDetail
    extends IMetaObjectImpl
{

    public abstract Integer getSendMessageId();

    public abstract void setSendMessageId(Integer integer);

    public abstract String getAddress();

    public abstract void setAddress(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Date getSendTime();

    public abstract void setSendTime(Date date);

    public abstract String getEmpid();

    public abstract void setEmpid(String s);

    public static final String ENTITY_NAME = "SIRMSENDMESSAGEDETAIL";
}
