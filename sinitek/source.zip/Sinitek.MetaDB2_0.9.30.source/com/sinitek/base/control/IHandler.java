// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IHandler.java

package com.sinitek.base.control;

import java.util.Properties;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.control:
//            HandleException, IRequest, IHandleResult

public interface IHandler
{

    public abstract void init(Logger logger, Properties properties)
        throws HandleException;

    public abstract IHandleResult handle(IRequest irequest)
        throws HandleException;
}
