// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerListenThread.java

package com.sinitek.base.synserver.server;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.synserver.server:
//            ServerWorkThread, ClientGroup

public class ServerListenThread extends Thread
{

    public ServerListenThread(ServerSocketChannel serverChannel, ClientGroup clientGroup)
    {
        isRun = true;
        this.serverChannel = serverChannel;
        this.clientGroup = clientGroup;
    }

    public void exit()
    {
        isRun = false;
    }

    public void run()
    {
        while(serverChannel.isOpen() && isRun) 
            try
            {
                java.nio.channels.SocketChannel sc = serverChannel.accept();
                new ServerWorkThread(sc, clientGroup);
            }
            catch(Exception e)
            {
                LOGGER.info("\u670D\u52A1\u5668\u76D1\u542C\u7EBF\u7A0B\u9000\u51FA", e);
            }
        if(serverChannel.isOpen())
            try
            {
                serverChannel.close();
            }
            catch(IOException e)
            {
                LOGGER.info("\u5173\u95ED\u670D\u52A1\u5668\u76D1\u542C\u7AEF\u53E3\u5931\u8D25", e);
            }
    }

    private ServerSocketChannel serverChannel;
    private ClientGroup clientGroup;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/synserver/server/ServerListenThread);
    private boolean isRun;

}
