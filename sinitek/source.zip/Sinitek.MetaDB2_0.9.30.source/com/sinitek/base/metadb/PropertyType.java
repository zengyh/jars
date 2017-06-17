// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropertyType.java

package com.sinitek.base.metadb;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class PropertyType extends AbstractEnumItem
{

    private PropertyType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final PropertyType STRING = new PropertyType("STRING", 0, "\u5B57\u7B26\u4E32\u7C7B\u578B", "");
    public static final PropertyType INTEGER = new PropertyType("INTEGER", 1, "\u6574\u6570\u7C7B\u578B", "");
    public static final PropertyType FLOAT = new PropertyType("FLOAT", 2, "\u6D6E\u70B9\u7C7B\u578B", "");
    public static final PropertyType DOUBLE = new PropertyType("DOUBLE", 3, "\u53CC\u7CBE\u5EA6\u6D6E\u70B9\u7C7B\u578B", "");
    public static final PropertyType BOOLEAN = new PropertyType("BOOLEAN", 4, "\u5E03\u5C14\u7C7B\u578B", "");
    public static final PropertyType UID = new PropertyType("UID", 5, "UID\u7C7B\u578B", "");
    public static final PropertyType CLOB = new PropertyType("CLOB", 6, "CLOB\u7C7B\u578B", "");
    public static final PropertyType STREAM = new PropertyType("STREAM", 7, "\u6D41\u7C7B\u578B", "");
    public static final PropertyType BYTES = new PropertyType("BYTES", 8, "\u5B57\u8282\u7C7B\u578B", "");
    public static final PropertyType ENUM = new PropertyType("ENUM", 9, "\u679A\u4E3E\u7C7B\u578B", "");
    public static final PropertyType CLASS = new PropertyType("CLASS", 10, "\u5E8F\u5217\u5316\u7C7B\u578B", "");
    public static final PropertyType ENTITY = new PropertyType("ENTITY", 12, "\u5B9E\u4F53\u7C7B\u578B", "");
    public static final PropertyType DATE = new PropertyType("DATE", 13, "\u65E5\u671F\u7C7B\u578B", "");
    public static final PropertyType ID = new PropertyType("ID", 14, "\u4E3B\u952E\u7C7B\u578B", "");
    public static final PropertyType CREATETIMESTAMP = new PropertyType("CREATETIMESTAMP", 15, "\u521B\u5EFA\u65F6\u95F4\u6233\u7C7B\u578B", "");
    public static final PropertyType UPDATETIMESTAMP = new PropertyType("UPDATETIMESTAMP", 16, "\u66F4\u65B0\u65F6\u95F4\u6233\u7C7B\u578B", "");
    public static final PropertyType ENTITYNAME = new PropertyType("ENTITYNAME", 17, "\u5B9E\u4F53\u540D\u79F0\u7C7B\u578B", "");
    public static final PropertyType VERSION = new PropertyType("VERSION", 18, "\u4E50\u89C2\u9501\u7C7B\u578B", "");
    public static final PropertyType LASTID = new PropertyType("LASTID", 19, "\u7EBF\u7D22\u4E3B\u952E\u7C7B\u578B", "");

}
