// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConnectionProviderImpl.java

package com.sinitek.base.metadb.config.impl;

import com.sinitek.base.metadb.config.IDataSourceProvider;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.HibernateException;
import org.hibernate.connection.ConnectionProvider;

public class ConnectionProviderImpl
    implements ConnectionProvider
{

    public ConnectionProviderImpl()
    {
    }

    public void configure(Properties properties)
        throws HibernateException
    {
    }

    public Connection getConnection()
        throws SQLException
    {
        return dsProvider.getDataSource().getConnection();
    }

    public void closeConnection(Connection conn)
        throws SQLException
    {
        conn.close();
    }

    public void close()
        throws HibernateException
    {
    }

    public boolean supportsAggressiveRelease()
    {
        return true;
    }

    public static IDataSourceProvider dsProvider;
}
