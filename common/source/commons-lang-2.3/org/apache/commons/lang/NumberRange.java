// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NumberRange.java

package org.apache.commons.lang;


/**
 * @deprecated Class NumberRange is deprecated
 */

public final class NumberRange
{

    public NumberRange(Number num)
    {
        if(num == null)
        {
            throw new NullPointerException("The number must not be null");
        } else
        {
            min = num;
            max = num;
            return;
        }
    }

    public NumberRange(Number min, Number max)
    {
        if(min == null)
            throw new NullPointerException("The minimum value must not be null");
        if(max == null)
            throw new NullPointerException("The maximum value must not be null");
        if(max.doubleValue() < min.doubleValue())
        {
            this.min = this.max = min;
        } else
        {
            this.min = min;
            this.max = max;
        }
    }

    public Number getMinimum()
    {
        return min;
    }

    public Number getMaximum()
    {
        return max;
    }

    public boolean includesNumber(Number number)
    {
        if(number == null)
            return false;
        else
            return min.doubleValue() <= number.doubleValue() && max.doubleValue() >= number.doubleValue();
    }

    public boolean includesRange(NumberRange range)
    {
        if(range == null)
            return false;
        else
            return includesNumber(range.min) && includesNumber(range.max);
    }

    public boolean overlaps(NumberRange range)
    {
        if(range == null)
            return false;
        else
            return range.includesNumber(min) || range.includesNumber(max) || includesRange(range);
    }

    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(!(obj instanceof NumberRange))
        {
            return false;
        } else
        {
            NumberRange range = (NumberRange)obj;
            return min.equals(range.min) && max.equals(range.max);
        }
    }

    public int hashCode()
    {
        int result = 17;
        result = 37 * result + min.hashCode();
        result = 37 * result + max.hashCode();
        return result;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        if(min.doubleValue() < 0.0D)
            sb.append('(').append(min).append(')');
        else
            sb.append(min);
        sb.append('-');
        if(max.doubleValue() < 0.0D)
            sb.append('(').append(max).append(')');
        else
            sb.append(max);
        return sb.toString();
    }

    private final Number min;
    private final Number max;
}
