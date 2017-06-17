// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IllegalClassException.java

package org.apache.commons.lang;


public class IllegalClassException extends IllegalArgumentException
{

    public IllegalClassException(Class expected, Object actual)
    {
        super("Expected: " + safeGetClassName(expected) + ", actual: " + (actual != null ? actual.getClass().getName() : "null"));
    }

    public IllegalClassException(Class expected, Class actual)
    {
        super("Expected: " + safeGetClassName(expected) + ", actual: " + safeGetClassName(actual));
    }

    public IllegalClassException(String message)
    {
        super(message);
    }

    private static final String safeGetClassName(Class cls)
    {
        return cls != null ? cls.getName() : null;
    }

    private static final long serialVersionUID = 0x6fe67fae48e1eda3L;
}
