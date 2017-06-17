// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbsTableResult.java

package com.sinitek.spirit.webcontrol.table;

import java.util.List;
import java.util.Map;

abstract class AbsTableResult
{

    AbsTableResult()
    {
    }

    public abstract List getResultList(Object obj, Map map);
}
