// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QualifyType.java

package com.sinitek.sirm.org.busin.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class QualifyType extends AbstractEnumItem
{

    protected QualifyType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final QualifyType ONE = new QualifyType("1", 1, "\u5206\u6790\u5E08\u4E1A\u52A1", null);
    public static final QualifyType TWO = new QualifyType("2", 2, "\u4E00\u822C\u8BC1\u5238\u4E1A\u52A1", null);
    public static final QualifyType THREE = new QualifyType("3", 3, "\u6295\u8D44\u987E\u95EE\u4E1A\u52A1", null);
    public static final QualifyType ZERO = new QualifyType("4", 4, "\u65E0\u8D44\u683C", null);

}
