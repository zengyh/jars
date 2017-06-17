// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynServerListernThread.java

package com.sinitek.base.metadb.syncenter.server;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.AcceptThread;
import java.io.IOException;
import java.net.*;
import org.apache.commons.collections.Buffer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.server:
//            SynCenterServerException, SynServerConfig

public class SynServerListernThread
    implements Runnable
{

    public SynServerListernThread(Buffer dataBuffer, SynServerConfig config)
    {
        this.dataBuffer = dataBuffer;
        this.config = config;
    }

    public void run()
    {
        if(LOGGER.isInfoEnabled())
        {
            if(StringUtils.isNotBlank(config.getListenIpAddress()))
                LOGGER.info((new StringBuilder()).append("\u76D1\u542C\u5730\u5740\uFF1A[").append(config.getListenIpAddress()).append("]").toString());
            else
                LOGGER.info("\u76D1\u542C\u5730\u5740\uFF1A[\u672C\u673A\u6240\u6709IP\u5730\u5740]");
            LOGGER.info((new StringBuilder()).append("\u76D1\u542C\u7AEF\u53E3\uFF1A[").append(config.getListenPort()).append("]").toString());
        }
        ServerSocket serverSocket = null;
        try
        {
            serverSocket = new ServerSocket();
            if(StringUtils.isNotBlank(config.getListenIpAddress()))
                serverSocket.bind(new InetSocketAddress(InetAddress.getByName(config.getListenIpAddress()), config.getListenPort()));
            else
                serverSocket.bind(new InetSocketAddress(config.getListenPort()));
        }
        catch(IOException e)
        {
            throw new SynCenterServerException("0003", e, new Object[] {
                config.getListenIpAddress(), String.valueOf(config.getListenPort())
            });
        }
        do
            try
            {
                java.net.Socket socket = serverSocket.accept();
                AcceptThread acceptThread = new AcceptThread(socket, dataBuffer);
                Thread _thread = new Thread(acceptThread, (new StringBuilder()).append("Socket\u63A5\u6536\u7EBF\u7A0B_").append(System.currentTimeMillis()).toString());
                _thread.start();
            }
            catch(Exception e)
            {
                LOGGER.warn("\u63A5\u53D7Socket\u6570\u636E\u65F6\u53D1\u751F\u5F02\u5E38", e);
            }
        while(true);
    }

    private final Buffer dataBuffer;
    private final SynServerConfig config;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }
}
