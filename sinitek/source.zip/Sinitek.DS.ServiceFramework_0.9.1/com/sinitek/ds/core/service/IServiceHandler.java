// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IServiceHandler.java

package com.sinitek.ds.core.service;

import com.sinitek.base.common.BaseException;
import java.io.Serializable;
import java.util.Properties;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.ds.core.service:
//            IServiceCoreRequest, IServiceResponse

public interface IServiceHandler
    extends Serializable
{

    public abstract IServiceResponse handle(IServiceCoreRequest iservicecorerequest)
        throws BaseException;

    public abstract void setLogger(Logger logger);

    public abstract void setParams(Properties properties);
}
