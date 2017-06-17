// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OperationType.java

package com.sinitek.spirit.org.server.service;


public final class OperationType extends Enum
{

    public static OperationType[] values()
    {
        return (OperationType[])$VALUES.clone();
    }

    public static OperationType valueOf(String name)
    {
        return (OperationType)Enum.valueOf(com/sinitek/spirit/org/server/service/OperationType, name);
    }

    private OperationType(String s, int i)
    {
        super(s, i);
    }

    public static final OperationType EQUALS;
    public static final OperationType LIKE;
    public static final OperationType IN;
    public static final OperationType GREATER;
    public static final OperationType LESS;
    public static final OperationType GREATER_OR_EQUAL;
    public static final OperationType LESS_OR_EQUAL;
    private static final OperationType $VALUES[];

    static 
    {
        EQUALS = new OperationType("EQUALS", 0);
        LIKE = new OperationType("LIKE", 1);
        IN = new OperationType("IN", 2);
        GREATER = new OperationType("GREATER", 3);
        LESS = new OperationType("LESS", 4);
        GREATER_OR_EQUAL = new OperationType("GREATER_OR_EQUAL", 5);
        LESS_OR_EQUAL = new OperationType("LESS_OR_EQUAL", 6);
        $VALUES = (new OperationType[] {
            EQUALS, LIKE, IN, GREATER, LESS, GREATER_OR_EQUAL, LESS_OR_EQUAL
        });
    }
}
