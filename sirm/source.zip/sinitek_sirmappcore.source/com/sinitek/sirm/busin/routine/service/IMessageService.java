// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMessageService.java

package com.sinitek.sirm.busin.routine.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.routine.entity.IMessageTemplate;
import com.sinitek.sirm.busin.routine.entity.IMessageTemplateRela;
import java.util.List;
import java.util.Map;

public interface IMessageService
{

    public abstract IMessageTemplate getMessageTemplateByCode(String s);

    public abstract IMessageTemplate getMessageTemplateById(Integer integer);

    public abstract List findMessageReceiversByTemplateId(int i);

    public abstract List findMessageReceiversByFlag(String s);

    public abstract List findCancelMessageReceiversByOrgid(String s);

    public abstract List findMessageReceiversBytemplateid(String s);

    public abstract List findCancelMessageReceiversBytemplateid(String s);

    public abstract IMetaDBQuery findMessageReceiversBy(String s, String s1);

    public abstract IMetaDBQuery findMessageReceiversBy(String s, String s1, Integer integer);

    public abstract Integer saveMessageTemplate(IMessageTemplate imessagetemplate);

    public abstract List findTemplateById(String s);

    public abstract void removeTemplateById(int i);

    public abstract void removeMesssageReceiverByTemplateId(String s);

    public abstract void removeMesssageReceiverByUserid(String s);

    public abstract void removeCancelMesssageReceiverByOrgid(String s);

    public abstract void saveMesssageReceiver(List list);

    public abstract List findMesssageReceiverByTemplateId(String s);

    public abstract Map findAlltemp();

    public abstract Map findTemplateByProcessType(int i);

    public abstract void saveMessageTemplateReal(IMessageTemplateRela imessagetemplaterela);

    public abstract List findMessageTemplateRelasBySource(Integer integer, String s, Integer integer1);

    public abstract void delMessageTemplateRealBySource(String s, String s1);

    public abstract String getContent(String s, Map map);

    public abstract Map findTemplateByFlag();

    public abstract List findMessageReceiverByTemplateCode(String s);
}
