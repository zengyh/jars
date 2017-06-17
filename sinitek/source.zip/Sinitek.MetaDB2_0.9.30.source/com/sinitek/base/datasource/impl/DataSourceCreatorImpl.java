// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSourceCreatorImpl.java

package com.sinitek.base.datasource.impl;

import com.sinitek.base.datasource.DataSourceException;
import com.sinitek.base.datasource.IDataSourceCreator;
import com.sinitek.base.datasource.config.*;
import java.util.*;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.base.datasource.impl:
//            StrongC3P0DataSource, StrongDBCPDataSource, StrongJNDIDataSource

public class DataSourceCreatorImpl
    implements IDataSourceCreator
{

    private DataSourceCreatorImpl()
    {
    }

    public static IDataSourceCreator getInstance()
    {
        if(null == instance)
            instance = new DataSourceCreatorImpl();
        return instance;
    }

    public DataSource createLocalDataSource(ILocalDataSourceConfig localDataSourceConfig)
        throws DataSourceException
    {
        if(null == localDataSourceConfig)
            return null;
        String provider = localDataSourceConfig.getProvider();
        if(StringUtils.isEmpty(provider) || provider.equalsIgnoreCase("c3p0"))
            return new StrongC3P0DataSource(localDataSourceConfig);
        if(provider.equalsIgnoreCase("dbcp"))
            return new StrongDBCPDataSource(localDataSourceConfig);
        else
            throw new DataSourceException("0009", new Object[] {
                provider
            });
    }

    public DataSource createRemoteDataSource(IRemoteDataSourceConfig remoteDataSourceConfig)
        throws DataSourceException
    {
        if(null == remoteDataSourceConfig)
            return null;
        else
            return new StrongJNDIDataSource(remoteDataSourceConfig);
    }

    public Map createLocalDataSource(Map localDataSourceMap)
        throws DataSourceException
    {
        if(null == localDataSourceMap || localDataSourceMap.isEmpty())
            return new HashMap();
        Map dataSourceMap = new HashMap();
        java.util.Map.Entry entry;
        DataSource dataSource;
        for(Iterator dataSourceIter = localDataSourceMap.entrySet().iterator(); dataSourceIter.hasNext(); dataSourceMap.put(entry.getKey(), dataSource))
        {
            entry = (java.util.Map.Entry)dataSourceIter.next();
            ILocalDataSourceConfig localDataSourceConfig = (ILocalDataSourceConfig)entry.getValue();
            dataSource = createLocalDataSource(localDataSourceConfig);
        }

        return dataSourceMap;
    }

    public Map createRemoteDataSource(Map remoteDataSourceMap)
        throws DataSourceException
    {
        if(null == remoteDataSourceMap || remoteDataSourceMap.isEmpty())
            return new HashMap();
        Map dataSourceMap = new HashMap();
        java.util.Map.Entry entry;
        DataSource dataSource;
        for(Iterator dataSourceIter = remoteDataSourceMap.entrySet().iterator(); dataSourceIter.hasNext(); dataSourceMap.put(entry.getKey(), dataSource))
        {
            entry = (java.util.Map.Entry)dataSourceIter.next();
            IRemoteDataSourceConfig remoteDataSourceConfig = (IRemoteDataSourceConfig)entry.getValue();
            dataSource = createRemoteDataSource(remoteDataSourceConfig);
        }

        return dataSourceMap;
    }

    public Map createDataSource(IDataSourceInfoConfig dataSourceInfoConfig)
        throws DataSourceException
    {
        if(null == dataSourceInfoConfig)
        {
            return null;
        } else
        {
            Map dataSourceMap = new HashMap();
            Map localDataSourceInfoMap = dataSourceInfoConfig.getLocalDataSourceInfo();
            dataSourceMap.putAll(createLocalDataSource(localDataSourceInfoMap));
            Map remoteDataSourceInfoMap = dataSourceInfoConfig.getRemoteDataSourceInfo();
            dataSourceMap.putAll(createRemoteDataSource(remoteDataSourceInfoMap));
            return dataSourceMap;
        }
    }

    private static IDataSourceCreator instance;
}
