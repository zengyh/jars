// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SinitekDES.java

package com.sinitek.base.common;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.log4j.Logger;

public class SinitekDES
{

    public SinitekDES()
    {
    }

    protected static byte[] encrypt(byte src[], byte key[])
        throws Exception
    {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        javax.crypto.SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1, securekey, sr);
        return cipher.doFinal(src);
    }

    protected static byte[] decrypt(byte src[], byte key[])
        throws Exception
    {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        javax.crypto.SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, securekey, sr);
        return cipher.doFinal(src);
    }

    public static String decrypt(String src)
    {
        try
        {
            LOGGER.trace("\u5F00\u59CB\u89E3\u5BC6\uFF01");
            LOGGER.trace((new StringBuilder()).append("\u5BC6\u6587:[").append(src).append("]").toString());
            String result = new String(decrypt(hex2byte(src.getBytes()), "_Sinitek".getBytes()));
            LOGGER.trace("\u89E3\u5BC6\u5B8C\u6BD5\uFF01");
            LOGGER.trace((new StringBuilder()).append("\u660E\u6587:[").append(result).append("]").toString());
            return result;
        }
        catch(Exception e)
        {
            LOGGER.error((new StringBuilder()).append("\u89E3\u5BC6\u51FA\u9519:").append(e.getMessage()).toString(), e);
            throw new RuntimeException((new StringBuilder()).append("\u89E3\u5BC6\u51FA\u9519:").append(e.getMessage()).toString(), e);
        }
    }

    public static String encrypt(String src)
    {
        try
        {
            LOGGER.trace("\u5F00\u59CB\u52A0\u5BC6\uFF01");
            LOGGER.trace((new StringBuilder()).append("\u660E\u6587:[").append(src).append("]").toString());
            String result = byte2hex(encrypt(src.getBytes(), "_Sinitek".getBytes()));
            LOGGER.trace("\u52A0\u5BC6\u5B8C\u6BD5\uFF01");
            LOGGER.trace((new StringBuilder()).append("\u5BC6\u6587:[").append(result).append("]").toString());
            return result;
        }
        catch(Exception e)
        {
            LOGGER.error((new StringBuilder()).append("\u52A0\u5BC6\u51FA\u9519:").append(e.getMessage()).toString(), e);
            throw new RuntimeException((new StringBuilder()).append("\u52A0\u5BC6\u51FA\u9519:").append(e.getMessage()).toString(), e);
        }
    }

    protected static String byte2hex(byte b[])
    {
        String hs = "";
        for(int n = 0; n < b.length; n++)
        {
            String stmp = Integer.toHexString(b[n] & 0xff);
            if(stmp.length() == 1)
                hs = (new StringBuilder()).append(hs).append("0").append(stmp).toString();
            else
                hs = (new StringBuilder()).append(hs).append(stmp).toString();
        }

        return hs.toUpperCase();
    }

    protected static byte[] hex2byte(byte b[])
    {
        if(b.length % 2 != 0)
            throw new IllegalArgumentException("\u957F\u5EA6\u4E0D\u662F\u5076\u6570!");
        byte b2[] = new byte[b.length / 2];
        for(int n = 0; n < b.length; n += 2)
        {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte)Integer.parseInt(item, 16);
        }

        return b2;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/common/SinitekDES);
    private static final String PASSWORD_CRYPT_KEY = "_Sinitek";
    private static final String DES = "DES";

}
