// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContextSupportConfigReader.java

package com.sinitek.base.metadb.support;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.metadb.MetaDBLoggerFactory;
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

// Referenced classes of package com.sinitek.base.metadb.support:
//            MetaDBContextSupportException, MethodInvocationInfoImpl, TransactionType, IExceptionTranslator, 
//            IExceptionHandler, IContextSupportConfigInfo, IMethodInvocationInfo

public class ContextSupportConfigReader
{
    private static class ContextSupportConfigInfoImpl
        implements IContextSupportConfigInfo
    {

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public Class getInterface()
        {
            return interfaceClass;
        }

        public Class getImplClass()
        {
            return implClass;
        }

        public String[] getInterfaces()
        {
            return interfaces;
        }

        public Properties getInitProperties()
        {
            return initProperties;
        }

        public List getMethodInvocationInfos()
        {
            return methodInvocationInfos;
        }

        public void setImplClassName(Class implClass)
        {
            this.implClass = implClass;
        }

        public void setInitProperties(Properties initProperties)
        {
            this.initProperties = initProperties;
        }

        public void setInterfaceName(Class interfaceClass)
        {
            this.interfaceClass = interfaceClass;
        }

        public void setInterfaces(String interfaces[])
        {
            this.interfaces = interfaces;
        }

        public void setMethodInvocationInfos(List methodInvocationInfos)
        {
            this.methodInvocationInfos = methodInvocationInfos;
        }

        private String name;
        private Class interfaceClass;
        private Class implClass;
        private String interfaces[];
        private Properties initProperties;
        private List methodInvocationInfos;

        private ContextSupportConfigInfoImpl()
        {
        }

    }

    private static class ContextSupportEntityResolver
        implements EntityResolver
    {

        public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException
        {
            if("-//SINITEK//METADB//CONTEXTSUPPORT//1_0".equals(publicId))
                return new InputSource(Thread.currentThread().getContextClassLoader().getResource("com/sinitek/base/metadb/support/contextsupport.dtd").openStream());
            else
                return null;
        }

        private ContextSupportEntityResolver()
        {
        }

    }


    public ContextSupportConfigReader()
    {
    }

