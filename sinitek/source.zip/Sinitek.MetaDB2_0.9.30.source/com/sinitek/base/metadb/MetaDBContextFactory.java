// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBContextFactory.java

package com.sinitek.base.metadb;

import com.sinitek.base.control.server.ControlServer;
import com.sinitek.base.control.server.HandlerConfig;
import com.sinitek.base.control.server.IServerConfig;
import com.sinitek.base.enumsupport.AbstractEnumItem;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.base.metadb.cache.IMetaDBCacheContextImpl;
import com.sinitek.base.metadb.cache.MetaDBCacheContextBuilder;
import com.sinitek.base.metadb.config.GlobalConfig;
import com.sinitek.base.metadb.config.IDataSourceProvider;
import com.sinitek.base.metadb.config.IMetaDBSysLoader;
import com.sinitek.base.metadb.config.MetaDBConfigException;
import com.sinitek.base.metadb.config.impl.MetaDBSysLoaderDefaultImpl;
import com.sinitek.base.metadb.hibernate.MetaDBInterceptor;
import com.sinitek.base.metadb.util.TempFileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

// Referenced classes of package com.sinitek.base.metadb:
//            MetaDBCoreException, MetaDBContextInfoImpl, IMetaDBContext, MetaDBContextImpl, 
//            MetaDBException, IEntity, MetaDBLoggerFactory, IMetaDBContextInfo

public class MetaDBContextFactory
{
    public static class CreateType extends AbstractEnumItem
    {

        protected CreateType(String enumItemName, int enumItemValue, String enumItemInfo, String enumItemDisplayValue)
        {
            super(enumItemName, enumItemValue, enumItemInfo, enumItemDisplayValue);
        }
    }


    private MetaDBContextFactory()
    {
        useCountMap = new Hashtable();
        init();
    }

    public static synchronized MetaDBContextFactory getInstance()
        throws MetaDBException
    {
        if(instance == null)
            instance = new MetaDBContextFactory();
        return instance;
    }

    public DataSource getDataSource()
    {
        return config.getDataSourceProvider().getDataSource();
    }

    public IDataSourceProvider getDataSourceProvider()
    {
        return config.getDataSourceProvider();
    }

    public File createTempFile(IEntity entity)
        throws MetaDBException
    {
        return tempFileUtil.createTempFile(tempFileUtil.createTempFileName(entity.getEntityName()));
    }

    public IEntity getEntity(String entityName)
        throws MetaDBException
    {
        return metaDBLoader.getEntity(entityName);
    }

    public IMetaDBContext createContext()
        throws MetaDBException
    {
        return createContext(AUTO);
    }

    public synchronized IMetaDBContext createContext(CreateType type)
        throws MetaDBException
    {
        if(type.equals(AUTO) && config.getMetaDBProperties().getProperty("metadb.autosessionmode.createnew", "false").equals("true"))
            type = CREATE_ONLY;
        IMetaDBContext impl;
        if(type.equals(CREATE_ONLY))
        {
            impl = createNewContext();
            addUseCount(impl.getId());
            return impl;
        }
        if(type.equals(CURRENT_ONLY))
        {
            impl = getCurrentContext();
            if(impl == null)
                throw new MetaDBCoreException("0017");
            if(LOGGER.isDebugEnabled())
            {
                int count = ((Integer)useCountMap.get(Integer.valueOf(impl.getId()))).intValue();
                LOGGER.debug((new StringBuilder()).append("MetaDB\u4F1A\u8BDD[").append(impl.getId()).append("]\u7684\u5F15\u7528\u6B21\u6570\u4ECE\u539F\u6765\u7684[").append(count).append("]\u6B21\u589E\u52A0\u4E00\u6B21").toString());
            }
            addUseCount(impl.getId());
            return impl;
        }
        impl = getCurrentContext();
        if(impl == null)
        {
            impl = createNewContext();
            addUseCount(impl.getId());
        } else
        {
            if(LOGGER.isDebugEnabled())
            {
                int count = ((Integer)useCountMap.get(Integer.valueOf(impl.getId()))).intValue();
                LOGGER.debug((new StringBuilder()).append("MetaDB\u4F1A\u8BDD[").append(impl.getId()).append("]\u7684\u5F15\u7528\u6B21\u6570\u4ECE\u539F\u6765\u7684[").append(count).append("]\u6B21\u589E\u52A0\u4E00\u6B21").toString());
            }
            addUseCount(impl.getId());
        }
        return impl;
    }

