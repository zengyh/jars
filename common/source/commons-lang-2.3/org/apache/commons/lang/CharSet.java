// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharSet.java

package org.apache.commons.lang;

import java.io.Serializable;
import java.util.*;

// Referenced classes of package org.apache.commons.lang:
//            CharRange

public class CharSet
    implements Serializable
{

    public static CharSet getInstance(String setStr)
    {
        Object set = COMMON.get(setStr);
        if(set != null)
            return (CharSet)set;
        else
            return new CharSet(setStr);
    }

    protected CharSet(String setStr)
    {
        set = new HashSet();
        add(setStr);
    }

    protected CharSet(String set[])
    {
        this.set = new HashSet();
        int sz = set.length;
        for(int i = 0; i < sz; i++)
            add(set[i]);

    }

    protected void add(String str)
    {
        if(str == null)
            return;
        int len = str.length();
        for(int pos = 0; pos < len;)
        {
            int remainder = len - pos;
            if(remainder >= 4 && str.charAt(pos) == '^' && str.charAt(pos + 2) == '-')
            {
                set.add(new CharRange(str.charAt(pos + 1), str.charAt(pos + 3), true));
                pos += 4;
            } else
            if(remainder >= 3 && str.charAt(pos + 1) == '-')
            {
                set.add(new CharRange(str.charAt(pos), str.charAt(pos + 2)));
                pos += 3;
            } else
            if(remainder >= 2 && str.charAt(pos) == '^')
            {
                set.add(new CharRange(str.charAt(pos + 1), true));
                pos += 2;
            } else
            {
                set.add(new CharRange(str.charAt(pos)));
                pos++;
            }
        }

    }

    public CharRange[] getCharRanges()
    {
        return (CharRange[])set.toArray(new CharRange[set.size()]);
    }

    public boolean contains(char ch)
    {
        for(Iterator it = set.iterator(); it.hasNext();)
        {
            CharRange range = (CharRange)it.next();
            if(range.contains(ch))
                return true;
        }

        return false;
    }

    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(!(obj instanceof CharSet))
        {
            return false;
        } else
        {
            CharSet other = (CharSet)obj;
            return set.equals(other.set);
        }
    }

    public int hashCode()
    {
        return 89 + set.hashCode();
    }

    public String toString()
    {
        return set.toString();
    }

    private static final long serialVersionUID = 0x528affa5f57a3936L;
    public static final CharSet EMPTY;
    public static final CharSet ASCII_ALPHA;
    public static final CharSet ASCII_ALPHA_LOWER;
    public static final CharSet ASCII_ALPHA_UPPER;
    public static final CharSet ASCII_NUMERIC;
    protected static final Map COMMON;
    private Set set;

    static 
    {
        EMPTY = new CharSet((String)null);
        ASCII_ALPHA = new CharSet("a-zA-Z");
        ASCII_ALPHA_LOWER = new CharSet("a-z");
        ASCII_ALPHA_UPPER = new CharSet("A-Z");
        ASCII_NUMERIC = new CharSet("0-9");
        COMMON = new HashMap();
        COMMON.put(null, EMPTY);
        COMMON.put("", EMPTY);
        COMMON.put("a-zA-Z", ASCII_ALPHA);
        COMMON.put("A-Za-z", ASCII_ALPHA);
        COMMON.put("a-z", ASCII_ALPHA_LOWER);
        COMMON.put("A-Z", ASCII_ALPHA_UPPER);
        COMMON.put("0-9", ASCII_NUMERIC);
    }
}
