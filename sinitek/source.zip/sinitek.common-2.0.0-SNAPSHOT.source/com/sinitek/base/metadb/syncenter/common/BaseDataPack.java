// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseDataPack.java

package com.sinitek.base.metadb.syncenter.common;

import java.io.ByteArrayOutputStream;
import java.text.*;
import java.util.*;

// Referenced classes of package com.sinitek.base.metadb.syncenter.common:
//            IDataPack

public class BaseDataPack
    implements IDataPack
{

    public BaseDataPack(byte data[])
    {
        this.data = data;
        applicationCode = (new String(data, 2, 10)).trim();
        packCode = (new String(data, 12, 20)).trim();
        String szBodyLength = (new String(data, 32, 8)).trim();
        bodyLength = Integer.parseInt(szBodyLength);
        remark = new byte[24];
        System.arraycopy(data, 40, remark, 0, 24);
        senderAppKey = (new String(data, 64, 128)).trim();
        int localAddressPos = senderAppKey.lastIndexOf("|");
        if(localAddressPos > 0)
        {
            senderAddress = senderAppKey.substring(localAddressPos + 1);
            senderAppKey = senderAppKey.substring(0, localAddressPos);
        }
    }

    public static IDataPack getResponsePack(IDataPack requestPack)
    {
        if(requestPack.isRequestPack())
        {
            byte _temp[] = new byte[192];
            System.arraycopy(requestPack.getBytes(), 0, _temp, 0, 192);
            _temp[1] = 49;
            Arrays.fill(_temp, 32, 40, (byte)48);
            return new BaseDataPack(_temp);
        } else
        {
            return new BaseDataPack(requestPack.getBytes());
        }
    }

    public static IDataPack craeteAppPack(String appCode, byte bodyData[], String sendAppKey)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        makeData(baos, true, appCode, sendAppKey, bodyData, false);
        return new BaseDataPack(baos.toByteArray());
    }

    public boolean isSystemPack()
    {
        return 48 == data[0];
    }

    public boolean isRequestPack()
    {
        return 48 == data[1];
    }

    public String getApplicationCode()
    {
        return applicationCode;
    }

    public String getPackCode()
    {
        return packCode;
    }

    public int getBodySize()
    {
        return bodyLength;
    }

    public byte[] getRemark()
    {
        return remark;
    }

    public String getSenderAppKey()
    {
        return senderAppKey;
    }

    public synchronized byte[] getBodyPack()
    {
        if(bodyPack == null)
        {
            bodyPack = new byte[data.length - 192];
            System.arraycopy(data, 192, bodyPack, 0, bodyPack.length);
        }
        return bodyPack;
    }

    public byte[] getBytes()
    {
        byte ret[] = new byte[data.length];
        System.arraycopy(data, 0, ret, 0, ret.length);
        return ret;
    }

    public String getSenderAddress()
    {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress)
    {
        this.senderAddress = senderAddress;
        String currentSenderKey = (new String(data, 64, 128)).trim();
        currentSenderKey = (new StringBuilder()).append(currentSenderKey).append("|").append(senderAddress).toString();
        byte newSenderKey[] = currentSenderKey.getBytes();
        System.arraycopy(newSenderKey, 0, data, 64, newSenderKey.length);
    }

    public static IDataPack createHelloPack(String senderKey)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        makeData(baos, true, "HELLO", senderKey, null, true);
        return new BaseDataPack(baos.toByteArray());
    }

    private static void makeData(ByteArrayOutputStream baos, boolean isRequest, String applicationCode, String senderKey, byte bodyData[], boolean isSystem)
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
        int sendLength = 128;
        byte bSendKey[] = senderKey.getBytes();
        sendLength -= bSendKey.length;
        baos.write(bSendKey, 0, bSendKey.length);
        for(int i = 0; i < sendLength; i++)
            baos.write(0);

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

    private byte data[];
    private byte bodyPack[];
    private String applicationCode;
    private String packCode;
    private int bodyLength;
    private String senderAppKey;
    private byte remark[];
    private String senderAddress;
    private static final Random RAM = new Random(System.currentTimeMillis());

}
