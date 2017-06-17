// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractHandler.java

package com.sinitek.base.control;

import java.util.Properties;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.control:
//            IHandler, HandleException

public abstract class AbstractHandler
    implements IHandler
{

    public AbstractHandler()
    {
    }

    public final void init(Logger logger, Properties initProp)
        throws HandleException
    {
        this.logger = logger;
        initParam(initProp);
    }

    protected abstract void initParam(Properties properties)
        throws HandleException;

    protected Logger logger;
}
