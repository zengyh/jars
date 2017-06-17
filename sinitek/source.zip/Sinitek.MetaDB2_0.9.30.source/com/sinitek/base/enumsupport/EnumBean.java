// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnumBean.java

package com.sinitek.base.enumsupport;

import java.util.ArrayList;
import java.util.List;

public class EnumBean
{

    public EnumBean()
    {
        enumItemList = new ArrayList();
    }

    public List getEnumItemList()
    {
        return enumItemList;
    }

    public void setEnumItemList(List enumItemList)
    {
        this.enumItemList = enumItemList;
    }

    public String getEnumName()
    {
        return enumName;
    }

    public void setEnumName(String enumName)
    {
        this.enumName = enumName;
    }

    private String enumName;
    private List enumItemList;
}
