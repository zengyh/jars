// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerWorkThread.java

package com.sinitek.base.synserver.server;

import com.sinitek.base.enumsupport.AbstractEnumItem;
import com.sinitek.base.synserver.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.BlockingBuffer;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.synserver.server:
//            ClientBean, ClientGroup

public class ServerWorkThread
{
    private class SendThread extends Thread
    {

        public void run()
        {
            do
            {
                if(!isRun)
                    break;
                IDataPack sendPack = null;
                try
                {
                    sendPack = (IDataPack)sendBuffer.remove();
                }
                catch(Exception e)
                {
                    ServerWorkThread.LOGGER.info((new StringBuilder()).append("\u9488\u5BF9").append(clientBean.getInfo()).append("]\u7684\u53D1\u9001\u961F\u5217\u88AB\u4E2D\u65AD").toString(), e);
                    break;
                }
                if(ServerWorkThread.LOGGER.isTraceEnabled())
                    ServerWorkThread.LOGGER.trace((new StringBuilder()).append("\u51C6\u5907\u5411").append(clientBean.getInfo()).append("\u53D1\u9001").append(sendPack.isSystemPack() ? "\u7CFB\u7EDF" : "\u5E94\u7528").append(sendPack.isRequestPack() ? "\u8BF7\u6C42" : "\u54CD\u5E94").append("\u6570\u636E\u5305").toString());
                else
                if(ServerWorkThread.LOGGER.isDebugEnabled() && !sendPack.isSystemPack())
                    ServerWorkThread.LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u5411").append(clientBean.getInfo()).append("\u53D1\u9001\u5E94\u7528").append(sendPack.isRequestPack() ? "\u8BF7\u6C42" : "\u54CD\u5E94").append("\u6570\u636E\u5305\uFF0C\u7F16\u53F7[").append(sendPack.getPackCode()).append("]").toString());
                if("GOODBYE".equalsIgnoreCase(sendPack.getApplicationCode()))
                {
                    if(ServerWorkThread.LOGGER.isInfoEnabled())
                        ServerWorkThread.LOGGER.info((new StringBuilder()).append("\u51C6\u5907\u5411").append(clientBean.getInfo()).append("\u53D1\u9001Goodbye\u4FE1\u53F7").toString());
                    isRun = false;
                    clientGroup.removeClient(clientBean);
                }
                byte toSend[] = sendPack.getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(toSend.length);
                writeBuffer.put(toSend);
                writeBuffer.flip();
                try
                {
                    channel.write(writeBuffer);
                }
                catch(IOException e)
                {
                    ServerWorkThread.LOGGER.warn((new StringBuilder()).append("\u5411").append(clientBean.getInfo()).append("\u53D1\u9001\u6570\u636E\u5305\u5931\u8D25").toString(), e);
                    isRun = false;
                }
                catch(Exception e)
                {
                    ServerWorkThread.LOGGER.warn((new StringBuilder()).append("\u5411").append(clientBean.getInfo()).append("\u53D1\u9001\u6570\u636E\u5305\u5931\u8D25").toString(), e);
                    isRun = channel.isOpen() && channel.isConnected();
                }
            } while(true);
            if(ServerWorkThread.LOGGER.isInfoEnabled())
            {
                ServerWorkThread.LOGGER.info((new StringBuilder()).append("\u9488\u5BF9").append(clientBean.getInfo()).append("\u7684\u6570\u636E\u53D1\u9001\u7EBF\u7A0B\u9000\u51FA").toString());
                clientGroup.removeClient(clientBean);
            }
            if(channel.isOpen())
                try
                {
                    channel.close();
                }
                catch(IOException e)
                {
                    ServerWorkThread.LOGGER.warn((new StringBuilder()).append("\u5173\u95ED").append(clientBean.getInfo()).append("\u7684\u6570\u636E\u6D41\u5931\u8D25").toString());
                }
        }

        private SocketChannel channel;
        private boolean isRun;
        final ServerWorkThread this$0;

        public SendThread(SocketChannel channel)
        {
            this$0 = ServerWorkThread.this;
            super();
            isRun = true;
            this.channel = channel;
        }
    }

