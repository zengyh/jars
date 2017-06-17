// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ControlServer.java

package com.sinitek.base.control.server;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.control.IHandler;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.*;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

// Referenced classes of package com.sinitek.base.control.server:
//            HandlerConfig, ControlServerException, IServerConfig, HandleThread

public class ControlServer
{

    public ControlServer()
    {
        handlerMap = new HashMap();
        isRun = true;
    }

    public void startServer(IServerConfig config)
    {
        startServer(config, false);
    }

    public void startServer(IServerConfig config, final boolean isDaemon)
    {
        for(Iterator iter = config.getHandlerConfigs().iterator(); iter.hasNext(); createHandler((HandlerConfig)iter.next(), config.getLogPath()));
        try
        {
            serverSocket = new ServerSocket(config.getPort());
            LOGGER.info((new StringBuilder()).append("\u542F\u52A8\u76D1\u542C\u7AEF\u53E3[").append(config.getPort()).append("]").toString());
        }
        catch(IOException e)
        {
            throw new ControlServerException("0009", e, new Object[] {
                new Integer(config.getPort())
            });
        }
        Runnable run = new Runnable() {

            public void run()
            {
                try
                {
                    while(isRun) 
                    {
                        Thread t = new Thread(new HandleThread(serverSocket.accept(), handlerMap));
                        t.setDaemon(isDaemon);
                        t.start();
                    }
                }
                catch(IOException e)
                {
                    ControlServer.LOGGER.error("\u76D1\u542C\u8FC7\u7A0B\u51FA\u73B0\u5F02\u5E38\uFF0C\u670D\u52A1\u5C06\u505C\u6B62", e);
                    stopServer();
                }
            }

            final boolean val$isDaemon;
            final ControlServer this$0;

            
            {
                this$0 = ControlServer.this;
                isDaemon = flag;
                super();
            }
        }
;
        Thread t = new Thread(run);
        t.setDaemon(isDaemon);
        t.start();
        mainThread = t;
        LOGGER.info("\u670D\u52A1\u542F\u52A8");
    }

    public void stopServer()
    {
        LOGGER.info("\u51C6\u5907\u505C\u6B62\u670D\u52A1");
        isRun = false;
        if(serverSocket != null)
            try
            {
                serverSocket.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95ED\u76D1\u542C\u7AEF\u53E3\u5931\u8D25", e);
            }
        mainThread.interrupt();
        LOGGER.info("\u670D\u52A1\u505C\u6B62");
    }

    private void createHandler(HandlerConfig config, String rootLogFile)
    {
        if(handlerMap.containsKey(config.getName().toLowerCase()))
            throw new ControlServerException("0005", new Object[] {
                config.getName()
            });
        ClassLoader loader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
        try
        {
            Class handlerCls = loader.loadClass(config.getHandlerClass());
            if(!com/sinitek/base/control/IHandler.isAssignableFrom(handlerCls))
                throw new ControlServerException("0006", new Object[] {
                    config.getHandlerClass()
                });
            IHandler handler = (IHandler)handlerCls.newInstance();
            handler.init(createLogger(loader, config, rootLogFile), config.getHandlerProperties());
            handlerMap.put(config.getName().toLowerCase(), handler);
        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception ex)
        {
            throw new ControlServerException("0007", ex, new Object[] {
                config.getName()
            });
        }
    }

    private Logger createLogger(ClassLoader loader, HandlerConfig config, String rootLogFile)
    {
        try
        {
            Class propConfigCls = loader.loadClass(org/apache/log4j/PropertyConfigurator.getName());
            Method configMethod = propConfigCls.getMethod("configure", new Class[] {
                java/util/Properties
            });
            Properties logProperties = new Properties();
            logProperties.setProperty("log4j.rootLogger", "off");
            logProperties.setProperty((new StringBuilder()).append("log4j.logger.").append(config.getName()).toString(), (new StringBuilder()).append(config.getLogLevel()).append(",").append(config.getName()).toString());
            logProperties.setProperty((new StringBuilder()).append("log4j.appender.").append(config.getName()).toString(), "org.apache.log4j.DailyRollingFileAppender");
            logProperties.setProperty((new StringBuilder()).append("log4j.appender.").append(config.getName()).append(".DatePattern").toString(), "'.'yyyy-MM-dd");
            logProperties.setProperty((new StringBuilder()).append("log4j.appender.").append(config.getName()).append(".layout").toString(), "org.apache.log4j.PatternLayout");
            logProperties.setProperty((new StringBuilder()).append("log4j.appender.").append(config.getName()).append(".layout.ConversionPattern").toString(), (new StringBuilder()).append("[").append(config.getName()).append("][%p][%d{yyyy-MM-dd HH:mm:ss}] - %m%n").toString());
            File logDir = new File((new StringBuilder()).append(rootLogFile).append(File.separator).append(config.getName()).toString());
            if(!logDir.exists())
                logDir.mkdirs();
            logProperties.setProperty((new StringBuilder()).append("log4j.appender.").append(config.getName()).append(".File").toString(), (new StringBuilder()).append(logDir.getPath()).append(File.separator).append(config.getName()).append(".log").toString());
            configMethod.invoke(null, new Object[] {
                logProperties
            });
            Class loggerCls = loader.loadClass(org/apache/log4j/Logger.getName());
            Method method = loggerCls.getMethod("getLogger", new Class[] {
                java/lang/String
            });
            return (Logger)method.invoke(null, new Object[] {
                config.getName()
            });
        }
        catch(Exception e)
        {
            throw new ControlServerException("0008", e, new Object[] {
                config.getName()
            });
        }
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/control/server/ControlServer);
    private Map handlerMap;
    private ServerSocket serverSocket;
    private boolean isRun;
    private Thread mainThread;





}
