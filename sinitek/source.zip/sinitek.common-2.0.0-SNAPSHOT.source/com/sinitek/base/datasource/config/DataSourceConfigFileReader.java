// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSourceConfigFileReader.java

package com.sinitek.base.datasource.config;

import com.sinitek.base.common.SinitekDES;
import com.sinitek.base.datasource.DataSourceException;
import com.sinitek.base.datasource.config.impl.DataSourceInfoConfig;
import com.sinitek.base.datasource.config.impl.DataSourceStrategyConfig;
import com.sinitek.base.datasource.config.impl.LocalDataSourceConfig;
import com.sinitek.base.datasource.config.impl.RemoteDataSourceConfig;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.*;

// Referenced classes of package com.sinitek.base.datasource.config:
//            IDataSourceInfoConfig

public class DataSourceConfigFileReader
{
    private static class DtdEntityResolver
        implements EntityResolver
    {

        public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException
        {
            if("-//SINITEK//DATASOURCE//DATASOURCECONFIG//1_0".equalsIgnoreCase(publicId))
                return new InputSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/sinitek/base/datasource/config/datasourceconfig_1_0.dtd"));
            else
                return null;
        }

        private static final String DTD_PATH = "com/sinitek/base/datasource/config/datasourceconfig_1_0.dtd";

        private DtdEntityResolver()
        {
        }

    }


    public DataSourceConfigFileReader()
    {
    }

    public IDataSourceInfoConfig addConfigLocation(String path)
    {
        return addConfigLocation(new String[] {
            path
        });
    }

    public IDataSourceInfoConfig addConfigLocation(String path[])
    {
        configLocations = path;
        return parseFile(path);
    }

    public String[] getConfigLocations()
    {
        return configLocations;
    }

