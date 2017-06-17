// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISendMessageDetailService.java

package com.sinitek.sirm.busin.routine.service;

import java.util.List;

public interface ISendMessageDetailService
{

    public abstract List findReceivePerson(int i);

    public abstract List findOnlyCopyPerson(int i);

    public abstract List findOnlyReceiverPerson(int i);
}
