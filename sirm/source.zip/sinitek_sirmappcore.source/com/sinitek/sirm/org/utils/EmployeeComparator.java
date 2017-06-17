// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmployeeComparator.java

package com.sinitek.sirm.org.utils;

import com.sinitek.base.common.pinyin.PinYinComparator;
import com.sinitek.sirm.org.busin.entity.Employee;
import java.util.Comparator;

public class EmployeeComparator
    implements Comparator
{

    public EmployeeComparator()
    {
    }

    public int compare(Object o1, Object o2)
    {
        Employee e1 = (Employee)o1;
        Employee e2 = (Employee)o2;
        return (new PinYinComparator(true)).compare(e1.getEmpName(), e2.getEmpName());
    }
}
