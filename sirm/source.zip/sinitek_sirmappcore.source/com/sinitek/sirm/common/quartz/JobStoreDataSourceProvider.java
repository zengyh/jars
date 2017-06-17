// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JobStoreDataSourceProvider.java

package com.sinitek.sirm.common.quartz;

import com.sinitek.base.metadb.MetaDBContextFactory;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.quartz.utils.ConnectionProvider;

public class JobStoreDataSourceProvider
    implements ConnectionProvider
{

    public JobStoreDataSourceProvider()
    {
    }

    public Connection getConnection()
        throws SQLException
    {
        DataSource _ds = MetaDBContextFactory.getInstance().getDataSource();
        return _ds.getConnection();
    }

    public void shutdown()
        throws SQLException
    {
    }
}
