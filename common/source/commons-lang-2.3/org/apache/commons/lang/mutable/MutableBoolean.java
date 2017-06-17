// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MutableBoolean.java

package org.apache.commons.lang.mutable;

import java.io.Serializable;
import org.apache.commons.lang.BooleanUtils;

// Referenced classes of package org.apache.commons.lang.mutable:
//            Mutable

public class MutableBoolean
    implements Mutable, Serializable, Comparable
{

    public MutableBoolean()
    {
    }

    public MutableBoolean(boolean value)
    {
        this.value = value;
    }

    public MutableBoolean(Boolean value)
    {
        this.value = value.booleanValue();
    }

    public boolean booleanValue()
    {
        return value;
    }

    public int compareTo(Object obj)
    {
        MutableBoolean other = (MutableBoolean)obj;
        boolean anotherVal = other.value;
        return value != anotherVal ? value ? 1 : -1 : 0;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof MutableBoolean)
            return value == ((MutableBoolean)obj).booleanValue();
        else
            return false;
    }

    public Object getValue()
    {
        return BooleanUtils.toBooleanObject(value);
    }

    public int hashCode()
    {
        return value ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode();
    }

    public void setValue(boolean value)
    {
        this.value = value;
    }

    public void setValue(Object value)
    {
        setValue(((Boolean)value).booleanValue());
    }

    public String toString()
    {
        return String.valueOf(value);
    }

    private static final long serialVersionUID = 0xbcf5ce5a3a90e379L;
    private boolean value;
}
