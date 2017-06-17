// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MutableObject.java

package org.apache.commons.lang.mutable;

import java.io.Serializable;

// Referenced classes of package org.apache.commons.lang.mutable:
//            Mutable

public class MutableObject
    implements Mutable, Serializable
{

    public MutableObject()
    {
    }

    public MutableObject(Object value)
    {
        this.value = value;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof MutableObject)
        {
            Object other = ((MutableObject)obj).value;
            return value == other || value != null && value.equals(other);
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return value != null ? value.hashCode() : 0;
    }

    public String toString()
    {
        return value != null ? value.toString() : "null";
    }

    private static final long serialVersionUID = 0x14146a94f5L;
    private Object value;
}
