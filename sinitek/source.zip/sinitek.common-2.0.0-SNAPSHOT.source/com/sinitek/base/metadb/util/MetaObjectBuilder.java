// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectBuilder.java

package com.sinitek.base.metadb.util;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.hibernate.proxy.*;
import java.util.Iterator;
import java.util.List;

public class MetaObjectBuilder
{

    public MetaObjectBuilder()
    {
    }

    public static IMetaObjectImpl createNewMetaObject(IEntity entity)
    {
        if(entity.getImplClass() == null)
        {
            MetaObjectImpl mo = new MetaObjectImpl(entity);
            initMetaObject(mo, entity);
            return mo;
        }
        try
        {
            IMetaObjectImpl mo = (IMetaObjectImpl)entity.getImplClass().newInstance();
            initMetaObject(mo, entity);
            if(mo instanceof IInitializeObject)
            {
                IInitializeObject initObj = (IInitializeObject)mo;
                initObj.init();
            }
            return mo;
        }
        catch(Exception e)
        {
            throw new MetaDBCoreException("0036", e, new Object[] {
                entity.getEntityName()
            });
        }
    }

    private static void initMetaObject(IMetaObjectImpl mo, IEntity entity)
    {
        Iterator iter = entity.getProperties().iterator();
        do
        {
            if(!iter.hasNext())
                break;
            IProperty prop = (IProperty)iter.next();
            if(prop.getDefaultValue() != null)
                mo.put(prop.getPropertyName(), prop.getDefaultValue());
            if(prop.getPropertyType() == PropertyType.CLOB)
                mo.put(prop, new ClobProxy());
            if(prop.getPropertyType() == PropertyType.STREAM)
                if(prop.getPropertyDBType() == PropertyDBType.BLOB)
                    mo.put(prop, new BlobStreamValue(null, mo.getEntityName()));
                else
                if(prop.getPropertyDBType() == PropertyDBType.CLOB)
                    mo.put(prop, new ClobStreamValue(null, mo.getEntityName()));
                else
                    mo.put(prop, new FileStreamValue(null, mo.getEntityName()));
        } while(true);
    }
}
