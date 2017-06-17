// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerConfig.java

package com.sinitek.base.control.server;

import com.sinitek.base.common.BaseException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.*;

// Referenced classes of package com.sinitek.base.control.server:
//            ControlServerException, HandlerConfig, IServerConfig

public class ServerConfig
    implements IServerConfig
{
    private static class ControlServerDtdEntityResolver
        implements EntityResolver
    {

        public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException
        {
            if("-//SINITEK//SERVICE//CONTROLSERVERCONFIG//1_0".equalsIgnoreCase(publicId))
                return new InputSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/sinitek/base/control/server/controlserverconfig.dtd"));
            else
                return null;
        }

        private static final String DTD_PATH = "com/sinitek/base/control/server/controlserverconfig.dtd";

        private ControlServerDtdEntityResolver()
        {
        }

    }


    public ServerConfig()
    {
        URL url = Thread.currentThread().getContextClassLoader().getResource("controlserver_config.xml");
        if(url == null)
        {
            throw new ControlServerException("0001");
        } else
        {
            init(url);
            return;
        }
    }

    public ServerConfig(URL url)
    {
        init(url);
    }

    private void init(URL url)
    {
        InputStream is;
        LOGGER.info((new StringBuilder()).append("\u51C6\u5907\u8BFB\u53D6\u914D\u7F6E\u6587\u4EF6[").append(url.getPath()).append("]").toString());
        is = null;
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver(new ControlServerDtdEntityResolver());
            is = url.openStream();
            Document doc = db.parse(is);
            DocumentType docType = doc.getDoctype();
            if(!"-//SINITEK//SERVICE//CONTROLSERVERCONFIG//1_0".equalsIgnoreCase(docType.getPublicId()))
                throw new ControlServerException("0003", new Object[] {
                    docType.getPublicId()
                });
            Element root = doc.getDocumentElement();
            String szPort = readSubElementContent(root, "port");
            try
            {
                port = Integer.parseInt(szPort);
            }
            catch(NumberFormatException nfe)
            {
                throw new ControlServerException("0004", nfe, new Object[] {
                    szPort
                });
            }
            logPath = readSubElementContent(root, "logFilePath");
            Element handlerConfigsEle = (Element)root.getElementsByTagName("handlerConfigs").item(0);
            NodeList handlerConfigsNodes = handlerConfigsEle.getElementsByTagName("handlerConfig");
            handlerConfigs = new ArrayList(handlerConfigsNodes.getLength());
            for(int i = 0; i < handlerConfigsNodes.getLength(); i++)
                handlerConfigs.add(readHandlerConfig((Element)handlerConfigsNodes.item(i)));

            String szThreadCount = readSubElementContent(root, "threadCount");
            try
            {
                threadCount = Integer.parseInt(szThreadCount);
            }
            catch(NumberFormatException nfe)
            {
                throw new ControlServerException("0010", nfe, new Object[] {
                    szThreadCount
                });
            }
        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception e)
        {
            throw new ControlServerException("0002", e);
        }
        if(is != null)
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95ED\u914D\u7F6E\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        break MISSING_BLOCK_LABEL_390;
        Exception exception;
        exception;
        if(is != null)
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95ED\u914D\u7F6E\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        throw exception;
    }

    private HandlerConfig readHandlerConfig(Element element)
    {
        HandlerConfig config = new HandlerConfig();
        config.setHandlerClass(readSubElementContent(element, "handlerClass"));
        config.setName(readSubElementContent(element, "name"));
        config.setLogLevel(readSubElementContent(element, "handlerLogLevel"));
        Properties prop = new Properties();
        NodeList serviceParamsElements = element.getElementsByTagName("handlerParams");
        if(serviceParamsElements != null && serviceParamsElements.getLength() > 0)
        {
            Element serviceParamsElement = (Element)serviceParamsElements.item(0);
            NodeList paramsList = serviceParamsElement.getElementsByTagName("handlerParam");
            for(int i = 0; i < paramsList.getLength(); i++)
            {
                Element paramElement = (Element)paramsList.item(i);
                String paramName = readSubElementContent(paramElement, "handlerParamName");
                String paramValue = readSubElementContent(paramElement, "handlerParamValue");
                prop.put(paramName, paramValue);
            }

        }
        config.setHandlerProperties(prop);
        return config;
    }

    private static String readSubElementContent(Element element, String subElementName)
    {
        NodeList nodes = element.getElementsByTagName(subElementName);
        if(nodes == null || nodes.getLength() == 0)
            return "";
        Node targetNode = nodes.item(0);
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

    public List getHandlerConfigs()
    {
        return handlerConfigs;
    }

    public int getPort()
    {
        return port;
    }

    public String getLogPath()
    {
        return logPath;
    }

    public int getThreadCount()
    {
        return threadCount;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/control/server/ServerConfig);
    private static final String PUBLIC_ID_1_0 = "-//SINITEK//SERVICE//CONTROLSERVERCONFIG//1_0";
    private int port;
    private int threadCount;
    private List handlerConfigs;
    private String logPath;

}
