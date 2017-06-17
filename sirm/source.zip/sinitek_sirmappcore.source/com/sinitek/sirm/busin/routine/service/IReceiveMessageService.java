// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IReceiveMessageService.java

package com.sinitek.sirm.busin.routine.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.routine.entity.IReceiveMessage;
import com.sinitek.sirm.busin.routine.entity.ISendMessage;
import java.util.List;

public interface IReceiveMessageService
{

    public abstract IMetaDBQuery listReceiveMessage(String s, String s1, String s2, String s3, String s4);

    public abstract IMetaDBQuery listReceiveMessage(String s, String s1, String s2, String s3, String s4, int i);

    public abstract List findNewReceiveMessage(String s);

    public abstract void saveupdateReceiveMessage(IReceiveMessage ireceivemessage);

    public abstract List findReceiveMessage(String s, String s1);

    public abstract ISendMessage getSendMessageById(int i);

    public abstract IReceiveMessage findReceiveMessageById(int i);

    public abstract void deleteReceive(int i);

    public abstract Integer findReceiveMessageByReceiverId(String s);

    public abstract int removeReceiveMessage(String s);

    public abstract List listReceiveMessage(String s);
}
