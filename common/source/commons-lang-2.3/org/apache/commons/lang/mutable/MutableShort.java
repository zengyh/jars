// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MutableShort.java

package org.apache.commons.lang.mutable;


// Referenced classes of package org.apache.commons.lang.mutable:
//            Mutable

public class MutableShort extends Number
    implements Comparable, Mutable
{

    public MutableShort()
    {
    }

    public MutableShort(short value)
    {
        this.value = value;
    }

    public MutableShort(Number value)
    {
        this.value = value.shortValue();
    }

    public Object getValue()
    {
        return new Short(value);
    }

    public void setValue(short value)
    {
        this.value = value;
    }

    public void setValue(Object value)
    {
        setValue(((Number)value).shortValue());
    }

    public void increment()
    {
        value++;
    }

    public void decrement()
    {
        value--;
    }

    public void add(short operand)
    {
        value += operand;
    }

    public void add(Number operand)
    {
        value += operand.shortValue();
    }

    public void subtract(short operand)
    {
        value -= operand;
    }

    public void subtract(Number operand)
    {
        value -= operand.shortValue();
    }

    public short shortValue()
    {
        return value;
    }

    public int intValue()
    {
        return value;
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
        return (double)value;
    }

    public Short toShort()
    {
        return new Short(shortValue());
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof MutableShort)
            return value == ((MutableShort)obj).shortValue();
        else
            return false;
    }

    public int hashCode()
    {
        return value;
    }

    public int compareTo(Object obj)
    {
        MutableShort other = (MutableShort)obj;
        short anotherVal = other.value;
        return value >= anotherVal ? ((int) (value != anotherVal ? 1 : 0)) : -1;
    }

    public String toString()
    {
        return String.valueOf(value);
    }

    private static final long serialVersionUID = 0xffffffff80b267c1L;
    private short value;
}
