// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSendUtils.java

package com.sinitek.base.metadb.syncenter.client;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.IDataPack;
import java.io.*;
import java.net.*;
import org.apache.log4j.Logger;

public class DataSendUtils
{

    public DataSendUtils()
    {
    }

    public static boolean sendData(IDataPack dataPack, InetSocketAddress address)
    {
        boolean sendSuccess;
        Socket socket;
        sendSuccess = false;
        socket = new Socket();
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u8FDE\u63A5MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]").toString());
        socket.connect(address, 10000);
        dataPack.setSenderAddress(socket.getLocalAddress().getHostAddress());
        if(LOGGER.isTraceEnabled())
            LOGGER.trace((new StringBuilder()).append("\u8FDE\u63A5MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u6210\u529F").toString());
        OutputStream os = socket.getOutputStream();
        os.write(dataPack.getBytes());
        os.flush();
        if(LOGGER.isTraceEnabled())
            LOGGER.trace((new StringBuilder()).append("\u5BF9MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u7684\u8F93\u51FA\u6D41\u5199\u5165\u6210\u529F").toString());
        socket.shutdownOutput();
        InputStream is = socket.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(192);
        int b;
        for(b = is.read(); b != -1 && baos.size() < 192; b = is.read())
            baos.write(b);

        if(baos.size() < 192)
            LOGGER.error((new StringBuilder()).append("\u5BF9MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u53D1\u9001\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u540E\u6536\u5230\u7684\u8FD4\u56DE\u62A5\u6587\u957F\u5EA6\u4E0D\u8DB3192\u4F4D\uFF0C\u5B9E\u9645\u4E3A[").append(baos.size()).append("]\u4F4D\uFF0C\u5224\u4E3A\u975E\u6CD5\u8FD4\u56DE\u62A5\u6587").toString());
        else
        if(b != -1)
        {
            LOGGER.error((new StringBuilder()).append("\u5BF9MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u53D1\u9001\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u540E\u6536\u5230\u7684\u8FD4\u56DE\u62A5\u6587\u957F\u5EA6\u8D85\u8FC7192\u4F4D\uFF0C\u5224\u4E3A\u975E\u6CD5\u8FD4\u56DE\u62A5\u6587").toString());
        } else
        {
            if(LOGGER.isTraceEnabled())
                LOGGER.trace((new StringBuilder()).append("\u5BF9MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u53D1\u9001\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u540E\u6536\u5230\u7684\u8FD4\u56DE\u62A5\u6587\u957F\u5EA6\u4E3A192\u4F4D\uFF0C\u51C6\u5907\u5224\u65AD\u62A5\u6587\u7F16\u53F7\u662F\u5426\u5339\u914D").toString());
            String backPackCode = (new String(baos.toByteArray(), 12, 20)).trim();
            if(!dataPack.getPackCode().equals(backPackCode))
            {
                LOGGER.error((new StringBuilder()).append("\u5BF9MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u53D1\u9001\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u540E\u6536\u5230\u7684\u8FD4\u56DE\u62A5\u6587\u4E2D\u7684\u62A5\u6587\u7F16\u53F7\u4E0D\u4E00\u81F4\uFF0C\u5B9E\u9645\u4E3A[").append(backPackCode).append("]\uFF0C\u5224\u4E3A\u975E\u6CD5\u8FD4\u56DE\u62A5\u6587").toString());
            } else
            {
                if(LOGGER.isTraceEnabled())
                    LOGGER.trace((new StringBuilder()).append("\u5BF9MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u53D1\u9001\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u540E\u6536\u5230\u7684\u8FD4\u56DE\u62A5\u6587\u4E2D\u7684\u62A5\u6587\u7F16\u53F7\u4E00\u81F4\uFF0C\u5224\u4E3A\u6B63\u786E\u8FD4\u56DE\u62A5\u6587").toString());
                sendSuccess = true;
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u5BF9MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u6210\u529F").toString());
            }
        }
        socket.shutdownInput();
        IOException e;
        if(socket.isConnected())
            try
            {
                socket.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException e)
            {
                LOGGER.debug((new StringBuilder()).append("\u5BF9MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u7684Socket\u5BF9\u8C61\u5931\u8D25").toString(), e);
            }
        break MISSING_BLOCK_LABEL_1082;
        e;
        LOGGER.debug((new StringBuilder()).append("\u5BF9MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u5931\u8D25").toString(), e);
        if(socket.isConnected())
            try
            {
                socket.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException e)
            {
                LOGGER.debug((new StringBuilder()).append("\u5BF9MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u7684Socket\u5BF9\u8C61\u5931\u8D25").toString(), e);
            }
        break MISSING_BLOCK_LABEL_1082;
        Exception exception;
        exception;
        if(socket.isConnected())
            try
            {
                socket.close();
            }
            catch(IOException e)
            {
                LOGGER.debug((new StringBuilder()).append("\u5BF9MetaDB\u540C\u6B65\u4E2D\u5FC3\uFF0C\u5730\u5740[").append(address.getHostName()).append(":").append(address.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u7684Socket\u5BF9\u8C61\u5931\u8D25").toString(), e);
            }
        throw exception;
        return sendSuccess;
    }

    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }
}
