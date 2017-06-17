// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMessageEngineService.java

package com.sinitek.sirm.busin.routine.service;

import com.sinitek.sirm.busin.routine.utils.MessageContext;

public interface IMessageEngineService
{

    public abstract void sendMessage(MessageContext messagecontext);

    public abstract void asynSendMessage(MessageContext messagecontext);

    public abstract void synSendMessage(MessageContext messagecontext);
}
