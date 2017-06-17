// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceParamType.java

package com.sinitek.ds.core.service.config;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public final class ServiceParamType extends AbstractEnumItem
{

    protected ServiceParamType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final ServiceParamType STRING = new ServiceParamType("String", 1, "\u5B57\u7B26\u7C7B\u578B", null);
    public static final ServiceParamType INTEGER = new ServiceParamType("Integer", 2, "\u6574\u6570\u7C7B\u578B", null);
    public static final ServiceParamType FLOAT = new ServiceParamType("Float", 3, "\u6D6E\u70B9\u7C7B\u578B", null);
    public static final ServiceParamType BOOLEAN = new ServiceParamType("Boolean", 4, "\u5E03\u5C14\u7C7B\u578B", null);
    public static final ServiceParamType DOUBLE = new ServiceParamType("Double", 5, "\u957F\u6D6E\u70B9\u7C7B\u578B", null);
    public static final ServiceParamType DATE = new ServiceParamType("DATE", 6, "\u65E5\u671F\u7C7B\u578B\uFF0C\u4E0D\u5305\u62EC\u65F6\u95F4", null);
    public static final ServiceParamType TIME = new ServiceParamType("TIME", 7, "\u65F6\u95F4\u7C7B\u578B\uFF0C\u4E0D\u5305\u62EC\u65E5\u671F", null);
    public static final ServiceParamType DATETIME = new ServiceParamType("DATETIME", 8, "\u65E5\u671F\u65F6\u95F4\u7C7B\u578B", null);
    public static final ServiceParamType ENUM = new ServiceParamType("Enum", 9, "\u679A\u4E3E\u7C7B\u578B", null);
    public static final ServiceParamType DICT = new ServiceParamType("Dict", 10, "\u6570\u636E\u5B57\u5178", null);

}
