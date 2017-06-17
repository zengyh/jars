// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JndiDataSourceProviderImpl.java

package com.sinitek.base.metadb.config.impl;

import com.sinitek.base.metadb.MetaDBException;
import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.config.IDataSourceProvider;
import com.sinitek.base.metadb.config.MetaDBConfigException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class JndiDataSourceProviderImpl
    implements IDataSourceProvider
{

    public JndiDataSourceProviderImpl(String jndi)
    {
        this.jndi = jndi;
    }

    public DataSource getDataSource()
        throws MetaDBException
    {
        synchronized(this)
        {
            if(dataSource == null)
                try
                {
                    Context ctx = new InitialContext();
                    Object remoteObj = ctx.lookup(jndi);
                    dataSource = (DataSource)remoteObj;
                }
                catch(Exception e)
                {
                    throw new MetaDBConfigException("0001", e, new Object[] {
                        jndi
                    });
                }
        }
        return dataSource;
    }

    public void reflashDataSource()
    {
        synchronized(this)
        {
            LOGGER.info("\u5237\u65B0\u6570\u636E\u6E90\u63D0\u4F9B\u5668");
            dataSource = null;
        }
    }

    private DataSource dataSource;
    private String jndi;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CONFIG;
    }
}
