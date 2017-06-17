// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientConfigReader.java

package com.sinitek.ds.core.service.client;

import com.sinitek.ds.core.service.IServiceContext;
import com.sinitek.ds.core.service.client.ejb.EjbServiceContextProxy;
import com.sinitek.ds.core.service.client.socket.SocketServiceContextProxy;
import com.sinitek.ds.core.service.client.webservice.WebServiceContextProxy;
import com.sinitek.ds.core.service.impl.local.ServiceContextFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.*;

// Referenced classes of package com.sinitek.ds.core.service.client:
//            ClientConfigInfoImpl, ServiceClientException, IClientConfigInfo

public class ClientConfigReader
{
    private static class ServiceClientEntityResolver
        implements EntityResolver
    {

        public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException
        {
            if("-//SINITEK//SERVICE//SERVICECLIENTCONFIG//1_0".equalsIgnoreCase(publicId))
                return new InputSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/sinitek/ds/core/service/client/serviceclient.dtd"));
            else
                return null;
        }

        private ServiceClientEntityResolver()
        {
        }

    }


    public ClientConfigReader()
    {
    }

    public static IClientConfigInfo readConfig(URL configFile)
    {
        ClientConfigInfoImpl ret = new ClientConfigInfoImpl();
        InputStream is = null;
        try
        {
            ClientConfigInfoImpl clientconfiginfoimpl;
            try
            {
                is = configFile.openStream();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                db.setEntityResolver(new ServiceClientEntityResolver());
                Document doc = db.parse(is);
                Element root = doc.getDocumentElement();
                Element servicesElement = (Element)root.getElementsByTagName("services").item(0);
                Map contextMap = new HashMap();
                NodeList ejbContextNodes = servicesElement.getElementsByTagName("ejb");
                readEjbProxy(contextMap, ejbContextNodes);
                NodeList localContextNodes = servicesElement.getElementsByTagName("local");
                for(int i = 0; i < localContextNodes.getLength(); i++)
                {
                    Element ele = (Element)localContextNodes.item(i);
                    contextMap.put(ele.getAttribute("name"), ServiceContextFactory.getInstance().getContext());
                }

                NodeList wsContextNodes = servicesElement.getElementsByTagName("webservice");
                for(int i = 0; i < wsContextNodes.getLength(); i++)
                {
                    Element ele = (Element)wsContextNodes.item(i);
                    String name = ele.getAttribute("name").trim();
                    contextMap.put(name, createWebServiceContext(ele));
                }

                NodeList socketContextNodes = servicesElement.getElementsByTagName("socket");
                for(int i = 0; i < socketContextNodes.getLength(); i++)
                {
                    Element ele = (Element)socketContextNodes.item(i);
                    String ip = ele.getAttribute("ip").trim();
                    int port = Integer.parseInt(ele.getAttribute("port"));
                    String name = ele.getAttribute("name").trim();
                    contextMap.put(name, new SocketServiceContextProxy(ip, port));
                }

                Element dispatchElement = (Element)root.getElementsByTagName("dispatch").item(0);
                NodeList callNodes = dispatchElement.getElementsByTagName("call");
                for(int i = 0; i < callNodes.getLength(); i++)
                {
                    Element callEle = (Element)callNodes.item(i);
                    String functionCode = callEle.getAttribute("functionCode").trim();
                    IServiceContext ctx = (IServiceContext)contextMap.get(callEle.getAttribute("service"));
                    ret.registCall(functionCode, ctx);
                }

                clientconfiginfoimpl = ret;
            }
            catch(Exception e)
            {
                throw new ServiceClientException("0001", e);
            }
            return clientconfiginfoimpl;
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
    }

    private static IServiceContext createWebServiceContext(Element wsElement)
    {
        String url = wsElement.getAttribute("url");
        return new WebServiceContextProxy(url);
    }

    private static void readEjbProxy(Map contextMap, NodeList ejbContextNodes)
    {
        for(int i = 0; i < ejbContextNodes.getLength(); i++)
        {
            Element contextEle = (Element)ejbContextNodes.item(i);
            String name = contextEle.getAttribute("name");
            String jndi = contextEle.getAttribute("jndi");
            String url = contextEle.getAttribute("url");
            boolean checkFlag = "true".equalsIgnoreCase(contextEle.getAttribute("testFlag"));
            String szTestTime = contextEle.getAttribute("testTime");
            if(StringUtils.isBlank(szTestTime))
                szTestTime = "10";
            int testTime = Integer.parseInt(szTestTime) * 1000;
            NodeList jndiPropNodes = contextEle.getElementsByTagName("jndiProp");
            Properties prop = new Properties();
            for(int j = 0; j < jndiPropNodes.getLength(); j++)
            {
                Element propEle = (Element)jndiPropNodes.item(j);
                String key = propEle.getAttribute("name");
                String value = readElementContent(propEle);
                prop.put(key, value);
            }

            contextMap.put(name, new EjbServiceContextProxy(jndi, url, prop, testTime, checkFlag));
        }

    }

    private static String readElementContent(Element element)
    {
        NodeList contentList = element.getChildNodes();
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
    private static final String PUBLIC_ID_1_0 = "-//SINITEK//SERVICE//SERVICECLIENTCONFIG//1_0";

    static 
    {
        LOGGER = Logger.getLogger(com.sinitek.ds.core.service.client.ClientConfigReader.class);
    }
}
