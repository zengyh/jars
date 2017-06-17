// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceContextHome.java

package com.sinitek.ds.core.service.impl.ejb;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

// Referenced classes of package com.sinitek.ds.core.service.impl.ejb:
//            ServiceContext

public interface ServiceContextHome
    extends EJBHome
{

    public abstract ServiceContext create()
        throws RemoteException, CreateException;
}