    private IDataSourceInfoConfig parseFile(String configPaths[])
        throws DataSourceException
    {
        InputStream is;
        StringBuilder dataSourceNameString;
        Map localDataSourceMap;
        Map remoteDataSourceMap;
        DataSourceStrategyConfig dataSourceStrategyConfig;
        int k;
        is = null;
        dataSourceNameString = new StringBuilder();
        localDataSourceMap = new HashMap();
        remoteDataSourceMap = new HashMap();
        dataSourceStrategyConfig = null;
        if(null == configPaths || configPaths.length == 0)
            return null;
        k = 0;
_L3:
        if(k >= configPaths.length) goto _L2; else goto _L1
_L1:
        String configPath;
        configPath = configPaths[k];
        LOGGER.debug((new StringBuilder()).append("\u5F00\u59CB\u89E3\u6790\u914D\u7F6E\u6587\u4EF6\uFF1A[").append(configPath).append("]").toString());
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver(new DtdEntityResolver());
            String fileName = URLDecoder.decode(configPath, "utf-8");
            URL fileUrl = Thread.currentThread().getContextClassLoader().getResource(fileName);
            if(null == fileUrl)
            {
                LOGGER.error((new StringBuilder()).append("\u8BFB\u53D6\u914D\u7F6E\u6587\u4EF6\uFF1A[").append(configPath).append("]\u5931\u8D25\uFF0C\u6CA1\u6709\u627E\u5230\u6587\u4EF6\uFF01").toString());
                throw new DataSourceException("0005", new Object[] {
                    configPath
                });
            }
            is = fileUrl.openStream();
            Document doc = db.parse(is);
            DocumentType docType = doc.getDoctype();
            if(!"-//SINITEK//DATASOURCE//DATASOURCECONFIG//1_0".equalsIgnoreCase(docType.getPublicId()))
                throw new DataSourceException("0002", new Object[] {
                    configPath, docType.getPublicId()
                });
            Element root = doc.getDocumentElement();
            NodeList localNodes = root.getElementsByTagName("local");
            NodeList remoteNodes = root.getElementsByTagName("remote");
            NodeList strategyNodes = root.getElementsByTagName("strategy");
            if(null != localNodes)
            {
                for(int i = 0; i < localNodes.getLength(); i++)
                {
                    Element localElement = (Element)localNodes.item(i);
                    String dataSourceName = localElement.getAttribute("name");
                    boolean bind = false;
                    String strBind = localElement.getAttribute("bind");
                    if(StringUtils.isNotBlank(strBind) && "true".equals(strBind))
                        bind = true;
                    if(dataSourceNameString.indexOf(dataSourceName) > 0)
                        throw new DataSourceException("0003", new Object[] {
                            fileName, dataSourceName
                        });
                    LocalDataSourceConfig localDataSource = new LocalDataSourceConfig();
                    localDataSource.setDataSourceName(dataSourceName);
                    localDataSource.setProvider(readSubElementContent(localElement, "provider"));
                    localDataSource.setDriverClassName(readSubElementContent(localElement, "driverClassName"));
                    localDataSource.setUrl(readSubElementContent(localElement, "url"));
                    localDataSource.setUserName(readSubElementContent(localElement, "username"));
                    localDataSource.setPassword(readSubElementContent(localElement, "password"));
                    localDataSource.setNeedBind(bind);
                    String encryptedPassword = readSubElementContent(localElement, "encryptedPassword");
                    if(StringUtils.isNotBlank(encryptedPassword))
                        localDataSource.setPassword(SinitekDES.decrypt(encryptedPassword));
                    dataSourceNameString.append(dataSourceName).append(',');
                    localDataSourceMap.put(dataSourceName, localDataSource);
                    LOGGER.debug((new StringBuilder()).append("\u6210\u529F\u4ECE\u914D\u7F6E\u6587\u4EF6\u4E2D\u83B7\u53D6\u672C\u5730\u6570\u636E\u6E90\uFF1A[").append(dataSourceName).append("]").toString());
                }

            }
            if(null != remoteNodes)
            {
                for(int i = 0; i < remoteNodes.getLength(); i++)
                {
                    Element remoteElement = (Element)remoteNodes.item(i);
                    String dataSourceName = remoteElement.getAttribute("name");
                    boolean bind = false;
                    String strBind = remoteElement.getAttribute("bind");
                    if(StringUtils.isNotBlank(strBind) && "true".equals(strBind))
                        bind = true;
                    if(dataSourceNameString.indexOf(dataSourceName) > 0)
                        throw new DataSourceException("0003", new Object[] {
                            fileName, dataSourceName
                        });
                    Map propMap = new HashMap();
                    NodeList jndiProp = remoteElement.getElementsByTagName("jndiProp");
                    if(jndiProp.getLength() > 0)
                    {
                        NodeList propNodes = ((Element)jndiProp.item(0)).getElementsByTagName("prop");
                        for(int m = 0; m < propNodes.getLength(); m++)
                        {
                            Element propElement = (Element)propNodes.item(m);
                            String key = propElement.getAttribute("key");
                            String value = readElementContent(propNodes.item(m));
                            propMap.put(key, value);
                        }

                    }
                    RemoteDataSourceConfig remoteDataSource = new RemoteDataSourceConfig();
                    remoteDataSource.setDataSourceName(dataSourceName);
                    remoteDataSource.setJndi(readSubElementContent(remoteElement, "jndi"));
                    remoteDataSource.setJndiProp(propMap);
                    remoteDataSource.setNeedBind(bind);
                    remoteDataSource.setUserName(readSubElementContent(remoteElement, "username"));
                    remoteDataSource.setPassword(readSubElementContent(remoteElement, "password"));
                    String encryptedPassword = readSubElementContent(remoteElement, "encryptedPassword");
                    if(StringUtils.isNotBlank(encryptedPassword))
                        remoteDataSource.setPassword(SinitekDES.decrypt(encryptedPassword));
                    dataSourceNameString.append(dataSourceName).append(',');
                    remoteDataSourceMap.put(dataSourceName, remoteDataSource);
                    LOGGER.debug((new StringBuilder()).append("\u6210\u529F\u4ECE\u914D\u7F6E\u6587\u4EF6\u4E2D\u83B7\u53D6\u672C\u5730\u6570\u636E\u6E90\uFF1A[").append(dataSourceName).append("]").toString());
                }

            }
            if(null != strategyNodes)
            {
                Element strategyElement = (Element)strategyNodes.item(0);
                String environment = strategyElement.getAttribute("environment");
                String strCheckoutConnectionInterval = readSubElementContent(strategyElement, "checkoutConnectionInterval");
                String checkoutConnectionSql = readSubElementContent(strategyElement, "checkoutConnectionSql");
                String strFirstReconnectionTimes = readSubElementContent(strategyElement, "firstReconnectionTimes");
                String strFirstReconnectionInterval = readSubElementContent(strategyElement, "firstReconnectionInterval");
                String strLongTimeReconnectionTimes = readSubElementContent(strategyElement, "longTimeReconnectionTimes");
                String strLongTimeReconnectionInterval = readSubElementContent(strategyElement, "longTimeReconnectionInterval");
                dataSourceStrategyConfig = new DataSourceStrategyConfig(environment);
                if(StringUtils.isNotBlank(strCheckoutConnectionInterval))
                    dataSourceStrategyConfig.setCheckoutConnectionInterval(Long.valueOf(strCheckoutConnectionInterval).longValue());
                if(StringUtils.isNotBlank(checkoutConnectionSql))
                    dataSourceStrategyConfig.setCheckoutConnectionSql(checkoutConnectionSql);
                if(StringUtils.isNotBlank(strFirstReconnectionTimes))
                    dataSourceStrategyConfig.setFirstReconnectionTimes(Integer.valueOf(strFirstReconnectionTimes).intValue());
                if(StringUtils.isNotBlank(strFirstReconnectionInterval))
                    dataSourceStrategyConfig.setFirstReconnectionInterval(Long.valueOf(strFirstReconnectionInterval).longValue());
                if(StringUtils.isNotBlank(strLongTimeReconnectionTimes))
                    dataSourceStrategyConfig.setLongTimeReconnectionTimes(Integer.valueOf(strLongTimeReconnectionTimes).intValue());
                if(StringUtils.isNotBlank(strLongTimeReconnectionInterval))
                    dataSourceStrategyConfig.setLongTimeReconnectionInterval(Long.valueOf(strLongTimeReconnectionInterval).longValue());
            }
        }
        catch(ParserConfigurationException e)
        {
            throw new DataSourceException("0001", e, new Object[] {
                configPath
            });
        }
        catch(UnsupportedEncodingException e)
        {
            throw new DataSourceException("0001", e, new Object[] {
                configPath
            });
        }
        catch(MalformedURLException e)
        {
            throw new DataSourceException("0001", e, new Object[] {
                configPath
            });
        }
        catch(IOException e)
        {
            throw new DataSourceException("0001", e, new Object[] {
                configPath
            });
        }
        catch(SAXException e)
        {
            throw new DataSourceException("0001", e, new Object[] {
                configPath
            });
        }
        if(is == null)
            continue; /* Loop/switch isn't completed */
        try
        {
            is.close();
        }
        catch(IOException e)
        {
            LOGGER.error("\u5173\u95EDxml\u8F93\u5165\u6D41\u5931\u8D25", e);
        }
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        if(is != null)
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95EDxml\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        throw exception;
        k++;
          goto _L3
_L2:
        DataSourceInfoConfig dataSourceInfoConfig = new DataSourceInfoConfig();
        dataSourceInfoConfig.setLocalDataSourceInfo(localDataSourceMap);
        dataSourceInfoConfig.setRemoteDataSourceInfo(remoteDataSourceMap);
        dataSourceInfoConfig.setDataSourceStrategy(dataSourceStrategyConfig);
        return dataSourceInfoConfig;
    }

    private static String readElementContent(Node targetNode)
    {
        NodeList contentList = targetNode.getChildNodes();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < contentList.getLength(); i++)
        {
            Node subNode = contentList.item(i);
            if(subNode.getNodeType() == 3)
                sb.append(subNode.getNodeValue().trim());
        }

        return sb.toString();
    }

    private static String readSubElementContent(Element element, String subElementName)
    {
        NodeList nodes = element.getElementsByTagName(subElementName);
        if(nodes == null || nodes.getLength() == 0)
            return "";
        Node targetNode = nodes.item(0);
        NodeList contentList = targetNode.getChildNodes();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < contentList.getLength(); i++)
        {
            Node subNode = contentList.item(i);
            if(subNode.getNodeType() == 3)
                sb.append(subNode.getNodeValue().trim());
        }

        return sb.toString();
    }

    private String configLocations[];
    public static final String PUBLIC_ID_1_0 = "-//SINITEK//DATASOURCE//DATASOURCECONFIG//1_0";
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/datasource/config/DataSourceConfigFileReader);

}
