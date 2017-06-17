// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSourceInfoConfig.java

package com.sinitek.base.datasource.config.impl;

import com.sinitek.base.datasource.config.IDataSourceInfoConfig;
import com.sinitek.base.datasource.config.IDataSourceStrategyConfig;
import java.util.Hashtable;
import java.util.Map;

public class DataSourceInfoConfig
    implements IDataSourceInfoConfig
{

    public DataSourceInfoConfig()
    {
        localDataSourceInfo = new Hashtable();
        remoteDataSourceInfo = new Hashtable();
    }

    public Map getLocalDataSourceInfo()
    {
        return localDataSourceInfo;
    }

    public Map getRemoteDataSourceInfo()
    {
        return remoteDataSourceInfo;
    }

    public IDataSourceStrategyConfig getDataSourceStrategyConfig()
    {
        return dataSourceStrategyConfig;
    }

    public void setDataSourceStrategy(IDataSourceStrategyConfig dataSourceStrategyConfig)
    {
        this.dataSourceStrategyConfig = dataSourceStrategyConfig;
    }

    public void setLocalDataSourceInfo(Map localDataSourceInfo)
    {
        this.localDataSourceInfo = localDataSourceInfo;
    }

    public void setRemoteDataSourceInfo(Map remoteDataSourceInfo)
    {
        this.remoteDataSourceInfo = remoteDataSourceInfo;
    }

    private Map localDataSourceInfo;
    private Map remoteDataSourceInfo;
    private IDataSourceStrategyConfig dataSourceStrategyConfig;
}
