// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDataSourceStrategyConfig.java

package com.sinitek.base.datasource.config;


public interface IDataSourceStrategyConfig
{

    public abstract String getEnvironment();

    public abstract long getCheckoutConnectionInterval();

    public abstract String getCheckoutConnectionSql();

    public abstract long getFirstReconnectionInterval();

    public abstract int getFirstReconnectionTimes();

    public abstract long getLongTimeReconnectionInterval();

    public abstract int getLongTimeReconnectionTimes();

    public static final String ENVIRONMENT_REAL = "real";
    public static final String ENVIRONMENT_TEST = "test";
}
