// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Validate.java

package org.apache.commons.lang;

import java.util.*;

public class Validate
{

    public Validate()
    {
    }

    public static void isTrue(boolean expression, String message, Object value)
    {
        if(!expression)
            throw new IllegalArgumentException(message + value);
        else
            return;
    }

    public static void isTrue(boolean expression, String message, long value)
    {
        if(!expression)
            throw new IllegalArgumentException(message + value);
        else
            return;
    }

    public static void isTrue(boolean expression, String message, double value)
    {
        if(!expression)
            throw new IllegalArgumentException(message + value);
        else
            return;
    }

    public static void isTrue(boolean expression, String message)
    {
        if(!expression)
            throw new IllegalArgumentException(message);
        else
            return;
    }

    public static void isTrue(boolean expression)
    {
        if(!expression)
            throw new IllegalArgumentException("The validated expression is false");
        else
            return;
    }

    public static void notNull(Object object, String message)
    {
        if(object == null)
            throw new IllegalArgumentException(message);
        else
            return;
    }

    public static void notNull(Object object)
    {
        if(object == null)
            throw new IllegalArgumentException("The validated object is null");
        else
            return;
    }

    public static void notEmpty(Object array[], String message)
    {
        if(array == null || array.length == 0)
            throw new IllegalArgumentException(message);
        else
            return;
    }

    public static void notEmpty(Object array[])
    {
        if(array == null || array.length == 0)
            throw new IllegalArgumentException("The validated array is empty");
        else
            return;
    }

    public static void notEmpty(Collection collection, String message)
    {
        if(collection == null || collection.size() == 0)
            throw new IllegalArgumentException(message);
        else
            return;
    }

    public static void notEmpty(Collection collection)
    {
        if(collection == null || collection.size() == 0)
            throw new IllegalArgumentException("The validated collection is empty");
        else
            return;
    }

    public static void notEmpty(Map map, String message)
    {
        if(map == null || map.size() == 0)
            throw new IllegalArgumentException(message);
        else
            return;
    }

    public static void notEmpty(Map map)
    {
        if(map == null || map.size() == 0)
            throw new IllegalArgumentException("The validated map is empty");
        else
            return;
    }

    public static void notEmpty(String string, String message)
    {
        if(string == null || string.length() == 0)
            throw new IllegalArgumentException(message);
        else
            return;
    }

    public static void notEmpty(String string)
    {
        if(string == null || string.length() == 0)
            throw new IllegalArgumentException("The validated string is empty");
        else
            return;
    }

    public static void noNullElements(Object array[], String message)
    {
        notNull(((Object) (array)));
        for(int i = 0; i < array.length; i++)
            if(array[i] == null)
                throw new IllegalArgumentException(message);

    }

    public static void noNullElements(Object array[])
    {
        notNull(((Object) (array)));
        for(int i = 0; i < array.length; i++)
            if(array[i] == null)
                throw new IllegalArgumentException("The validated array contains null element at index: " + i);

    }

    public static void noNullElements(Collection collection, String message)
    {
        notNull(collection);
        for(Iterator it = collection.iterator(); it.hasNext();)
            if(it.next() == null)
                throw new IllegalArgumentException(message);

    }

    public static void noNullElements(Collection collection)
    {
        notNull(collection);
        int i = 0;
        for(Iterator it = collection.iterator(); it.hasNext();)
        {
            if(it.next() == null)
                throw new IllegalArgumentException("The validated collection contains null element at index: " + i);
            i++;
        }

    }

    public static void allElementsOfType(Collection collection, Class clazz, String message)
    {
        notNull(collection);
        notNull(clazz);
        for(Iterator it = collection.iterator(); it.hasNext();)
            if(!clazz.isInstance(it.next()))
                throw new IllegalArgumentException(message);

    }

    public static void allElementsOfType(Collection collection, Class clazz)
    {
        notNull(collection);
        notNull(clazz);
        int i = 0;
        for(Iterator it = collection.iterator(); it.hasNext();)
        {
            if(!clazz.isInstance(it.next()))
                throw new IllegalArgumentException("The validated collection contains an element not of type " + clazz.getName() + " at index: " + i);
            i++;
        }

    }
}
