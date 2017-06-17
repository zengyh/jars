// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IServiceContext.java

package com.sinitek.ds.core.service;

import java.io.Serializable;

// Referenced classes of package com.sinitek.ds.core.service:
//            ServiceException, IServiceRequest, IServiceResponse

public interface IServiceContext
    extends Serializable
{

    public abstract IServiceResponse handleService(IServiceRequest iservicerequest)
        throws ServiceException;
}
