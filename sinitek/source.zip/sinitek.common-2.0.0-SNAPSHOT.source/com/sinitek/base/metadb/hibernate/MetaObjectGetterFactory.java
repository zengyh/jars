// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectGetterFactory.java

package com.sinitek.base.metadb.hibernate;

import com.sinitek.base.metadb.IProperty;
import com.sinitek.base.metadb.PropertyType;
import org.hibernate.property.Getter;

// Referenced classes of package com.sinitek.base.metadb.hibernate:
//            MetaObjectGetter

public class MetaObjectGetterFactory
{

    public MetaObjectGetterFactory()
    {
    }

    public static Getter createGetter(IProperty property)
    {
        if(property.getPropertyType().equals(PropertyType.ID))
            return new MetaObjectGetter.IdGetter(property);
        if(property.getPropertyType().equals(PropertyType.CREATETIMESTAMP))
            return new MetaObjectGetter.CreateTimeStampGetter(property);
        if(property.getPropertyType().equals(PropertyType.UPDATETIMESTAMP))
            return new MetaObjectGetter.UpdateTimeStampGetter(property);
        if(property.getPropertyType().equals(PropertyType.VERSION))
            return new MetaObjectGetter.VersionGetter(property);
        if(property.getPropertyType().equals(PropertyType.LASTID))
            return new MetaObjectGetter.LastIdGetter(property);
        if(property.getPropertyType().equals(PropertyType.ENTITYNAME))
            return new MetaObjectGetter.EntityNameGetter(property);
        else
            return new MetaObjectGetter(property);
    }
}
