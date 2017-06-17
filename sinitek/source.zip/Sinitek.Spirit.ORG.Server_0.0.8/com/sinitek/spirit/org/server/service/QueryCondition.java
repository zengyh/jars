// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QueryCondition.java

package com.sinitek.spirit.org.server.service;


public final class QueryCondition extends Enum
{

    public static QueryCondition[] values()
    {
        return (QueryCondition[])$VALUES.clone();
    }

    public static QueryCondition valueOf(String name)
    {
        return (QueryCondition)Enum.valueOf(com/sinitek/spirit/org/server/service/QueryCondition, name);
    }

    private QueryCondition(String s, int i)
    {
        super(s, i);
    }

    public static final QueryCondition ORGNAME;
    public static final QueryCondition RELATIONSHIPTYPE;
    public static final QueryCondition ORGTYPE;
    public static final QueryCondition LEVEL;
    private static final QueryCondition $VALUES[];

    static 
    {
        ORGNAME = new QueryCondition("ORGNAME", 0);
        RELATIONSHIPTYPE = new QueryCondition("RELATIONSHIPTYPE", 1);
        ORGTYPE = new QueryCondition("ORGTYPE", 2);
        LEVEL = new QueryCondition("LEVEL", 3);
        $VALUES = (new QueryCondition[] {
            ORGNAME, RELATIONSHIPTYPE, ORGTYPE, LEVEL
        });
    }
}
