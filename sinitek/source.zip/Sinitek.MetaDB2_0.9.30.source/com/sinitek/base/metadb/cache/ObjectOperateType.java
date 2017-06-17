// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ObjectOperateType.java

package com.sinitek.base.metadb.cache;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class ObjectOperateType extends AbstractEnumItem
{

    protected ObjectOperateType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final ObjectOperateType INSERT = new ObjectOperateType("insert", 1, "\u5BF9\u8C61\u65B0\u589E", "");
    public static final ObjectOperateType UPDATE = new ObjectOperateType("update", 2, "\u5BF9\u8C61\u66F4\u65B0", "");
    public static final ObjectOperateType DELETE = new ObjectOperateType("delete", 3, "\u5BF9\u8C61\u5220\u9664", "");

}
