// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnumItemComparator.java

package com.sinitek.base.enumsupport;

import com.sinitek.base.common.pinyin.PinYinComparator;
import java.util.Comparator;

// Referenced classes of package com.sinitek.base.enumsupport:
//            IEnumItem

public class EnumItemComparator
    implements Comparator
{

    public EnumItemComparator(int mode, boolean asc)
    {
        this.mode = mode;
        this.asc = asc;
    }

    public int compare(Object o1, Object o2)
    {
        if(o1 == null || o2 == null)
            return 0;
        if(!(o1 instanceof IEnumItem) || !(o2 instanceof IEnumItem))
        {
            return 0;
        } else
        {
            IEnumItem item1 = (IEnumItem)o1;
            IEnumItem item2 = (IEnumItem)o2;
            int ret = compare(item1, item2);
            return asc ? ret : ret * -1;
        }
    }

    public int compare(IEnumItem item1, IEnumItem item2)
    {
        switch(mode)
        {
        case 1: // '\001'
            return item1.getEnumItemValue() - item2.getEnumItemValue();

        case 2: // '\002'
            return item1.getEnumItemName().compareTo(item2.getEnumItemName());

        case 3: // '\003'
            return item1.getEnumItemInfo().compareTo(item2.getEnumItemInfo());

        case 4: // '\004'
            return (new PinYinComparator(true)).compare(item1.getEnumItemInfo(), item2.getEnumItemInfo());
        }
        return 0;
    }

    public static final int MODE_ITEMVALUE = 1;
    public static final int MODE_ITEMNAME = 2;
    public static final int MODE_ITEMINFO = 3;
    public static final int MODE_ITEMINFO_PY = 4;
    private int mode;
    private boolean asc;
}
