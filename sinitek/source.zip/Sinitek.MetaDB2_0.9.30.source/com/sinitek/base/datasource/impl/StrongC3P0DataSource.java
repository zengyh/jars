// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrongC3P0DataSource.java

package com.sinitek.base.datasource.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sinitek.base.datasource.DataSourceException;
import com.sinitek.base.datasource.IDataSourceProvider;
import com.sinitek.base.datasource.config.IDataSourceStrategyConfig;
import com.sinitek.base.datasource.config.ILocalDataSourceConfig;
import java.beans.PropertyVetoException;
import java.net.URL;
import java.sql.SQLFeatureNotSupportedException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.datasource.impl:
//            StrongLocalDataSource, DataSourceProviderImpl

public class StrongC3P0DataSource extends StrongLocalDataSource
{

    public StrongC3P0DataSource(ILocalDataSourceConfig localDataSourceConfig)
    {
        this.localDataSourceConfig = localDataSourceConfig;
        try
        {
            init(localDataSourceConfig.isNeedBind());
        }
        catch(Exception e)
        {
            startReconnect();
        }
    }

    public String getDataSourceName()
    {
        return localDataSourceConfig.getDataSourceName();
    }

    public DataSource getOriDataSource()
    {
        return ds;
    }

    public void createOriDataSource()
    {
        try
        {
            String environment = DataSourceProviderImpl.getInstance().getDataSourceStrategy().getEnvironment();
            System.setProperty("com.mchange.v2.c3p0.cfg.xml", Thread.currentThread().getContextClassLoader().getResource("com/sinitek/base/datasource/config/c3p0-config.xml").toExternalForm().substring(6));
            ComboPooledDataSource cpds = new ComboPooledDataSource(environment);
            cpds.setDataSourceName(localDataSourceConfig.getDataSourceName());
            cpds.setDriverClass(localDataSourceConfig.getDriverClassName());
            cpds.setJdbcUrl(localDataSourceConfig.getUrl());
            cpds.setUser(localDataSourceConfig.getUserName());
            cpds.setPassword(localDataSourceConfig.getPassword());
            ds = cpds;
        }
        catch(PropertyVetoException e)
        {
            LOGGER.error((new StringBuilder()).append("\u521B\u5EFA\u6570\u636E\u6E90[").append(getDataSourceName()).append("]\u65F6\uFF0C\u914D\u7F6Edriver[").append(localDataSourceConfig.getDriverClassName()).append("]\u9519\u8BEF\uFF1A").append(e.getMessage()).toString(), e);
            throw new DataSourceException("0007", e, new Object[] {
                localDataSourceConfig.getDataSourceName(), localDataSourceConfig.getDriverClassName(), e.getMessage()
            });
        }
    }

    public boolean getNeedBind()
    {
        return localDataSourceConfig.isNeedBind();
    }

    public java.util.logging.Logger getParentLogger()
        throws SQLFeatureNotSupportedException
    {
        return null;
    }

    private DataSource ds;
    private ILocalDataSourceConfig localDataSourceConfig;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/datasource/impl/StrongC3P0DataSource);

}