    public static List readConfig(URL configFile)
        throws MetaDBContextSupportException
    {
        List ret;
        InputStream is;
        Exception exception;
        ret = new ArrayList();
        is = null;
        try
        {
            is = configFile.openStream();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver(new ContextSupportEntityResolver());
            Document doc = db.parse(is);
            if(!"-//SINITEK//METADB//CONTEXTSUPPORT//1_0".equals(doc.getDoctype().getPublicId()))
                throw new MetaDBContextSupportException("0008", new Object[] {
                    configFile.toString()
                });
            Element rootElement = doc.getDocumentElement();
            NodeList beans = rootElement.getElementsByTagName("bean");
            for(int i = 0; i < beans.getLength(); i++)
            {
                Element bean = (Element)beans.item(i);
                try
                {
                    ret.add(readBeanConfig(bean));
                }
                catch(Exception ex)
                {
                    LOGGER.warn((new StringBuilder()).append("bean.name").append(bean.getAttribute("name".trim())).append(" ERROR").toString());
                }
            }

        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception e)
        {
            throw new MetaDBContextSupportException("0007", e, new Object[] {
                configFile.toString()
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
                LOGGER.warn("\u5173\u95EDContextSupport\u914D\u7F6E\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        break MISSING_BLOCK_LABEL_278;
        try
        {
            is.close();
        }
        catch(IOException e)
        {
            LOGGER.warn("\u5173\u95EDContextSupport\u914D\u7F6E\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
        }
        throw exception;
        return ret;
    }

    public static List readConfig(String configFile)
        throws MetaDBContextSupportException
    {
        URL url = Thread.currentThread().getContextClassLoader().getResource(configFile);
        if(url == null)
            throw new MetaDBContextSupportException("0006", new Object[] {
                configFile
            });
        else
            return readConfig(url);
    }

    private static IContextSupportConfigInfo readBeanConfig(Element bean)
        throws ClassNotFoundException
    {
        ContextSupportConfigInfoImpl ret = new ContextSupportConfigInfoImpl();
        ret.setName(bean.getAttribute("name".trim()));
        String interfaceName = bean.getAttribute("interface").trim();
        Class interfaceClass = Class.forName(interfaceName);
        ret.setInterfaceName(interfaceClass);
        String className = bean.getAttribute("impl").trim();
        Class implClass = Class.forName(className);
        ret.setImplClassName(implClass);
        Properties prop = new Properties();
        NodeList props = bean.getElementsByTagName("properties");
        if(props != null && props.getLength() > 0)
        {
            Element propsEle = (Element)props.item(0);
            NodeList propList = propsEle.getElementsByTagName("prop");
            for(int i = 0; i < propList.getLength(); i++)
            {
                Element propEle = (Element)propList.item(i);
                String key = propEle.getAttribute("key").trim();
                String value = readElementContent(propEle);
                prop.setProperty(key, value);
            }

        }
        ret.setInitProperties(prop);
        NodeList interfacesList = bean.getElementsByTagName("interfaces");
        if(interfacesList != null && interfacesList.getLength() != 0)
        {
            String interfaces[] = new String[interfacesList.getLength()];
            for(int i = 0; i < interfacesList.getLength(); i++)
                interfaces[i] = readElementContent(interfacesList.item(i));

            ret.setInterfaces(interfaces);
        } else
        {
            ret.setInterfaces(new String[0]);
        }
        NodeList methodDefineList = bean.getElementsByTagName("methodDefine");
        if(methodDefineList == null || methodDefineList.getLength() == 0)
        {
            ret.setMethodInvocationInfos(createDefaultInvocationInfo(interfaceClass, implClass));
        } else
        {
            NodeList methodNodeList = ((Element)methodDefineList.item(0)).getElementsByTagName("method");
            List methodList = new ArrayList(methodNodeList.getLength());
            for(int i = 0; i < methodNodeList.getLength(); i++)
                methodList.add(readMethodDefine((Element)methodNodeList.item(i), interfaceClass, implClass));

            ret.setMethodInvocationInfos(methodList);
        }
        return ret;
    }

    private static String readElementContent(Node element)
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

    private static IMethodInvocationInfo readMethodDefine(Element methodElement, Class interfacleClass, Class implClass)
    {
        MethodInvocationInfoImpl impl = new MethodInvocationInfoImpl();
        impl.setInterfaceName(interfacleClass);
        impl.setImplementsClassName(implClass);
        impl.setMethodNamePattern(methodElement.getAttribute("namePattern").trim());
        String transactionMode = methodElement.getAttribute("transaction");
        if(StringUtils.isBlank(transactionMode))
            impl.setTransactionType(TransactionType.TRANSACTION_SUPPORT);
        else
            impl.setTransactionType((TransactionType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/support/TransactionType.getName(), transactionMode.trim()));
        NodeList translaters = methodElement.getElementsByTagName("exceptionTranslator");
        if(translaters == null || translaters.getLength() == 0)
        {
            impl.setExceptionTranslate(IExceptionTranslator.DUMMY_TRANSLATOR);
        } else
        {
            String translaterName = readElementContent(translaters.item(0));
            impl.setExceptionTranslate(initTranslater(translaterName));
        }
        NodeList handlersNodeList = methodElement.getElementsByTagName("exceptionHandlers");
        if(handlersNodeList != null && handlersNodeList.getLength() != 0)
        {
            Element handlersElement = (Element)handlersNodeList.item(0);
            NodeList handlerList = handlersElement.getElementsByTagName("exceptionHandler");
            for(int i = 0; i < handlerList.getLength(); i++)
            {
                String handlerClzName = readElementContent(handlerList.item(i));
                impl.addExceptionHandler(initHandler(handlerClzName));
            }

        }
        return impl;
    }

    private static List createDefaultInvocationInfo(Class interfacleClass, Class implClass)
    {
        MethodInvocationInfoImpl impl = new MethodInvocationInfoImpl();
        impl.setInterfaceName(interfacleClass);
        impl.setImplementsClassName(implClass);
        impl.setExceptionTranslate(IExceptionTranslator.DUMMY_TRANSLATOR);
        impl.setTransactionType(TransactionType.TRANSACTION_SUPPORT);
        impl.setMethodNamePattern("*");
        List ret = new ArrayList(1);
        ret.add(impl);
        return ret;
    }

    private static IExceptionTranslator initTranslater(String className)
    {
        Class clz;
        clz = Class.forName(className);
        if(!com/sinitek/base/metadb/support/IExceptionTranslator.isAssignableFrom(clz))
            throw new MetaDBContextSupportException("0009", new Object[] {
                className
            });
        return (IExceptionTranslator)clz.newInstance();
        BaseException be;
        be;
        throw be;
        Exception e;
        e;
        throw new MetaDBContextSupportException("0010", e, new Object[] {
            className
        });
    }

    private static IExceptionHandler initHandler(String className)
    {
        Class clz;
        clz = Class.forName(className);
        if(!com/sinitek/base/metadb/support/IExceptionHandler.isAssignableFrom(clz))
            throw new MetaDBContextSupportException("0011", new Object[] {
                className
            });
        return (IExceptionHandler)clz.newInstance();
        BaseException be;
        be;
        throw be;
        Exception e;
        e;
        throw new MetaDBContextSupportException("0012", e, new Object[] {
            className
        });
    }

    public static final String PUBLIC_ID_1_0 = "-//SINITEK//METADB//CONTEXTSUPPORT//1_0";
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CORE;
    }
}
