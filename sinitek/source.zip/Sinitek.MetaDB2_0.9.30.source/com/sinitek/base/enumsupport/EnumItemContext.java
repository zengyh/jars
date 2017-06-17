// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnumItemContext.java

package com.sinitek.base.enumsupport;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.enumsupport.config.XmlConfigFileReader;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.enumsupport:
//            EnumException, EnumBean, EnumItemClassUtil, IEnumItem

public final class EnumItemContext
{
    private static class EnumCacheValue
    {

        public IEnumItem getEnumByValue(int enumItemValue)
            throws EnumException
        {
            IEnumItem item = (IEnumItem)idCacheMap.get(new Integer(enumItemValue));
            if(item == null)
                throw new EnumException("0008", new Object[] {
                    enumName, new Integer(enumItemValue)
                });
            else
                return item;
        }

        public IEnumItem getEnumByName(String enumItemName)
            throws EnumException
        {
            IEnumItem item = (IEnumItem)nameCacheMap.get(enumItemName);
            if(item == null)
                throw new EnumException("0009", new Object[] {
                    enumName, enumItemName
                });
            else
                return item;
        }

        private Map idCacheMap;
        private Map nameCacheMap;
        private String enumName;


        public EnumCacheValue(EnumBean enumBean)
        {
            idCacheMap = new HashMap();
            nameCacheMap = new CaseInsensitiveMap();
            enumName = enumBean.getEnumName();
            IEnumItem item;
            for(Iterator iter = enumBean.getEnumItemList().iterator(); iter.hasNext(); nameCacheMap.put(item.getEnumItemName(), item))
            {
                item = (IEnumItem)iter.next();
                idCacheMap.put(new Integer(item.getEnumItemValue()), item);
            }

        }
    }

    private static class EnumCacheKey
    {

        public boolean equals(Object obj)
        {
            if(obj != null)
            {
                if(obj instanceof EnumCacheKey)
                {
                    EnumCacheKey target = (EnumCacheKey)obj;
                    return target.enumName.equalsIgnoreCase(enumName);
                } else
                {
                    return false;
                }
            } else
            {
                return false;
            }
        }

        public int hashCode()
        {
            return enumName.toLowerCase().hashCode();
        }

        private String enumName;
        private boolean checkFlag;
        private Date lastModifyDate;
        private URL fileUrl;








        private EnumCacheKey()
        {
        }

    }


    private EnumItemContext()
    {
        urlCache = new Hashtable();
        enumCache = new Hashtable();
        enumNameCache = new Hashtable();
    }

    public static synchronized EnumItemContext getInstance()
    {
        if(instance == null)
            instance = new EnumItemContext();
        return instance;
    }

    public synchronized void addConfigFile(URL url)
        throws EnumException
    {
        File file;
        boolean checkFlag;
        Date lastModifyDate;
        try
        {
            if(urlCache.containsKey(url))
            {
                LOGGER.warn((new StringBuilder()).append("\u914D\u7F6E\u6587\u4EF6[").append(url.getPath()).append("]\u5DF2\u7ECF\u88AB\u88C5\u8F7D").toString());
                return;
            }
        }
        catch(BaseException be)
        {
            throw be;
        }
        catch(Exception ex)
        {
            throw new EnumException("0001", ex, new Object[] {
                url
            });
        }
        file = new File(URLDecoder.decode(url.getPath(), "utf-8"));
        if(file.exists())
        {
            checkFlag = true;
            lastModifyDate = new Date(file.lastModified());
        } else
        {
            checkFlag = false;
            lastModifyDate = new Date();
        }
        readConfigFile(url, checkFlag, lastModifyDate, true);
    }

    public List getEnumItems(String enumName)
        throws EnumException
    {
        EnumCacheValue bean = getCacheValue(enumName);
        return new ArrayList(bean.idCacheMap.values());
    }

    public List getEnumItems(Class enumName)
        throws EnumException
    {
        return getEnumItems(enumName.getName());
    }

    public IEnumItem getEnumItem(String enumName, int enumItemValue)
        throws EnumException
    {
        return getCacheValue(enumName).getEnumByValue(enumItemValue);
    }

    public IEnumItem getEnumItem(Class enumName, int enumItemValue)
        throws EnumException
    {
        return getEnumItem(enumName.getName(), enumItemValue);
    }

    public IEnumItem getEnumItem(String enumName, String enumItemName)
        throws EnumException
    {
        return getCacheValue(enumName).getEnumByName(enumItemName);
    }

    public IEnumItem getEnumItem(Class enumName, String enumItemName)
        throws EnumException
    {
        return getEnumItem(enumName.getName(), enumItemName);
    }

    private synchronized EnumCacheValue getCacheValue(String enumName)
    {
        EnumCacheKey testKey;
        EnumCacheValue value;
        testKey = new EnumCacheKey();
        testKey.enumName = enumName;
        value = (EnumCacheValue)enumCache.get(testKey);
        if(value != null)
            break MISSING_BLOCK_LABEL_126;
        Class cls = Class.forName(enumName);
        EnumBean bean = EnumItemClassUtil.parseEnumItemClass(cls);
        testKey.checkFlag = false;
        testKey.lastModifyDate = new Date(0L);
        value = new EnumCacheValue(bean);
        enumCache.put(testKey, value);
        enumNameCache.put(enumName.toLowerCase(), testKey);
        return value;
        ClassNotFoundException cnfe;
        cnfe;
        throw new EnumException("0011", cnfe, new Object[] {
            enumName
        });
        EnumCacheKey key = (EnumCacheKey)enumNameCache.get(enumName.toLowerCase());
        if(key.checkFlag)
            try
            {
                File urlFile = new File(URLDecoder.decode(key.fileUrl.getPath(), "utf-8"));
                if(urlFile.exists() && urlFile.lastModified() != key.lastModifyDate.getTime())
                {
                    readConfigFile(key.fileUrl, true, new Date(urlFile.lastModified()), false);
                    value = (EnumCacheValue)enumCache.get(testKey);
                }
            }
            catch(UnsupportedEncodingException e)
            {
                throw new EnumException("0014", e);
            }
        return value;
    }

    private void readConfigFile(URL url, boolean checkFlag, Date lastModifyDate, boolean isCheckExists)
    {
        List enumBeans = XmlConfigFileReader.readCofigFile(url);
        List urlCacheValue = new ArrayList();
        EnumBean enumBean;
        EnumCacheKey key;
        for(Iterator iter = enumBeans.iterator(); iter.hasNext(); enumNameCache.put(enumBean.getEnumName().toLowerCase(), key))
        {
            enumBean = (EnumBean)iter.next();
            key = new EnumCacheKey();
            key.checkFlag = checkFlag;
            key.lastModifyDate = lastModifyDate;
            key.enumName = enumBean.getEnumName();
            key.fileUrl = url;
            if(isCheckExists && enumCache.containsKey(key))
                throw new EnumException("0010", new Object[] {
                    url.getFile(), enumBean.getEnumName()
                });
            EnumCacheValue value = new EnumCacheValue(enumBean);
            urlCacheValue.add(value);
            enumCache.put(key, value);
        }

        urlCache.put(url, urlCacheValue);
    }

    private static EnumItemContext instance;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/enumsupport/EnumItemContext);
    private Map urlCache;
    private Map enumCache;
    private Map enumNameCache;

}
