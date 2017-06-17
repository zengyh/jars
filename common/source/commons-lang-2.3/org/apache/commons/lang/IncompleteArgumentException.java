// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IncompleteArgumentException.java

package org.apache.commons.lang;

import java.util.Arrays;

public class IncompleteArgumentException extends IllegalArgumentException
{

    public IncompleteArgumentException(String argName)
    {
        super(argName + " is incomplete.");
    }

    public IncompleteArgumentException(String argName, String items[])
    {
        super(argName + " is missing the following items: " + safeArrayToString(items));
    }

    private static final String safeArrayToString(Object array[])
    {
        return array != null ? Arrays.asList(array).toString() : null;
    }

    private static final long serialVersionUID = 0x44c0d4a6f2e17952L;
}
