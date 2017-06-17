// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropertiesHelper.java

package com.sinitek.base.common;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import org.apache.log4j.Logger;

public class PropertiesHelper
{
    private static class ErrorReadProperties extends Properties
    {

        public String getProperty(String key, String defaultValue)
        {
            return getProperty(key);
        }

        public String getProperty(String key)
        {
            return (new StringBuilder()).append("\u53D6\u5F02\u5E38\u4EE3\u7801[").append(key).append("]\u7684\u9519\u8BEF\u8BE6\u7EC6\u4FE1\u606F\u9519\u8BEF-\uFF08").append(propPath).append("\uFF09\u6587\u4EF6\u65E0\u6CD5\u8BFB\u53D6").toString();
        }

        private String propPath;

        public ErrorReadProperties(String propPath)
        {
            this.propPath = propPath;
        }
    }

    private static class CacheBean
    {

        private boolean isNeedCheck;
        private Date lastUpdateTime;
        private Properties content;







        private CacheBean()
        {
        }

    }


    public PropertiesHelper()
    {
    }

    public static Properties getProperties(String path)
    {
        path = path.trim();
        CacheBean cb = (CacheBean)cacheMap.get(path);
        if(cb == null)
            return loadIntoCache(path).content;
        if(cb.isNeedCheck)
            updateCacheBean(path, cb);
        return cb.content;
    }

    private static CacheBean loadIntoCache(String path)
    {
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        CacheBean cb = new CacheBean();
        if(url == null)
        {
            cb = createErrorBean(path);
        } else
        {
            boolean isFile = isFile(url);
            if(isFile)
            {
                try
                {
                    File file = getFileByUrl(url);
                    cb.lastUpdateTime = new Date(file.lastModified());
                    cb.isNeedCheck = true;
                }
                catch(UnsupportedEncodingException e)
                {
                    LOGGER.error("\u8BFB\u53D6\u6587\u4EF6\u65E5\u671F\u5931\u8D25", e);
                    cb.lastUpdateTime = new Date(0L);
                    cb.isNeedCheck = false;
                }
            } else
            {
                cb.isNeedCheck = false;
                cb.lastUpdateTime = new Date();
            }
            try
            {
                cb.content = readPropertiesFormUrl(url);
            }
            catch(Exception ex)
            {
                LOGGER.error((new StringBuilder()).append("\u8BFB\u53D6Properties\u6587\u4EF6\u5931\u8D25[").append(path).append("]").toString(), ex);
                cb = createErrorBean(path);
            }
        }
        cacheMap.put(path, cb);
        return cb;
    }

    private static synchronized void updateCacheBean(String path, CacheBean cb)
    {
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        try
        {
            File file = getFileByUrl(url);
            Date newDate = new Date(file.lastModified());
            if(newDate.compareTo(cb.lastUpdateTime) > 0)
            {
                LOGGER.info((new StringBuilder()).append("\u5237\u65B0\u9488\u5BF9\u8DEF\u5F84[").append(path).append("]\u7684Properties\u5185\u5BB9\u7F13\u5B58").toString());
                cb.content = readPropertiesFormUrl(url);
                cb.lastUpdateTime = newDate;
            }
        }
        catch(Exception e)
        {
            LOGGER.error("\u66F4\u65B0\u7F13\u5B58Bean\u5931\u8D25", e);
        }
    }

    private static boolean isFile(URL url)
    {
        try
        {
            File file = getFileByUrl(url);
            return file.exists();
        }
        catch(UnsupportedEncodingException e)
        {
            return false;
        }
    }

    private static CacheBean createErrorBean(String path)
    {
        CacheBean ret = new CacheBean();
        ret.content = new ErrorReadProperties(path);
        ret.isNeedCheck = true;
        ret.lastUpdateTime = new Date(0L);
        return ret;
    }

    private static File getFileByUrl(URL url)
        throws UnsupportedEncodingException
    {
        return new File(URLDecoder.decode(url.getFile(), "utf-8"));
    }

    private static Properties readPropertiesFormUrl(URL url)
        throws Exception
    {
        InputStream is = null;
        Properties properties;
        Properties prop = new Properties();
        is = url.openStream();
        prop.load(is);
        properties = prop;
        if(is != null)
            is.close();
        return properties;
        Exception exception;
        exception;
        if(is != null)
            is.close();
        throw exception;
    }

    private static Map cacheMap = new Hashtable();
    private static Logger LOGGER = Logger.getLogger(com/sinitek/base/common/PropertiesHelper);

}
