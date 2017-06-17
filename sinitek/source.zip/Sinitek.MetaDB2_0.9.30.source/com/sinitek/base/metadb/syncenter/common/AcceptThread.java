// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AcceptThread.java

package com.sinitek.base.metadb.syncenter.common;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import java.io.*;
import java.net.Socket;
import org.apache.commons.collections.Buffer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.common:
//            BaseDataPack, IDataPack, LogByteUtils

public class AcceptThread
    implements Runnable
{

    public AcceptThread(Socket socket, Buffer dataBuffer)
    {
        this.socket = socket;
        this.dataBuffer = dataBuffer;
    }

    public void run()
    {
        InputStream is = socket.getInputStream();
        ByteArrayOutputStream headBaos = new ByteArrayOutputStream(192);
        int b;
        for(b = is.read(); b != -1 && headBaos.size() < 192; b = is.read())
            headBaos.write(b);

        int bodyLength = -1;
        if(headBaos.size() < 192)
        {
            LOGGER.warn("\u63A5\u6536\u5230\u7684\u6570\u636E\u62A5\u6587\u5934\u4E0D\u8DB364\u4F4D\u957F\u5EA6\uFF0C\u4E0D\u5408\u6CD5\u6570\u636E\uFF0C\u629B\u5F03");
        } else
        {
            byte headBytes[] = headBaos.toByteArray();
            String szBodyLength = (new String(headBytes, 32, 8)).trim();
            try
            {
                bodyLength = Integer.parseInt(szBodyLength);
                if(LOGGER.isTraceEnabled())
                    LOGGER.trace((new StringBuilder()).append("\u89E3\u6790\u5F97\u5230\u62A5\u6587\u4F53\u957F\u5EA6[").append(bodyLength).append("]").toString());
            }
            catch(NumberFormatException e)
            {
                LOGGER.warn((new StringBuilder()).append("\u6536\u5230\u975E\u6CD5\u7684\u6570\u636E\u62A5\u6587\u5934\uFF0C\u8868\u793A\u62A5\u6587\u4F53\u957F\u5EA6\u7684\u5B57\u7B26\u65E0\u6CD5\u89E3\u6790\u4E3A\u6574\u6570[").append(szBodyLength).append("]\uFF0C\u4E0D\u5408\u6CD5\u6570\u636E\uFF0C\u629B\u5F03").toString());
            }
        }
        if(bodyLength >= 0)
        {
            ByteArrayOutputStream bodyBaos;
            for(bodyBaos = new ByteArrayOutputStream(bodyLength); b != -1 && bodyBaos.size() < bodyLength; b = is.read())
                bodyBaos.write(b);

            if(b != -1 && LOGGER.isDebugEnabled())
                LOGGER.warn((new StringBuilder()).append("\u5728\u63A5\u6536\u5230[").append(bodyLength).append("]\u5B57\u8282\u6307\u5B9A\u7684\u6570\u636E\u63D0\u540E\uFF0C\u53D1\u9001\u65B9\u4ECD\u7136\u5728\u7EE7\u7EED\u8F93\u51FA\uFF0C\u5FFD\u7565\u540E\u7EED\u6570\u636E").toString());
            IDataPack responsePack = null;
            try
            {
                bodyBaos.writeTo(headBaos);
                IDataPack pack = new BaseDataPack(headBaos.toByteArray());
                dataBuffer.add(pack);
                responsePack = BaseDataPack.getResponsePack(pack);
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u6210\u529F\u63A5\u6536\u5230\u8BF7\u6C42\u62A5\u6587\uFF0C\u62A5\u6587\u7F16\u53F7[").append(pack.getPackCode()).append("]").toString());
            }
            catch(Exception ex)
            {
                LOGGER.error("\u5C06\u63A5\u6536\u5230\u7684\u6570\u636E\u62A5\u6587\u5904\u7406\u4E3AIDataPack\u5BF9\u8C61\u65F6\u5931\u8D25", ex);
            }
            if(responsePack != null)
            {
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u53D1\u9001\u54CD\u5E94\u62A5\u6587\uFF0C\u62A5\u6587\u7F16\u53F7[").append(responsePack.getPackCode()).append("]").toString());
                OutputStream os = socket.getOutputStream();
                os.write(responsePack.getBytes());
                os.flush();
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u53D1\u9001\u54CD\u5E94\u62A5\u6587\u6210\u529F\uFF0C\u62A5\u6587\u7F16\u53F7[").append(responsePack.getPackCode()).append("]").toString());
            }
        } else
        {
            if(LOGGER.isDebugEnabled())
                LOGGER.debug("\u7531\u4E8E\u6570\u636E\u62A5\u6587\u5934\u4E0D\u5408\u6CD5\uFF0C\u4E0D\u518D\u63A5\u6536\u6570\u636E\u4F53\uFF0C\u51C6\u5907\u5173\u95ED\u6570\u636E\u4F20\u8F93\u8FDE\u63A5\uFF0C\u4E0D\u8FD4\u56DE\u54CD\u5E94\u62A5\u6587");
            LogByteUtils.logByte(LOGGER, headBaos.toByteArray(), Level.TRACE);
        }
        socket.shutdownOutput();
        socket.shutdownInput();
        try
        {
            if(socket.isConnected())
                socket.close();
        }
        catch(IOException ioe)
        {
            LOGGER.warn("\u5173\u95EDSocket\u94FE\u8DEF\u5931\u8D25", ioe);
        }
        break MISSING_BLOCK_LABEL_655;
        IOException e;
        e;
        LOGGER.error("\u5904\u7406Socket\u901A\u8BAF\u53D1\u751F\u5F02\u5E38", e);
        try
        {
            if(socket.isConnected())
                socket.close();
        }
        catch(IOException ioe)
        {
            LOGGER.warn("\u5173\u95EDSocket\u94FE\u8DEF\u5931\u8D25", ioe);
        }
        break MISSING_BLOCK_LABEL_655;
        Exception exception;
        exception;
        try
        {
            if(socket.isConnected())
                socket.close();
        }
        catch(IOException ioe)
        {
            LOGGER.warn("\u5173\u95EDSocket\u94FE\u8DEF\u5931\u8D25", ioe);
        }
        throw exception;
        LOGGER.debug("Socket\u5904\u7406\u7EBF\u7A0B\u5B8C\u6210\uFF0C\u5373\u5C06\u9000\u51FA");
        return;
    }

    private static Logger LOGGER;
    private Socket socket;
    private final Buffer dataBuffer;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }
}
