// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractDispatchHandler.java

package com.sinitek.base.control;

import com.sinitek.base.common.BaseException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.control:
//            AbstractHandler, HandleCoreException, IHandleResult, IRequest, 
//            HandleException

public abstract class AbstractDispatchHandler extends AbstractHandler
{

    public AbstractDispatchHandler()
    {
    }

    public IHandleResult handle(IRequest request)
        throws HandleException
    {
        String method;
        method = (String)request.getRequestParameter("method");
        if(method == null)
            throw new HandleCoreException("0010");
        return (IHandleResult)MethodUtils.invokeMethod(this, method, request);
        InvocationTargetException ex;
        ex;
        logger.error((new StringBuilder()).append("\u8C03\u7528[").append(method).append("]\u65B9\u6CD5\u5931\u8D25").toString(), ex);
        Exception origEx = (Exception)ex.getCause();
        if(origEx instanceof BaseException)
            throw (BaseException)origEx;
        else
            throw new HandleCoreException("0011", origEx, new Object[] {
                method
            });
        Exception e;
        e;
        logger.error((new StringBuilder()).append("\u8C03\u7528[").append(method).append("]\u65B9\u6CD5\u5931\u8D25").toString(), e);
        throw new HandleCoreException("0011", e, new Object[] {
            method
        });
    }

    protected void initParam(Properties properties)
        throws HandleException
    {
    }
}