    private class ReadThread extends Thread
    {

        public void run()
        {
            while(isRun) 
                try
                {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ByteBuffer headBuffer = ByteBuffer.allocateDirect(64);
                    if(channel.read(headBuffer) > 0)
                    {
                        headBuffer.rewind();
                        byte head[] = new byte[64];
                        headBuffer.get(head);
                        baos.write(head, 0, 64);
                        int bodyLength = DataPackUtil.getBodyLengthFromHead(head);
                        byte body[] = new byte[bodyLength];
                        if(bodyLength > 0)
                        {
                            ByteBuffer bodyBuffer = ByteBuffer.allocateDirect(bodyLength);
                            channel.read(bodyBuffer);
                            bodyBuffer.rewind();
                            bodyBuffer.get(body);
                        }
                        baos.write(body, 0, bodyLength);
                        BaseDataPack pack = new BaseDataPack(baos.toByteArray());
                        if(ServerWorkThread.LOGGER.isTraceEnabled())
                            ServerWorkThread.LOGGER.trace((new StringBuilder()).append("\u6536\u5230\u6765\u81EA").append(clientBean.getInfo()).append("\u7684\u6570\u636E\u5305\uFF0C\u4E1A\u52A1\u4EE3\u7801[").append(pack.getApplicationCode()).append("]\uFF0C\u7F16\u53F7[").append(pack.getPackCode()).append("]").toString());
                        else
                        if(ServerWorkThread.LOGGER.isDebugEnabled() && !pack.isSystemPack())
                            ServerWorkThread.LOGGER.debug((new StringBuilder()).append("\u6536\u5230\u6765\u81EA").append(clientBean.getInfo()).append("\u7684\u6570\u636E\u5305\uFF0C\u4E1A\u52A1\u4EE3\u7801[").append(pack.getApplicationCode()).append("]\uFF0C\u7F16\u53F7[").append(pack.getPackCode()).append("]").toString());
                        dealPack(pack);
                    } else
                    {
                        if(ServerWorkThread.LOGGER.isInfoEnabled())
                            ServerWorkThread.LOGGER.info((new StringBuilder()).append(clientBean.getInfo()).append("\u4E3B\u52A8\u5173\u95ED\u4E86\u6570\u636E\u8FDE\u63A5").toString());
                        isRun = false;
                    }
                }
                catch(IOException e)
                {
                    ServerWorkThread.LOGGER.warn((new StringBuilder()).append("\u63A5\u6536\u6765\u81EA").append(clientBean.getInfo()).append("\u7684\u6570\u636E\u5305\u7684\u6570\u636E\u5305\u5931\u8D25").toString(), e);
                    isRun = false;
                }
                catch(Exception e)
                {
                    ServerWorkThread.LOGGER.warn((new StringBuilder()).append("\u5904\u7406\u6765\u81EA").append(clientBean.getInfo()).append("\u7684\u6570\u636E\u5305\u7684\u6570\u636E\u5305\u5931\u8D25").toString(), e);
                    isRun = channel.isOpen() && channel.isConnected();
                }
            if(ServerWorkThread.LOGGER.isInfoEnabled())
            {
                ServerWorkThread.LOGGER.info((new StringBuilder()).append("\u9488\u5BF9").append(clientBean.getInfo()).append("\u7684\u6570\u636E\u8BFB\u53D6\u7EBF\u7A0B\u9000\u51FA").toString());
                clientGroup.removeClient(clientBean);
                if(checkThread != null && checkThread.isAlive())
                    checkThread.interrupt();
                if(!status.equals(WorkThreadStatus.GOODBYE) && sendThread != null && sendThread.isAlive())
                    sendThread.interrupt();
            }
        }

