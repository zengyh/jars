// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PinYinComparator.java

package com.sinitek.base.common.pinyin;

import java.util.Comparator;

// Referenced classes of package com.sinitek.base.common.pinyin:
//            PinYinHelper

public class PinYinComparator
    implements Comparator
{

    public PinYinComparator(boolean asc)
    {
        iPrefix = 1;
        iPrefix = asc ? 1 : -1;
    }

    public int _compare(Object o1, Object o2)
    {
        if(o1 == null && o2 == null)
            return 0;
        if(o1 == null)
            return -1;
        if(o2 == null)
        {
            return 1;
        } else
        {
            String sz1 = o1.toString();
            String sz2 = o2.toString();
            sz1 = PinYinHelper.getInstance().getSimplePinYin(sz1).toUpperCase();
            sz2 = PinYinHelper.getInstance().getSimplePinYin(sz2).toUpperCase();
            return sz1.compareTo(sz2);
        }
    }

    public int compare(Object o1, Object o2)
    {
        return iPrefix * _compare(o1, o2);
    }

    public static final PinYinComparator PINYIN_ASC_COMPARATOR = new PinYinComparator(true);
    public static final PinYinComparator PINYIN_DESC_COMPARATOR = new PinYinComparator(false);
    private int iPrefix;

}
