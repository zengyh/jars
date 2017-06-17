// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemoEnum.java

package com.sinitek.spirit.webcontrol.demo;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class DemoEnum extends AbstractEnumItem
{

    private DemoEnum(String s, int i, String s1, String s2)
    {
        super(s, i, s1, null);
    }

    public static final DemoEnum NORMAL = new DemoEnum("0", 0, "\u6B63\u5E38", null);
    public static final DemoEnum DELETE = new DemoEnum("1", 1, "\u6CE8\u9500", null);
    public static final DemoEnum LOCK = new DemoEnum("2", 2, "\u9501\u5B9A", null);

}
