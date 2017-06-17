// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableResultList.java

package com.sinitek.spirit.webcontrol.table;

import java.util.List;
import java.util.Map;

// Referenced classes of package com.sinitek.spirit.webcontrol.table:
//            AbsTableResult

public class TableResultList extends AbsTableResult
{

    public TableResultList()
    {
    }

    public List getResultList(Object o, Map tableConfig)
    {
        return (List)o;
    }
}
