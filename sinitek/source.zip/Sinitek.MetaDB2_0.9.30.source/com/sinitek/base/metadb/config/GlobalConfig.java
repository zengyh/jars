// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GlobalConfig.java

package com.sinitek.base.metadb.config;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.config.impl.BaseDataSourceProviderImpl;
import com.sinitek.base.metadb.config.impl.ExtDataSourceProvider;
import com.sinitek.base.metadb.config.impl.JndiDataSourceProviderImpl;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.config:
//            MetaDBConfigException, IDataSourceProvider

public class GlobalConfig
{

    public GlobalConfig()
        throws MetaDBException
    {
        this("");
    }

    public GlobalConfig(String fileName)
        throws MetaDBException
    {
        Properties sysProps = System.getProperties();
        if(StringUtils.isBlank(fileName))
            fileName = sysProps.getProperty("com.sinitek.base.metadb.config.GlobalConfig.CONFIGFILE", "metadbconfig.properties");
        LOGGER.info((new StringBuilder()).append("MetaDB\u914D\u7F6E\u6587\u4EF6\u8DEF\u5F84\u4E3A[").append(fileName).append("]").toString());
        String readMethod = sysProps.getProperty("com.sinitek.base.metadb.config.GlobalConfig.CONFIGREADMETHOD", "classpath");
        LOGGER.info((new StringBuilder()).append("MetaDB\u914D\u7F6E\u6587\u4EF6\u52A0\u8F7D\u65B9\u5F0F\u4E3A[").append(readMethod).append("]").toString());
        if("classpath".equalsIgnoreCase(readMethod))
        {
            URL configUrl = Thread.currentThread().getContextClassLoader().getResource(fileName);
            if(configUrl == null)
                throw new MetaDBConfigException("0004", new Object[] {
                    fileName
                });
            readConfigFile(configUrl);
        } else
        if("file".equalsIgnoreCase(readMethod))
        {
            File file = new File(fileName);
            if(file.exists() && file.isFile())
                try
                {
                    readConfigFile(file.toURL());
                }
                catch(MalformedURLException e)
                {
                    throw new MetaDBConfigException("0006", e, new Object[] {
                        fileName
                    });
                }
            else
                throw new MetaDBConfigException("0004", new Object[] {
                    fileName
                });
        }
    }

    public GlobalConfig(URL configFile)
        throws MetaDBException
    {
        if(configFile == null)
        {
            throw new MetaDBConfigException("0005");
        } else
        {
            LOGGER.info((new StringBuilder()).append("\u6307\u5B9A\u7684MetaDB\u914D\u7F6E\u6587\u4EF6\u4E3A[").append(configFile.getFile()).append("]").toString());
            readConfigFile(configFile);
            return;
        }
    }

    public IDataSourceProvider getDataSourceProvider()
    {
        return dataSourceProvider;
    }

    public Properties getHibernateProperties()
    {
        return hibernateProperties;
    }

    public Properties getMetaDBProperties()
    {
        return metaDBProperties;
    }

    public Properties getMongoDBProperties()
    {
        return mongoDBProperties;
    }

    public Properties getCacheProperties()
    {
        return cacheProperties;
    }

