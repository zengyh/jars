// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ObjectUtils.java

package org.apache.commons.lang;

import java.io.Serializable;

public class ObjectUtils
{
    public static class Null
        implements Serializable
    {

        private Object readResolve()
        {
            return ObjectUtils.NULL;
        }

        private static final long serialVersionUID = 0x626e04ed40667ec5L;

        Null()
        {
        }
    }


    public ObjectUtils()
    {
    }

    public static Object defaultIfNull(Object object, Object defaultValue)
    {
        return object == null ? defaultValue : object;
    }

    public static boolean equals(Object object1, Object object2)
    {
        if(object1 == object2)
            return true;
        if(object1 == null || object2 == null)
            return false;
        else
            return object1.equals(object2);
    }

    public static int hashCode(Object obj)
    {
        return obj != null ? obj.hashCode() : 0;
    }

    public static String identityToString(Object object)
    {
        if(object == null)
            return null;
        else
            return appendIdentityToString(null, object).toString();
    }

    public static StringBuffer appendIdentityToString(StringBuffer buffer, Object object)
    {
        if(object == null)
            return null;
        if(buffer == null)
            buffer = new StringBuffer();
        return buffer.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
    }

    public static String toString(Object obj)
    {
        return obj != null ? obj.toString() : "";
    }

    public static String toString(Object obj, String nullStr)
    {
        return obj != null ? obj.toString() : nullStr;
    }

    public static Object min(Comparable c1, Comparable c2)
    {
        if(c1 != null && c2 != null)
            return c1.compareTo(c2) >= 1 ? c2 : c1;
        else
            return c1 == null ? c2 : c1;
    }

    public static Object max(Comparable c1, Comparable c2)
    {
        if(c1 != null && c2 != null)
            return c1.compareTo(c2) < 0 ? c2 : c1;
        else
            return c1 == null ? c2 : c1;
    }

    public static final Null NULL = new Null();

}
