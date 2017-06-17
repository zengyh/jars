// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QueryDir.java

package com.sinitek.spirit.org.server.service;


public final class QueryDir extends Enum
{

    public static QueryDir[] values()
    {
        return (QueryDir[])$VALUES.clone();
    }

    public static QueryDir valueOf(String name)
    {
        return (QueryDir)Enum.valueOf(com/sinitek/spirit/org/server/service/QueryDir, name);
    }

    private QueryDir(String s, int i)
    {
        super(s, i);
    }

    public static final QueryDir UP;
    public static final QueryDir DOWN;
    private static final QueryDir $VALUES[];

    static 
    {
        UP = new QueryDir("UP", 0);
        DOWN = new QueryDir("DOWN", 1);
        $VALUES = (new QueryDir[] {
            UP, DOWN
        });
    }
}