    public IMetaDBCacheContext getCacheContext()
    {
        return cacheContext;
    }

    public IMetaDBContextInfo getContextInfo()
    {
        MetaDBContextInfoImpl impl = new MetaDBContextInfoImpl();
        int contextCount = Integer.parseInt(config.getMetaDBProperties().getProperty("metadb.contextsize").trim());
        impl.setMaxContextCount(contextCount);
        impl.setIdelContextCount(contextIdStack.size());
        Map temp = new HashMap(useCountMap);
        List useCountList = new ArrayList(temp.size());
        for(int i = 1; i <= contextCount; i++)
        {
            Object _c = temp.get(new Integer(i));
            if(_c == null)
                _c = new Integer(0);
            useCountList.add(_c);
        }

        impl.setUseCountList(useCountList);
        return impl;
    }

    private IMetaDBContext getCurrentContext()
    {
        Stack stack = (Stack)CONTEXT_CACHE.get();
        if(stack != null && !stack.isEmpty())
            return (IMetaDBContext)stack.peek();
        else
            return null;
    }

    private synchronized IMetaDBContext createNewContext()
    {
        if(LOGGER.isDebugEnabled())
            LOGGER.debug("\u51C6\u5907\u521B\u5EFA\u5168\u65B0\u7684MetaDB\u4F1A\u8BDD");
        boolean isAutoCommit = "true".equalsIgnoreCase(config.getMetaDBProperties().getProperty("metadb.autocommit"));
        boolean isFlushOnOperation = "true".equalsIgnoreCase(config.getCacheProperties().getProperty("metadb.flushonoperation", "true"));
        Session session = metaDBLoader.getSessionFactory().openSession(new MetaDBInterceptor());
        session.setFlushMode(FlushMode.COMMIT);
        MetaDBContextImpl context = new MetaDBContextImpl(getContextId().intValue(), session, (IMetaDBCacheContextImpl)getCacheContext(), isAutoCommit);
        context.setDefaultIteratorFatchSize(defaultIteratorFatchSize);
        context.setFlushOnOperation(isFlushOnOperation);
        Stack stack = (Stack)CONTEXT_CACHE.get();
        if(stack == null)
        {
            stack = new Stack();
            CONTEXT_CACHE.set(stack);
        }
        stack.push(context);
        return context;
    }

    private void addUseCount(int id)
    {
        Integer key = new Integer(id);
        Integer count = (Integer)useCountMap.get(key);
        if(count == null)
            count = new Integer(1);
        else
            count = new Integer(count.intValue() + 1);
        useCountMap.put(key, count);
    }

    private void init()
    {
        config = new GlobalConfig();
        metaDBLoader = new MetaDBSysLoaderDefaultImpl();
        metaDBLoader.initData(config);
        metaDBLoader.initOtherData(config);
        initTempFileUtil();
        checkMetaDBCoreConfig();
        initCacheContext();
        startControl();
    }

    public void reloadMongoDB()
    {
        metaDBLoader.reloadMongoDB(config);
    }

    private void initCacheContext()
    {
        if("true".equalsIgnoreCase(config.getCacheProperties().getProperty("cache.enable", "false")))
        {
            LOGGER.info("\u542F\u7528\u7F13\u5B58\u6A21\u5757");
            cacheContext = MetaDBCacheContextBuilder.buildCacheContext(config.getCacheProperties(), metaDBLoader);
        } else
        {
            LOGGER.info("\u7F13\u5B58\u6A21\u5757\u88AB\u914D\u7F6E\u4E3A\u7981\u7528");
        }
    }

