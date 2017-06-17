// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExtDataSourceProvider.java

package com.sinitek.base.metadb.config.impl;

import com.sinitek.base.datasource.DataSourceFactory;
import com.sinitek.base.metadb.MetaDBException;
import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.config.IDataSourceProvider;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ExtDataSourceProvider
    implements IDataSourceProvider
{

    public ExtDataSourceProvider(String extConfigFile, String extDataSourceName)
    {
        if(StringUtils.isBlank(extDataSourceName))
        {
            extDataSourceName = "metadb";
            LOGGER.debug("\u4F7F\u7528\u9ED8\u8BA4\u6570\u636E\u6E90\u540D\u79F0[metadb]");
        } else
        {
            LOGGER.debug((new StringBuilder()).append("\u4F7F\u7528\u6570\u636E\u6E90\u540D\u79F0[").append(extDataSourceName).append("]").toString());
        }
        if(StringUtils.isNotBlank(extConfigFile))
        {
            LOGGER.debug((new StringBuilder()).append("\u4F7F\u7528\u5916\u90E8\u6570\u636E\u6E90\u914D\u7F6E\u6587\u4EF6[").append(extConfigFile).append("]").toString());
            dataSource = DataSourceFactory.getInstance(extConfigFile).getDataSouce(extDataSourceName);
        } else
        {
            LOGGER.debug("\u4F7F\u7528\u9ED8\u8BA4\u5916\u90E8\u6570\u636E\u6E90\u914D\u7F6E\u6587\u4EF6");
            dataSource = DataSourceFactory.getInstance().getDataSouce(extDataSourceName);
        }
    }

    public DataSource getDataSource()
        throws MetaDBException
    {
        return dataSource;
    }

    public void reflashDataSource()
    {
    }

    private static final Logger LOGGER;
    private DataSource dataSource;
    public static final String DEFAULT_EXT_DSNAME = "metadb";

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CONFIG;
    }
}
