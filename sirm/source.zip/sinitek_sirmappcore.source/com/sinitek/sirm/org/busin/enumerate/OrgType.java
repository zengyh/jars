// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgType.java

package com.sinitek.sirm.org.busin.enumerate;

import com.sinitek.base.enumsupport.AbstractEnumItem;

public class OrgType extends AbstractEnumItem
{

    protected OrgType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
    }

    public static OrgType getOrgTypeByValue(int value)
    {
        OrgType r = null;
        switch(value)
        {
        case 1: // '\001'
            r = DEPT;
            break;

        case 2: // '\002'
            r = POSITION;
            break;

        case 4: // '\004'
            r = TEAM;
            break;

        case 8: // '\b'
            r = EMPLOYEE;
            break;

        case 16: // '\020'
            r = ROLE;
            break;
        }
        return r;
    }

    public static OrgType getOrgTypeName(String enumItemInfo)
    {
        OrgType orgType = DEPT;
        if(enumItemInfo.equals("UNIT") || enumItemInfo.equals("unit"))
            orgType = DEPT;
        if(enumItemInfo.equals("POSITION") || enumItemInfo.equals("position"))
            orgType = POSITION;
        if(enumItemInfo.equals("EMPLOYEE") || enumItemInfo.equals("employee"))
            orgType = EMPLOYEE;
        if(enumItemInfo.equals("TEAM") || enumItemInfo.equals("team"))
            orgType = TEAM;
        if(enumItemInfo.equals("ROLE") || enumItemInfo.equals("role"))
            orgType = ROLE;
        return orgType;
    }

    public static final OrgType DEPT = new OrgType("1", 1, "UNIT", null);
    public static final OrgType POSITION = new OrgType("2", 2, "POSITION", null);
    public static final OrgType EMPLOYEE = new OrgType("8", 8, "EMPLOYEE", null);
    public static final OrgType TEAM = new OrgType("4", 4, "TEAM", null);
    public static final OrgType ROLE = new OrgType("16", 16, "ROLE", null);

}
