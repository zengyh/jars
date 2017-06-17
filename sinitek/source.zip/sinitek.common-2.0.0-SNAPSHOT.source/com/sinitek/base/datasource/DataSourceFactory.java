// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSourceFactory.java

package com.sinitek.base.datasource;

import com.sinitek.base.datasource.config.DataSourceConfigFileReader;
import com.sinitek.base.datasource.impl.DataSourceProviderImpl;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.datasource:
//            IDataSourceProvider

public class DataSourceFactory
{

    private DataSourceFactory()
    {
        dataSourceConfigFileReader = new DataSourceConfigFileReader();
        dataSourceProvider = DataSourceProviderImpl.getInstance();
    }

    public static DataSourceFactory getInstance()
    {
        return getInstance(new String[] {
            DEFAULT_CONFIG_LOCATION
        });
    }

    public static DataSourceFactory getInstance(String dataSourceConfig)
    {
        return getInstance(new String[] {
            dataSourceConfig
        });
    }

    public static DataSourceFactory getInstance(String dataSourceConfigs[])
    {
        if(null == instance)
        {
            instance = new DataSourceFactory();
            LOGGER.debug("\u5F00\u59CB\u521D\u59CB\u5316\u6570\u636E\u6E90\uFF01");
            com.sinitek.base.datasource.config.IDataSourceInfoConfig infoConfig = instance.dataSourceConfigFileReader.addConfigLocation(dataSourceConfigs);
            instance.dataSourceProvider.addDataSourceAndSetStrategy(infoConfig);
            LOGGER.debug("\u521D\u59CB\u5316\u6570\u636E\u6E90\u6210\u529F\uFF01");
        }
        return instance;
    }

    public DataSource getDataSouce(String name)
    {
        return dataSourceProvider.getDataSource(name);
    }

    public IDataSourceProvider getDataSourceProvider()
    {
        return dataSourceProvider;
    }

    public void setDataSourceProvider(IDataSourceProvider dataSourceProvider)
    {
        this.dataSourceProvider = dataSourceProvider;
    }

    private static DataSourceFactory instance;
    private static String DEFAULT_CONFIG_LOCATION = "datasource.xml";
    private DataSourceConfigFileReader dataSourceConfigFileReader;
    private IDataSourceProvider dataSourceProvider;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/datasource/DataSourceFactory);

}
