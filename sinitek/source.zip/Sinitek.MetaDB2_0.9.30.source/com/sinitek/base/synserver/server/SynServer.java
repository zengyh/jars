// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynServer.java

package com.sinitek.base.synserver.server;

import com.sinitek.base.synserver.SynException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.synserver.server:
//            SynServerException, ClientGroup, ServerListenThread, ServerStatusThread

public class SynServer
{

    private SynServer()
    {
        initFlag = false;
        stoped = true;
    }

    public static SynServer createServer()
        throws SynException
    {
        Properties prop;
        InputStream is;
        Exception exception;
        prop = new Properties();
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream("synserverconfig.properties");
        if(is == null)
            throw new SynServerException("0007", new Object[] {
                "synserverconfig.properties"
            });
        try
        {
            prop.load(is);
        }
        catch(IOException ioe)
        {
            throw new SynServerException("0007", ioe, new Object[] {
                "synserverconfig.properties"
            });
        }
        finally { }
        try
        {
            is.close();
        }
        catch(IOException e)
        {
            LOGGER.warn("\u5173\u95ED\u914D\u7F6E\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
        }
        break MISSING_BLOCK_LABEL_111;
        try
        {
            is.close();
        }
        catch(IOException e)
        {
            LOGGER.warn("\u5173\u95ED\u914D\u7F6E\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
        }
        throw exception;
        SynServer server = new SynServer();
        server.init(prop);
        return server;
    }

    public static SynServer createServer(Properties configProps)
        throws SynException
    {
        SynServer ret = new SynServer();
        ret.init(configProps);
        return ret;
    }

    public void startServer()
        throws SynException
    {
        LOGGER.debug("\u51C6\u5907\u5EFA\u7ACB\u670D\u52A1\u5668\u7AEFSocket\u8FDE\u63A5");
        try
        {
            serverChannel = ServerSocketChannel.open();
            serverChannel.socket().setReuseAddress(true);
            serverChannel.socket().bind(new InetSocketAddress(serverPort));
            LOGGER.info((new StringBuilder()).append("\u6210\u529F\u6253\u5F00[").append(serverPort).append("]\u76D1\u542C\u7AEF\u53E3").toString());
        }
        catch(IOException e)
        {
            throw new SynServerException("0008", e, new Object[] {
                new Integer(serverPort)
            });
        }
        clientGroup = new ClientGroup();
        listenThread = new ServerListenThread(serverChannel, clientGroup);
        listenThread.start();
        if(statusPort > 0)
            try
            {
                ServerStatusThread _statusThread = new ServerStatusThread(clientGroup, statusPort);
                _statusThread.start();
                statusThread = _statusThread;
            }
            catch(Exception e)
            {
                LOGGER.error("\u542F\u52A8\u72B6\u6001\u76D1\u63A7\u7AEF\u53E3\u5931\u8D25", e);
            }
        stoped = false;
    }

    public synchronized void stopServer()
        throws SynException
    {
        if(!stoped)
        {
            listenThread.exit();
            try
            {
                serverChannel.close();
            }
            catch(IOException e)
            {
                LOGGER.warn("\u5173\u95ED\u670D\u52A1\u5668\u76D1\u542C\u7AEF\u53E3\u5931\u8D25", e);
            }
            clientGroup.sendExit();
            if(statusThread != null)
                statusThread.exit();
            stoped = true;
        } else
        {
            LOGGER.info("\u670D\u52A1\u5668\u5DF2\u7ECF\u505C\u6B62");
        }
    }

    private void init(Properties initProp)
        throws SynException
    {
        if(initFlag)
            throw new SynServerException("0001");
        String szServerPort = initProp.getProperty("synserver.serverport");
        if(StringUtils.isNotBlank(szServerPort))
        {
            int _serverPort;
            try
            {
                _serverPort = Integer.parseInt(szServerPort);
            }
            catch(NumberFormatException e)
            {
                throw new SynServerException("0003", e, new Object[] {
                    szServerPort
                });
            }
            if(_serverPort <= 0 || _serverPort > 65535)
                throw new SynServerException("0003", new Object[] {
                    szServerPort
                });
            serverPort = _serverPort;
            LOGGER.info((new StringBuilder()).append("\u670D\u52A1\u7AEF\u53E3\u53F7[").append(serverPort).append("]").toString());
        } else
        {
            throw new SynServerException("0002");
        }
        if("true".equalsIgnoreCase(initProp.getProperty("synserver.status.enable", "false").trim()))
        {
            LOGGER.info("\u5F00\u542F\u72B6\u6001\u7AEF\u53E3");
            String szStatusPort = initProp.getProperty("sysserver.status.port");
            if(StringUtils.isNotBlank(szStatusPort))
            {
                int _statusPort;
                try
                {
                    _statusPort = Integer.parseInt(szStatusPort);
                }
                catch(NumberFormatException e)
                {
                    throw new SynServerException("0005", e, new Object[] {
                        szServerPort
                    });
                }
                if(_statusPort <= 0 || _statusPort > 65535)
                    throw new SynServerException("0005", new Object[] {
                        szServerPort
                    });
                if(_statusPort == serverPort)
                    throw new SynServerException("0006");
                statusPort = _statusPort;
                LOGGER.info((new StringBuilder()).append("\u72B6\u6001\u7AEF\u53E3\u53F7[").append(statusPort).append("]").toString());
            } else
            {
                throw new SynServerException("0004");
            }
        } else
        {
            LOGGER.info("\u672A\u5F00\u542F\u72B6\u6001\u7AEF\u53E3");
            statusPort = -1;
        }
        initFlag = true;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/synserver/server/SynServer);
    private boolean initFlag;
    private int serverPort;
    private int statusPort;
    private boolean stoped;
    private ClientGroup clientGroup;
    private ServerSocketChannel serverChannel;
    private ServerListenThread listenThread;
    private ServerStatusThread statusThread;
    public static final String DEFAULT_CONFIGFILE_NAME = "synserverconfig.properties";

}
