// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrMatcher.java

package org.apache.commons.lang.text;

import java.util.Arrays;

public abstract class StrMatcher
{
    static final class TrimMatcher extends StrMatcher
    {

        public int isMatch(char buffer[], int pos, int bufferStart, int bufferEnd)
        {
            return buffer[pos] > ' ' ? 0 : 1;
        }

        TrimMatcher()
        {
        }
    }

    static final class NoMatcher extends StrMatcher
    {

        public int isMatch(char buffer[], int pos, int bufferStart, int i)
        {
            return 0;
        }

        NoMatcher()
        {
        }
    }

    static final class StringMatcher extends StrMatcher
    {

        public int isMatch(char buffer[], int pos, int bufferStart, int bufferEnd)
        {
            int len = chars.length;
            if(pos + len > bufferEnd)
                return 0;
            for(int i = 0; i < chars.length;)
            {
                if(chars[i] != buffer[pos])
                    return 0;
                i++;
                pos++;
            }

            return len;
        }

        private char chars[];

        StringMatcher(String str)
        {
            chars = str.toCharArray();
        }
    }

    static final class CharMatcher extends StrMatcher
    {

        public int isMatch(char buffer[], int pos, int bufferStart, int bufferEnd)
        {
            return ch != buffer[pos] ? 0 : 1;
        }

        private char ch;

        CharMatcher(char ch)
        {
            this.ch = ch;
        }
    }

    static final class CharSetMatcher extends StrMatcher
    {

        public int isMatch(char buffer[], int pos, int bufferStart, int bufferEnd)
        {
            return Arrays.binarySearch(chars, buffer[pos]) < 0 ? 0 : 1;
        }

        private char chars[];

        CharSetMatcher(char chars[])
        {
            this.chars = (char[])chars.clone();
            Arrays.sort(this.chars);
        }
    }


    public static StrMatcher commaMatcher()
    {
        return COMMA_MATCHER;
    }

    public static StrMatcher tabMatcher()
    {
        return TAB_MATCHER;
    }

    public static StrMatcher spaceMatcher()
    {
        return SPACE_MATCHER;
    }

    public static StrMatcher splitMatcher()
    {
        return SPLIT_MATCHER;
    }

    public static StrMatcher trimMatcher()
    {
        return TRIM_MATCHER;
    }

    public static StrMatcher singleQuoteMatcher()
    {
        return SINGLE_QUOTE_MATCHER;
    }

    public static StrMatcher doubleQuoteMatcher()
    {
        return DOUBLE_QUOTE_MATCHER;
    }

    public static StrMatcher quoteMatcher()
    {
        return QUOTE_MATCHER;
    }

    public static StrMatcher noneMatcher()
    {
        return NONE_MATCHER;
    }

    public static StrMatcher charMatcher(char ch)
    {
        return new CharMatcher(ch);
    }

    public static StrMatcher charSetMatcher(char chars[])
    {
        if(chars == null || chars.length == 0)
            return NONE_MATCHER;
        if(chars.length == 1)
            return new CharMatcher(chars[0]);
        else
            return new CharSetMatcher(chars);
    }

    public static StrMatcher charSetMatcher(String chars)
    {
        if(chars == null || chars.length() == 0)
            return NONE_MATCHER;
        if(chars.length() == 1)
            return new CharMatcher(chars.charAt(0));
        else
            return new CharSetMatcher(chars.toCharArray());
    }

    public static StrMatcher stringMatcher(String str)
    {
        if(str == null || str.length() == 0)
            return NONE_MATCHER;
        else
            return new StringMatcher(str);
    }

    protected StrMatcher()
    {
    }

    public abstract int isMatch(char ac[], int i, int j, int k);

    private static final StrMatcher COMMA_MATCHER = new CharMatcher(',');
    private static final StrMatcher TAB_MATCHER = new CharMatcher('\t');
    private static final StrMatcher SPACE_MATCHER = new CharMatcher(' ');
    private static final StrMatcher SPLIT_MATCHER = new CharSetMatcher(" \t\n\r\f".toCharArray());
    private static final StrMatcher TRIM_MATCHER = new TrimMatcher();
    private static final StrMatcher SINGLE_QUOTE_MATCHER = new CharMatcher('\'');
    private static final StrMatcher DOUBLE_QUOTE_MATCHER = new CharMatcher('"');
    private static final StrMatcher QUOTE_MATCHER = new CharSetMatcher("'\"".toCharArray());
    private static final StrMatcher NONE_MATCHER = new NoMatcher();

}
