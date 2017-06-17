// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmMessageServiceImpl.java

package com.sinitek.sirm.common.message.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.message.entity.ISirmSendMessage;
import com.sinitek.sirm.common.message.entity.ISirmSendMessageDetail;
import com.sinitek.sirm.common.message.enumerate.SirmMessageDetailStatus;
import com.sinitek.sirm.common.message.enumerate.SirmMessageStatus;
import com.sinitek.sirm.common.message.service.ISirmMessageService;
import java.util.*;

public class SirmMessageServiceImpl extends MetaDBContextSupport
    implements ISirmMessageService
{

    public SirmMessageServiceImpl()
    {
    }

    public void saveSirmSendMessage(ISirmSendMessage sirmSendMessage)
    {
        sirmSendMessage.save();
    }

    public List findSirmSendMessage(SirmMessageStatus status)
    {
        StringBuilder sql = new StringBuilder();
        Map map = new HashMap();
        if(status != null)
        {
            sql.append(" status=:status");
            map.put("status", Integer.valueOf(status.getEnumItemValue()));
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("SirmSendMessage", sql.toString());
        iMetaDBQuery.setParameters(map);
        return iMetaDBQuery.getResult();
    }

    public List findMessageDetailBySendMessageId(int sendMessageId, SirmMessageDetailStatus status)
    {
        StringBuilder sql = new StringBuilder("1=1");
        Map map = new HashMap();
        if(sendMessageId != 0)
        {
            sql.append("and sendMessageId=:sendMessageId");
            map.put("sendMessageId", Integer.valueOf(sendMessageId));
        }
        if(status != null)
        {
            sql.append(" and status=:status");
            map.put("status", Integer.valueOf(status.getEnumItemValue()));
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("SIRMSENDMESSAGEDETAIL", sql.toString());
        iMetaDBQuery.setParameters(map);
        return iMetaDBQuery.getResult();
    }

    public void saveSirmSendMessageDetail(ISirmSendMessageDetail sirmSendMessageDetail)
    {
        sirmSendMessageDetail.save();
    }
}
