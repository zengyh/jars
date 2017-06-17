// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISendMessage.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface ISendMessage
    extends IMetaObjectImpl
{

    public abstract Integer getEditFlag();

    public abstract void setEditFlag(Integer integer);

    public abstract Integer getTimingFlag();

    public abstract void setTimingFlag(Integer integer);

    public abstract Integer getSender();

    public abstract void setSender(Integer integer);

    public abstract Date getDefineTime();

    public abstract void setDefineTime(Date date);

    public abstract String getTitle();

    public abstract void setTitle(String s);

    public abstract String getContent();

    public abstract void setContent(String s);

    public abstract String getReceiver();

    public abstract void setReceiver(String s);

    public abstract String getSendMode();

    public abstract void setSendMode(String s);

    public abstract Integer getRelapseFlag();

    public abstract void setRelapseFlag(Integer integer);

    public abstract Integer getSendStatus();

    public abstract void setSendStatus(Integer integer);

    public abstract Integer getDeleteFlag();

    public abstract void setDeleteFlag(Integer integer);

    public abstract Date getSendTime();

    public abstract void setSendTime(Date date);

    public abstract Integer getReplyMessageId();

    public abstract void setReplyMessageId(Integer integer);

    public static final String ENTITY_NAME = "SENDMESSAGE";
}
