// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PositionType.java

package com.sinitek.sirm.org.busin.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class PositionType extends AbstractEnumItem
{

    protected PositionType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final PositionType Execute = new PositionType("SUPEXECUTE", 1, "\u884C\u653F\u4E0A\u7EA7", null);
    public static final PositionType Business = new PositionType("SUPBUSIN", 2, "\u4E1A\u52A1\u4E0A\u7EA7", null);

}
