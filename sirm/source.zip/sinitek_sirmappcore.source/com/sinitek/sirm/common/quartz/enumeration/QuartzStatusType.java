// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QuartzStatusType.java

package com.sinitek.sirm.common.quartz.enumeration;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class QuartzStatusType extends AbstractEnumItem
{

    protected QuartzStatusType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final QuartzStatusType ERROR = new QuartzStatusType("0", 0, "\u5904\u7406\u5F02\u5E38", null);
    public static final QuartzStatusType ALL = new QuartzStatusType("1", 1, "\u6B63\u5728\u5904\u7406", null);
    public static final QuartzStatusType SORT = new QuartzStatusType("2", 2, "\u5904\u7406\u7ED3\u675F", null);
    public static final QuartzStatusType PERIOD = new QuartzStatusType("3", 3, "\u5904\u7406\u5F02\u5E38", null);

}
