// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharUtils.java

package org.apache.commons.lang;


// Referenced classes of package org.apache.commons.lang:
//            StringUtils

public class CharUtils
{

    public CharUtils()
    {
    }

    public static Character toCharacterObject(char ch)
    {
        if(ch < CHAR_ARRAY.length)
            return CHAR_ARRAY[ch];
        else
            return new Character(ch);
    }

    public static Character toCharacterObject(String str)
    {
        if(StringUtils.isEmpty(str))
            return null;
        else
            return toCharacterObject(str.charAt(0));
    }

    public static char toChar(Character ch)
    {
        if(ch == null)
            throw new IllegalArgumentException("The Character must not be null");
        else
            return ch.charValue();
    }

    public static char toChar(Character ch, char defaultValue)
    {
        if(ch == null)
            return defaultValue;
        else
            return ch.charValue();
    }

    public static char toChar(String str)
    {
        if(StringUtils.isEmpty(str))
            throw new IllegalArgumentException("The String must not be empty");
        else
            return str.charAt(0);
    }

    public static char toChar(String str, char defaultValue)
    {
        if(StringUtils.isEmpty(str))
            return defaultValue;
        else
            return str.charAt(0);
    }

    public static int toIntValue(char ch)
    {
        if(!isAsciiNumeric(ch))
            throw new IllegalArgumentException("The character " + ch + " is not in the range '0' - '9'");
        else
            return ch - 48;
    }

    public static int toIntValue(char ch, int defaultValue)
    {
        if(!isAsciiNumeric(ch))
            return defaultValue;
        else
            return ch - 48;
    }

    public static int toIntValue(Character ch)
    {
        if(ch == null)
            throw new IllegalArgumentException("The character must not be null");
        else
            return toIntValue(ch.charValue());
    }

    public static int toIntValue(Character ch, int defaultValue)
    {
        if(ch == null)
            return defaultValue;
        else
            return toIntValue(ch.charValue(), defaultValue);
    }

    public static String toString(char ch)
    {
        if(ch < '\200')
            return CHAR_STRING_ARRAY[ch];
        else
            return new String(new char[] {
                ch
            });
    }

    public static String toString(Character ch)
    {
        if(ch == null)
            return null;
        else
            return toString(ch.charValue());
    }

    public static String unicodeEscaped(char ch)
    {
        if(ch < '\020')
            return "\\u000" + Integer.toHexString(ch);
        if(ch < '\u0100')
            return "\\u00" + Integer.toHexString(ch);
        if(ch < '\u1000')
            return "\\u0" + Integer.toHexString(ch);
        else
            return "\\u" + Integer.toHexString(ch);
    }

    public static String unicodeEscaped(Character ch)
    {
        if(ch == null)
            return null;
        else
            return unicodeEscaped(ch.charValue());
    }

    public static boolean isAscii(char ch)
    {
        return ch < '\200';
    }

    public static boolean isAsciiPrintable(char ch)
    {
        return ch >= ' ' && ch < '\177';
    }

    public static boolean isAsciiControl(char ch)
    {
        return ch < ' ' || ch == '\177';
    }

    public static boolean isAsciiAlpha(char ch)
    {
        return ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z';
    }

    public static boolean isAsciiAlphaUpper(char ch)
    {
        return ch >= 'A' && ch <= 'Z';
    }

    public static boolean isAsciiAlphaLower(char ch)
    {
        return ch >= 'a' && ch <= 'z';
    }

    public static boolean isAsciiNumeric(char ch)
    {
        return ch >= '0' && ch <= '9';
    }

    public static boolean isAsciiAlphanumeric(char ch)
    {
        return ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' || ch >= '0' && ch <= '9';
    }

    private static final String CHAR_STRING = "\000\001\002\003\004\005\006\007\b\t\n\013\f\r\016\017\020\021\022\023\024\025\026\027\030\031\032\033\034\035\036\037 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\177";
    private static final String CHAR_STRING_ARRAY[];
    private static final Character CHAR_ARRAY[];
    public static final char LF = 10;
    public static final char CR = 13;

    static 
    {
        CHAR_STRING_ARRAY = new String[128];
        CHAR_ARRAY = new Character[128];
        for(int i = 127; i >= 0; i--)
        {
            CHAR_STRING_ARRAY[i] = "\000\001\002\003\004\005\006\007\b\t\n\013\f\r\016\017\020\021\022\023\024\025\026\027\030\031\032\033\034\035\036\037 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\177".substring(i, i + 1);
            CHAR_ARRAY[i] = new Character((char)i);
        }

    }
}
