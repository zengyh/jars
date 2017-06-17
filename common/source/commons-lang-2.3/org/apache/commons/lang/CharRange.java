// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharRange.java

package org.apache.commons.lang;

import java.io.Serializable;

public final class CharRange
    implements Serializable
{

    public CharRange(char ch)
    {
        this(ch, ch, false);
    }

    public CharRange(char ch, boolean negated)
    {
        this(ch, ch, negated);
    }

    public CharRange(char start, char end)
    {
        this(start, end, false);
    }

    public CharRange(char start, char end, boolean negated)
    {
        if(start > end)
        {
            char temp = start;
            start = end;
            end = temp;
        }
        this.start = start;
        this.end = end;
        this.negated = negated;
    }

    public char getStart()
    {
        return start;
    }

    public char getEnd()
    {
        return end;
    }

    public boolean isNegated()
    {
        return negated;
    }

    public boolean contains(char ch)
    {
        return (ch >= start && ch <= end) != negated;
    }

    public boolean contains(CharRange range)
    {
        if(range == null)
            throw new IllegalArgumentException("The Range must not be null");
        if(negated)
            if(range.negated)
                return start >= range.start && end <= range.end;
            else
                return range.end < start || range.start > end;
        if(range.negated)
            return start == 0 && end == '\uFFFF';
        else
            return start <= range.start && end >= range.end;
    }

    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(!(obj instanceof CharRange))
        {
            return false;
        } else
        {
            CharRange other = (CharRange)obj;
            return start == other.start && end == other.end && negated == other.negated;
        }
    }

    public int hashCode()
    {
        return 83 + start + 7 * end + (negated ? 1 : 0);
    }

    public String toString()
    {
        if(iToString == null)
        {
            StringBuffer buf = new StringBuffer(4);
            if(isNegated())
                buf.append('^');
            buf.append(start);
            if(start != end)
            {
                buf.append('-');
                buf.append(end);
            }
            iToString = buf.toString();
        }
        return iToString;
    }

    private static final long serialVersionUID = 0x72c597c5037807eeL;
    private final char start;
    private final char end;
    private final boolean negated;
    private transient String iToString;
}
