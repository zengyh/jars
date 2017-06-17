// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSourceProviderImpl.java

package com.sinitek.base.datasource.impl;

import com.sinitek.base.datasource.*;
import com.sinitek.base.datasource.config.IDataSourceInfoConfig;
import com.sinitek.base.datasource.config.IDataSourceStrategyConfig;
import java.util.*;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.datasource.impl:
//            DataSourceCreatorImpl

public class DataSourceProviderImpl
    implements IDataSourceProvider
{

    private DataSourceProviderImpl()
    {
        dataSourceMap = new Hashtable();
    }

    public static IDataSourceProvider getInstance()
    {
        if(null == instance)
            instance = new DataSourceProviderImpl();
        return instance;
    }

    public void addDataSourceAndSetStrategy(IDataSourceInfoConfig infoConfig)
        throws DataSourceException
    {
        dataSourceStrategyConfig = infoConfig.getDataSourceStrategyConfig();
        Map dataSourceMap = DataSourceCreatorImpl.getInstance().createDataSource(infoConfig);
        if(null != dataSourceMap && !dataSourceMap.isEmpty())
            addDataSource(dataSourceMap);
    }

    public DataSource getDataSource(String dataSourceName)
        throws DataSourceException
    {
        DataSource ret = (DataSource)dataSourceMap.get(dataSourceName);
        if(ret == null)
            throw new DataSourceException("0013", new Object[] {
                dataSourceName
            });
        else
            return ret;
    }

    public Map getDataSource()
        throws DataSourceException
    {
        return dataSourceMap;
    }

    public void addDataSource(Map dataSourceMap)
    {
        for(Iterator newDataSourceKeyIter = dataSourceMap.keySet().iterator(); newDataSourceKeyIter.hasNext();)
        {
            String dataSourceName = (String)newDataSourceKeyIter.next();
            if(this.dataSourceMap.containsKey(dataSourceName))
            {
                LOGGER.error((new StringBuilder()).append("\u6DFB\u52A0\u6570\u636E\u6E90\u5931\u8D25\uFF0C\u5B58\u5728\u91CD\u590D\u7684\u6570\u636E\u6E90\u540D\u79F0[").append(dataSourceName).append("]").toString());
                throw new DataSourceException("0004", new Object[] {
                    dataSourceName
                });
            }
        }

        this.dataSourceMap.putAll(dataSourceMap);
    }

    public IDataSourceStrategyConfig getDataSourceStrategy()
    {
        return dataSourceStrategyConfig;
    }

    public void setDataSourceProvider(IDataSourceStrategyConfig dataSourceStrategyConfig)
    {
        this.dataSourceStrategyConfig = dataSourceStrategyConfig;
    }

    protected Map dataSourceMap;
    protected IDataSourceStrategyConfig dataSourceStrategyConfig;
    private static DataSourceProviderImpl instance;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/datasource/impl/DataSourceProviderImpl);

}
