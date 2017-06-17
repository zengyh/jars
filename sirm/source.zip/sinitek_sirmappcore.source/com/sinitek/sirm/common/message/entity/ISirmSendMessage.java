// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISirmSendMessage.java

package com.sinitek.sirm.common.message.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface ISirmSendMessage
    extends IMetaObjectImpl
{

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract String getTitle();

    public abstract void setTitle(String s);

    public abstract Integer getType();

    public abstract void setType(Integer integer);

    public abstract Integer getIsTime();

    public abstract void setIsTime(Integer integer);

    public abstract Date getSendTime();

    public abstract void setSendTime(Date date);

    public abstract String getContent();

    public abstract void setContent(String s);

    public abstract String getSenderId();

    public abstract void setSenderId(String s);

    public abstract Integer getExampleownerid();

    public abstract void setExampleownerid(Integer integer);

    public abstract Integer getTemplatetypeid();

    public abstract void setTemplatetypeid(Integer integer);

    public static final String ENTITY_NAME = "SirmSendMessage";
}
