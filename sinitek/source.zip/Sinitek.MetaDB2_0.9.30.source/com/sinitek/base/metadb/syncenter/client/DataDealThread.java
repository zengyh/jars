// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataDealThread.java

package com.sinitek.base.metadb.syncenter.client;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.IDataPack;
import org.apache.commons.collections.Buffer;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.client:
//            ISynClientListener

public class DataDealThread
    implements Runnable
{

    public DataDealThread(Buffer dataBuffer, ISynClientListener listener)
    {
        this.dataBuffer = dataBuffer;
        this.listener = listener;
    }

    public void run()
    {
        LOGGER.debug("\u62A5\u6587\u6570\u636E\u5904\u7406\u7EBF\u7A0B\u5F00\u59CB\u5FAA\u73AF\u5904\u7406\u62A5\u6587\u961F\u5217");
        do
        {
            IDataPack dataPack = (IDataPack)dataBuffer.remove();
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u5904\u7406\u6570\u636E\u5305\uFF1A[").append(dataPack.getPackCode()).append("],APPCODE=[").append(dataPack.getApplicationCode()).append("]").toString());
            try
            {
                listener.onRecieveData(dataPack);
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u5904\u7406\u6570\u636E\u5305\uFF1A[").append(dataPack.getPackCode()).append("],APPCODE=[").append(dataPack.getApplicationCode()).append("]\u6210\u529F").toString());
            }
            catch(Exception e)
            {
                LOGGER.error((new StringBuilder()).append("\u5904\u7406\u6570\u636E\u5305\uFF1A[").append(dataPack.getPackCode()).append("],APPCODE=[").append(dataPack.getApplicationCode()).append("]\u5931\u8D25").toString(), e);
            }
        } while(true);
    }

    private Buffer dataBuffer;
    private ISynClientListener listener;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }
}
