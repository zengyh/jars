// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LongRange.java

package org.apache.commons.lang.math;

import java.io.Serializable;

// Referenced classes of package org.apache.commons.lang.math:
//            Range

public final class LongRange extends Range
    implements Serializable
{

    public LongRange(long number)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        min = number;
        max = number;
    }

    public LongRange(Number number)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(number == null)
            throw new IllegalArgumentException("The number must not be null");
        min = number.longValue();
        max = number.longValue();
        if(number instanceof Long)
        {
            minObject = (Long)number;
            maxObject = (Long)number;
        }
    }

    public LongRange(long number1, long number2)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(number2 < number1)
        {
            min = number2;
            max = number1;
        } else
        {
            min = number1;
            max = number2;
        }
    }

    public LongRange(Number number1, Number number2)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(number1 == null || number2 == null)
            throw new IllegalArgumentException("The numbers must not be null");
        long number1val = number1.longValue();
        long number2val = number2.longValue();
        if(number2val < number1val)
        {
            min = number2val;
            max = number1val;
            if(number2 instanceof Long)
                minObject = (Long)number2;
            if(number1 instanceof Long)
                maxObject = (Long)number1;
        } else
        {
            min = number1val;
            max = number2val;
            if(number1 instanceof Long)
                minObject = (Long)number1;
            if(number2 instanceof Long)
                maxObject = (Long)number2;
        }
    }

    public Number getMinimumNumber()
    {
        if(minObject == null)
            minObject = new Long(min);
        return minObject;
    }

    public long getMinimumLong()
    {
        return min;
    }

    public int getMinimumInteger()
    {
        return (int)min;
    }

    public double getMinimumDouble()
    {
        return (double)min;
    }

    public float getMinimumFloat()
    {
        return (float)min;
    }

    public Number getMaximumNumber()
    {
        if(maxObject == null)
            maxObject = new Long(max);
        return maxObject;
    }

    public long getMaximumLong()
    {
        return max;
    }

    public int getMaximumInteger()
    {
        return (int)max;
    }

    public double getMaximumDouble()
    {
        return (double)max;
    }

    public float getMaximumFloat()
    {
        return (float)max;
    }

    public boolean containsNumber(Number number)
    {
        if(number == null)
            return false;
        else
            return containsLong(number.longValue());
    }

    public boolean containsLong(long value)
    {
        return value >= min && value <= max;
    }

    public boolean containsRange(Range range)
    {
        if(range == null)
            return false;
        else
            return containsLong(range.getMinimumLong()) && containsLong(range.getMaximumLong());
    }

    public boolean overlapsRange(Range range)
    {
        if(range == null)
            return false;
        else
            return range.containsLong(min) || range.containsLong(max) || containsLong(range.getMinimumLong());
    }

    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(!(obj instanceof LongRange))
        {
            return false;
        } else
        {
            LongRange range = (LongRange)obj;
            return min == range.min && max == range.max;
        }
    }

    public int hashCode()
    {
        if(hashCode == 0)
        {
            hashCode = 17;
            hashCode = 37 * hashCode + getClass().hashCode();
            hashCode = 37 * hashCode + (int)(min ^ min >> 32);
            hashCode = 37 * hashCode + (int)(max ^ max >> 32);
        }
        return hashCode;
    }

    public String toString()
    {
        if(toString == null)
        {
            StringBuffer buf = new StringBuffer(32);
            buf.append("Range[");
            buf.append(min);
            buf.append(',');
            buf.append(max);
            buf.append(']');
            toString = buf.toString();
        }
        return toString;
    }

    private static final long serialVersionUID = 0x4158bbcfe9f0L;
    private final long min;
    private final long max;
    private transient Long minObject;
    private transient Long maxObject;
    private transient int hashCode;
    private transient String toString;
}
