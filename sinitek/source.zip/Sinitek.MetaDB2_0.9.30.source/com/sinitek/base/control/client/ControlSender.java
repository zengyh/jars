// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ControlSender.java

package com.sinitek.base.control.client;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.control.*;
import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.control.client:
//            ClientHandlerException, HandleWrapException

public class ControlSender
{

    public ControlSender()
    {
    }

    public static IHandleResult send(String address, int port, String name, IRequest request)
        throws HandleException
    {
        InputStream is;
        OutputStream os;
        Socket socket;
        Exception exception;
        is = null;
        os = null;
        socket = null;
        IHandleResult ihandleresult;
        try
        {
            try
            {
                socket = new Socket(address, port);
            }
            catch(IOException e)
            {
                throw new ClientHandlerException("0004", e, new Object[] {
                    address, new Integer(port)
                });
            }
            is = socket.getInputStream();
            os = socket.getOutputStream();
            sendRequest(request, name, os);
            os.flush();
            ihandleresult = readResult(is);
        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception e)
        {
            throw new ClientHandlerException("0003", e, new Object[] {
                address, new Integer(port), name
            });
        }
        finally
        {
            if(is == null) goto _L0; else goto _L0
        }
        if(is != null)
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95ED\u7F51\u7EDC\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        if(os != null)
            try
            {
                os.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95ED\u7F51\u7EDC\u8F93\u51FA\u6D41\u5931\u8D25", e);
            }
        if(socket != null)
            try
            {
                socket.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95ED\u7F51\u7EDC\u8FDE\u63A5\u5931\u8D25", e);
            }
        return ihandleresult;
        try
        {
            is.close();
        }
        catch(IOException e)
        {
            LOGGER.error("\u5173\u95ED\u7F51\u7EDC\u8F93\u5165\u6D41\u5931\u8D25", e);
        }
        if(os != null)
            try
            {
                os.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95ED\u7F51\u7EDC\u8F93\u51FA\u6D41\u5931\u8D25", e);
            }
        if(socket != null)
            try
            {
                socket.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95ED\u7F51\u7EDC\u8FDE\u63A5\u5931\u8D25", e);
            }
        throw exception;
    }

    private static void sendRequest(IRequest request, String name, OutputStream os)
        throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String paramName;
        for(Iterator iter = request.getRequestParameterNames().iterator(); iter.hasNext(); SerializeHelper.serialize(request.getRequestParameter(paramName), baos, paramName))
            paramName = (String)iter.next();

        os.write(48);
        os.write(48);
        byte bName[] = name.getBytes();
        os.write(bName);
        os.write(new byte[54 - bName.length]);
        DecimalFormat df = new DecimalFormat("00000000");
        os.write(df.format(baos.size()).getBytes());
        baos.writeTo(os);
    }

    private static IHandleResult readResult(InputStream is)
        throws IOException
    {
        byte head[] = new byte[64];
        readBytes(is, head);
        int bodyLength = Integer.parseInt(new String(head, 56, 8));
        byte body[] = new byte[bodyLength];
        readBytes(is, body);
        List datas;
        if(head[1] == 50)
        {
            Map errorMsgs = new HashMap();
            datas = SerializeHelper.devideData(body);
            for(int i = 0; i < datas.size(); i++)
            {
                byte data[] = (byte[])(byte[])datas.get(i);
                java.util.Map.Entry entry = SerializeHelper.deserialize(data);
                errorMsgs.put(entry.getKey(), entry.getValue());
            }

            String code = (String)errorMsgs.get("code");
            String msg = (String)errorMsgs.get("errorMsg");
            if(code != null)
                throw new HandleWrapException(code, msg, (String)errorMsgs.get("stacktrace"));
            else
                throw new HandleWrapException("04020005", "\u8FDC\u7A0B\u670D\u52A1\u5668\u629B\u51FA\u5F02\u5E38", (String)errorMsgs.get("stacktrace"));
        }
        SimpleHanlderResult result = new SimpleHanlderResult(48 == head[1]);
        datas = SerializeHelper.devideData(body);
        result.setReturnMessage((String)SerializeHelper.deserialize((byte[])(byte[])datas.get(0)).getValue());
        for(int i = 1; i < datas.size(); i++)
        {
            byte data[] = (byte[])(byte[])datas.get(i);
            java.util.Map.Entry entry = SerializeHelper.deserialize(data);
            result.addReturnParameter((String)entry.getKey(), entry.getValue());
        }

        return result;
    }

    private static void readBytes(InputStream is, byte buffer[])
        throws IOException
    {
        for(int count = 0; count < buffer.length; count += is.read(buffer, count, buffer.length - count));
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/control/client/ControlSender);

}