    private void initTempFileUtil()
    {
        String tmpFilePath = config.getMetaDBProperties().getProperty("metadb.tempfile.path");
        tmpFilePath = tmpFilePath != null ? tmpFilePath.trim() : "";
        if(tmpFilePath.length() == 0)
            throw new MetaDBConfigException("0036");
        TempFileUtil _fileUtil = new TempFileUtil(tmpFilePath);
        String isAutoDel = config.getMetaDBProperties().getProperty("metadb.tempfile.autodelete");
        if(isAutoDel != null && "true".equalsIgnoreCase(isAutoDel.trim()))
        {
            MetaDBLoggerFactory.LOGGER_CONFIG.debug("\u51C6\u5907\u542F\u52A8\u8FC7\u671F\u4E34\u65F6\u6587\u4EF6\u81EA\u52A8\u5220\u9664\u7EBF\u7A0B");
            String szCheckTime = config.getMetaDBProperties().getProperty("metadb.tempfile.autodelete.checktime", "60").trim();
            try
            {
                int checkTime = Integer.parseInt(szCheckTime);
                MetaDBLoggerFactory.LOGGER_CONFIG.debug((new StringBuilder()).append("\u8FC7\u671F\u4E34\u65F6\u6587\u4EF6\u81EA\u52A8\u5220\u9664\u7EBF\u7A0B\u8FD0\u884C\u95F4\u9694[").append(checkTime).append("]\u79D2").toString());
                _fileUtil.startCheck(checkTime);
            }
            catch(NumberFormatException e)
            {
                throw new MetaDBConfigException("0037", new Object[] {
                    szCheckTime
                });
            }
            MetaDBLoggerFactory.LOGGER_CONFIG.debug("\u542F\u52A8\u8FC7\u671F\u4E34\u65F6\u6587\u4EF6\u81EA\u52A8\u5220\u9664\u7EBF\u7A0B\u6210\u529F");
        }
        tempFileUtil = _fileUtil;
    }

    private void startControl()
    {
        if("true".equalsIgnoreCase(this.config.getMetaDBProperties().getProperty("metadb.control.enable", "false")))
        {
            MetaDBLoggerFactory.LOGGER_CONFIG.debug("\u542F\u7528MetaDB\u76D1\u63A7\u7AEF\u53E3");
            assertConfigExist(this.config.getMetaDBProperties(), "metadb.control.port");
            final int port = Integer.parseInt(this.config.getMetaDBProperties().getProperty("metadb.control.port"));
            assertConfigExist(this.config.getMetaDBProperties(), "metadb.control.logfile");
            String logDir = this.config.getMetaDBProperties().getProperty("metadb.control.logfile");
            final File dir = new File(logDir);
            if(!dir.exists())
                dir.mkdirs();
            MetaDBLoggerFactory.LOGGER_CONFIG.debug((new StringBuilder()).append("MetaDB\u76D1\u63A7\u7AEF\u53E3[").append(port).append("]").toString());
            IServerConfig config = new IServerConfig() {

                public List getHandlerConfigs()
                {
                    List configs = new ArrayList(1);
                    HandlerConfig hc = new HandlerConfig();
                    hc.setHandlerClass("com.sinitek.base.metadb.control.MetaDBContextInfoHandler");
                    hc.setLogLevel("error");
                    hc.setName("getInfo");
                    hc.setHandlerProperties(new Properties());
                    configs.add(hc);
                    return configs;
                }

                public String getLogPath()
                {
                    return dir.getAbsolutePath();
                }

                public int getPort()
                {
                    return port;
                }

                public int getThreadCount()
                {
                    return 2;
                }

                final File val$dir;
                final int val$port;
                final MetaDBContextFactory this$0;

            
            {
                this$0 = MetaDBContextFactory.this;
                dir = file;
                port = i;
                super();
            }
            };
            ControlServer server = new ControlServer();
            server.startServer(config, true);
        }
    }

    private void checkMetaDBCoreConfig()
        throws MetaDBException
    {
        assertConfigExist(config.getMetaDBProperties(), "metadb.file.path");
        File filePath = new File(config.getMetaDBProperties().getProperty("metadb.file.path"));
        if(!filePath.exists() && !filePath.mkdirs())
            throw new MetaDBConfigException("0039", new Object[] {
                filePath
            });
        assertConfigExist(config.getMetaDBProperties(), "metadb.contextsize");
        String szMaxContext = config.getMetaDBProperties().getProperty("metadb.contextsize").trim();
        try
        {
            int maxContext = Integer.parseInt(szMaxContext);
            MetaDBLoggerFactory.LOGGER_CONFIG.debug((new StringBuilder()).append("MetaDB\u7684\u6700\u5927\u4F1A\u8BDD\u6570\u91CF\u4E3A[").append(maxContext).append("]").toString());
            contextIdStack = new Stack();
            for(int i = maxContext; i > 0; i--)
                contextIdStack.push(new Integer(i));

        }
        catch(NumberFormatException e)
        {
            throw new MetaDBConfigException("0040", e, new Object[] {
                szMaxContext
            });
        }
        assertConfigExist(config.getMetaDBProperties(), "metadb.default_iterate_fatchsize");
        String szIterFatchSize = config.getMetaDBProperties().getProperty("metadb.default_iterate_fatchsize").trim();
        try
        {
            int _iterFatchSize = Integer.parseInt(szIterFatchSize);
            MetaDBLoggerFactory.LOGGER_CONFIG.debug((new StringBuilder()).append("\u8FED\u4EE3\u6A21\u5F0F\u4E0B\u7684\u6570\u636E\u6279\u6B21\u5927\u5C0F\u4E3A[").append(_iterFatchSize).append("]").toString());
            defaultIteratorFatchSize = _iterFatchSize;
        }
        catch(NumberFormatException nfe)
        {
            throw new MetaDBConfigException("0041", nfe, new Object[] {
                szIterFatchSize
            });
        }
    }