        private synchronized void dealPack(BaseDataPack pack)
        {
            if(pack.isSystemPack())
            {
                if("CHECK".equalsIgnoreCase(pack.getApplicationCode()) && pack.isRequestPack())
                {
                    if(pack.getBodySize() > 0)
                    {
                        clientBean.setLastCheckTime(new Date());
                        String szNextCheckStamp = (new String(pack.getBodyPack())).trim();
                        int nextCheckDelay = Integer.parseInt(szNextCheckStamp);
                        clientBean.setNextCheckDelay(nextCheckDelay);
                        if(checkThread == null && nextCheckDelay != 0)
                        {
                            if(ServerWorkThread.LOGGER.isInfoEnabled())
                                ServerWorkThread.LOGGER.info((new StringBuilder()).append("\u6536\u5230\u6765\u81EA").append(clientBean.getInfo()).append("\u7684\u5FC3\u8DF3\u6570\u636E\u5305").toString());
                            checkThread = new CheckStatusThread(clientBean);
                            checkThread.setDaemon(true);
                            checkThread.start();
                        }
                        notifyAll();
                    }
                    sendBuffer.add(DataPackUtil.getResponsePack(pack));
                } else
                if("READY".equalsIgnoreCase(pack.getApplicationCode()))
                {
                    if(pack.isRequestPack())
                    {
                        if(ServerWorkThread.LOGGER.isInfoEnabled())
                            ServerWorkThread.LOGGER.info((new StringBuilder()).append("\u6536\u5230").append(clientBean.getInfo()).append("\u7684\u51C6\u5907\u4FE1\u53F7").toString());
                        changeStatus(WorkThreadStatus.WORK);
                        sendBuffer.add(DataPackUtil.getResponsePack(pack));
                    }
                } else
                if("EXIT".equalsIgnoreCase(pack.getApplicationCode()))
                {
                    if(ServerWorkThread.LOGGER.isInfoEnabled())
                        ServerWorkThread.LOGGER.info((new StringBuilder()).append("\u6536\u5230").append(clientBean.getInfo()).append("\u7684\u9000\u51FA\u8BF7\u6C42").toString());
                    changeStatus(WorkThreadStatus.GOODBYE);
                    sendBuffer.add(DataPackUtil.createGoodbyePack());
                    isRun = false;
                } else
                if("GOODBYE".equalsIgnoreCase(pack.getApplicationCode()))
                {
                    if(ServerWorkThread.LOGGER.isInfoEnabled())
                        ServerWorkThread.LOGGER.info((new StringBuilder()).append("\u6536\u5230").append(clientBean.getInfo()).append("\u7684Goodbye\u4FE1\u53F7").toString());
                    changeStatus(WorkThreadStatus.EXIT);
                    clientGroup.markExit(clientBean);
                    clientGroup.waitAllExit();
                    sendBuffer.add(DataPackUtil.createGoodbyePack());
                    changeStatus(WorkThreadStatus.GOODBYE);
                    isRun = false;
                }
            } else
            if(pack.isRequestPack())
            {
                if(ServerWorkThread.LOGGER.isDebugEnabled())
                    ServerWorkThread.LOGGER.debug((new StringBuilder()).append("\u6536\u5230\u6765\u81EA").append(clientBean.getInfo()).append("\u7684\u4E1A\u52A1\u6570\u636E\u5305\uFF0C\u7F16\u53F7[").append(pack.getPackCode()).append("]\uFF0C\u51C6\u5907\u5411\u5916\u5E7F\u64AD").toString());
                clientGroup.broadcastAppPack(pack, clientBean);
                sendBuffer.add(DataPackUtil.getResponsePack(pack));
            }
        }

        private SocketChannel channel;
        private Thread checkThread;
        private boolean isRun;
        final ServerWorkThread this$0;

        public ReadThread(SocketChannel channel)
        {
            this$0 = ServerWorkThread.this;
            super();
            isRun = true;
            this.channel = channel;
        }
    }

    private class CheckStatusThread extends Thread
    {

        public void run()
        {
            try
            {
                while(System.currentTimeMillis() - clientBean.getLastCheckTime().getTime() < (long)(2 * clientBean.getNextCheckDelay())) 
                {
                    long sleepTime = clientBean.getNextCheckDelay() * 2;
                    synchronized(this)
                    {
                        wait(sleepTime);
                    }
                }
                if(ServerWorkThread.LOGGER.isInfoEnabled())
                    ServerWorkThread.LOGGER.info((new StringBuilder()).append(clientBean.getInfo()).append("\u7684\u5FC3\u8DF3\u4FE1\u53F7\u8D85\u65F6\uFF0C\u670D\u52A1\u5668\u7AEF\u5C06\u4E2D\u65AD\u8FDE\u63A5").toString());
                terminate();
            }
            catch(InterruptedException e)
            {
                ServerWorkThread.LOGGER.info((new StringBuilder()).append(clientBean.getInfo()).append("\u72B6\u6001\u68C0\u67E5\u7EBF\u7A0B\u9000\u51FA").toString());
            }
        }

