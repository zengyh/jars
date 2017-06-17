// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MutableLong.java

package org.apache.commons.lang.mutable;


// Referenced classes of package org.apache.commons.lang.mutable:
//            Mutable

public class MutableLong extends Number
    implements Comparable, Mutable
{

    public MutableLong()
    {
    }

    public MutableLong(long value)
    {
        this.value = value;
    }

    public MutableLong(Number value)
    {
        this.value = value.longValue();
    }

    public Object getValue()
    {
        return new Long(value);
    }

    public void setValue(long value)
    {
        this.value = value;
    }

    public void setValue(Object value)
    {
        setValue(((Number)value).longValue());
    }

    public void increment()
    {
        value++;
    }

    public void decrement()
    {
        value--;
    }

    public void add(long operand)
    {
        value += operand;
    }

    public void add(Number operand)
    {
        value += operand.longValue();
    }

    public void subtract(long operand)
    {
        value -= operand;
    }

    public void subtract(Number operand)
    {
        value -= operand.longValue();
    }

    public int intValue()
    {
        return (int)value;
    }

    public long longValue()
    {
        return value;
    }

    public float floatValue()
    {
        return (float)value;
    }

    public double doubleValue()
    {
        return (double)value;
    }

    public Long toLong()
    {
        return new Long(longValue());
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof MutableLong)
            return value == ((MutableLong)obj).longValue();
        else
            return false;
    }

    public int hashCode()
    {
        return (int)(value ^ value >>> 32);
    }

    public int compareTo(Object obj)
    {
        MutableLong other = (MutableLong)obj;
        long anotherVal = other.value;
        return value >= anotherVal ? ((int) (value != anotherVal ? 1 : 0)) : -1;
    }

    public String toString()
    {
        return String.valueOf(value);
    }

    private static final long serialVersionUID = 0xeaa4a2677L;
    private long value;
}
