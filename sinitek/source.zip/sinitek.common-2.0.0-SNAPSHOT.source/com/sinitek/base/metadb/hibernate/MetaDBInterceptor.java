// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBInterceptor.java

package com.sinitek.base.metadb.hibernate;

import com.sinitek.base.metadb.IMetaObjectImpl;
import com.sinitek.base.metadb.IProperty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class MetaDBInterceptor extends EmptyInterceptor
{

    public MetaDBInterceptor()
    {
    }

    public int[] findDirty(Object entity, Serializable id, Object currentState[], Object previousState[], String propertyNames[], Type types[])
    {
        IMetaObjectImpl mo = (IMetaObjectImpl)entity;
        if(mo.getDirtyPropertyCount() == 0)
            return new int[0];
        int ret[] = new int[IProperty.SYSTEM_PROPERTY_NAMES.length + mo.getDirtyPropertyCount()];
        int index = 0;
        for(int i = 0; i < propertyNames.length; i++)
            if(SYS_PROP.containsKey(propertyNames[i]) || mo.isDirtyProperty(propertyNames[i]))
                ret[index++] = i;

        mo.setPersistStatus(true);
        return ret;
    }

    public String getEntityName(Object object)
    {
        IMetaObjectImpl mo = (IMetaObjectImpl)object;
        return mo.getEntityName();
    }

    private static final Map SYS_PROP;

    static 
    {
        SYS_PROP = new HashMap();
        for(int i = 0; i < IProperty.SYSTEM_PROPERTY_NAMES.length; i++)
            SYS_PROP.put(IProperty.SYSTEM_PROPERTY_NAMES[i], IProperty.SYSTEM_PROPERTY_NAMES[i]);

    }
}
