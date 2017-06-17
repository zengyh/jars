// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropertyDBType.java

package com.sinitek.base.metadb;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class PropertyDBType extends AbstractEnumItem
{

    protected PropertyDBType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final PropertyDBType CLOB = new PropertyDBType("CLOB", 1, "CLOB\u7C7B\u578B", "");
    public static final PropertyDBType BLOB = new PropertyDBType("BLOB", 2, "BLOB\u7C7B\u578B", "");
    public static final PropertyDBType FILE = new PropertyDBType("FILE", 3, "\u5916\u90E8\u6587\u4EF6\u7C7B\u578B", "");
    public static final PropertyDBType INTEGER = new PropertyDBType("INTEGER", 4, "\u6574\u6570\u7C7B\u578B", "");
    public static final PropertyDBType DOUBLE = new PropertyDBType("DOUBLE", 5, "\u53CC\u7CBE\u5EA6\u6D6E\u70B9\u7C7B\u578B", "");
    public static final PropertyDBType FLOAT = new PropertyDBType("FLOAT", 6, "\u5355\u7CBE\u5EA6\u6D6E\u70B9\u7C7B\u578B", "");
    public static final PropertyDBType VARCHAR = new PropertyDBType("VARCHAR", 7, "\u53D8\u957F\u5B57\u7B26\u7C7B\u578B", "");
    public static final PropertyDBType CHAR = new PropertyDBType("CHAR", 8, "\u5B9A\u957F\u5B57\u7B26\u7C7B\u578B", "");
    public static final PropertyDBType DATE = new PropertyDBType("DATE", 8, "\u65E5\u671F\u7C7B\u578B", "");

}
