// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractEnumItem.java

package com.sinitek.base.enumsupport;

import java.io.Serializable;

// Referenced classes of package com.sinitek.base.enumsupport:
//            IEnumItem

public abstract class AbstractEnumItem
    implements IEnumItem, Serializable
{

    protected AbstractEnumItem(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
    {
        this.enumItemDisplayValue = enumItemDisplayValue;
        this.enumItemInfo = enumItemInfo;
        this.enumItemName = enumItemName;
        this.enumItemValue = enumItemValue;
    }

    public String getEnumItemDisplayValue()
    {
        return enumItemDisplayValue;
    }

    public String getEnumItemInfo()
    {
        return enumItemInfo;
    }

    public String getEnumItemName()
    {
        return enumItemName;
    }

    public int getEnumItemValue()
    {
        return enumItemValue;
    }

    public String getEnumName()
    {
        return getClass().getName();
    }

    public String toString()
    {
        return (new StringBuilder()).append("[").append(enumItemName).append("]-[").append(enumItemValue).append("]").toString();
    }

    public final boolean equals(Object obj)
    {
        if(obj instanceof IEnumItem)
        {
            IEnumItem item = (IEnumItem)obj;
            if(getEnumName().equalsIgnoreCase(item.getEnumName()))
                return item.getEnumItemValue() == enumItemValue && item.getEnumItemName().equalsIgnoreCase(enumItemName);
        }
        return false;
    }

    protected String enumItemDisplayValue;
    protected String enumItemInfo;
    protected String enumItemName;
    protected int enumItemValue;
}
