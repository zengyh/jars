// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerStatusThread.java

package com.sinitek.base.synserver.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.synserver.server:
//            SynServerException, ClientBean, ClientGroup

public class ServerStatusThread
{
    private class DealThread extends Thread
    {

        public void run()
        {
_L2:
            Socket socket;
            OutputStream os;
            socket = null;
            os = null;
            socket = serverSocket.accept();
            os = socket.getOutputStream();
            os.write(createStatusData());
            IOException e;
            if(os != null)
                try
                {
                    os.close();
                }
                // Misplaced declaration of an exception variable
                catch(IOException e)
                {
                    ServerStatusThread.LOGGER.warn("\u5173\u95ED\u8F93\u51FA\u6D41\u5931\u8D25", e);
                }
            if(socket != null)
                try
                {
                    socket.close();
                }
                // Misplaced declaration of an exception variable
                catch(IOException e)
                {
                    ServerStatusThread.LOGGER.warn("\u5173\u95ED\u76D1\u63A7\u8FDE\u63A5\u5931\u8D25", e);
                }
            continue; /* Loop/switch isn't completed */
            e;
            ServerStatusThread.LOGGER.error("\u53D1\u9001\u72B6\u6001\u4FE1\u606F\u5931\u8D25", e);
            if(os != null)
                try
                {
                    os.close();
                }
                // Misplaced declaration of an exception variable
                catch(IOException e)
                {
                    ServerStatusThread.LOGGER.warn("\u5173\u95ED\u8F93\u51FA\u6D41\u5931\u8D25", e);
                }
            if(socket != null)
                try
                {
                    socket.close();
                }
                // Misplaced declaration of an exception variable
                catch(IOException e)
                {
                    ServerStatusThread.LOGGER.warn("\u5173\u95ED\u76D1\u63A7\u8FDE\u63A5\u5931\u8D25", e);
                }
            if(true) goto _L2; else goto _L1
_L1:
            Exception exception;
            exception;
            if(os != null)
                try
                {
                    os.close();
                }
                catch(IOException e)
                {
                    ServerStatusThread.LOGGER.warn("\u5173\u95ED\u8F93\u51FA\u6D41\u5931\u8D25", e);
                }
            if(socket != null)
                try
                {
                    socket.close();
                }
                catch(IOException e)
                {
                    ServerStatusThread.LOGGER.warn("\u5173\u95ED\u76D1\u63A7\u8FDE\u63A5\u5931\u8D25", e);
                }
            throw exception;
        }

        final ServerStatusThread this$0;

        private DealThread()
        {
            this$0 = ServerStatusThread.this;
            super();
        }

    }


    public ServerStatusThread(ClientGroup clientGroup, int port)
    {
        this.clientGroup = clientGroup;
        this.port = port;
    }

    public void start()
    {
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u6253\u5F00[").append(port).append("]\u7AEF\u53E3\u4F5C\u4E3A\u76D1\u63A7\u7AEF\u53E3").toString());
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch(IOException e)
        {
            throw new SynServerException("0009", e);
        }
        dealThread = new DealThread();
        dealThread.start();
    }

    public void exit()
    {
        if(dealThread != null && dealThread.isAlive())
            dealThread.interrupt();
        if(serverSocket != null && !serverSocket.isClosed())
            try
            {
                serverSocket.close();
            }
            catch(IOException e)
            {
                LOGGER.warn("\u5173\u95ED\u76D1\u63A7\u7AEF\u53E3\u5931\u8D25", e);
            }
    }

    private byte[] createStatusData()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        DecimalFormat df = new DecimalFormat("000000");
        List clientBeans = clientGroup.getAllClient();
        baos.write(sdf.format(new Date()).getBytes(), 0, 14);
        baos.write(df.format(clientBeans.size() * 37).getBytes(), 0, 6);
        for(Iterator iter = clientBeans.iterator(); iter.hasNext(); baos.write(toBytes((ClientBean)iter.next()), 0, 37));
        return baos.toByteArray();
    }

    private byte[] toBytes(ClientBean cb)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte ip[] = cb.getClientIp().getBytes();
        baos.write(ip, 0, ip.length);
        for(int i = ip.length; i < 15; i++)
            baos.write(32);

        baos.write(0xff & cb.getClientPort() >>> 8);
        baos.write(0xff & cb.getClientPort());
        for(int i = 7; i >= 0; i--)
        {
            long time = cb.getConnectTime().getTime();
            int _t = (int)(255L & time >>> 8 * i);
            baos.write(_t);
        }

        for(int i = 7; i >= 0; i--)
        {
            long time = cb.getLastCheckTime().getTime();
            int _t = (int)(255L & time >>> 8 * i);
            baos.write(_t);
        }

        for(int i = 3; i >= 0; i--)
        {
            int time = cb.getNextCheckDelay();
            int _t = 0xff & time >>> 8 * i;
            baos.write(_t);
        }

        return baos.toByteArray();
    }

    private ClientGroup clientGroup;
    private int port;
    private ServerSocket serverSocket;
    private DealThread dealThread;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/synserver/server/ServerStatusThread);




}
