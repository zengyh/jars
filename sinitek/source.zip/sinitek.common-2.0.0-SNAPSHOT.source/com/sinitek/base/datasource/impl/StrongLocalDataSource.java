// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrongLocalDataSource.java

package com.sinitek.base.datasource.impl;

import com.sinitek.base.datasource.ILocalStrongDataSource;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.datasource.impl:
//            StrongDataSource, LocalDataSourceReconnectThread

public abstract class StrongLocalDataSource extends StrongDataSource
    implements ILocalStrongDataSource
{

    public StrongLocalDataSource()
    {
    }

    protected void init(boolean isBind)
        throws NamingException
    {
        createOriDataSource();
        if(isBind)
            bindDataSource(getDataSourceName(), getOriDataSource(), true);
        startCheckoutConnection();
    }

    public synchronized void startReconnect()
    {
        if(reconnectionStarted)
        {
            LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(getDataSourceName()).append("]\u91CD\u8FDE\u673A\u5236\u5DF2\u542F\u52A8\uFF0C\u5F53\u524D\u7B56\u7565\u4E0D\u4F1A\u542F\u52A8\u591A\u4E2A\uFF01").toString());
        } else
        {
            new LocalDataSourceReconnectThread(getOriDataSource(), this);
            reconnectionStarted = true;
            checkoutConnectionStarted = false;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/datasource/impl/StrongLocalDataSource);

}
