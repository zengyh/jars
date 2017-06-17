// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IUserSynchronousService.java

package com.sinitek.sirm.org.busin.service;

import com.sinitek.sirm.org.busin.entity.IUserSynchronous;
import java.util.List;

public interface IUserSynchronousService
{

    public abstract void saveUserSynchronous(IUserSynchronous iusersynchronous);

    public abstract void delUserSynchronous(int i);

    public abstract IUserSynchronous getUserSynchronous(int i);

    public abstract IUserSynchronous getUserSynchronousByUserName(String s);

    public abstract List findAllIUserSynchronous();

    public abstract List findFailureAllIUserSynchronous();
}
