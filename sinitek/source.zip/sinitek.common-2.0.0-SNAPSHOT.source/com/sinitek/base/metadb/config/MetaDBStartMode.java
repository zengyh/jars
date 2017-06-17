// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBStartMode.java

package com.sinitek.base.metadb.config;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class MetaDBStartMode extends AbstractEnumItem
{

    protected MetaDBStartMode(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final MetaDBStartMode FULL = new MetaDBStartMode("full", 0, "\u5168\u88C5\u8F7D\u6A21\u5F0F", null);
    public static final MetaDBStartMode CATALOG = new MetaDBStartMode("catalog", 1, "\u6309\u5B9E\u4F53\u76EE\u5F55\u88C5\u8F7D\u6A21\u5F0F", null);
    public static final MetaDBStartMode ENTITY = new MetaDBStartMode("entity", 2, "\u6309\u5B9E\u4F53\u88C5\u8F7D\u6A21\u5F0F", null);

}
