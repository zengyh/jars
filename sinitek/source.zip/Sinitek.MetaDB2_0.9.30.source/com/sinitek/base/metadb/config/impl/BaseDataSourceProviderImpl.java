// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseDataSourceProviderImpl.java

package com.sinitek.base.metadb.config.impl;

import com.sinitek.base.metadb.MetaDBException;
import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.config.IDataSourceProvider;
import com.sinitek.base.metadb.config.MetaDBConfigException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

public class BaseDataSourceProviderImpl
    implements IDataSourceProvider
{

    public BaseDataSourceProviderImpl(Properties configProp)
    {
        this.configProp = configProp;
    }

    public DataSource getDataSource()
        throws MetaDBException
    {
        synchronized(this)
        {
            if(dataSource == null)
            {
                BasicDataSource temp = new BasicDataSource();
                checkProperty("datasource.driverClassName");
                checkProperty("datasource.username");
                checkProperty("datasource.password");
                checkProperty("datasource.url");
                setProp(temp);
                dataSource = temp;
            }
        }
        return dataSource;
    }

    public void reflashDataSource()
    {
        synchronized(this)
        {
            LOGGER.info("\u5237\u65B0\u6570\u636E\u6E90\u63D0\u4F9B\u5668");
            if(dataSource != null)
                try
                {
                    dataSource.close();
                }
                catch(SQLException e)
                {
                    LOGGER.warn("\u5173\u95ED\u6570\u636E\u6E90\u65F6\u53D1\u751F\u9519\u8BEF", e);
                }
            dataSource = null;
        }
    }

    private void checkProperty(String propKey)
        throws MetaDBConfigException
    {
        if(!configProp.containsKey(propKey))
            throw new MetaDBConfigException("0002", new Object[] {
                propKey
            });
        else
            return;
    }

    private void setProp(BasicDataSource ds)
        throws MetaDBConfigException
    {
        Enumeration enumeration;
        ds.setDefaultAutoCommit(false);
        enumeration = configProp.propertyNames();
_L2:
        String propName;
        String propValue;
        String key;
        do
        {
            if(!enumeration.hasMoreElements())
                break MISSING_BLOCK_LABEL_110;
            key = (String)enumeration.nextElement();
        } while(!key.startsWith("datasource."));
        propName = key.substring(11);
        propValue = configProp.getProperty(key);
        if(!PropertyUtils.isWriteable(ds, propName)) goto _L2; else goto _L1
_L1:
        BeanUtils.setProperty(ds, propName, propValue);
          goto _L2
        Exception e;
        e;
        throw new MetaDBConfigException("0003", e, new Object[] {
            propName, propValue
        });
    }

    private static final Logger LOGGER;
    private BasicDataSource dataSource;
    private Properties configProp;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CONFIG;
    }
}
