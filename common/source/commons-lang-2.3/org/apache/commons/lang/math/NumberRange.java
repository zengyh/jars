// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NumberRange.java

package org.apache.commons.lang.math;

import java.io.Serializable;

// Referenced classes of package org.apache.commons.lang.math:
//            Range

public final class NumberRange extends Range
    implements Serializable
{

    public NumberRange(Number num)
    {
        hashCode = 0;
        toString = null;
        if(num == null)
            throw new IllegalArgumentException("The number must not be null");
        if(!(num instanceof Comparable))
            throw new IllegalArgumentException("The number must implement Comparable");
        if((num instanceof Double) && ((Double)num).isNaN())
            throw new IllegalArgumentException("The number must not be NaN");
        if((num instanceof Float) && ((Float)num).isNaN())
        {
            throw new IllegalArgumentException("The number must not be NaN");
        } else
        {
            min = num;
            max = num;
            return;
        }
    }

    public NumberRange(Number num1, Number num2)
    {
        hashCode = 0;
        toString = null;
        if(num1 == null || num2 == null)
            throw new IllegalArgumentException("The numbers must not be null");
        if(num1.getClass() != num2.getClass())
            throw new IllegalArgumentException("The numbers must be of the same type");
        if(!(num1 instanceof Comparable))
            throw new IllegalArgumentException("The numbers must implement Comparable");
        if(num1 instanceof Double)
        {
            if(((Double)num1).isNaN() || ((Double)num2).isNaN())
                throw new IllegalArgumentException("The number must not be NaN");
        } else
        if((num1 instanceof Float) && (((Float)num1).isNaN() || ((Float)num2).isNaN()))
            throw new IllegalArgumentException("The number must not be NaN");
        int compare = ((Comparable)num1).compareTo(num2);
        if(compare == 0)
        {
            min = num1;
            max = num1;
        } else
        if(compare > 0)
        {
            min = num2;
            max = num1;
        } else
        {
            min = num1;
            max = num2;
        }
    }

    public Number getMinimumNumber()
    {
        return min;
    }

    public Number getMaximumNumber()
    {
        return max;
    }

    public boolean containsNumber(Number number)
    {
        if(number == null)
            return false;
        if(number.getClass() != min.getClass())
        {
            throw new IllegalArgumentException("The number must be of the same type as the range numbers");
        } else
        {
            int compareMin = ((Comparable)min).compareTo(number);
            int compareMax = ((Comparable)max).compareTo(number);
            return compareMin <= 0 && compareMax >= 0;
        }
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
        if(hashCode == 0)
        {
            hashCode = 17;
            hashCode = 37 * hashCode + getClass().hashCode();
            hashCode = 37 * hashCode + min.hashCode();
            hashCode = 37 * hashCode + max.hashCode();
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

    private static final long serialVersionUID = 0x4158bbcfe9e6L;
    private final Number min;
    private final Number max;
    private transient int hashCode;
    private transient String toString;
}
