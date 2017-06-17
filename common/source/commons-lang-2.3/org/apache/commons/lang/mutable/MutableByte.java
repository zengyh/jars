// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MutableByte.java

package org.apache.commons.lang.mutable;


// Referenced classes of package org.apache.commons.lang.mutable:
//            Mutable

public class MutableByte extends Number
    implements Comparable, Mutable
{

    public MutableByte()
    {
    }

    public MutableByte(byte value)
    {
        this.value = value;
    }

    public MutableByte(Number value)
    {
        this.value = value.byteValue();
    }

    public Object getValue()
    {
        return new Byte(value);
    }

    public void setValue(byte value)
    {
        this.value = value;
    }

    public void setValue(Object value)
    {
        setValue(((Number)value).byteValue());
    }

    public byte byteValue()
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

    public Byte toByte()
    {
        return new Byte(byteValue());
    }

    public void increment()
    {
        value++;
    }

    public void decrement()
    {
        value--;
    }

    public void add(byte operand)
    {
        value += operand;
    }

    public void add(Number operand)
    {
        value += operand.byteValue();
    }

    public void subtract(byte operand)
    {
        value -= operand;
    }

    public void subtract(Number operand)
    {
        value -= operand.byteValue();
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof MutableByte)
            return value == ((MutableByte)obj).byteValue();
        else
            return false;
    }

    public int hashCode()
    {
        return value;
    }

    public int compareTo(Object obj)
    {
        MutableByte other = (MutableByte)obj;
        byte anotherVal = other.value;
        return value >= anotherVal ? ((int) (value != anotherVal ? 1 : 0)) : -1;
    }

    public String toString()
    {
        return String.valueOf(value);
    }

    private static final long serialVersionUID = 0xffffffffa17a41dfL;
    private byte value;
}
