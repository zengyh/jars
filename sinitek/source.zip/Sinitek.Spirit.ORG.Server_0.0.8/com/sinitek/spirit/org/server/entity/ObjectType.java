// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ObjectType.java

package com.sinitek.spirit.org.server.entity;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public final class ObjectType extends AbstractEnumItem
{

    protected ObjectType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final ObjectType EMPLOYEE = new ObjectType("EMP", 1, "\u5458\u5DE5", null);
    public static final ObjectType UNIT = new ObjectType("UNIT", 2, "\u673A\u6784", null);

}
