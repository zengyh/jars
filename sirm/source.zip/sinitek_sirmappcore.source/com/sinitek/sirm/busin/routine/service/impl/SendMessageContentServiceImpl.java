// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SendMessageContentServiceImpl.java

package com.sinitek.sirm.busin.routine.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.busin.routine.service.ISendMessageContentService;
import java.util.*;

public class SendMessageContentServiceImpl extends AbstractMetaDBContextSupport
    implements ISendMessageContentService
{

    public SendMessageContentServiceImpl()
    {
    }

    public List findSendMessageType(int sendMessageId)
    {
        Map _param = new HashMap();
        StringBuilder _builder = new StringBuilder();
        _builder.append((new StringBuilder()).append("select * from rt_sendmessagecontent  where 1=1 and sendmessageid =").append(sendMessageId).toString());
        IMetaDBQuery _metaDBQuery = getMetaDBContext().createSqlQuery(_builder.toString());
        if(_param != null)
            _metaDBQuery.setParameters(_param);
        return _metaDBQuery.getResult();
    }
}
