// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmMessageDetailStatus.java

package com.sinitek.sirm.common.message.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class SirmMessageDetailStatus extends AbstractEnumItem
{

    protected SirmMessageDetailStatus(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final SirmMessageDetailStatus ASSEMBLING = new SirmMessageDetailStatus("0", 0, "\u7EC4\u88C5\u4E2D", null);
    public static final SirmMessageDetailStatus SENDING = new SirmMessageDetailStatus("1", 1, "\u5F85\u53D1\u9001", null);
    public static final SirmMessageDetailStatus FAIL = new SirmMessageDetailStatus("2", 2, "\u5931\u8D25\u53D1\u9001", null);
    public static final SirmMessageDetailStatus SUCCESS = new SirmMessageDetailStatus("3", 3, "\u53D1\u9001\u6210\u529F", null);

}
