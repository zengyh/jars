// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HandleThread.java

package com.sinitek.base.control.server;

import com.sinitek.base.control.IHandler;
import java.io.*;
import java.net.Socket;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.control.server:
//            RequestResponseHelper

public class HandleThread
    implements Runnable
{

    public HandleThread(Socket socket, Map handlerMap)
    {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    public void run()
    {
        InputStream is;
        OutputStream os;
        is = null;
        os = null;
        byte head[];
        String name;
        is = socket.getInputStream();
        os = socket.getOutputStream();
        head = new byte[64];
        readBytes(is, head);
        name = (new String(head, 2, 54)).trim();
        LOGGER.info((new StringBuilder()).append("\u6536\u5230\u6570\u636E\u5305\uFF0C\u540D\u79F0=").append(name).toString());
        if(!StringUtils.isEmpty(name))
            break MISSING_BLOCK_LABEL_158;
        LOGGER.warn("\u670D\u52A1\u540D\u79F0\u4E3A\u7A7A\uFF0C\u4E22\u5F03\u8BE5\u6570\u636E\u5305");
        if(is != null)
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95EDSocket\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        if(os != null)
            try
            {
                os.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95EDSocket\u8F93\u51FA\u6D41\u5931\u8D25", e);
            }
        try
        {
            socket.close();
        }
        catch(Exception ex)
        {
            LOGGER.error("\u5173\u95EDSocket\u5931\u8D25", ex);
        }
        return;
        int bodyLength = Integer.parseInt(new String(head, 56, 8));
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u4F53\u957F\u5EA6=").append(bodyLength).toString());
        byte body[] = new byte[bodyLength];
        readBytes(is, body);
        com.sinitek.base.control.IRequest request = RequestResponseHelper.createRequest(body);
        IHandler handler = (IHandler)handlerMap.get(name);
        if(handler != null)
        {
            com.sinitek.base.control.IHandleResult result = null;
            try
            {
                LOGGER.debug((new StringBuilder()).append("\u8C03\u7528\u5904\u7406\u5668[").append(handler.getClass().getName()).append("]\u5904\u7406").toString());
                result = handler.handle(request);
                LOGGER.debug((new StringBuilder()).append("\u8C03\u7528\u5904\u7406\u5668[").append(handler.getClass().getName()).append("]\u5904\u7406\u6210\u529F").toString());
            }
            catch(Exception e)
            {
                LOGGER.error((new StringBuilder()).append("\u5904\u7406\u5668[").append(handler.getClass().getName()).append("]\u5904\u7406\u51FA\u9519").toString());
                os.write(RequestResponseHelper.createExceptionPack(e));
            }
            if(result != null)
                os.write(RequestResponseHelper.createResponsePack(result, name));
        } else
        {
            LOGGER.debug((new StringBuilder()).append("\u9488\u5BF9\u670D\u52A1\u540D[").append(name).append("]\u627E\u4E0D\u5230\u5904\u7406\u5668").toString());
            os.write(RequestResponseHelper.createResponsePack(RequestResponseHelper.createNoHandlerResult(name), name));
        }
        os.flush();
        IOException e;
        if(is != null)
            try
            {
                is.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95EDSocket\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        if(os != null)
            try
            {
                os.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95EDSocket\u8F93\u51FA\u6D41\u5931\u8D25", e);
            }
        try
        {
            socket.close();
        }
        catch(Exception ex)
        {
            LOGGER.error("\u5173\u95EDSocket\u5931\u8D25", ex);
        }
        break MISSING_BLOCK_LABEL_662;
        ex;
        LOGGER.error("\u5904\u7406\u6307\u4EE4\u5931\u8D25", ex);
        if(is != null)
            try
            {
                is.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex)
            {
                LOGGER.error("\u5173\u95EDSocket\u8F93\u5165\u6D41\u5931\u8D25", ex);
            }
        if(os != null)
            try
            {
                os.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception ex)
            {
                LOGGER.error("\u5173\u95EDSocket\u8F93\u51FA\u6D41\u5931\u8D25", ex);
            }
        try
        {
            socket.close();
        }
        catch(Exception ex)
        {
            LOGGER.error("\u5173\u95EDSocket\u5931\u8D25", ex);
        }
        break MISSING_BLOCK_LABEL_662;
        Exception exception;
        exception;
        if(is != null)
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95EDSocket\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        if(os != null)
            try
            {
                os.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95EDSocket\u8F93\u51FA\u6D41\u5931\u8D25", e);
            }
        try
        {
            socket.close();
        }
        catch(Exception ex)
        {
            LOGGER.error("\u5173\u95EDSocket\u5931\u8D25", ex);
        }
        throw exception;
    }

    private void readBytes(InputStream is, byte buffer[])
        throws IOException
    {
        for(int count = 0; count < buffer.length; count += is.read(buffer, count, buffer.length - count));
    }

    private Socket socket;
    private Map handlerMap;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/control/server/HandleThread);

}
