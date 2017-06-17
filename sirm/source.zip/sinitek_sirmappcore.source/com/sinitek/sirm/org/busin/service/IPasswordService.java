// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IPasswordService.java

package com.sinitek.sirm.org.busin.service;

import com.sinitek.sirm.org.busin.entity.IOrgLogonLog;
import com.sinitek.sirm.org.busin.entity.IOrgPswHis;

public interface IPasswordService
{

    public abstract void savePswHis(IOrgPswHis iorgpswhis);

    public abstract void savePswLog(IOrgLogonLog iorglogonlog);

    public abstract void savePswLog(String s);

    public abstract IOrgLogonLog getLastPswLog(String s);

    public abstract int pswRepeatCheck(String s, String s1);

    public abstract int pswErrorCount(String s);

    public abstract void clearLogonLog(String s);

    public abstract String lockUserCheck(String s);

    public abstract String getUserPsw(String s);

    public abstract int getPasswordPloy();

    public abstract void clearLogonFailedCount(String s);

    public abstract void enforceClearFailedCount(String s);

    public abstract String getDefaultPswDate(String s);
}
