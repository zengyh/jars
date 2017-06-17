// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaDBSysLoader.java

package com.sinitek.base.metadb.config;

import com.sinitek.base.metadb.IEntity;
import com.sinitek.base.metadb.MetaDBException;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;

// Referenced classes of package com.sinitek.base.metadb.config:
//            GlobalConfig

public interface IMetaDBSysLoader
{

    public abstract void initData(GlobalConfig globalconfig)
        throws MetaDBException;

    public abstract IEntity getEntity(String s)
        throws MetaDBException;

    public abstract boolean isEntityExists(String s);

    public abstract void reloadConfig(GlobalConfig globalconfig)
        throws MetaDBException;

    public abstract SessionFactory getSessionFactory()
        throws MetaDBException;

    public abstract DataSource getDataSource()
        throws MetaDBException;
}
