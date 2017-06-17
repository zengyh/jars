// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceContextBean.java

package com.sinitek.ds.core.service.impl.ejb;

import com.sinitek.ds.core.service.*;
import com.sinitek.ds.core.service.impl.local.ServiceContextFactory;
import javax.ejb.*;

public class ServiceContextBean
    implements SessionBean
{

    public ServiceContextBean()
    {
    }

    public void ejbCreate()
        throws CreateException
    {
    }

    public void setSessionContext(SessionContext sessioncontext)
        throws EJBException
    {
    }

    public void ejbRemove()
        throws EJBException
    {
    }

    public void ejbActivate()
        throws EJBException
    {
    }

    public void ejbPassivate()
        throws EJBException
    {
    }

    public IServiceResponse handleService(IServiceRequest request)
        throws ServiceException
    {
        return ServiceContextFactory.getInstance().getContext().handleService(request);
    }

    public void testCall()
    {
    }
}
