// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractDataSourceFactoryBean.java

package com.sinitek.base.datasource.support;

import com.sinitek.base.datasource.IDataSourceProvider;
import com.sinitek.base.datasource.config.IDataSourceInfoConfig;
import com.sinitek.base.datasource.config.IDataSourceStrategyConfig;
import com.sinitek.base.datasource.config.impl.DataSourceInfoConfig;
import com.sinitek.base.datasource.config.impl.DataSourceStrategyConfig;
import com.sinitek.base.datasource.impl.DataSourceProviderImpl;
import java.util.*;
import javax.sql.DataSource;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.*;

public abstract class AbstractDataSourceFactoryBean
    implements FactoryBean, InitializingBean, BeanNameAware
{

    public AbstractDataSourceFactoryBean()
    {
    }

    public void afterPropertiesSet()
        throws Exception
    {
        IDataSourceProvider provider = DataSourceProviderImpl.getInstance();
        provider.addDataSourceAndSetStrategy(getDataSourceConfig());
        dataSource = provider.getDataSource(name);
    }

    protected abstract Map getLocalDataSourceConfig();

    protected abstract Map getRemoteDataSourceConfig();

    protected IDataSourceStrategyConfig getStrategyConfig()
    {
        if(strategyProperties == null)
            return new DataSourceStrategyConfig("real");
        String mode = strategyProperties.getProperty("strategy", "real");
        strategyProperties.remove("strategy");
        DataSourceStrategyConfig ret = new DataSourceStrategyConfig(mode);
        for(Enumeration props = strategyProperties.propertyNames(); props.hasMoreElements();)
        {
            String name = (String)props.nextElement();
            try
            {
                if(PropertyUtils.isWriteable(ret, name))
                    BeanUtils.setProperty(ret, name, strategyProperties.getProperty(name));
                else
                    LOGGER.warn((new StringBuilder()).append("\u65E0\u6CD5\u8BC6\u522B\u7684\u8FDE\u63A5\u6C60\u7B56\u7565\u5C5E\u6027[").append(name).append("]").toString());
            }
            catch(Exception ex)
            {
                LOGGER.error((new StringBuilder()).append("\u5904\u7406\u8FDE\u63A5\u6C60\u7B56\u7565\u5C5E\u6027[").append(name).append("]\u5931\u8D25").toString(), ex);
            }
        }

        return ret;
    }

    protected IDataSourceInfoConfig getDataSourceConfig()
    {
        DataSourceInfoConfig ret = new DataSourceInfoConfig();
        ret.setDataSourceStrategy(getStrategyConfig());
        ret.setLocalDataSourceInfo(getLocalDataSourceConfig());
        ret.setRemoteDataSourceInfo(getRemoteDataSourceConfig());
        return ret;
    }

    public Object getObject()
        throws Exception
    {
        return dataSource;
    }

    public Class getObjectType()
    {
        return javax/sql/DataSource;
    }

    public boolean isSingleton()
    {
        return true;
    }

    public Properties getStrategyProperties()
    {
        return strategyProperties;
    }

    public void setStrategyProperties(Properties strategyProperties)
    {
        this.strategyProperties = strategyProperties;
    }

    public final void setBeanName(String name)
    {
        this.name = name;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/datasource/support/AbstractDataSourceFactoryBean);
    private DataSource dataSource;
    private Properties strategyProperties;
    protected String name;

}