        private ClientBean clientBean;
        final ServerWorkThread this$0;

        private CheckStatusThread(ClientBean clientBean)
        {
            this$0 = ServerWorkThread.this;
            super();
            this.clientBean = clientBean;
        }

    }

    public static class WorkThreadStatus extends AbstractEnumItem
    {

        public static final WorkThreadStatus INIT = new WorkThreadStatus("init", 0, "\u521D\u59CB\u5316\u72B6\u6001", "");
        public static final WorkThreadStatus READY = new WorkThreadStatus("ready", 1, "\u51C6\u5907\u72B6\u6001", "");
        public static final WorkThreadStatus WORK = new WorkThreadStatus("work", 2, "\u5DE5\u4F5C\u72B6\u6001", "");
        public static final WorkThreadStatus EXIT = new WorkThreadStatus("exit", 3, "\u9000\u51FA\u72B6\u6001", "");
        public static final WorkThreadStatus GOODBYE = new WorkThreadStatus("goodbye", 4, "\u79BB\u7EBF\u72B6\u6001", "");


        protected WorkThreadStatus(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
        {
            super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
        }
    }


    public ServerWorkThread(SocketChannel channel, ClientGroup clientGroup)
    {
        status = WorkThreadStatus.INIT;
        this.channel = channel;
        this.clientGroup = clientGroup;
        clientBean = new ClientBean();
        InetSocketAddress address = (InetSocketAddress)channel.socket().getRemoteSocketAddress();
        byte rawIp[] = address.getAddress().getAddress();
        String ip = (new StringBuilder()).append(String.valueOf(getIpPart(rawIp[0]))).append(".").append(String.valueOf(getIpPart(rawIp[1]))).append(".").append(String.valueOf(getIpPart(rawIp[2]))).append(".").append(String.valueOf(getIpPart(rawIp[3]))).toString();
        clientBean.setClientIp(ip);
        clientBean.setClientPort(address.getPort());
        clientBean.setConnectTime(new Date());
        clientBean.setLastCheckTime(new Date());
        clientBean.setNextCheckDelay(-1);
        clientGroup.registClient(clientBean, this);
        sendBuffer = BlockingBuffer.decorate(new UnboundedFifoBuffer());
        readThread = new ReadThread(channel);
        readThread.start();
        sendThread = new SendThread(channel);
        sendThread.start();
        sendBuffer.add(DataPackUtil.createReadyPack(true));
    }

    private int getIpPart(byte rawIp)
    {
        return rawIp >= 0 ? rawIp : rawIp + 256;
    }

    public void addSendPack(IDataPack pack)
    {
        if(status.equals(WorkThreadStatus.READY) || status.equals(WorkThreadStatus.WORK))
            sendBuffer.add(pack);
    }

    private synchronized void changeStatus(WorkThreadStatus status)
    {
        this.status = status;
    }

    private void terminate()
    {
        if(readThread != null && readThread.isAlive())
            readThread.interrupt();
        if(sendThread != null && sendThread.isAlive())
            sendThread.interrupt();
        if(channel.isOpen())
            try
            {
                channel.close();
            }
            catch(IOException e)
            {
                LOGGER.warn((new StringBuilder()).append("\u5173\u95ED").append(clientBean.getInfo()).append("\u7684\u8FDE\u63A5\u5931\u8D25").toString(), e);
            }
    }

    private ClientBean clientBean;
    private SocketChannel channel;
    private ClientGroup clientGroup;
    private Buffer sendBuffer;
    private WorkThreadStatus status;
    private Thread readThread;
    private Thread sendThread;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/synserver/server/ServerWorkThread);









}
