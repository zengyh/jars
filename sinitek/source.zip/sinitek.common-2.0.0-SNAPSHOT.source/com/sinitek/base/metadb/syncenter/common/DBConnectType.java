// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DBConnectType.java

package com.sinitek.base.metadb.syncenter.common;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class DBConnectType extends AbstractEnumItem
{

    protected DBConnectType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static final DBConnectType EXT_DATASOURCE = new DBConnectType("ext_ds", 1, "\u5916\u90E8\u6570\u636E\u6E90", null);
    public static final DBConnectType DIRECT_CONN = new DBConnectType("directconn", 2, "\u672C\u5730\u914D\u7F6E\u76F4\u8FDE", null);
    public static final DBConnectType LOCAL = new DBConnectType("local", 3, "\u672C\u5730MetaDB\u6570\u636E\u6E90", null);
    public static final DBConnectType JNDI = new DBConnectType("jndi", 4, "\u672C\u5730JNDI\u65B9\u5F0F", null);

}
