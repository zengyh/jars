// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MutableFloat.java

package org.apache.commons.lang.mutable;

import org.apache.commons.lang.math.NumberUtils;

// Referenced classes of package org.apache.commons.lang.mutable:
//            Mutable

public class MutableFloat extends Number
    implements Comparable, Mutable
{

    public MutableFloat()
    {
    }

    public MutableFloat(float value)
    {
        this.value = value;
    }

    public MutableFloat(Number value)
    {
        this.value = value.floatValue();
    }

    public Object getValue()
    {
        return new Float(value);
    }

    public void setValue(float value)
    {
        this.value = value;
    }

    public void setValue(Object value)
    {
        setValue(((Number)value).floatValue());
    }

    public void increment()
    {
        value++;
    }

    public void decrement()
    {
        value--;
    }

    public void add(float operand)
    {
        value += operand;
    }

    public void add(Number operand)
    {
        value += operand.floatValue();
    }

    public void subtract(float operand)
    {
        value -= operand;
    }

    public void subtract(Number operand)
    {
        value -= operand.floatValue();
    }

    public int intValue()
    {
        return (int)value;
    }

    public long longValue()
    {
        return (long)value;
    }

    public float floatValue()
    {
        return value;
    }

    public double doubleValue()
    {
        return (double)value;
    }

    public boolean isNaN()
    {
        return Float.isNaN(value);
    }

    public boolean isInfinite()
    {
        return Float.isInfinite(value);
    }

    public Float toFloat()
    {
        return new Float(floatValue());
    }

    public boolean equals(Object obj)
    {
        return (obj instanceof MutableFloat) && Float.floatToIntBits(((MutableFloat)obj).value) == Float.floatToIntBits(value);
    }

    public int hashCode()
    {
        return Float.floatToIntBits(value);
    }

    public int compareTo(Object obj)
    {
        MutableFloat other = (MutableFloat)obj;
        float anotherVal = other.value;
        return NumberUtils.compare(value, anotherVal);
    }

    public String toString()
    {
        return String.valueOf(value);
    }

    private static final long serialVersionUID = 0x158f131a2L;
    private float value;
}
