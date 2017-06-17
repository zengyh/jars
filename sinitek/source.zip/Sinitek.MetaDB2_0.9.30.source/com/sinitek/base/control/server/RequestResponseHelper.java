// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RequestResponseHelper.java

package com.sinitek.base.control.server;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.control.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.*;
import org.apache.log4j.Logger;

public class RequestResponseHelper
{

    public RequestResponseHelper()
    {
    }

    public static IRequest createRequest(byte data[])
    {
        List datas = SerializeHelper.devideData(data);
        SimpleRequest ret = new SimpleRequest();
        java.util.Map.Entry entry;
        for(Iterator iter = datas.iterator(); iter.hasNext(); ret.addRequestParameter((String)entry.getKey(), entry.getValue()))
            entry = SerializeHelper.deserialize((byte[])(byte[])iter.next());

        return ret;
    }

    public static IHandleResult createNoHandlerResult(String hanlderName)
    {
        SimpleHanlderResult result = new SimpleHanlderResult(false);
        result.setReturnMessage((new StringBuilder()).append("\u5904\u7406\u5668[").append(hanlderName).append("]\u4E0D\u5B58\u5728").toString());
        return result;
    }

    public static byte[] createResponsePack(IHandleResult result, String name)
    {
        byte ret[];
        DecimalFormat df = new DecimalFormat("00000000");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(49);
        baos.write(result.isSuccess() ? 48 : 49);
        byte nameBytes[] = name.getBytes("utf-8");
        baos.write(nameBytes);
        baos.write(new byte[54 - nameBytes.length]);
        baos.write(new byte[8]);
        SerializeHelper.serialize(result.getReturnMessage(), baos, "returnMessage");
        String paramName;
        for(Iterator iter = result.getReturnParameterNames().iterator(); iter.hasNext(); SerializeHelper.serialize(result.getReturnParameter(paramName), baos, paramName))
            paramName = iter.next().toString();

        ret = baos.toByteArray();
        System.arraycopy(df.format(ret.length - 64).getBytes(), 0, ret, 56, 8);
        return ret;
        Exception e;
        e;
        LOGGER.error("\u5904\u7406\u54CD\u5E94\u6570\u636E\u5305\u5931\u8D25", e);
        return createExceptionPack(e);
    }

    public static byte[] createExceptionPack(Exception ex)
    {
        byte ret[];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(49);
        baos.write(50);
        baos.write(new byte[54], 0, 54);
        baos.write(new byte[8], 0, 8);
        if(ex instanceof BaseException)
        {
            BaseException be = (BaseException)ex;
            SerializeHelper.serialize(be.getCode(), baos, "code");
            SerializeHelper.serialize(be.getErrorMsg(), baos, "errorMsg");
        }
        ByteArrayOutputStream stackTrace = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(stackTrace);
        ex.printStackTrace(writer);
        writer.flush();
        String strStackTrace = new String(stackTrace.toByteArray());
        SerializeHelper.serialize(strStackTrace, baos, "stacktrace");
        ret = baos.toByteArray();
        DecimalFormat df = new DecimalFormat("00000000");
        System.arraycopy(df.format(ret.length - 64).getBytes(), 0, ret, 56, 8);
        return ret;
        Exception e;
        e;
        LOGGER.error("\u5904\u7406\u5F02\u5E38\u6570\u636E\u5305\u5931\u8D25", e);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(49);
        baos.write(50);
        baos.write(new byte[54], 0, 54);
        baos.write("00000000".getBytes(), 0, 8);
        return baos.toByteArray();
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/control/server/RequestResponseHelper);

}
