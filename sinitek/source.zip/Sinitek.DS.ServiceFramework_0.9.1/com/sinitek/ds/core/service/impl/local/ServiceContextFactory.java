// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceContextFactory.java

package com.sinitek.ds.core.service.impl.local;

import com.sinitek.ds.core.service.IServiceContext;

// Referenced classes of package com.sinitek.ds.core.service.impl.local:
//            ServiceContextLocalImpl

public class ServiceContextFactory
{

    private ServiceContextFactory()
    {
        context = new ServiceContextLocalImpl();
    }

    public static ServiceContextFactory getInstance()
    {
        if(instance == null)
            instance = new ServiceContextFactory();
        return instance;
    }

    public IServiceContext getContext()
    {
        return context;
    }

    private static ServiceContextFactory instance;
    private IServiceContext context;
}
