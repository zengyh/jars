// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharEncoding.java

package org.apache.commons.lang;

import java.io.UnsupportedEncodingException;

// Referenced classes of package org.apache.commons.lang:
//            ArrayUtils

public class CharEncoding
{

    public CharEncoding()
    {
    }

    public static boolean isSupported(String name)
    {
        if(name == null)
            return false;
        try
        {
            new String(ArrayUtils.EMPTY_BYTE_ARRAY, name);
        }
        catch(UnsupportedEncodingException e)
        {
            return false;
        }
        return true;
    }

    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String US_ASCII = "US-ASCII";
    public static final String UTF_16 = "UTF-16";
    public static final String UTF_16BE = "UTF-16BE";
    public static final String UTF_16LE = "UTF-16LE";
    public static final String UTF_8 = "UTF-8";
}
