// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaObjectPropertyUpdateInfo.java

package com.sinitek.base.metadb;


// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBException, IEntity, IProperty

public interface IMetaObjectPropertyUpdateInfo
{
    public static interface IOrigValueBean
    {

        public abstract Object getOrigValue();

        public abstract Object getCurrentValue();

        public abstract IProperty getProperty();
    }


    public abstract boolean isPersistStatus();

    public abstract IEntity getEntity();

    public abstract IProperty[] getUpdatedProperties();

    public abstract boolean isPropertyUpdated(IProperty iproperty);

    public abstract boolean isPropertyUpdated(String s);

    public abstract IOrigValueBean getOrigValueBean(IProperty iproperty)
        throws MetaDBException;

    public abstract IOrigValueBean getOrigValueBean(String s)
        throws MetaDBException;

    public abstract IOrigValueBean[] getOrigValueBeans();
}
