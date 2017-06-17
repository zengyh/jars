// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDataSourceProvider.java

package com.sinitek.base.metadb.config;

import com.sinitek.base.metadb.MetaDBException;
import javax.sql.DataSource;

public interface IDataSourceProvider
{

    public abstract DataSource getDataSource()
        throws MetaDBException;

    public abstract void reflashDataSource();
}
