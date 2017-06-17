// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RandomStringUtils.java

package org.apache.commons.lang;

import java.util.Random;

public class RandomStringUtils
{

    public RandomStringUtils()
    {
    }

    public static String random(int count)
    {
        return random(count, false, false);
    }

    public static String randomAscii(int count)
    {
        return random(count, 32, 127, false, false);
    }

    public static String randomAlphabetic(int count)
    {
        return random(count, true, false);
    }

    public static String randomAlphanumeric(int count)
    {
        return random(count, true, true);
    }

    public static String randomNumeric(int count)
    {
        return random(count, false, true);
    }

    public static String random(int count, boolean letters, boolean numbers)
    {
        return random(count, 0, 0, letters, numbers);
    }

    public static String random(int count, int start, int end, boolean letters, boolean numbers)
    {
        return random(count, start, end, letters, numbers, null, RANDOM);
    }

    public static String random(int count, int start, int end, boolean letters, boolean numbers, char chars[])
    {
        return random(count, start, end, letters, numbers, chars, RANDOM);
    }

    public static String random(int count, int start, int end, boolean letters, boolean numbers, char chars[], Random random)
    {
        if(count == 0)
            return "";
        if(count < 0)
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        if(start == 0 && end == 0)
        {
            end = 123;
            start = 32;
            if(!letters && !numbers)
            {
                start = 0;
                end = 0x7fffffff;
            }
        }
        char buffer[] = new char[count];
        int gap = end - start;
        while(count-- != 0) 
        {
            char ch;
            if(chars == null)
                ch = (char)(random.nextInt(gap) + start);
            else
                ch = chars[random.nextInt(gap) + start];
            if(letters && Character.isLetter(ch) || numbers && Character.isDigit(ch) || !letters && !numbers)
            {
                if(ch >= '\uDC00' && ch <= '\uDFFF')
                {
                    if(count == 0)
                    {
                        count++;
                    } else
                    {
                        buffer[count] = ch;
                        count--;
                        buffer[count] = (char)(55296 + random.nextInt(128));
                    }
                } else
                if(ch >= '\uD800' && ch <= '\uDB7F')
                {
                    if(count == 0)
                    {
                        count++;
                    } else
                    {
                        buffer[count] = (char)(56320 + random.nextInt(128));
                        count--;
                        buffer[count] = ch;
                    }
                } else
                if(ch >= '\uDB80' && ch <= '\uDBFF')
                    count++;
                else
                    buffer[count] = ch;
            } else
            {
                count++;
            }
        }
        return new String(buffer);
    }

    public static String random(int count, String chars)
    {
        if(chars == null)
            return random(count, 0, 0, false, false, null, RANDOM);
        else
            return random(count, chars.toCharArray());
    }

    public static String random(int count, char chars[])
    {
        if(chars == null)
            return random(count, 0, 0, false, false, null, RANDOM);
        else
            return random(count, 0, chars.length, false, false, chars, RANDOM);
    }

    private static final Random RANDOM = new Random();

}
