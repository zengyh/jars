// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnumUtils.java

package org.apache.commons.lang.enums;

import java.util.*;

// Referenced classes of package org.apache.commons.lang.enums:
//            ValuedEnum, Enum

public class EnumUtils
{

    public EnumUtils()
    {
    }

    public static Enum getEnum(Class enumClass, String name)
    {
        return Enum.getEnum(enumClass, name);
    }

    public static ValuedEnum getEnum(Class enumClass, int value)
    {
        return (ValuedEnum)ValuedEnum.getEnum(enumClass, value);
    }

    public static Map getEnumMap(Class enumClass)
    {
        return Enum.getEnumMap(enumClass);
    }

    public static List getEnumList(Class enumClass)
    {
        return Enum.getEnumList(enumClass);
    }

    public static Iterator iterator(Class enumClass)
    {
        return Enum.getEnumList(enumClass).iterator();
    }
}
