// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharSetUtils.java

package org.apache.commons.lang;


// Referenced classes of package org.apache.commons.lang:
//            CharSet, StringUtils, ArrayUtils

public class CharSetUtils
{

    public CharSetUtils()
    {
    }

    /**
     * @deprecated Method evaluateSet is deprecated
     */

    public static CharSet evaluateSet(String set[])
    {
        if(set == null)
            return null;
        else
            return new CharSet(set);
    }

    public static String squeeze(String str, String set)
    {
        if(StringUtils.isEmpty(str) || StringUtils.isEmpty(set))
        {
            return str;
        } else
        {
            String strs[] = new String[1];
            strs[0] = set;
            return squeeze(str, strs);
        }
    }

    public static String squeeze(String str, String set[])
    {
        if(StringUtils.isEmpty(str) || ArrayUtils.isEmpty(set))
            return str;
        CharSet chars = evaluateSet(set);
        StringBuffer buffer = new StringBuffer(str.length());
        char chrs[] = str.toCharArray();
        int sz = chrs.length;
        char lastChar = ' ';
        char ch = ' ';
        for(int i = 0; i < sz; i++)
        {
            ch = chrs[i];
            if(!chars.contains(ch) || ch != lastChar || i == 0)
            {
                buffer.append(ch);
                lastChar = ch;
            }
        }

        return buffer.toString();
    }

    public static int count(String str, String set)
    {
        if(StringUtils.isEmpty(str) || StringUtils.isEmpty(set))
        {
            return 0;
        } else
        {
            String strs[] = new String[1];
            strs[0] = set;
            return count(str, strs);
        }
    }

    public static int count(String str, String set[])
    {
        if(StringUtils.isEmpty(str) || ArrayUtils.isEmpty(set))
            return 0;
        CharSet chars = evaluateSet(set);
        int count = 0;
        char chrs[] = str.toCharArray();
        int sz = chrs.length;
        for(int i = 0; i < sz; i++)
            if(chars.contains(chrs[i]))
                count++;

        return count;
    }

    public static String keep(String str, String set)
    {
        if(str == null)
            return null;
        if(str.length() == 0 || StringUtils.isEmpty(set))
        {
            return "";
        } else
        {
            String strs[] = new String[1];
            strs[0] = set;
            return keep(str, strs);
        }
    }

    public static String keep(String str, String set[])
    {
        if(str == null)
            return null;
        if(str.length() == 0 || ArrayUtils.isEmpty(set))
            return "";
        else
            return modify(str, set, true);
    }

    public static String delete(String str, String set)
    {
        if(StringUtils.isEmpty(str) || StringUtils.isEmpty(set))
        {
            return str;
        } else
        {
            String strs[] = new String[1];
            strs[0] = set;
            return delete(str, strs);
        }
    }

    public static String delete(String str, String set[])
    {
        if(StringUtils.isEmpty(str) || ArrayUtils.isEmpty(set))
            return str;
        else
            return modify(str, set, false);
    }

    private static String modify(String str, String set[], boolean expect)
    {
        CharSet chars = evaluateSet(set);
        StringBuffer buffer = new StringBuffer(str.length());
        char chrs[] = str.toCharArray();
        int sz = chrs.length;
        for(int i = 0; i < sz; i++)
            if(chars.contains(chrs[i]) == expect)
                buffer.append(chrs[i]);

        return buffer.toString();
    }

    /**
     * @deprecated Method translate is deprecated
     */

    public static String translate(String str, String searchChars, String replaceChars)
    {
        if(StringUtils.isEmpty(str))
            return str;
        StringBuffer buffer = new StringBuffer(str.length());
        char chrs[] = str.toCharArray();
        char withChrs[] = replaceChars.toCharArray();
        int sz = chrs.length;
        int withMax = replaceChars.length() - 1;
        for(int i = 0; i < sz; i++)
        {
            int idx = searchChars.indexOf(chrs[i]);
            if(idx != -1)
            {
                if(idx > withMax)
                    idx = withMax;
                buffer.append(withChrs[idx]);
            } else
            {
                buffer.append(chrs[i]);
            }
        }

        return buffer.toString();
    }
}
