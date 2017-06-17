// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableResultMetaDBSql.java

package com.sinitek.spirit.webcontrol.table;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.sinitek.spirit.webcontrol.table:
//            AbsTableResult

public class TableResultMetaDBSql extends AbsTableResult
{

    public TableResultMetaDBSql()
    {
    }

    public List getResultList(Object o, Map tableConfig)
    {
        IMetaDBQuery query = (IMetaDBQuery)o;
        return query.getResult();
    }
}
