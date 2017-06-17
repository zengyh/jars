// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataPackUtil.java

package com.sinitek.base.synserver;

import java.io.ByteArrayOutputStream;
import java.text.*;
import java.util.*;

// Referenced classes of package com.sinitek.base.synserver:
//            BaseDataPack, IDataPack

public class DataPackUtil
{

    public DataPackUtil()
    {
    }

    public static IDataPack createReadyPack(boolean isRequest)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        makeData(baos, isRequest, "READY", null, true);
        return new BaseDataPack(baos.toByteArray());
    }

    public static IDataPack createExitPack()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        makeData(baos, true, "EXIT", null, true);
        return new BaseDataPack(baos.toByteArray());
    }

    public static IDataPack createGoodbyePack()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        makeData(baos, true, "GOODBYE", null, true);
        return new BaseDataPack(baos.toByteArray());
    }

    public static IDataPack craeteAppPack(String appCode, byte bodyData[])
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        makeData(baos, true, appCode, bodyData, false);
        return new BaseDataPack(baos.toByteArray());
    }

    public static IDataPack createCheckPack(int checkStamp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(checkStamp == 0)
        {
            makeData(baos, true, "CHECK", null, true);
        } else
        {
            DecimalFormat df = new DecimalFormat("0000000000");
            byte body[] = df.format(checkStamp).getBytes();
            makeData(baos, true, "CHECK", body, true);
        }
        return new BaseDataPack(baos.toByteArray());
    }

    public static int getBodyLengthFromHead(byte headPack[])
    {
        String szLength = new String(headPack, 32, 8);
        return Integer.parseInt(szLength.trim());
    }

    public static IDataPack getResponsePack(IDataPack requestPack)
    {
        if(requestPack.isRequestPack())
        {
            byte _temp[] = new byte[64];
            System.arraycopy(requestPack.getBytes(), 0, _temp, 0, 64);
            _temp[1] = 49;
            Arrays.fill(_temp, 32, 40, (byte)48);
            return new BaseDataPack(_temp);
        } else
        {
            return new BaseDataPack(requestPack.getBytes());
        }
    }

    private static void makeData(ByteArrayOutputStream baos, boolean isRequest, String applicationCode, byte bodyData[], boolean isSystem)
    {
        byte _appCode[] = applicationCode.getBytes();
        if(_appCode.length > 10)
            throw new IllegalArgumentException("\u4E1A\u52A1\u4EE3\u7801\u8D85\u957F");
        baos.write(isSystem ? 48 : 49);
        baos.write(isRequest ? 48 : 49);
        baos.write(_appCode, 0, _appCode.length);
        int delta = 10 - _appCode.length;
        for(int i = 0; i < delta; i++)
            baos.write(0);

        byte packCode[] = genPackCode();
        baos.write(packCode, 0, 20);
        int bodyLength = bodyData == null ? 0 : bodyData.length;
        NumberFormat nf = new DecimalFormat("00000000");
        baos.write(nf.format(bodyLength).getBytes(), 0, 8);
        baos.write(new byte[24], 0, 24);
        if(bodyLength > 0)
            baos.write(bodyData, 0, bodyLength);
    }

    private static byte[] genPackCode()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        NumberFormat nf = new DecimalFormat("000000");
        String str = sdf.format(new Date());
        synchronized(RAM)
        {
            str = (new StringBuilder()).append(str).append(nf.format(RAM.nextInt(0xf4240))).toString();
        }
        return str.getBytes();
    }

    private static final Random RAM = new Random(System.currentTimeMillis());

}