    private void readConfigFile(URL configFile)
        throws MetaDBException
    {
        Properties prop;
        InputStream configIs;
        Exception exception;
        LOGGER.debug((new StringBuilder()).append("\u5F00\u59CB\u8BFB\u53D6MetaDB\u914D\u7F6E\u6587\u4EF6\uFF0C\u8DEF\u5F84[").append(configFile.getFile()).append("]").toString());
        prop = new Properties();
        configIs = null;
        try
        {
            configIs = configFile.openStream();
            prop.load(configIs);
        }
        catch(Exception ex)
        {
            throw new MetaDBConfigException("0006", ex);
        }
        finally
        {
            if(configIs == null) goto _L0; else goto _L0
        }
        if(configIs != null)
            try
            {
                configIs.close();
            }
            catch(IOException e)
            {
                LOGGER.warn("\u5173\u95EDMetaDB\u914D\u7F6E\u6587\u4EF6\u7684\u8F93\u5165\u6D41\u65F6\u5931\u8D25", e);
            }
        break MISSING_BLOCK_LABEL_121;
        try
        {
            configIs.close();
        }
        catch(IOException e)
        {
            LOGGER.warn("\u5173\u95EDMetaDB\u914D\u7F6E\u6587\u4EF6\u7684\u8F93\u5165\u6D41\u65F6\u5931\u8D25", e);
        }
        throw exception;
        String dsStrategy = prop.getProperty("datasource.strategy");
        if(dsStrategy == null)
            throw new MetaDBConfigException("0007");
        if("jndi".equalsIgnoreCase(dsStrategy.trim()))
        {
            String jndi = prop.getProperty("datasource.jndi");
            if(jndi == null)
                throw new MetaDBConfigException("0009");
            LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\u91C7\u7528\u672C\u5730jndi\u65B9\u5F0F\uFF0CJNDI\u540D[").append(jndi).append("]").toString());
            dataSourceProvider = new JndiDataSourceProviderImpl(jndi);
        } else
        if("basic".equalsIgnoreCase(dsStrategy.trim()))
        {
            LOGGER.debug("\u6570\u636E\u6E90\u91C7\u7528\u57FA\u672C\u65B9\u5F0F\u5B9A\u4E49");
            dataSourceProvider = new BaseDataSourceProviderImpl(prop);
        } else
        if("ext".equalsIgnoreCase(dsStrategy.trim()))
        {
            String extFile = prop.getProperty("datasource.extconfigfile");
            String extName = prop.getProperty("datasource.extdatasourcename");
            LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\u91C7\u7528\u5916\u90E8\u5B9A\u4E49\u6A21\u5F0F\uFF0C\u5916\u90E8\u914D\u7F6E\u6587\u4EF6[").append(extFile).append("]\uFF0C\u5916\u90E8\u6570\u636E\u6E90\u540D\u79F0[").append(extName).append("]").toString());
            dataSourceProvider = new ExtDataSourceProvider(extFile, extName);
        } else
        if("custom".equalsIgnoreCase(dsStrategy.trim()))
        {
            String providerName = prop.getProperty("datasource.provider");
            if(StringUtils.isEmpty(providerName))
                throw new MetaDBConfigException("0051");
            LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\u91C7\u7528\u81EA\u5B9A\u4E49\u6A21\u5F0F\uFF0C\u5B9E\u73B0\u7C7B[").append(providerName).append("]").toString());
            try
            {
                dataSourceProvider = (IDataSourceProvider)Class.forName(providerName).newInstance();
            }
            catch(Exception ex)
            {
                throw new MetaDBConfigException("0052", ex, new Object[] {
                    providerName
                });
            }
        } else
        if("none".equalsIgnoreCase(dsStrategy.trim()))
            LOGGER.debug("\u4E0D\u4F7F\u7528\u6570\u636E\u6E90");
        else
            throw new MetaDBConfigException("0008", new Object[] {
                dsStrategy
            });
        String dsType = StringUtils.lowerCase(prop.getProperty("datasource.type"));
        if("mysql".equals(dsType))
            MetaDBContext.TYPE = 1;
        Properties _hibProp = new Properties();
        Properties _metaDBProp = new Properties();
        Properties _mongoDBProp = new Properties();
        Properties _cacheProp = new Properties();
        _metaDBProp.setProperty("datasource.strategy", dsStrategy.trim());
        Enumeration enumer = prop.propertyNames();
        do
        {
            if(!enumer.hasMoreElements())
                break;
            String propName = (String)enumer.nextElement();
            if(propName.startsWith("hibernate"))
                _hibProp.setProperty(propName, prop.getProperty(propName));
            else
            if(propName.startsWith("cache") || propName.startsWith("sync"))
                _cacheProp.setProperty(propName, prop.getProperty(propName));
            else
            if(propName.startsWith("metadb"))
                _metaDBProp.setProperty(propName, prop.getProperty(propName));
            else
            if(propName.startsWith("mongodb"))
                _mongoDBProp.setProperty(propName, prop.getProperty(propName));
        } while(true);
        hibernateProperties = _hibProp;
        metaDBProperties = _metaDBProp;
        cacheProperties = _cacheProp;
        mongoDBProperties = _mongoDBProp;
        return;
    }

    public static final String DEFAULT_CONFIG_PATH = "metadbconfig.properties";
    public static final String CONFIG_PATH_PARAM_NAME = "com.sinitek.base.metadb.config.GlobalConfig.CONFIGFILE";
    public static final String CONFIG_READMETHOD_PARAM_NAME = "com.sinitek.base.metadb.config.GlobalConfig.CONFIGREADMETHOD";
    private IDataSourceProvider dataSourceProvider;
    private Properties hibernateProperties;
    private Properties metaDBProperties;
    private Properties mongoDBProperties;
    private Properties cacheProperties;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CONFIG;
    }
}
