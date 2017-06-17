// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectProxyFactory.java

package com.sinitek.base.metadb.cache.impl;

import com.sinitek.base.metadb.IEntity;
import com.sinitek.base.metadb.IMetaObjectImpl;
import java.lang.reflect.Proxy;

// Referenced classes of package com.sinitek.base.metadb.cache.impl:
//            MetaObjectInvocationHandler, MetaObjectWrapperImpl

public class MetaObjectProxyFactory
{

    public MetaObjectProxyFactory()
    {
    }

    public static IMetaObjectImpl createProxy(IMetaObjectImpl impl)
    {
        IEntity entity = impl.getEntity();
        if(entity.getInterface() != null)
            return (IMetaObjectImpl)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {
                entity.getInterface()
            }, new MetaObjectInvocationHandler(impl));
        else
            return new MetaObjectWrapperImpl(impl);
    }
}
