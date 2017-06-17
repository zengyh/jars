// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceContextLocalImpl.java

package com.sinitek.ds.core.service.impl.local;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.support.*;
import com.sinitek.ds.core.service.*;
import com.sinitek.ds.core.service.config.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.ds.core.service.impl.local:
//            ServiceCoreRequestImpl, ErrorResponse

public class ServiceContextLocalImpl
    implements IServiceContext
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


    public ServiceContextLocalImpl()
    {
        handlerMap = new HashMap();
        loggerMap = new HashMap();
        dealingList = new HashMap();
        waitList = new HashMap();
        logRequest = false;
        logResponse = false;
        URL configFileUrl = Thread.currentThread().getContextClassLoader().getResource("servicecoreconfig.xml");
        if(configFileUrl == null)
            throw new ServiceRuntimeException("0010");
        Map config = ServiceConfigReader.readServiceConfig(configFileUrl);
        configMap = config;
        InputStream is = null;
        Properties prop = new Properties();
        try
        {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("servicecore.properties");
            if(is == null)
                throw new ServiceConfigException("0007");
            prop.load(is);
        }
        catch(IOException ex)
        {
            throw new ServiceConfigException("0006", ex);
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
                    LOGGER.error("\u5173\u95EDservicecore.properties\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
                }
        }
        checkProperties(prop, "service.logger.level");
        checkProperties(prop, "service.logger.path");
        checkProperties(prop, "service.logger.pattern");
        logRequest = "true".equalsIgnoreCase(prop.getProperty("service.logger.logrequest", "false"));
        logResponse = "true".equalsIgnoreCase(prop.getProperty("service.logger.logresponse", "false"));
        java.util.Map.Entry entry;
        for(Iterator iter = config.entrySet().iterator(); iter.hasNext(); handlerMap.put(entry.getKey(), createHandler((IServiceConfigBean)entry.getValue(), prop)))
            entry = (java.util.Map.Entry)iter.next();

        coreLogger = createLogger("servicecore", null, prop);
    }

    public IServiceResponse handleService(IServiceRequest request)
        throws ServiceException
    {
        String functionCode;
        List keyParams;
        StringBuffer keyWord;
        Date startTime;
        functionCode = request.getFunctionCode();
        keyParams = new ArrayList();
        keyWord = new StringBuffer(functionCode);
        startTime = new Date();
        IServiceResponse iserviceresponse;
        IServiceHandler handler = (IServiceHandler)handlerMap.get(functionCode);
        if(handler == null)
            throw new ServiceRuntimeException("0011", new Object[] {
                functionCode
            });
        IServiceConfigBean configBean = (IServiceConfigBean)configMap.get(functionCode);
        keyParams = configBean.getKeyParams();
        if(keyParams.size() != 0)
        {
            for(Iterator iter = keyParams.iterator(); iter.hasNext();)
            {
                String paramName = (String)iter.next();
                if(request.contains(paramName))
                    keyWord.append(request.getString(paramName));
                else
                    keyWord.append("null");
            }

            lock(keyWord.toString());
        }
        configBean.checkRequest(request);
        Logger handlerLogger = (Logger)loggerMap.get(functionCode);
        if(logRequest && handlerLogger.isDebugEnabled())
        {
            String key;
            for(Iterator iter = request.getParamNames().iterator(); iter.hasNext(); handlerLogger.debug("REQUEST:[" + key + "]=[" + request.getObject(key) + "]"))
                key = (String)iter.next();

        }
        IServiceResponse resp = handler.handle(new ServiceCoreRequestImpl(configBean, request));
        if(logResponse && handlerLogger.isDebugEnabled())
        {
            if(resp.isSuccess())
                handlerLogger.debug("\u8FD4\u56DE\u6210\u529F");
            else
                handlerLogger.debug("\u8FD4\u56DE\u5931\u8D25");
            handlerLogger.debug("\u8FD4\u56DE\u7801:" + resp.getReturnCode());
            handlerLogger.debug("\u8FD4\u56DE\u4FE1\u606F:" + resp.getReturnMessage());
            String key;
            for(Iterator iter = resp.getParamNames().iterator(); iter.hasNext(); handlerLogger.debug("RESPONSE:[" + key + "]=[" + resp.getObject(key) + "]"))
                key = (String)iter.next();

        }
        resp.setCallEndTime(new Date());
        iserviceresponse = resp;
        return iserviceresponse;
        BaseException e;
        e;
        ErrorResponse errorresponse;
        coreLogger.error("\u8C03\u7528\u670D\u52A1[" + functionCode + "]\u629B\u51FA\u5E95\u5C42\u5F02\u5E38", e);
        errorresponse = new ErrorResponse(e.getCode(), e.getErrorMsg(), startTime);
        return errorresponse;
        Exception ex;
        ex;
        coreLogger.error("\u8C03\u7528\u670D\u52A1[" + functionCode + "]\u629B\u51FA\u672A\u77E5\u5F02\u5E38", ex);
        errorresponse = new ErrorResponse("99999999", ex.getMessage(), startTime);
        return errorresponse;
        local;
        if(keyParams.size() != 0)
            unlock(keyWord.toString());
        JVM INSTR ret 13;
    }

    private IServiceHandler createHandler(IServiceConfigBean configBean, Properties prop)
    {
        IServiceHandler handler;
        try
        {
            handler = (IServiceHandler)Class.forName(configBean.getServiceClass()).newInstance();
        }
        catch(Exception e)
        {
            throw new ServiceConfigException("0005", e, new Object[] {
                configBean.getServiceClass()
            });
        }
        if((com.sinitek.base.metadb.support.IMetaDBContextSupport.class).isAssignableFrom(handler.getClass()))
        {
            ContextSupportConfigInfoImpl configInfo = new ContextSupportConfigInfoImpl();
            configInfo.setImplClassName(handler.getClass());
            configInfo.setInterfaceName(com.sinitek.ds.core.service.IServiceHandler.class);
            configInfo.setName(configBean.getFunctionCode());
            configInfo.setInitProperties(new Properties());
            configInfo.setInterfaces(new String[0]);
            List temp = new ArrayList(2);
            MethodInvocationInfoImpl invocationInfo = new MethodInvocationInfoImpl();
            invocationInfo.setExceptionTranslate(IExceptionTranslator.DUMMY_TRANSLATOR);
            invocationInfo.setImplementsClassName(handler.getClass());
            invocationInfo.setInterfaceName(com.sinitek.ds.core.service.IServiceContext.class);
            invocationInfo.setMethodNamePattern("handle");
            invocationInfo.setTransactionType(TransactionType.TRANSACTION_NEW);
            temp.add(invocationInfo);
            invocationInfo = new MethodInvocationInfoImpl();
            invocationInfo.setExceptionTranslate(IExceptionTranslator.DUMMY_TRANSLATOR);
            invocationInfo.setImplementsClassName(handler.getClass());
            invocationInfo.setInterfaceName(com.sinitek.ds.core.service.IServiceContext.class);
            invocationInfo.setMethodNamePattern("*");
            invocationInfo.setTransactionType(TransactionType.TRANSACTION_NONE);
            temp.add(invocationInfo);
            configInfo.setMethodInvocationInfos(temp);
            handler = (IServiceHandler)MetaDBContextSupportBuilder.createProxy(configInfo);
        }
        Logger logger = createLogger(configBean.getFunctionCode(), configBean.getFunctionName(), prop);
        loggerMap.put(configBean.getFunctionCode(), logger);
        handler.setLogger(logger);
        handler.setParams(configBean.getInitParams());
        return handler;
    }

    private void checkProperties(Properties prop, String key)
    {
        if(!prop.containsKey(key))
            throw new ServiceConfigException("0008", new Object[] {
                key
            });
        else
            return;
    }

    private synchronized void lock(String keyWord)
    {
        while(dealingList.containsKey(keyWord)) 
            try
            {
                LOGGER.debug("\u5173\u952E\u5B57\u7B26\u4E32\u4E3A[" + keyWord + "]\u7684\u7533\u8BF7\u5C06\u8FDB\u5165\u7B49\u5F85\u961F\u5217");
                System.out.println("\u5173\u952E\u5B57\u7B26\u4E32\u4E3A[" + keyWord + "]\u7684\u7533\u8BF7\u5C06\u8FDB\u5165\u7B49\u5F85\u961F\u5217");
                if(!waitList.containsKey(keyWord))
                    waitList.put(keyWord, keyWord);
                wait();
            }
            catch(InterruptedException e)
            {
                LOGGER.warn("\u5173\u952E\u5B57\u7B26\u4E32\u4E3A[" + keyWord + "]\u7684\u7533\u8BF7\u8FDB\u5165\u7B49\u5F85\u961F\u5217\u5931\u8D25", e);
            }
        LOGGER.debug("\u5173\u952E\u5B57\u7B26\u4E32\u4E3A[" + keyWord + "]\u7684\u7533\u8BF7\u5F00\u59CB\u6267\u884C");
        dealingList.put(keyWord, keyWord);
    }

    private synchronized void unlock(String keyWord)
    {
        dealingList.remove(keyWord);
        if(waitList.containsKey(keyWord))
        {
            waitList.remove(keyWord);
            notifyAll();
        }
    }

    private Logger createLogger(String functionCode, String functionName, Properties prop)
    {
        try
        {
            ClassLoader loader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
            Class propConfigCls = loader.loadClass((org.apache.log4j.PropertyConfigurator.class).getName());
            Method configMethod = propConfigCls.getMethod("configure", new Class[] {
                java.util.Properties.class
            });
            Properties logProperties = new Properties();
            logProperties.setProperty("log4j.rootLogger", "off");
            logProperties.setProperty("log4j.logger." + functionCode, prop.getProperty("service.logger.level") + "," + functionCode);
            logProperties.setProperty("log4j.appender." + functionCode, "org.apache.log4j.DailyRollingFileAppender");
            logProperties.setProperty("log4j.appender." + functionCode + ".DatePattern", "'.'yyyy-MM-dd");
            logProperties.setProperty("log4j.appender." + functionCode + ".layout", "org.apache.log4j.PatternLayout");
            logProperties.setProperty("log4j.appender." + functionCode + ".layout.ConversionPattern", prop.getProperty("service.logger.pattern"));
            File logDir = new File(prop.getProperty("service.logger.path"));
            if(!logDir.exists())
                logDir.mkdirs();
            if(StringUtils.isBlank(functionName))
                logProperties.setProperty("log4j.appender." + functionCode + ".File", logDir.getPath() + File.separator + functionCode + ".log");
            else
                logProperties.setProperty("log4j.appender." + functionCode + ".File", logDir.getPath() + File.separator + "[" + functionCode + "]" + functionName + ".log");
            configMethod.invoke(null, new Object[] {
                logProperties
            });
            Class loggerCls = loader.loadClass((org.apache.log4j.Logger.class).getName());
            Method method = loggerCls.getMethod("getLogger", new Class[] {
                java.lang.String.class
            });
            return (Logger)method.invoke(null, new Object[] {
                functionCode
            });
        }
        catch(Exception e)
        {
            throw new ServiceConfigException("0009", e);
        }
    }

    private static final Logger LOGGER;
    private Map handlerMap;
    private Map configMap;
    private Map loggerMap;
    private Map dealingList;
    private Map waitList;
    private boolean logRequest;
    private boolean logResponse;
    private Logger coreLogger;

    static 
    {
        LOGGER = Logger.getLogger(com.sinitek.ds.core.service.impl.local.ServiceContextLocalImpl.class);
    }
}
