// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DoubleRange.java

package org.apache.commons.lang.math;

import java.io.Serializable;

// Referenced classes of package org.apache.commons.lang.math:
//            Range

public final class DoubleRange extends Range
    implements Serializable
{

    public DoubleRange(double number)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(Double.isNaN(number))
        {
            throw new IllegalArgumentException("The number must not be NaN");
        } else
        {
            min = number;
            max = number;
            return;
        }
    }

    public DoubleRange(Number number)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(number == null)
            throw new IllegalArgumentException("The number must not be null");
        min = number.doubleValue();
        max = number.doubleValue();
        if(Double.isNaN(min) || Double.isNaN(max))
            throw new IllegalArgumentException("The number must not be NaN");
        if(number instanceof Double)
        {
            minObject = (Double)number;
            maxObject = (Double)number;
        }
    }

    public DoubleRange(double number1, double number2)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(Double.isNaN(number1) || Double.isNaN(number2))
            throw new IllegalArgumentException("The numbers must not be NaN");
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

    public DoubleRange(Number number1, Number number2)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(number1 == null || number2 == null)
            throw new IllegalArgumentException("The numbers must not be null");
        double number1val = number1.doubleValue();
        double number2val = number2.doubleValue();
        if(Double.isNaN(number1val) || Double.isNaN(number2val))
            throw new IllegalArgumentException("The numbers must not be NaN");
        if(number2val < number1val)
        {
            min = number2val;
            max = number1val;
            if(number2 instanceof Double)
                minObject = (Double)number2;
            if(number1 instanceof Double)
                maxObject = (Double)number1;
        } else
        {
            min = number1val;
            max = number2val;
            if(number1 instanceof Double)
                minObject = (Double)number1;
            if(number2 instanceof Double)
                maxObject = (Double)number2;
        }
    }

    public Number getMinimumNumber()
    {
        if(minObject == null)
            minObject = new Double(min);
        return minObject;
    }

    public long getMinimumLong()
    {
        return (long)min;
    }

    public int getMinimumInteger()
    {
        return (int)min;
    }

    public double getMinimumDouble()
    {
        return min;
    }

    public float getMinimumFloat()
    {
        return (float)min;
    }

    public Number getMaximumNumber()
    {
        if(maxObject == null)
            maxObject = new Double(max);
        return maxObject;
    }

    public long getMaximumLong()
    {
        return (long)max;
    }

    public int getMaximumInteger()
    {
        return (int)max;
    }

    public double getMaximumDouble()
    {
        return max;
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
            return containsDouble(number.doubleValue());
    }

    public boolean containsDouble(double value)
    {
        return value >= min && value <= max;
    }

    public boolean containsRange(Range range)
    {
        if(range == null)
            return false;
        else
            return containsDouble(range.getMinimumDouble()) && containsDouble(range.getMaximumDouble());
    }

    public boolean overlapsRange(Range range)
    {
        if(range == null)
            return false;
        else
            return range.containsDouble(min) || range.containsDouble(max) || containsDouble(range.getMinimumDouble());
    }

    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(!(obj instanceof DoubleRange))
        {
            return false;
        } else
        {
            DoubleRange range = (DoubleRange)obj;
            return Double.doubleToLongBits(min) == Double.doubleToLongBits(range.min) && Double.doubleToLongBits(max) == Double.doubleToLongBits(range.max);
        }
    }

    public int hashCode()
    {
        if(hashCode == 0)
        {
            hashCode = 17;
            hashCode = 37 * hashCode + getClass().hashCode();
            long lng = Double.doubleToLongBits(min);
            hashCode = 37 * hashCode + (int)(lng ^ lng >> 32);
            lng = Double.doubleToLongBits(max);
            hashCode = 37 * hashCode + (int)(lng ^ lng >> 32);
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

    private static final long serialVersionUID = 0x4158bbcfea04L;
    private final double min;
    private final double max;
    private transient Double minObject;
    private transient Double maxObject;
    private transient int hashCode;
    private transient String toString;
}
