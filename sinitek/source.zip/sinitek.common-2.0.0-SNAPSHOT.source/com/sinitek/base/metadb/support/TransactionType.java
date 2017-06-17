// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransactionType.java

package com.sinitek.base.metadb.support;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class TransactionType extends AbstractEnumItem
{

    protected TransactionType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final TransactionType TRANSACTION_SUPPORT = new TransactionType("SUPPORT", 1, "\u652F\u6301\u4E8B\u52A1", null);
    public static final TransactionType TRANSACTION_NEW = new TransactionType("NEW", 2, "\u5168\u65B0\u4E8B\u52A1", null);
    public static final TransactionType TRANSACTION_REQUIRED = new TransactionType("REQUIRED", 3, "\u9700\u8981\u4E8B\u52A1", null);
    public static final TransactionType TRANSACTION_NONE = new TransactionType("NONE", 4, "\u65E0\u9700\u4E8B\u52A1", null);

}