    private void assertConfigExist(Properties prop, String configName)
        throws MetaDBException
    {
        String configValue = prop.getProperty(configName);
        if(configValue == null || configValue.trim().length() == 0)
            throw new MetaDBConfigException("0038", new Object[] {
                configName
            });
        else
            return;
    }

    private synchronized Integer getContextId()
    {
        while(contextIdStack.isEmpty()) 
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
                throw new MetaDBCoreException("0016", e);
            }
        return (Integer)contextIdStack.pop();
    }

    private synchronized void returnContextId(Integer contextId)
    {
        contextIdStack.push(contextId);
        notifyAll();
    }

    synchronized boolean isNeedCloseCall(MetaDBContextImpl context)
    {
        Integer ctxId = new Integer(context.getId());
        int count = ((Integer)useCountMap.get(ctxId)).intValue();
        return count == 1;
    }

    synchronized boolean closeContext(MetaDBContextImpl context)
        throws MetaDBCoreException
    {
        Integer ctxId;
        int count;
        Exception exception;
        ctxId = new Integer(context.getId());
        count = ((Integer)useCountMap.get(ctxId)).intValue();
        if(count != 1)
            break MISSING_BLOCK_LABEL_211;
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("MetaDB\u4F1A\u8BDD[").append(ctxId).append("]\u5C06\u88AB\u5173\u95ED").toString());
        Stack stack;
        try
        {
            context.getSession().close();
        }
        catch(HibernateException e)
        {
            throw new MetaDBCoreException("0025", e, new Object[] {
                ctxId
            });
        }
        finally
        {
            useCountMap.remove(ctxId);
        }
        useCountMap.remove(ctxId);
        stack = (Stack)CONTEXT_CACHE.get();
        stack.pop();
        if(stack.isEmpty())
            CONTEXT_CACHE.set(null);
        returnContextId(ctxId);
        break MISSING_BLOCK_LABEL_209;
        Stack stack = (Stack)CONTEXT_CACHE.get();
        stack.pop();
        if(stack.isEmpty())
            CONTEXT_CACHE.set(null);
        returnContextId(ctxId);
        throw exception;
        return true;
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("MetaDB\u4F1A\u8BDD[").append(ctxId).append("]\u7684\u5F15\u7528\u6B21\u6570\u4ECE\u539F\u6765\u7684[").append(count).append("]\u6B21\u51CF\u5C11\u4E00\u6B21").toString());
        useCountMap.put(ctxId, new Integer(count - 1));
        return false;
    }

    public static final CreateType CREATE_ONLY = new CreateType("createonly", 1, "\u5FC5\u987B\u521B\u5EFA\u65B0\u4F1A\u8BDD", "");
    public static final CreateType CURRENT_ONLY = new CreateType("currentonly", 2, "\u5FC5\u987B\u4F7F\u7528\u5F53\u524D\u4F1A\u8BDD", "");
    public static final CreateType AUTO = new CreateType("auto", 3, "\u81EA\u52A8", "");
    private static MetaDBContextFactory instance;
    private IMetaDBSysLoader metaDBLoader;
    private GlobalConfig config;
    private TempFileUtil tempFileUtil;
    private IMetaDBCacheContextImpl cacheContext;
    private Stack contextIdStack;
    private int defaultIteratorFatchSize;
    private static final ThreadLocal CONTEXT_CACHE = new ThreadLocal();
    private Map useCountMap;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_CORE;
    }
}
