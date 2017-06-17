// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISirmMessageService.java

package com.sinitek.sirm.common.message.service;

import com.sinitek.sirm.common.message.entity.ISirmSendMessage;
import com.sinitek.sirm.common.message.entity.ISirmSendMessageDetail;
import com.sinitek.sirm.common.message.enumerate.SirmMessageDetailStatus;
import com.sinitek.sirm.common.message.enumerate.SirmMessageStatus;
import java.util.List;

public interface ISirmMessageService
{

    public abstract void saveSirmSendMessage(ISirmSendMessage isirmsendmessage);

    public abstract List findSirmSendMessage(SirmMessageStatus sirmmessagestatus);

    public abstract List findMessageDetailBySendMessageId(int i, SirmMessageDetailStatus sirmmessagedetailstatus);

    public abstract void saveSirmSendMessageDetail(ISirmSendMessageDetail isirmsendmessagedetail);
}
