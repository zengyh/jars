// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynServer.java

package com.sinitek.base.metadb.syncenter.server;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.IDataPack;
import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.BlockingBuffer;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.server:
//            ServerDealThreadGroup, SynServerListernThread, AppErrorLogCheckThread, SynServerConfig

public class SynServer
{

    public SynServer()
    {
    }

    public static void main(String args[])
    {
        if(args.length != 1)
        {
            showUseage();
        } else
        {
            String startParam = args[0];
            if(!startParam.toLowerCase().startsWith("-configfile"))
            {
                showUseage();
            } else
            {
                String param = startParam.substring(11).trim();
                if(param.startsWith("="))
                {
                    param = param.substring(1).trim();
                    System.out.println((new StringBuilder()).append("\u51C6\u5907\u542F\u52A8\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668\uFF0C\u542F\u52A8\u53C2\u6570\u914D\u7F6E\u6587\u4EF6\uFF1A").append(param).toString());
                    startServer(param);
                    System.out.println("\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668\u5DF2\u542F\u52A8");
                } else
                {
                    showUseage();
                }
            }
        }
    }

    private static void startServer(String configPath)
    {
        SynServerConfig config = SynServerConfig.loadConfig(configPath);
        Buffer dataBuffer = BlockingBuffer.decorate(new UnboundedFifoBuffer());
        LOGGER.debug("\u5F00\u59CB\u521B\u5EFA\u5904\u7406\u7EBF\u7A0B\u7EC4");
        ServerDealThreadGroup threadGroup = new ServerDealThreadGroup(config);
        LOGGER.debug("\u521B\u5EFA\u5904\u7406\u7EBF\u7A0B\u7EC4\u5B8C\u6210");
        Thread dispatchThread = new Thread(new Runnable(dataBuffer, threadGroup) {

            public void run()
            {
                do
                {
                    IDataPack pack = (IDataPack)dataBuffer.remove();
                    threadGroup.addDataPackToDeal(pack);
                } while(true);
            }

            final Buffer val$dataBuffer;
            final ServerDealThreadGroup val$threadGroup;

            
            {
                dataBuffer = buffer;
                threadGroup = serverdealthreadgroup;
                super();
            }
        }
, "\u5206\u53D1\u7EBF\u7A0B");
        dispatchThread.setDaemon(true);
        dispatchThread.start();
        LOGGER.debug("\u5F00\u59CB\u521B\u5EFA\u670D\u52A1\u5668\u76D1\u542C\u7EBF\u7A0B");
        Thread listernThread = new Thread(new SynServerListernThread(dataBuffer, config), "\u540C\u6B65\u4E2D\u5FC3\u7AEF\u53E3\u76D1\u542C\u7EBF\u7A0B");
        listernThread.start();
        LOGGER.debug("\u521B\u5EFA\u670D\u52A1\u5668\u76D1\u542C\u7EBF\u7A0B\u5B8C\u6210");
        Timer checkTimer = new Timer("\u5B9E\u4F8B\u72B6\u6001\u8F6E\u8BE2\u7EBF\u7A0B", true);
        checkTimer.schedule(new TimerTask(threadGroup) {

            public void run()
            {
                try
                {
                    threadGroup.checkDealThreads();
                }
                catch(Exception e)
                {
                    SynServer.LOGGER.error("\u68C0\u67E5\u5B9E\u4F8B\u5728\u7EBF\u72B6\u6001\u51FA\u9519", e);
                }
            }

            final ServerDealThreadGroup val$threadGroup;

            
            {
                threadGroup = serverdealthreadgroup;
                super();
            }
        }
, 30000L, 30000L);
        Timer appErrorChecker = new Timer("\u68C0\u67E5\u5E94\u7528\u672A\u63D0\u4EA4\u5230\u540C\u6B65\u4E2D\u5FC3\u6570\u636E\u7EBF\u7A0B", true);
        appErrorChecker.schedule(new AppErrorLogCheckThread(config, threadGroup), 60000L, 0x1d4c0L);
    }

    private static void showUseage()
    {
        System.out.println("\u4F7F\u7528\u65B9\u6CD5\uFF1A");
        System.out.println("java -cp YOUR_CLASS_PATH com.sinitek.base.metadb.syncenter.server.SynServer -configfile=yoursynserverconfig.properties");
    }

    private static Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }

}
