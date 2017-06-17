// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PinYinUtils.java

package com.sinitek.spirit.webcontrol.utils;


public class PinYinUtils
{

    public PinYinUtils()
    {
    }

    public static String getFirstLetter(String oriStr)
    {
        String str = oriStr.toLowerCase();
        StringBuilder buffer = new StringBuilder();
        for(int i = 0; i < str.length(); i++)
        {
            char ch = str.charAt(i);
            char temp[] = {
                ch
            };
            byte uniCode[] = (new String(temp)).getBytes();
            if(uniCode[0] < 128 && uniCode[0] > 0)
                buffer.append(temp);
            else
                buffer.append(convert(uniCode));
        }

        return buffer.toString();
    }

    private static char convert(byte bytes[])
    {
        char result = '-';
        int i;
        for(i = 0; i < bytes.length; i++)
            bytes[i] -= 160;

        int secPosvalue = bytes[0] * 100 + bytes[1];
        i = 0;
        do
        {
            if(i >= 23)
                break;
            if(secPosvalue >= secPosvalueList[i] && secPosvalue < secPosvalueList[i + 1])
            {
                result = firstLetter[i];
                break;
            }
            i++;
        } while(true);
        return result;
    }

    private static final int GB_SP_DIFF = 160;
    private static final int secPosvalueList[] = {
        1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 
        3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 
        4684, 4925, 5249, 5600
    };
    private static final char firstLetter[] = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 
        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 
        'x', 'y', 'z'
    };

}
