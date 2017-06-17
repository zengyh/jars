// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IMetaDBContext.java

package com.sinitek.base.metadb;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBException, IEntity, IMetaObject, ISQLUpdater

public interface IMetaDBContext
{

    public abstract int getId();

    public abstract Session getSession();

    public abstract IMetaObject createNewMetaObject(IEntity ientity);

    public abstract IMetaObject createNewMetaObject(String s);

    public abstract IMetaObject createNewMetaObject(Class class1);

    public abstract IMetaObject load(IEntity ientity, int i)
        throws MetaDBException;

    public abstract IMetaObject load(IEntity ientity, int i, boolean flag)
        throws MetaDBException;

    public abstract IMetaObject load(String s, int i)
        throws MetaDBException;

    public abstract IMetaObject load(Class class1, int i)
        throws MetaDBException;

    public abstract IMetaObject load(String s, int i, boolean flag)
        throws MetaDBException;

    public abstract IMetaObject load(Class class1, int i, boolean flag)
        throws MetaDBException;

    public abstract IMetaObject load(IEntity ientity, String s, Object obj)
        throws MetaDBException;

    public abstract IMetaObject load(IEntity ientity, String s, Object obj, boolean flag)
        throws MetaDBException;

    public abstract IMetaObject load(String s, String s1, Object obj)
        throws MetaDBException;

    public abstract IMetaObject load(Class class1, String s, Object obj)
        throws MetaDBException;

    public abstract IMetaObject load(String s, String s1, Object obj, boolean flag)
        throws MetaDBException;

    public abstract IMetaObject load(Class class1, String s, Object obj, boolean flag)
        throws MetaDBException;

    public abstract IMetaObject get(IEntity ientity, int i)
        throws MetaDBException;

    public abstract IMetaObject get(String s, int i)
        throws MetaDBException;

    public abstract IMetaObject get(Class class1, int i)
        throws MetaDBException;

    public abstract IMetaObject get(IEntity ientity, int i, boolean flag)
        throws MetaDBException;

    public abstract IMetaObject get(String s, int i, boolean flag)
        throws MetaDBException;

    public abstract IMetaObject get(Class class1, int i, boolean flag)
        throws MetaDBException;

    public abstract IMetaObject get(IEntity ientity, String s, Object obj)
        throws MetaDBException;

    public abstract IMetaObject get(IEntity ientity, String s, Object obj, boolean flag)
        throws MetaDBException;

    public abstract IMetaObject get(String s, String s1, Object obj)
        throws MetaDBException;

    public abstract IMetaObject get(Class class1, String s, Object obj)
        throws MetaDBException;

    public abstract IMetaObject get(String s, String s1, Object obj, boolean flag)
        throws MetaDBException;

    public abstract IMetaObject get(Class class1, String s, Object obj, boolean flag)
        throws MetaDBException;

    public abstract IMetaObject get(String s, Map map)
        throws MetaDBException;

    public abstract IMetaObject get(Class class1, Map map)
        throws MetaDBException;

    public abstract IMetaObject get(String s, String as[], Object aobj[])
        throws MetaDBException;

    public abstract IMetaObject get(Class class1, String as[], Object aobj[])
        throws MetaDBException;

    public transient abstract IMetaObject get(Class class1, String s, Object aobj[])
        throws MetaDBException;

    public abstract IMetaObject loadHistoryObject(IEntity ientity, int i)
        throws MetaDBException;

    public abstract IMetaObject loadHistoryObject(String s, int i)
        throws MetaDBException;

    public abstract IMetaObject loadHistoryObject(Class class1, int i)
        throws MetaDBException;

    public abstract List query(IEntity ientity, String s, Map map)
        throws MetaDBException;

    public abstract List query(String s, String s1, Map map)
        throws MetaDBException;

    public abstract List query(Class class1, String s, Map map)
        throws MetaDBException;

    public abstract List query(String s, Map map)
        throws MetaDBException;

    public abstract IMetaDBQuery createQuery(IEntity ientity, String s)
        throws MetaDBException;

    public abstract IMetaDBQuery createQuery(String s, String s1)
        throws MetaDBException;

    public abstract IMetaDBQuery createQuery(Class class1, String s)
        throws MetaDBException;

    public abstract IMetaDBQuery createSqlQuery(String s)
        throws MetaDBException;

    public abstract ISQLUpdater createSqlUpdater(String s);

    public abstract void begin()
        throws MetaDBException;

    public abstract void commit()
        throws MetaDBException;

    public abstract void rollback()
        throws MetaDBException;

    public abstract void close()
        throws MetaDBException;
}
