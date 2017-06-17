// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceConfigReader.java

package com.sinitek.ds.core.service.config;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.enumsupport.EnumException;
import com.sinitek.ds.core.service.config.impl.ServiceConfigBeanImpl;
import com.sinitek.ds.core.service.config.impl.ServiceParamConfigBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.*;

// Referenced classes of package com.sinitek.ds.core.service.config:
//            ServiceConfigException, IServiceParamConfig

public class ServiceConfigReader
{
    private static class ServiceCoreEntityResolver
        implements EntityResolver
    {

        public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException
        {
            if(publicId.equalsIgnoreCase("-//SINITEK//SERVICE//SERVICECORECONFIG//1_0"))
                return new InputSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/sinitek/ds/core/service/config/servicecoreconfig.dtd"));
            else
                return null;
        }

        private ServiceCoreEntityResolver()
        {
        }

    }


    public ServiceConfigReader()
    {
    }

    public static Map readServiceConfig(URL url)
    {
        Map ret = new HashMap();
        InputStream is = null;
        try
        {
            is = url.openStream();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver(new ServiceCoreEntityResolver());
            Document doc = db.parse(is);
            if(!doc.getDoctype().getPublicId().equalsIgnoreCase("-//SINITEK//SERVICE//SERVICECORECONFIG//1_0"))
                throw new ServiceConfigException("0001", new Object[] {
                    doc.getDoctype().getPublicId()
                });
            Element root = doc.getDocumentElement();
            NodeList serviceNodes = root.getElementsByTagName("service");
            for(int i = 0; i < serviceNodes.getLength(); i++)
            {
                Element serviceElement = (Element)serviceNodes.item(i);
                String functionCode = serviceElement.getAttribute("functionCode");
                String functionName = serviceElement.getAttribute("functionName");
                if(ret.containsKey(functionCode))
                    throw new ServiceConfigException("0002", new Object[] {
                        functionCode
                    });
                ServiceConfigBeanImpl bean = new ServiceConfigBeanImpl();
                bean.setFunctionCode(functionCode);
                bean.setFunctionName(functionName);
                String className = readSubElementContent(serviceElement, "className");
                bean.setServiceClass(className);
                Element serviceParamElement = (Element)serviceElement.getElementsByTagName("serviceParam").item(0);
                NodeList paramNodes = serviceParamElement.getChildNodes();
                List paramConfigs = new ArrayList();
                for(int j = 0; j < paramNodes.getLength(); j++)
                {
                    Node node = paramNodes.item(j);
                    if(node.getNodeType() == 1)
                        paramConfigs.add(ServiceParamConfigBuilder.createParamConfig((Element)node));
                }

                IServiceParamConfig configs[] = (IServiceParamConfig[])paramConfigs.toArray(new IServiceParamConfig[paramConfigs.size()]);
                bean.setParamConfigs(configs);
                Properties initProps = new Properties();
                NodeList initparam = serviceElement.getElementsByTagName("initparam");
                if(initparam != null)
                {
                    Element firstInitParam = (Element)initparam.item(0);
                    if(firstInitParam != null)
                    {
                        NodeList initParams = firstInitParam.getChildNodes();
                        for(int a = 0; a < initParams.getLength(); a++)
                        {
                            Node node = initParams.item(a);
                            if(node.getNodeType() == 1)
                            {
                                Element elementInitParam = (Element)node;
                                initProps.put(elementInitParam.getAttribute("name"), elementInitParam.getAttribute("value"));
                            }
                        }

                        bean.setInitParams(initProps);
                    }
                }
                ret.put(functionCode, bean);
            }

            NodeList includeNodes = root.getElementsByTagName("include");
            for(int i = 0; i < includeNodes.getLength(); i++)
            {
                Element ele = (Element)includeNodes.item(i);
                String path = ele.getAttribute("path");
                URL includeUrl = Thread.currentThread().getContextClassLoader().getResource(path);
                if(includeUrl == null)
                    throw new ServiceConfigException("0010", new Object[] {
                        path
                    });
                mergeServiceConfig(ret, readServiceConfig(includeUrl));
            }

        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception e)
        {
            throw new ServiceConfigException("0004", e);
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
                    LOGGER.error("\u5173\u95ED\u914D\u7F6E\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
                }
        }
        return ret;
    }

    private static void mergeServiceConfig(Map source, Map newData)
    {
        java.util.Map.Entry entry;
        for(Iterator iter = newData.entrySet().iterator(); iter.hasNext(); source.put(entry.getKey(), entry.getValue()))
        {
            entry = (java.util.Map.Entry)iter.next();
            if(source.containsKey(entry.getKey()))
                throw new ServiceConfigException("0002", new Object[] {
                    entry.getKey()
                });
        }

    }

    private static String readSubElementContent(Element element, String subElementName)
        throws EnumException
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

    static Class _mthclass$(String x0)
    {
        try
        {
            return Class.forName(x0);
        }
        catch(ClassNotFoundException x1)
        {
            throw (new NoClassDefFoundError()).initCause(x1);
        }
    }

    private static final Logger LOGGER;
    private static final String DTD_PUBLICID = "-//SINITEK//SERVICE//SERVICECORECONFIG//1_0";

    static 
    {
        LOGGER = Logger.getLogger(com.sinitek.ds.core.service.config.ServiceConfigReader.class);
    }
}
