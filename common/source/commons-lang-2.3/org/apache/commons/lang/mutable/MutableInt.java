// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MutableInt.java

package org.apache.commons.lang.mutable;


// Referenced classes of package org.apache.commons.lang.mutable:
//            Mutable

public class MutableInt extends Number
    implements Comparable, Mutable
{

    public MutableInt()
    {
    }

    public MutableInt(int value)
    {
        this.value = value;
    }

    public MutableInt(Number value)
    {
        this.value = value.intValue();
    }

    public Object getValue()
    {
        return new Integer(value);
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public void setValue(Object value)
    {
        setValue(((Number)value).intValue());
    }

    public void increment()
    {
        value++;
    }

    public void decrement()
    {
        value--;
    }

    public void add(int operand)
    {
        value += operand;
    }

    public void add(Number operand)
    {
        value += operand.intValue();
    }

    public void subtract(int operand)
    {
        value -= operand;
    }

    public void subtract(Number operand)
    {
        value -= operand.intValue();
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

    public Integer toInteger()
    {
        return new Integer(intValue());
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof MutableInt)
            return value == ((MutableInt)obj).intValue();
        else
            return false;
    }

    public int hashCode()
    {
        return value;
    }

    public int compareTo(Object obj)
    {
        MutableInt other = (MutableInt)obj;
        int anotherVal = other.value;
        return value >= anotherVal ? ((int) (value != anotherVal ? 1 : 0)) : -1;
    }

    public String toString()
    {
        return String.valueOf(value);
    }

    private static final long serialVersionUID = 0x77401786b8L;
    private int value;
}
