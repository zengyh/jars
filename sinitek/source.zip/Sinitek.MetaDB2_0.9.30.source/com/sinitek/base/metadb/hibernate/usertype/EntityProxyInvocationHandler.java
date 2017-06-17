// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntityProxyInvocationHandler.java

package com.sinitek.base.metadb.hibernate.usertype;

import com.sinitek.base.metadb.*;
import java.lang.reflect.*;

public class EntityProxyInvocationHandler
    implements InvocationHandler
{

    public static Object createEntityProxy(IProperty targetProperty, Object targetValue, Class targetInterface)
    {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {
            targetInterface, com/sinitek/base/metadb/IMetaRelaEntityObject
        }, new EntityProxyInvocationHandler(targetProperty, targetValue));
    }

    public EntityProxyInvocationHandler(IProperty targetProperty, Object targetValue)
    {
        this.targetProperty = targetProperty;
        this.targetValue = targetValue;
    }

    public Object invoke(Object proxy, Method method, Object args[])
        throws Throwable
    {
        if(method.getName().equals("getEntityOrigValue"))
            return targetValue;
        if(method.getName().equals("isValidRela"))
            return Boolean.valueOf(getMo() != null);
        if(method.getName().equals("resetRelaObject"))
        {
            mo = null;
            return null;
        }
        IMetaObject metaObject = getMo();
        if(metaObject != null)
            return method.invoke(metaObject, args != null ? args : new Object[0]);
        else
            return null;
    }

    private synchronized IMetaObject getMo()
    {
        if(mo == null)
            mo = (new MetaDBTemplate()).get(targetProperty.getEntity(), targetProperty.getPropertyName(), targetValue);
        return mo;
    }

    private Object targetValue;
    private IProperty targetProperty;
    private IMetaObject mo;
}
