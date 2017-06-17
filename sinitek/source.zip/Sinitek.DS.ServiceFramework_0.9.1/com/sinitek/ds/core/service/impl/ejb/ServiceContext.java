// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceContext.java

package com.sinitek.ds.core.service.impl.ejb;

import com.sinitek.ds.core.service.IServiceRequest;
import com.sinitek.ds.core.service.IServiceResponse;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ServiceContext
    extends EJBObject
{

    public abstract IServiceResponse handleService(IServiceRequest iservicerequest)
        throws RemoteException;

    public abstract void testCall()
        throws RemoteException;
}
