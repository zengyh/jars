// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NullArgumentException.java

package org.apache.commons.lang;


public class NullArgumentException extends IllegalArgumentException
{

    public NullArgumentException(String argName)
    {
        super((argName != null ? argName : "Argument") + " must not be null.");
    }

    private static final long serialVersionUID = 0x104c2a697aac8ad7L;
}
