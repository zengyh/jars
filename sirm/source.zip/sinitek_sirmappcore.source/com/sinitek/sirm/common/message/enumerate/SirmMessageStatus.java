// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SirmMessageStatus.java

package com.sinitek.sirm.common.message.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class SirmMessageStatus extends AbstractEnumItem
{

    protected SirmMessageStatus(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final SirmMessageStatus INIT = new SirmMessageStatus("1", 1, "\u521D\u59CB\u5316", null);
    public static final SirmMessageStatus WAIT = new SirmMessageStatus("5", 5, "\u65F6\u95F4\u672A\u5230\uFF08\u5B9A\u65F6\u53D1\u9001\uFF09", null);
    public static final SirmMessageStatus SENDING = new SirmMessageStatus("10", 10, "\u53D1\u9001\u4E2D", null);
    public static final SirmMessageStatus FAIL = new SirmMessageStatus("20", 20, "\u5931\u8D25\u53D1\u9001", null);
    public static final SirmMessageStatus PARTSUCCESS = new SirmMessageStatus("30", 30, "\u90E8\u5206\u5B8C\u6210", null);
    public static final SirmMessageStatus SUCCESS = new SirmMessageStatus("50", 50, "\u5168\u90E8\u5B8C\u6210", null);

}
