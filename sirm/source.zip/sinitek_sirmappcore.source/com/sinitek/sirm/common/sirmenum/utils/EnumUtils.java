// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnumUtils.java

package com.sinitek.sirm.common.sirmenum.utils;

import com.sinitek.base.metadb.MetaDBContextFactory;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.sirm.common.sirmenum.EnumMap;
import com.sinitek.sirm.common.sirmenum.entity.ISirmEnum;
import java.util.*;

// Referenced classes of package com.sinitek.sirm.common.sirmenum.utils:
//            EnumMetaDBPlugin

public class EnumUtils
{

    public EnumUtils()
    {
    }

    public static Map findSirmEnumByCataLogAndType(String cataLog, String type)
    {
        Map _map = new LinkedHashMap();
        List _list = getEnumMetaDBPlugin().findSirmEnumByCataLogAndType(cataLog.toUpperCase(), type);
        ISirmEnum _objs;
        String _value;
        for(Iterator i$ = _list.iterator(); i$.hasNext(); _map.put(_objs.getValue() != null ? ((Object) ((new StringBuilder()).append("").append(_objs.getValue()).toString())) : ((Object) (_objs.getStrvalue())), _value))
        {
            _objs = (ISirmEnum)i$.next();
            _value = _objs.getName();
        }

        return new EnumMap(_map);
    }

    public static Map findNewSirmEnumByCataLogAndType(String cataLog, String type)
    {
        Map _map = new LinkedHashMap();
        List _list = getEnumMetaDBPlugin().findSirmEnumByCataLogAndType(cataLog.toUpperCase(), type);
        ISirmEnum _objs;
        String _value;
        for(Iterator i$ = _list.iterator(); i$.hasNext(); _map.put(_objs.getValue() != null ? ((Object) ((new StringBuilder()).append("").append(_objs.getValue()).toString())) : ((Object) (_objs.getStrvalue())), _value))
        {
            _objs = (ISirmEnum)i$.next();
            _value = _objs.getName();
        }

        return new EnumMap(_map);
    }

    public static ISirmEnum getSirmEnumName(String cataLog, String type, Integer value)
    {
        return getEnumMetaDBPlugin().getSirmEnumName(cataLog.toUpperCase(), type, value);
    }

    public static ISirmEnum getSirmEnumName(String cataLog, String type, String strValue)
    {
        return getEnumMetaDBPlugin().getSirmEnumName(cataLog.toUpperCase(), type, strValue);
    }

    public static List findSirmEnum(Map typeKeyCatalogValue)
    {
        return getEnumMetaDBPlugin().findSirmEnum(typeKeyCatalogValue);
    }

    public static String getSirmEnumNameAsString(String cataLog, String type, Integer value)
    {
        ISirmEnum e = getEnumMetaDBPlugin().getSirmEnumName(cataLog.toUpperCase(), type, value);
        return e != null ? e.getName() : null;
    }

    private static EnumMetaDBPlugin getEnumMetaDBPlugin()
    {
        if(enumMetaDBPlugin == null)
            synchronized(com/sinitek/sirm/common/sirmenum/utils/EnumUtils)
            {
                if(enumMetaDBPlugin == null)
                {
                    enumMetaDBPlugin = new EnumMetaDBPlugin();
                    MetaDBContextFactory.getInstance().getCacheContext().registCachePlugin(enumMetaDBPlugin);
                }
            }
        return enumMetaDBPlugin;
    }

    private static EnumMetaDBPlugin enumMetaDBPlugin = null;

}
