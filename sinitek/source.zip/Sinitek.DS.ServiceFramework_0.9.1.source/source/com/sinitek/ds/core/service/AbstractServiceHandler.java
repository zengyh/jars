// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractServiceHandler.java

package com.sinitek.ds.core.service;

import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import java.util.Properties;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.ds.core.service:
//            IServiceHandler

public abstract class AbstractServiceHandler extends AbstractMetaDBContextSupport
    implements IServiceHandler
{

    public AbstractServiceHandler()
    {
    }

    public final void setLogger(Logger logger)
    {
        this.logger = logger;
    }

    public void setParams(Properties params)
    {
        this.params = params;
    }

    protected Logger logger;
    protected Properties params;
}
