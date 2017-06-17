// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynClient.java

package com.sinitek.base.synserver.client;

import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.base.synserver.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;
import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.BlockingBuffer;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.synserver.client:
//            SynClientException, ISynEventListener

public class SynClient
{
    protected class DealThread extends Thread
    {

        public void run()
        {
            do
            {
                IDataPack pack = (IDataPack)todoBuffer.remove();
                try
                {
                    listener.onRecieveData(pack);
                }
                catch(Exception ex)
                {
                    SynClient.LOGGER.error("\u76D1\u542C\u5668\u5904\u7406\u6570\u636E\u5305\u5931\u8D25", ex);
                }
            } while(true);
        }

        final SynClient this$0;

        protected DealThread()
        {
            this$0 = SynClient.this;
            super();
        }
    }

    protected class CheckThread extends Thread
    {

        public void run()
        {
            try
            {
                while(isRun) 
                {
                    int time = checkTime * 1000;
                    sendBuffer.add(DataPackUtil.createCheckPack(time));
                    Thread.sleep(time);
                }
            }
            catch(InterruptedException e)
            {
                SynClient.LOGGER.info("\u68C0\u67E5\u7EBF\u7A0B\u9000\u51FA");
            }
        }

        private boolean isRun;
        final SynClient this$0;

        protected CheckThread()
        {
            this$0 = SynClient.this;
            super();
            isRun = true;
        }
    }

