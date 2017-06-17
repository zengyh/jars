// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISendMessageService.java

package com.sinitek.sirm.busin.routine.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.routine.entity.ISendMessage;
import com.sinitek.sirm.common.message.entity.ISirmSendMessage;
import java.util.List;
import java.util.Map;

public interface ISendMessageService
{

    public abstract IMetaDBQuery listSendMessage(String s, String s1, String s2, String s3, String s4);

    public abstract void saveSendMessage(ISendMessage isendmessage, List list, List list1, List list2);

    public abstract ISendMessage findSendMessageById(Integer integer);

    public abstract void delSendMessageById(String s);

    public abstract List findReceiversByOrgId(String s);

    public abstract int removeSendMessage(String s);

    public abstract IMetaDBQuery findSendMessage(Map map);

    public abstract List findSender();

    public abstract List findReceiver();

    public abstract IMetaDBQuery findQuartzMessageList(Map map);

    public abstract ISirmSendMessage getSirmSendMessageById(Integer integer);
}
