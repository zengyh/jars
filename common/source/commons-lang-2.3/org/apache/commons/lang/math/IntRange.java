// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IntRange.java

package org.apache.commons.lang.math;

import java.io.Serializable;

// Referenced classes of package org.apache.commons.lang.math:
//            Range

public final class IntRange extends Range
    implements Serializable
{

    public IntRange(int number)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        min = number;
        max = number;
    }

    public IntRange(Number number)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(number == null)
            throw new IllegalArgumentException("The number must not be null");
        min = number.intValue();
        max = number.intValue();
        if(number instanceof Integer)
        {
            minObject = (Integer)number;
            maxObject = (Integer)number;
        }
    }

    public IntRange(int number1, int number2)
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

    public IntRange(Number number1, Number number2)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(number1 == null || number2 == null)
            throw new IllegalArgumentException("The numbers must not be null");
        int number1val = number1.intValue();
        int number2val = number2.intValue();
        if(number2val < number1val)
        {
            min = number2val;
            max = number1val;
            if(number2 instanceof Integer)
                minObject = (Integer)number2;
            if(number1 instanceof Integer)
                maxObject = (Integer)number1;
        } else
        {
            min = number1val;
            max = number2val;
            if(number1 instanceof Integer)
                minObject = (Integer)number1;
            if(number2 instanceof Integer)
                maxObject = (Integer)number2;
        }
    }

    public Number getMinimumNumber()
    {
        if(minObject == null)
            minObject = new Integer(min);
        return minObject;
    }

    public long getMinimumLong()
    {
        return (long)min;
    }

    public int getMinimumInteger()
    {
        return min;
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
            maxObject = new Integer(max);
        return maxObject;
    }

    public long getMaximumLong()
    {
        return (long)max;
    }

    public int getMaximumInteger()
    {
        return max;
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
            return containsInteger(number.intValue());
    }

    public boolean containsInteger(int value)
    {
        return value >= min && value <= max;
    }

    public boolean containsRange(Range range)
    {
        if(range == null)
            return false;
        else
            return containsInteger(range.getMinimumInteger()) && containsInteger(range.getMaximumInteger());
    }

    public boolean overlapsRange(Range range)
    {
        if(range == null)
            return false;
        else
            return range.containsInteger(min) || range.containsInteger(max) || containsInteger(range.getMinimumInteger());
    }

    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(!(obj instanceof IntRange))
        {
            return false;
        } else
        {
            IntRange range = (IntRange)obj;
            return min == range.min && max == range.max;
        }
    }

    public int hashCode()
    {
        if(hashCode == 0)
        {
            hashCode = 17;
            hashCode = 37 * hashCode + getClass().hashCode();
            hashCode = 37 * hashCode + min;
            hashCode = 37 * hashCode + max;
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

    private static final long serialVersionUID = 0x4158bbcfe9faL;
    private final int min;
    private final int max;
    private transient Integer minObject;
    private transient Integer maxObject;
    private transient int hashCode;
    private transient String toString;
}
