// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEmailLogonService.java

package com.sinitek.sirm.common.maillogon.service;

import com.sinitek.sirm.common.maillogon.entity.IEmailLogon;

public interface IEmailLogonService
{

    public abstract IEmailLogon getEmailLogon(int i);

    public abstract int saveEmailLogon(IEmailLogon iemaillogon);

    public abstract void deleteEmailLogon(IEmailLogon iemaillogon);

    public abstract IEmailLogon getEmailLogon(String s, String s1);
}
