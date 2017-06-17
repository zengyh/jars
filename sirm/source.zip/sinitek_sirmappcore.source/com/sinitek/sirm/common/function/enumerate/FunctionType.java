// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FunctionType.java

package com.sinitek.sirm.common.function.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class FunctionType extends AbstractEnumItem
{

    protected FunctionType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static String getFunctionInfoByValue(int value)
    {
        String functionInfo = "";
        switch(value)
        {
        case 1: // '\001'
            functionInfo = "\u83DC\u5355";
            break;

        case 2: // '\002'
            functionInfo = "\u64CD\u4F5C";
            break;
        }
        return functionInfo;
    }

    public static final FunctionType menu = new FunctionType("1", 1, "\u83DC\u5355", null);
    public static final FunctionType Operation = new FunctionType("2", 2, "\u64CD\u4F5C", null);

}
