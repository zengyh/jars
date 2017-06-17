// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSourceStrategyConfig.java

package com.sinitek.base.datasource.config.impl;

import com.sinitek.base.datasource.config.IDataSourceStrategyConfig;
import org.apache.commons.lang.StringUtils;

public class DataSourceStrategyConfig
    implements IDataSourceStrategyConfig
{

    public DataSourceStrategyConfig(String environment)
    {
        if(StringUtils.isBlank(environment))
            environment = "real";
        this.environment = environment;
        if(environment.equalsIgnoreCase("test"))
        {
            setCheckoutConnectionInterval(20000L);
            setCheckoutConnectionSql("select 1 from dual");
            setFirstReconnectionTimes(3);
            setFirstReconnectionInterval(10000L);
            setLongTimeReconnectionTimes(-1);
            setLongTimeReconnectionInterval(30000L);
        } else
        {
            setCheckoutConnectionInterval(60000L);
            setCheckoutConnectionSql("select 1 from dual");
            setFirstReconnectionTimes(5);
            setFirstReconnectionInterval(3000L);
            setLongTimeReconnectionTimes(-1);
            setLongTimeReconnectionInterval(5000L);
        }
    }

    public String getEnvironment()
    {
        return environment;
    }

    public void setEnvironment(String environment)
    {
        this.environment = environment;
    }

    public long getCheckoutConnectionInterval()
    {
        return checkoutConnectionInterval;
    }

    public void setCheckoutConnectionInterval(long checkoutConnectionInterval)
    {
        this.checkoutConnectionInterval = checkoutConnectionInterval;
    }

    public String getCheckoutConnectionSql()
    {
        return checkoutConnectionSql;
    }

    public void setCheckoutConnectionSql(String checkoutConnectionSql)
    {
        this.checkoutConnectionSql = checkoutConnectionSql;
    }

    public long getFirstReconnectionInterval()
    {
        return firstReconnectionInterval;
    }

    public void setFirstReconnectionInterval(long firstReconnectionInterval)
    {
        this.firstReconnectionInterval = firstReconnectionInterval;
    }

    public int getFirstReconnectionTimes()
    {
        return firstReconnectionTimes;
    }

    public void setFirstReconnectionTimes(int firstReconnectionTimes)
    {
        this.firstReconnectionTimes = firstReconnectionTimes;
    }

    public long getLongTimeReconnectionInterval()
    {
        return LongTimeReconnectionInterval;
    }

    public void setLongTimeReconnectionInterval(long longTimeReconnectionInterval)
    {
        LongTimeReconnectionInterval = longTimeReconnectionInterval;
    }

    public int getLongTimeReconnectionTimes()
    {
        return longTimeReconnectionTimes;
    }

    public void setLongTimeReconnectionTimes(int longTimeReconnectionTimes)
    {
        this.longTimeReconnectionTimes = longTimeReconnectionTimes;
    }

    private String environment;
    private long checkoutConnectionInterval;
    private String checkoutConnectionSql;
    private long firstReconnectionInterval;
    private int firstReconnectionTimes;
    private long LongTimeReconnectionInterval;
    private int longTimeReconnectionTimes;
}
