// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynClientListenThread.java

package com.sinitek.base.metadb.syncenter.client;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.AcceptThread;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import org.apache.commons.collections.Buffer;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.client:
//            SynCenterClientException, SynClientConfig

public class SynClientListenThread
    implements Runnable
{

    public SynClientListenThread(SynClientConfig clientConfig, Buffer dataBuffer)
    {
        this.dataBuffer = dataBuffer;
        if(LOGGER.isInfoEnabled())
            if(clientConfig.getListernPort() != 0)
                LOGGER.info((new StringBuilder()).append("\u76D1\u542C\u7AEF\u53E3\uFF1A[").append(clientConfig.getListernPort()).append("]").toString());
            else
                LOGGER.info("\u76D1\u542C\u7AEF\u53E3\uFF1A[\u81EA\u52A8\u5206\u914D]");
        serverSocket = null;
        try
        {
            serverSocket = new ServerSocket();
            if(clientConfig.getListernPort() != 0)
            {
                serverSocket.bind(new InetSocketAddress(clientConfig.getListernPort()));
            } else
            {
                serverSocket.bind(null);
                clientConfig.setListernPort(serverSocket.getLocalPort());
                if(LOGGER.isInfoEnabled())
                    LOGGER.info((new StringBuilder()).append("\u81EA\u52A8\u5206\u914D\u7684\u76D1\u542C\u7AEF\u53E3\u53F7\uFF1A[").append(clientConfig.getListernPort()).append("]").toString());
            }
        }
        catch(IOException e)
        {
            throw new SynCenterClientException("0014", e);
        }
    }

    public void run()
    {
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

    private static Logger LOGGER;
    private Buffer dataBuffer;
    private ServerSocket serverSocket;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }
}