    protected class ReadThread extends Thread
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
                        if(SynClient.LOGGER.isTraceEnabled() && pack.isSystemPack())
                            SynClient.LOGGER.trace((new StringBuilder()).append("\u6536\u5230").append(pack.isRequestPack() ? "\u8BF7\u6C42" : "\u54CD\u5E94").append("\u6570\u636E\u5305\uFF0C\u4E1A\u52A1\u4EE3\u7801[").append(pack.getApplicationCode()).append("]\uFF0C\u7F16\u53F7[").append(pack.getPackCode()).append("]").toString());
                        else
                        if(SynClient.LOGGER.isDebugEnabled() && !pack.isSystemPack())
                            SynClient.LOGGER.debug((new StringBuilder()).append("\u6536\u5230").append(pack.isRequestPack() ? "\u8BF7\u6C42" : "\u54CD\u5E94").append("\u6570\u636E\u5305\uFF0C\u4E1A\u52A1\u4EE3\u7801[").append(pack.getApplicationCode()).append("]\uFF0C\u7F16\u53F7[").append(pack.getPackCode()).append("]").toString());
                        dealPack(pack);
                    } else
                    {
                        if(SynClient.LOGGER.isInfoEnabled())
                            SynClient.LOGGER.info("\u670D\u52A1\u5668\u4E3B\u52A8\u5173\u95ED\u4E86\u6570\u636E\u8FDE\u63A5");
                        isRun = false;
                        startReconnect(new SynClientException("0012"));
                    }
                }
                catch(IOException e)
                {
                    SynClient.LOGGER.warn("\u63A5\u6536\u6570\u636E\u5305\u7684\u6570\u636E\u5305\u5931\u8D25", e);
                    isRun = false;
                    startReconnect(e);
                }
                catch(Exception e)
                {
                    SynClient.LOGGER.warn("\u5904\u7406\u6570\u636E\u5305\u7684\u6570\u636E\u5305\u5931\u8D25", e);
                    isRun = channel.isOpen() && channel.isConnected();
                }
            if(SynClient.LOGGER.isInfoEnabled())
            {
                SynClient.LOGGER.info("\u6570\u636E\u8BFB\u53D6\u7EBF\u7A0B\u9000\u51FA");
                terminateSendThread();
                terminateCheckThread();
                terminateDealThread();
                connected = false;
            }
            if(channel.isOpen())
                try
                {
                    SynClient.LOGGER.info("\u51C6\u5907\u5173\u95ED\u6570\u636E\u8FDE\u63A5");
                    channel.close();
                }
                catch(IOException e)
                {
                    SynClient.LOGGER.warn("\u6570\u636E\u8FDE\u63A5\u5173\u95ED\u5931\u8D25", e);
                }
        }

        private void dealPack(IDataPack pack)
        {
            if(pack.isSystemPack())
            {
                if("READY".equalsIgnoreCase(pack.getApplicationCode()) && pack.isRequestPack())
                {
                    SynClient.LOGGER.info("\u6536\u5230\u670D\u52A1\u5668\u7AEF\u7684\u63E1\u624B\u4FE1\u53F7");
                    sendBuffer.add(DataPackUtil.getResponsePack(pack));
                    try
                    {
                        listener.onInit();
                        sendBuffer.add(DataPackUtil.createReadyPack(true));
                        connected = true;
                        checkThread = new CheckThread();
                        checkThread.start();
                        synchronized(readThread)
                        {
                            readThread.notify();
                        }
                    }
                    catch(Exception ex)
                    {
                        SynClient.LOGGER.error("\u521D\u59CB\u5316\u6570\u636E\u5931\u8D25", ex);
                        isRun = false;
                        connectException = ex;
                        notifyAll();
                    }
                } else
                if("EXIT".equalsIgnoreCase(pack.getApplicationCode()))
                {
                    connected = false;
                    sendBuffer.add(DataPackUtil.createGoodbyePack());
                } else
                if("GOODBYE".equalsIgnoreCase(pack.getApplicationCode()))
                {
                    SynClient.LOGGER.info("\u670D\u52A1\u5668\u8FD4\u56DEgoodbye\u4FE1\u53F7\uFF0C\u9000\u51FA");
                    isRun = false;
                }
            } else
            if(pack.isRequestPack())
            {
                todoBuffer.add(pack);
            } else
            {
                waitingMap.remove(pack.getPackCode());
                synchronized(readThread)
                {
                    readThread.notify();
                }
            }
        }

        private boolean isRun;
        final SynClient this$0;

        protected ReadThread()
        {
            this$0 = SynClient.this;
            super();
            isRun = true;
        }
    }

    protected class SendThread extends Thread
    {

        public void run()
        {
            boolean isRun = true;
            do
            {
                if(!isRun)
                    break;
                IDataPack sendPack;
                try
                {
                    sendPack = (IDataPack)sendBuffer.remove();
                }
                catch(Exception e)
                {
                    SynClient.LOGGER.info("\u53D1\u9001\u961F\u5217\u88AB\u4E2D\u65AD", e);
                    break;
                }
                if(SynClient.LOGGER.isTraceEnabled() && sendPack.isSystemPack())
                    SynClient.LOGGER.trace((new StringBuilder()).append("\u51C6\u5907\u5411\u670D\u52A1\u5668\u53D1\u9001\u7CFB\u7EDF").append(sendPack.isRequestPack() ? "\u8BF7\u6C42" : "\u54CD\u5E94").append("\u6570\u636E\u5305\uFF0C\u4E1A\u52A1\u4EE3\u7801[").append(sendPack.getApplicationCode()).append("]\uFF0C\u7F16\u53F7[").append(sendPack.getPackCode()).append("]").toString());
                else
                if(SynClient.LOGGER.isDebugEnabled() && !sendPack.isSystemPack())
                    SynClient.LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u5411\u670D\u52A1\u5668\u53D1\u9001\u5E94\u7528").append(sendPack.isRequestPack() ? "\u8BF7\u6C42" : "\u54CD\u5E94").append("\u6570\u636E\u5305\uFF0C\u4E1A\u52A1\u4EE3\u7801[").append(sendPack.getApplicationCode()).append("]\uFF0C\u7F16\u53F7[").append(sendPack.getPackCode()).append("]").toString());
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
                    SynClient.LOGGER.warn("\u5411\u670D\u52A1\u5668\u53D1\u9001\u6570\u636E\u5305\u5931\u8D25", e);
                    isRun = false;
                    errorMap.put(sendPack.getPackCode(), e);
                    waitingMap.remove(sendPack.getPackCode());
                    notifyAll();
                    startReconnect(e);
                }
            } while(true);
            if(SynClient.LOGGER.isInfoEnabled())
                SynClient.LOGGER.info("\u6570\u636E\u53D1\u9001\u7EBF\u7A0B\u9000\u51FA");
        }

        final SynClient this$0;

        protected SendThread()
        {
            this$0 = SynClient.this;
            super();
        }
    }


    public SynClient(String server, int port, IMetaDBCacheContext cacheContext)
    {
        this(server, port, 15, cacheContext);
    }

    public SynClient(String server, int port, int checkTime, IMetaDBCacheContext cacheContext)
    {
        this.checkTime = 5;
        sendTimeout = 30;
        connected = false;
        retryListLock = false;
        this.cacheContext = cacheContext;
        this.server = server;
        this.port = port;
        this.checkTime = checkTime;
        sendBuffer = BlockingBuffer.decorate(new UnboundedFifoBuffer());
        todoBuffer = BlockingBuffer.decorate(new UnboundedFifoBuffer());
        waitingMap = new Hashtable();
        errorMap = new Hashtable();
        retryList = new Vector();
    }

    public void connect()
        throws SynException
    {
        if(connected)
            throw new SynClientException("0004");
        connectException = null;
        if(LOGGER.isInfoEnabled())
            LOGGER.info((new StringBuilder()).append("\u51C6\u5907\u8FDE\u63A5\u670D\u52A1\u5668[").append(server).append(":").append(port).append("]").toString());
        try
        {
            channel = SocketChannel.open(new InetSocketAddress(server, port));
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("\u6210\u529F\u8FDE\u63A5\u670D\u52A1\u5668\u7AEF\u53E3");
        }
        catch(Exception ex)
        {
            throw new SynClientException("0006", ex, new Object[] {
                server, new Integer(port)
            });
        }
        readThread = new ReadThread();
        readThread.start();
        sendThread = new SendThread();
        sendThread.start();
        dealThread = new DealThread();
        dealThread.start();
        while(!connected && connectException == null) 
            try
            {
                synchronized(readThread)
                {
                    readThread.wait();
                }
            }
            catch(InterruptedException e)
            {
                throw new SynClientException("0010", e);
            }
        if(connectException != null)
            throw new SynClientException("0010", connectException);
        if(LOGGER.isInfoEnabled())
            LOGGER.info((new StringBuilder()).append("\u8FDE\u63A5\u670D\u52A1\u5668[").append(server).append(":").append(port).append("]\u6210\u529F").toString());
        List sendList = new ArrayList(retryList);
        retryListLock = false;
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("\u5F00\u59CB\u91CD\u53D1\u6570\u636E\uFF0C\u5171[").append(sendList.size()).append("]\u6761\u6570\u636E").toString());
        IDataPack pack;
        for(Iterator iter = sendList.iterator(); iter.hasNext(); sendData(pack))
            pack = (IDataPack)iter.next();

    }

    public void sendData(IDataPack data)
        throws SynException
    {
        if(!connected)
        {
            addToRetry(data);
            throw new SynClientException("0003");
        }
        if(waitingMap.containsKey(data.getPackCode()))
            throw new SynClientException("0007", new Object[] {
                data.getApplicationCode(), data.getPackCode()
            });
        waitingMap.put(data.getPackCode(), data);
        sendBuffer.add(data);
        boolean timeout = false;
        long start = System.currentTimeMillis();
        int waitTime = sendTimeout * 1000;
        for(; connected && waitingMap.containsKey(data.getPackCode()) && !timeout; timeout = start + (long)waitTime < System.currentTimeMillis())
            try
            {
                synchronized(readThread)
                {
                    readThread.wait(waitTime);
                }
            }
            catch(InterruptedException e)
            {
                LOGGER.warn("\u7B49\u5F85\u6570\u636E\u5305\u53D1\u9001\u54CD\u5E94\u8FC7\u7A0B\u88AB\u6253\u65AD", e);
            }

        Exception error = (Exception)errorMap.remove(data.getPackCode());
        if(error != null)
        {
            addToRetry(data);
            throw new SynClientException("0009", error, new Object[] {
                data.getApplicationCode(), data.getPackCode()
            });
        }
        if(timeout)
            throw new SynClientException("0008", new Object[] {
                data.getApplicationCode(), data.getPackCode()
            });
        else
            return;
    }

    private void addToRetry(IDataPack data)
    {
        if(!retryListLock)
        {
            if(retryList.size() < 1000)
            {
                LOGGER.warn((new StringBuilder()).append("\u6570\u636E\u5305[").append(data.getPackCode()).append("]\u8FDB\u5165\u91CD\u53D1\u961F\u5217").toString());
                retryList.add(data);
            } else
            {
                LOGGER.warn("\u91CD\u53D1\u961F\u5217\u8D85\u8F7D\uFF0C\u9501\u5B9A\u91CD\u53D1\u961F\u5217\uFF0C\u53D1\u9001reloadall\u6570\u636E\u5305");
                retryListLock = true;
                retryList.clear();
                retryList.add(DataPackUtil.craeteAppPack("reloadall", new byte[0]));
            }
        } else
        {
            LOGGER.warn("\u91CD\u53D1\u961F\u5217\u5DF2\u8D85\u8F7D");
        }
    }

    public synchronized void check()
        throws SynClientException
    {
        if(!connected)
        {
            throw new SynClientException("0003");
        } else
        {
            sendData(DataPackUtil.createCheckPack(0));
            return;
        }
    }

    public synchronized void exit()
    {
        if(!connected)
        {
            throw new SynClientException("0005");
        } else
        {
            connected = false;
            sendBuffer.add(DataPackUtil.createExitPack());
            return;
        }
    }

    protected void terminateSendThread()
    {
        if(sendThread != null && sendThread.isAlive())
            sendThread.interrupt();
    }

    protected void terminateReadThread()
    {
        if(readThread != null && readThread.isAlive())
            readThread.interrupt();
    }

    protected void terminateCheckThread()
    {
        if(checkThread != null && checkThread.isAlive())
            checkThread.interrupt();
    }

    protected void terminateDealThread()
    {
        if(dealThread != null && dealThread.isAlive())
            dealThread.interrupt();
    }

    protected void startReconnect(Exception ex)
    {
        LOGGER.warn("\u4E0E\u670D\u52A1\u5668\u7AEF\u8FDE\u63A5\u4E2D\u65AD");
        connected = false;
        List sendList = new ArrayList(sendBuffer);
        IDataPack pack;
        for(Iterator iter = sendList.iterator(); iter.hasNext(); errorMap.put(pack.getPackCode(), ex))
            pack = (IDataPack)iter.next();

        waitingMap.clear();
        sendBuffer.clear();
        synchronized(readThread)
        {
            readThread.notify();
        }
        Runnable task = new Runnable() {

            public void run()
            {
                int retryTime = checkTime * 1000;
                do
                {
                    if(connected)
                        break;
                    try
                    {
                        Thread.sleep(retryTime);
                    }
                    catch(InterruptedException e)
                    {
                        SynClient.LOGGER.info("\u91CD\u8FDE\u7EBF\u7A0B\u9000\u51FA", e);
                        break;
                    }
                    SynClient.LOGGER.info("\u5C1D\u8BD5\u91CD\u8FDE\u670D\u52A1\u5668");
                    try
                    {
                        connect();
                    }
                    catch(SynException e)
                    {
                        SynClient.LOGGER.info("\u5C1D\u8BD5\u91CD\u8FDE\u670D\u52A1\u5668\u5931\u8D25", e);
                    }
                } while(true);
            }

            final SynClient this$0;

            
            {
                this$0 = SynClient.this;
                super();
            }
        };
        Thread retryThread = new Thread(task);
        retryThread.start();
    }

    public int getCheckTime()
    {
        return checkTime;
    }

    public int getPort()
    {
        return port;
    }

    public String getServer()
    {
        return server;
    }

    public int getSendTimeout()
    {
        return sendTimeout;
    }

    public void setSendTimeout(int sendTimeout)
    {
        if(sendTimeout <= 0)
        {
            throw new SynClientException("0001");
        } else
        {
            this.sendTimeout = sendTimeout;
            return;
        }
    }

    public ISynEventListener getListener()
    {
        return listener;
    }

    public void setListener(ISynEventListener listener)
    {
        if(listener == null)
        {
            throw new SynClientException("0002");
        } else
        {
            this.listener = listener;
            return;
        }
    }

    protected String server;
    protected int port;
    protected int checkTime;
    protected int sendTimeout;
    protected ISynEventListener listener;
    protected Buffer sendBuffer;
    protected Map waitingMap;
    protected Map errorMap;
    protected Buffer todoBuffer;
    protected SocketChannel channel;
    protected boolean connected;
    protected Thread sendThread;
    protected Thread readThread;
    protected CheckThread checkThread;
    protected Thread dealThread;
    protected List retryList;
    protected boolean retryListLock;
    protected Exception connectException;
    public static final int DEFAULT_CHECK_TIME = 15;
    public static final int RETRYLIST_MAXSIZE = 1000;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/synserver/client/SynClient);
    private IMetaDBCacheContext cacheContext;


}
