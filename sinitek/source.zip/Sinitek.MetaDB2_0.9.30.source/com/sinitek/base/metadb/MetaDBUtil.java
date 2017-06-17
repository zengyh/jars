// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBUtil.java

package com.sinitek.base.metadb;

import java.math.BigDecimal;
import java.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;

// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBContextImpl, IMetaObjectImpl, MetaDBCoreException, IEntity, 
//            MetaDBContextFactory, IProperty, PropertyType, IMetaObject

public class MetaDBUtil
{

    public MetaDBUtil()
    {
    }

    public static IMetaObject insertOrUpdate(Map srcObj, String entityName, String keyNames[])
    {
        MetaDBContextImpl ctx = (MetaDBContextImpl)MetaDBContextFactory.getInstance().createContext();
        Map srcProp = new CaseInsensitiveMap();
        srcProp.putAll(srcObj);
        Object keyValues[] = srcProp.values().toArray();
        IMetaObjectImpl destObj = (IMetaObjectImpl)ctx.get(entityName, keyNames, keyValues);
        if(destObj == null)
            destObj = (IMetaObjectImpl)ctx.createNewMetaObject(entityName);
        destObj.clear();
        IEntity entity = destObj.getEntity();
        Iterator i$ = srcProp.keySet().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            String key = (String)i$.next();
            if(destObj.getEntity().containsProperty(key) && srcProp.get(key) != null)
                try
                {
                    if(entity.getProperty(key).getPropertyType().equals(PropertyType.STRING))
                    {
                        String value = MapUtils.getString(srcProp, key, "");
                        destObj.put(key, value);
                    } else
                    if(entity.getProperty(key).getPropertyType().equals(PropertyType.INTEGER))
                    {
                        int value = MapUtils.getInteger(srcProp, key).intValue();
                        destObj.put(key, Integer.valueOf(value));
                    } else
                    if(entity.getProperty(key).getPropertyType().equals(PropertyType.FLOAT))
                    {
                        BigDecimal value = new BigDecimal(MapUtils.getString(srcProp, key));
                        destObj.put(key, Float.valueOf(value.floatValue()));
                    } else
                    if(entity.getProperty(key).getPropertyType().equals(PropertyType.DOUBLE))
                    {
                        BigDecimal value = new BigDecimal(MapUtils.getString(srcProp, key));
                        destObj.put(key, Double.valueOf(value.doubleValue()));
                    } else
                    if(entity.getProperty(key).getPropertyType().equals(PropertyType.BOOLEAN))
                    {
                        boolean value = MapUtils.getBoolean(srcProp, key).booleanValue();
                        destObj.put(key, Boolean.valueOf(value));
                    } else
                    {
                        destObj.put(key, srcProp.get(key));
                    }
                }
                catch(MetaDBCoreException e) { }
        } while(true);
        destObj.save();
        return destObj;
    }
}
