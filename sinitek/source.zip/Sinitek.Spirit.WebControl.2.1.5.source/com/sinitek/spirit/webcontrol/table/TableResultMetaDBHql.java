// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableResultMetaDBHql.java

package com.sinitek.spirit.webcontrol.table;

import com.sinitek.base.metadb.IMetaObject;
import com.sinitek.base.metadb.IMetaObjectImpl;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import java.util.*;
import org.apache.commons.collections.map.CaseInsensitiveMap;

// Referenced classes of package com.sinitek.spirit.webcontrol.table:
//            AbsTableResult

public class TableResultMetaDBHql extends AbsTableResult
{

    public TableResultMetaDBHql()
    {
    }

    public List getResultList(Object o, Map tableConfig)
    {
        IMetaDBQuery query = (IMetaDBQuery)o;
        List result = new ArrayList();
        List list = query.getResult();
        Map map;
label0:
        for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(map))
        {
            Map tmp = (Map)i$.next();
            map = new CaseInsensitiveMap();
            map.putAll(tmp);
            map.put("objid", Integer.valueOf(((IMetaObject)tmp).getId()));
            map.put("createtimestamp", ((IMetaObject)tmp).getCreateTimestamp());
            map.put("updatetimestamp", ((IMetaObject)tmp).getUpdateTimestamp());
            Iterator i$ = map.keySet().iterator();
            do
            {
                if(!i$.hasNext())
                    continue label0;
                Object key = i$.next();
                Object value = map.get(key);
                if(value instanceof IMetaObjectImpl)
                    map.put(key, null);
            } while(true);
        }

        return result;
    }
}
