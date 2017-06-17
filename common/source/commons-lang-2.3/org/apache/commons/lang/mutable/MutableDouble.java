// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MutableDouble.java

package org.apache.commons.lang.mutable;

import org.apache.commons.lang.math.NumberUtils;

// Referenced classes of package org.apache.commons.lang.mutable:
//            Mutable

public class MutableDouble extends Number
    implements Comparable, Mutable
{

    public MutableDouble()
    {
    }

    public MutableDouble(double value)
    {
        this.value = value;
    }

    public MutableDouble(Number value)
    {
        this.value = value.doubleValue();
    }

    public Object getValue()
    {
        return new Double(value);
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    public void setValue(Object value)
    {
        setValue(((Number)value).doubleValue());
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
        return (float)value;
    }

    public double doubleValue()
    {
        return value;
    }

    public boolean isNaN()
    {
        return Double.isNaN(value);
    }

    public boolean isInfinite()
    {
        return Double.isInfinite(value);
    }

    public Double toDouble()
    {
        return new Double(doubleValue());
    }

    public void increment()
    {
        value++;
    }

    public void decrement()
    {
        value--;
    }

    public void add(double operand)
    {
        value += operand;
    }

    public void add(Number operand)
    {
        value += operand.doubleValue();
    }

    public void subtract(double operand)
    {
        value -= operand;
    }

    public void subtract(Number operand)
    {
        value -= operand.doubleValue();
    }

    public boolean equals(Object obj)
    {
        return (obj instanceof MutableDouble) && Double.doubleToLongBits(((MutableDouble)obj).value) == Double.doubleToLongBits(value);
    }

    public int hashCode()
    {
        long bits = Double.doubleToLongBits(value);
        return (int)(bits ^ bits >>> 32);
    }

    public int compareTo(Object obj)
    {
        MutableDouble other = (MutableDouble)obj;
        double anotherVal = other.value;
        return NumberUtils.compare(value, anotherVal);
    }

    public String toString()
    {
        return String.valueOf(value);
    }

    private static final long serialVersionUID = 0x5e9a330cL;
    private double value;
}
