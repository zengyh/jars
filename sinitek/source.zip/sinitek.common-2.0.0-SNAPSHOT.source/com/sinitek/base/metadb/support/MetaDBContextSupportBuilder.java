// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBContextSupportBuilder.java

package com.sinitek.base.metadb.support;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.sinitek.base.metadb.support:
//            IMetaDBContextSupport, MetaDBContextSupportException, IConfigableSupport, MetaDBContextSuppportInvocationHandler, 
//            IContextSupportConfigInfo

public class MetaDBContextSupportBuilder
{

    public MetaDBContextSupportBuilder()
    {
    }

    public static IMetaDBContextSupport createProxy(IContextSupportConfigInfo configInfo)
    {
        Class mainInterfaceClass = configInfo.getInterface();
        boolean isInterfaceAdd = !com/sinitek/base/metadb/support/IMetaDBContextSupport.isAssignableFrom(mainInterfaceClass);
        Class implClass = configInfo.getImplClass();
        if(!com/sinitek/base/metadb/support/IMetaDBContextSupport.isAssignableFrom(implClass))
            throw new MetaDBContextSupportException("0003", new Object[] {
                implClass.getName()
            });
        IMetaDBContextSupport impl;
        try
        {
            impl = (IMetaDBContextSupport)implClass.newInstance();
        }
        catch(Exception ex)
        {
            throw new MetaDBContextSupportException("0005", ex, new Object[] {
                implClass.getName()
            });
        }
        if(com/sinitek/base/metadb/support/IConfigableSupport.isAssignableFrom(implClass))
        {
            IConfigableSupport config = (IConfigableSupport)impl;
            config.init(configInfo.getInitProperties());
        }
        List interfaceList = new ArrayList();
        if(isInterfaceAdd)
            interfaceList.add(com/sinitek/base/metadb/support/IMetaDBContextSupport);
        interfaceList.add(configInfo.getInterface());
        for(int i = 0; i < configInfo.getInterfaces().length; i++)
            try
            {
                Class _interfaceClz = Class.forName(configInfo.getInterfaces()[i]);
                if(!_interfaceClz.isInterface())
                    throw new MetaDBContextSupportException("0003", new Object[] {
                        configInfo.getInterfaces()[i]
                    });
                interfaceList.add(_interfaceClz);
            }
            catch(ClassNotFoundException e)
            {
                throw new MetaDBContextSupportException("0013", e, new Object[] {
                    configInfo.getInterfaces()[i]
                });
            }

        Class targetInterface[] = (Class[])(Class[])interfaceList.toArray(new Class[interfaceList.size()]);
        MetaDBContextSuppportInvocationHandler handler = new MetaDBContextSuppportInvocationHandler(configInfo.getMethodInvocationInfos(), impl, mainInterfaceClass);
        return (IMetaDBContextSupport)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetInterface, handler);
    }
}
