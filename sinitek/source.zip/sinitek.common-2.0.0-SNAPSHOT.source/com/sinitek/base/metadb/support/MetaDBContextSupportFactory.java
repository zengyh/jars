// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBContextSupportFactory.java

package com.sinitek.base.metadb.support;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.support:
//            IMetaDBContextSupport, MetaDBContextSupportException, IContextSupportConfigInfo, ContextSupportConfigReader, 
//            MetaDBContextSupportBuilder

public class MetaDBContextSupportFactory
{

    public MetaDBContextSupportFactory()
    {
        proxyInterfaceMap = new Hashtable();
        proxyNameMap = new Hashtable();
        configFileMap = new Hashtable();
        String configFile = System.getProperties().getProperty("MetaDBContextSupportFactory.configFile", "contextsupport.properties");
        Properties properties = new Properties();
        readPropertiesFile(configFile, properties);
        Enumeration enumeration = properties.propertyNames();
        do
        {
            if(!enumeration.hasMoreElements())
                break;
            String name = (String)enumeration.nextElement();
            if(name.startsWith("metadb.contextsupport.configfile."))
                readSingleConfigFile(properties.getProperty(name));
        } while(true);
    }

    public void registConfigFile(String configFile)
    {
        readSingleConfigFile(configFile);
    }

    public IMetaDBContextSupport getContextSupport(Class interfaceName)
    {
        IMetaDBContextSupport ret = (IMetaDBContextSupport)proxyInterfaceMap.get(interfaceName);
        if(ret == null)
            throw new MetaDBContextSupportException("0017", new Object[] {
                interfaceName.getName()
            });
        else
            return ret;
    }

    public IMetaDBContextSupport getContextSupport(String name)
    {
        IMetaDBContextSupport ret = (IMetaDBContextSupport)proxyNameMap.get(name);
        if(ret == null)
            throw new MetaDBContextSupportException("0018", new Object[] {
                name
            });
        else
            return ret;
    }

    protected void readSingleConfigFile(String configFile)
    {
        if(configFileMap.containsKey(configFile))
        {
            LOGGER.warn((new StringBuilder()).append("ContextSupport\u914D\u7F6E\u6587\u4EF6[").append(configFile).append("]\u5DF2\u7ECF\u88AB\u88C5\u8F7D").toString());
            return;
        }
        LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u8BFB\u53D6ContextSupport\u914D\u7F6E\u6587\u4EF6[").append(configFile).append("]").toString());
        java.net.URL url = Thread.currentThread().getContextClassLoader().getResource(configFile);
        if(url != null)
            try
            {
                List configInfos = ContextSupportConfigReader.readConfig(url);
                Map _tempInterface = new HashMap();
                Map _tempName = new HashMap();
                IContextSupportConfigInfo config;
                IMetaDBContextSupport proxy;
                for(Iterator iter = configInfos.iterator(); iter.hasNext(); _tempName.put(config.getName(), proxy))
                {
                    config = (IContextSupportConfigInfo)iter.next();
                    if(proxyInterfaceMap.containsKey(config.getInterface()))
                        throw new MetaDBContextSupportException("0015", new Object[] {
                            config.getInterface().getName()
                        });
                    if(proxyNameMap.containsKey(config.getName()))
                        throw new MetaDBContextSupportException("0016", new Object[] {
                            config.getInterface().getName()
                        });
                    proxy = MetaDBContextSupportBuilder.createProxy(config);
                    _tempInterface.put(config.getInterface(), proxy);
                }

                proxyInterfaceMap.putAll(_tempInterface);
                proxyNameMap.putAll(_tempName);
                LOGGER.info((new StringBuilder()).append("ContextSupport\u914D\u7F6E\u6587\u4EF6[").append(configFile).append("]\u8BFB\u53D6\u6210\u529F").toString());
                configFileMap.put(configFile, Boolean.TRUE);
            }
            catch(Exception ex)
            {
                LOGGER.warn((new StringBuilder()).append("ContextSupport\u914D\u7F6E\u6587\u4EF6[").append(configFile).append("]\u8BFB\u53D6\u5931\u8D25").toString(), ex);
            }
        else
            LOGGER.warn((new StringBuilder()).append("ContextSupport\u914D\u7F6E\u6587\u4EF6[").append(configFile).append("]\u4E0D\u5B58\u5728").toString());
    }

    protected void readPropertiesFile(String configFile, Properties properties)
    {
        InputStream is = null;
        try
        {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
            if(is != null)
                properties.load(is);
            else
                LOGGER.warn((new StringBuilder()).append("\u73AF\u5883\u914D\u7F6E\u6587\u4EF6[").append(configFile).append("]\u4E0D\u5B58\u5728").toString());
        }
        catch(Exception ex)
        {
            throw new MetaDBContextSupportException("0014", ex, new Object[] {
                configFile
            });
        }
        finally
        {
            if(is != null)
                try
                {
                    is.close();
                }
                catch(IOException e)
                {
                    LOGGER.error("\u5173\u95ED\u73AF\u5883\u914D\u7F6E\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
                }
        }
    }

    public static final String PARAM_CONFIGFILE = "MetaDBContextSupportFactory.configFile";
    protected static final Logger LOGGER;
    protected Map proxyInterfaceMap;
    protected Map proxyNameMap;
    protected Map configFileMap;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CORE;
    }
}
