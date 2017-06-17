// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FloatRange.java

package org.apache.commons.lang.math;

import java.io.Serializable;

// Referenced classes of package org.apache.commons.lang.math:
//            Range

public final class FloatRange extends Range
    implements Serializable
{

    public FloatRange(float number)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(Float.isNaN(number))
        {
            throw new IllegalArgumentException("The number must not be NaN");
        } else
        {
            min = number;
            max = number;
            return;
        }
    }

    public FloatRange(Number number)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(number == null)
            throw new IllegalArgumentException("The number must not be null");
        min = number.floatValue();
        max = number.floatValue();
        if(Float.isNaN(min) || Float.isNaN(max))
            throw new IllegalArgumentException("The number must not be NaN");
        if(number instanceof Float)
        {
            minObject = (Float)number;
            maxObject = (Float)number;
        }
    }

    public FloatRange(float number1, float number2)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(Float.isNaN(number1) || Float.isNaN(number2))
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

    public FloatRange(Number number1, Number number2)
    {
        minObject = null;
        maxObject = null;
        hashCode = 0;
        toString = null;
        if(number1 == null || number2 == null)
            throw new IllegalArgumentException("The numbers must not be null");
        float number1val = number1.floatValue();
        float number2val = number2.floatValue();
        if(Float.isNaN(number1val) || Float.isNaN(number2val))
            throw new IllegalArgumentException("The numbers must not be NaN");
        if(number2val < number1val)
        {
            min = number2val;
            max = number1val;
            if(number2 instanceof Float)
                minObject = (Float)number2;
            if(number1 instanceof Float)
                maxObject = (Float)number1;
        } else
        {
            min = number1val;
            max = number2val;
            if(number1 instanceof Float)
                minObject = (Float)number1;
            if(number2 instanceof Float)
                maxObject = (Float)number2;
        }
    }

    public Number getMinimumNumber()
    {
        if(minObject == null)
            minObject = new Float(min);
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
        return (double)min;
    }

    public float getMinimumFloat()
    {
        return min;
    }

    public Number getMaximumNumber()
    {
        if(maxObject == null)
            maxObject = new Float(max);
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
        return (double)max;
    }

    public float getMaximumFloat()
    {
        return max;
    }

    public boolean containsNumber(Number number)
    {
        if(number == null)
            return false;
        else
            return containsFloat(number.floatValue());
    }

    public boolean containsFloat(float value)
    {
        return value >= min && value <= max;
    }

    public boolean containsRange(Range range)
    {
        if(range == null)
            return false;
        else
            return containsFloat(range.getMinimumFloat()) && containsFloat(range.getMaximumFloat());
    }

    public boolean overlapsRange(Range range)
    {
        if(range == null)
            return false;
        else
            return range.containsFloat(min) || range.containsFloat(max) || containsFloat(range.getMinimumFloat());
    }

    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(!(obj instanceof FloatRange))
        {
            return false;
        } else
        {
            FloatRange range = (FloatRange)obj;
            return Float.floatToIntBits(min) == Float.floatToIntBits(range.min) && Float.floatToIntBits(max) == Float.floatToIntBits(range.max);
        }
    }

    public int hashCode()
    {
        if(hashCode == 0)
        {
            hashCode = 17;
            hashCode = 37 * hashCode + getClass().hashCode();
            hashCode = 37 * hashCode + Float.floatToIntBits(min);
            hashCode = 37 * hashCode + Float.floatToIntBits(max);
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

    private static final long serialVersionUID = 0x4158bbcfea0eL;
    private final float min;
    private final float max;
    private transient Float minObject;
    private transient Float maxObject;
    private transient int hashCode;
    private transient String toString;
}
