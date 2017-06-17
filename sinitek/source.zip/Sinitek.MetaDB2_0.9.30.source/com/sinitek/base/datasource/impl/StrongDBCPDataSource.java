// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrongDBCPDataSource.java

package com.sinitek.base.datasource.impl;

import com.sinitek.base.datasource.DataSourceException;
import com.sinitek.base.datasource.IDataSourceProvider;
import com.sinitek.base.datasource.config.IDataSourceStrategyConfig;
import com.sinitek.base.datasource.config.ILocalDataSourceConfig;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package com.sinitek.base.datasource.impl:
//            StrongLocalDataSource, DataSourceProviderImpl

public class StrongDBCPDataSource extends StrongLocalDataSource
{
    protected static class DBCPConfig
    {

        public static Map getProps(String environment)
        {
            return (Map)propsMap.get(environment);
        }

        private static void readConfigFile()
        {
            InputStream is;
            Exception exception;
            is = null;
            URL fileUrl = null;
            try
            {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                String fileName = URLDecoder.decode(DBCP_CONFIG_PATH, "utf-8");
                fileUrl = Thread.currentThread().getContextClassLoader().getResource(fileName);
                is = fileUrl.openStream();
                Document doc = db.parse(is);
                Element root = doc.getDocumentElement();
                NodeList environmentNodes = root.getElementsByTagName("environment");
                for(int i = 0; i < environmentNodes.getLength(); i++)
                {
                    Element environmentElement = (Element)environmentNodes.item(i);
                    String environmentName = environmentElement.getAttribute("name");
                    if(StringUtils.isBlank(environmentName))
                        environmentName = "real";
                    Map propMap = new HashMap();
                    NodeList propNodes = environmentElement.getElementsByTagName("prop");
                    for(int m = 0; m < propNodes.getLength(); m++)
                    {
                        Element propElement = (Element)propNodes.item(m);
                        String key = propElement.getAttribute("key");
                        String value = readElementContent(propNodes.item(m));
                        propMap.put(key, value);
                    }

                    propsMap.put(environmentName, propMap);
                }

            }
            catch(Exception e)
            {
                StrongDBCPDataSource.LOGGER.error("\u89E3\u6790dbcp\u914D\u7F6E\u6587\u4EF6\u65F6\u51FA\u73B0\u9519\u8BEF:", e);
                throw new DataSourceException("0010", e, new Object[] {
                    fileUrl.toString(), e.getMessage()
                });
            }
            finally
            {
                if(is == null) goto _L0; else goto _L0
            }
            if(is != null)
                try
                {
                    is.close();
                }
                catch(IOException e)
                {
                    StrongDBCPDataSource.LOGGER.error("\u5173\u95EDxml\u8F93\u5165\u6D41\u5931\u8D25", e);
                }
            break MISSING_BLOCK_LABEL_321;
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                StrongDBCPDataSource.LOGGER.error("\u5173\u95EDxml\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
            throw exception;
        }

        private static String readElementContent(Node targetNode)
        {
            NodeList contentList = targetNode.getChildNodes();
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < contentList.getLength(); i++)
            {
                Node subNode = contentList.item(i);
                if(subNode.getNodeType() == 3)
                    sb.append(subNode.getNodeValue().trim());
            }

            return sb.toString();
        }

        private static Map propsMap = new HashMap();
        private static String DBCP_CONFIG_PATH = "com/sinitek/base/datasource/config/dbcp-config.xml";

        static 
        {
            readConfigFile();
        }

        protected DBCPConfig()
        {
        }
    }


    public StrongDBCPDataSource(ILocalDataSourceConfig localDataSourceConfig)
    {
        this.localDataSourceConfig = localDataSourceConfig;
        try
        {
            init(localDataSourceConfig.isNeedBind());
        }
        catch(Exception e)
        {
            startReconnect();
        }
    }

    public String getDataSourceName()
    {
        return localDataSourceConfig.getDataSourceName();
    }

    public DataSource getOriDataSource()
    {
        return ds;
    }

    public void createOriDataSource()
    {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName(localDataSourceConfig.getDriverClassName());
        bds.setUrl(localDataSourceConfig.getUrl());
        bds.setUsername(localDataSourceConfig.getUserName());
        bds.setPassword(localDataSourceConfig.getPassword());
        String environment = DataSourceProviderImpl.getInstance().getDataSourceStrategy().getEnvironment();
        Map propMap = DBCPConfig.getProps(environment);
        Iterator propIter = propMap.entrySet().iterator();
        try
        {
            while(propIter.hasNext()) 
            {
                java.util.Map.Entry propEntry = (java.util.Map.Entry)propIter.next();
                String fieldName = (String)propEntry.getKey();
                if(fieldName.equalsIgnoreCase("removeAbandoned"))
                    bds.setRemoveAbandoned(Boolean.valueOf((String)propEntry.getValue()).booleanValue());
                else
                if(fieldName.equalsIgnoreCase("removeAbandonedTimeout"))
                    bds.setRemoveAbandonedTimeout(Integer.valueOf((String)propEntry.getValue()).intValue());
                else
                if(fieldName.equalsIgnoreCase("logAbandoned"))
                {
                    bds.setLogAbandoned(Boolean.valueOf((String)propEntry.getValue()).booleanValue());
                } else
                {
                    Field field = bds.getClass().getDeclaredField(fieldName);
                    String methodName = (new StringBuilder()).append("set").append(StringUtils.capitalize(fieldName)).toString();
                    Method method = bds.getClass().getDeclaredMethod(methodName, new Class[] {
                        field.getType()
                    });
                    Object value = ConvertUtils.convert((String)propEntry.getValue(), field.getType());
                    method.invoke(bds, new Object[] {
                        value
                    });
                }
            }
        }
        catch(Exception e)
        {
            LOGGER.error("\u6839\u636Edbcp\u81EA\u5B9A\u4E49\u914D\u7F6E\u6587\u4EF6\uFF0C\u8BBE\u7F6Edbcp\u53C2\u6570\u65F6\u51FA\u73B0\u9519\u8BEF:", e);
            throw new DataSourceException("0011", e, new Object[] {
                e.getMessage()
            });
        }
        ds = bds;
    }

    public boolean getNeedBind()
    {
        return localDataSourceConfig.isNeedBind();
    }

    public java.util.logging.Logger getParentLogger()
        throws SQLFeatureNotSupportedException
    {
        return null;
    }

    protected DataSource ds;
    private ILocalDataSourceConfig localDataSourceConfig;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/datasource/impl/StrongDBCPDataSource);


}
