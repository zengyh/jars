// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseDataPack.java

package com.sinitek.base.synserver;


// Referenced classes of package com.sinitek.base.synserver:
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

    public synchronized byte[] getBodyPack()
    {
        if(bodyPack == null)
        {
            bodyPack = new byte[data.length - 64];
            System.arraycopy(data, 64, bodyPack, 0, bodyPack.length);
        }
        return bodyPack;
    }

    public byte[] getBytes()
    {
        byte ret[] = new byte[data.length];
        System.arraycopy(data, 0, ret, 0, ret.length);
        return ret;
    }

    private byte data[];
    private byte bodyPack[];
    private String applicationCode;
    private String packCode;
    private int bodyLength;
    private byte remark[];
}
