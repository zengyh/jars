// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ValuedEnum.java

package org.apache.commons.lang.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.ClassUtils;

// Referenced classes of package org.apache.commons.lang.enums:
//            Enum

public abstract class ValuedEnum extends Enum
{

    protected ValuedEnum(String name, int value)
    {
        super(name);
        iValue = value;
    }

    protected static Enum getEnum(Class enumClass, int value)
    {
        if(enumClass == null)
            throw new IllegalArgumentException("The Enum Class must not be null");
        List list = Enum.getEnumList(enumClass);
        for(Iterator it = list.iterator(); it.hasNext();)
        {
            ValuedEnum enumeration = (ValuedEnum)it.next();
            if(enumeration.getValue() == value)
                return enumeration;
        }

        return null;
    }

    public final int getValue()
    {
        return iValue;
    }

    public int compareTo(Object other)
    {
        if(other == this)
            return 0;
        if(other.getClass() != getClass())
        {
            if(other.getClass().getName().equals(getClass().getName()))
                return iValue - getValueInOtherClassLoader(other);
            else
                throw new ClassCastException("Different enum class '" + ClassUtils.getShortClassName(other.getClass()) + "'");
        } else
        {
            return iValue - ((ValuedEnum)other).iValue;
        }
    }

    private int getValueInOtherClassLoader(Object other)
    {
        try
        {
            Method mth = other.getClass().getMethod("getValue", null);
            Integer value = (Integer)mth.invoke(other, null);
            return value.intValue();
        }
        catch(NoSuchMethodException e) { }
        catch(IllegalAccessException e) { }
        catch(InvocationTargetException e) { }
        throw new IllegalStateException("This should not happen");
    }

    public String toString()
    {
        if(super.iToString == null)
        {
            String shortName = ClassUtils.getShortClassName(getEnumClass());
            super.iToString = shortName + "[" + getName() + "=" + getValue() + "]";
        }
        return super.iToString;
    }

    private static final long serialVersionUID = 0x9d0e64a00ab435e3L;
    private final int iValue;
}
