// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectInvocationHandler.java

package com.sinitek.base.metadb.cache.impl;

import com.sinitek.base.metadb.IEntity;
import com.sinitek.base.metadb.IMetaObjectImpl;
import com.sinitek.base.metadb.hibernate.proxy.LastIdProxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.apache.commons.beanutils.MethodUtils;

public class MetaObjectInvocationHandler
    implements InvocationHandler
{

    public MetaObjectInvocationHandler(IMetaObjectImpl impl)
    {
        this.impl = impl;
    }

    public Object invoke(Object proxy, Method method, Object args[])
        throws Throwable
    {
        if(method.getName().equalsIgnoreCase("put") && args.length == 2)
            ensureBackup();
        if(args != null && args.length == 1 && method.getName().startsWith("set"))
        {
            ensureBackup();
            return method.invoke(impl, args);
        } else
        {
            return MethodUtils.invokeMethod(impl, method.getName(), args);
        }
    }

    private synchronized void ensureBackup()
    {
        if(!isBacked)
        {
            IMetaObjectImpl _clone = (IMetaObjectImpl)impl.clone();
            _clone.setId(impl.getId());
            _clone.setVersion(impl.getVersion());
            _clone.setCreateTimeStamp(impl.getCreateTimestamp());
            _clone.setUpdateTimeStamp(impl.getUpdateTimestamp());
            if(impl.getEntity().isHistorySupport())
            {
                LastIdProxy proxy = new LastIdProxy(impl.getEntity());
                proxy.setDBValue(new Integer(impl.getLastId()));
                _clone.setLastProxy(proxy);
            }
            _clone.setPersistStatus(true);
            impl = _clone;
            isBacked = true;
        }
    }

    private IMetaObjectImpl impl;
    private boolean isBacked;
}
