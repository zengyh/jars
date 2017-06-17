// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaObjectSetterFactory.java

package com.sinitek.base.metadb.hibernate;

import com.sinitek.base.metadb.IProperty;
import com.sinitek.base.metadb.PropertyType;
import org.hibernate.property.Setter;

// Referenced classes of package com.sinitek.base.metadb.hibernate:
//            MetaObjectSetter

public class MetaObjectSetterFactory
{

    public MetaObjectSetterFactory()
    {
    }

    public static Setter createSetter(IProperty property)
    {
        if(property.getPropertyType().equals(PropertyType.ID))
            return new MetaObjectSetter.IdSetter(property);
        if(property.getPropertyType().equals(PropertyType.CREATETIMESTAMP))
            return new MetaObjectSetter.CreateTimeStampSetter(property);
        if(property.getPropertyType().equals(PropertyType.UPDATETIMESTAMP))
            return new MetaObjectSetter.UpdateTimeStampSetter(property);
        if(property.getPropertyType().equals(PropertyType.VERSION))
            return new MetaObjectSetter.VersionSetter(property);
        if(property.getPropertyType().equals(PropertyType.LASTID))
            return new MetaObjectSetter.LastIdSetter(property);
        if(property.getPropertyType().equals(PropertyType.ENTITYNAME))
            return new MetaObjectSetter.EntityNameSetter(property);
        else
            return new MetaObjectSetter(property);
    }
}
