// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   I18nSupport.java

package com.sinitek.sirm.common.support.i18n;

import com.sinitek.sirm.common.i18n.*;
import com.sinitek.sirm.common.i18n.utils.LocaleUtil;
import com.sinitek.sirm.common.loader.utils.AppLoader;
import com.sinitek.sirm.common.loader.utils.IAppLoaderContext;
import com.sinitek.sirm.common.support.LocalSetting;
import com.sinitek.sirm.common.utils.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class I18nSupport
    implements AppLoader
{

    public I18nSupport()
    {
        isInitialized = false;
    }

    public boolean isInitialized()
    {
        return isInitialized;
    }

    public void initialize(IAppLoaderContext context)
    {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource arr$[];
        int len$;
        int i$;
        Resource resources[] = resolver.getResources(configFilePattern);
        arr$ = resources;
        len$ = arr$.length;
        i$ = 0;
_L3:
        if(i$ >= len$) goto _L2; else goto _L1
_L1:
        Resource res;
        InputStream inStream;
        res = arr$[i$];
        inStream = null;
        String path = res.getURL().getPath();
        String localeStr = path.substring(0, path.lastIndexOf("/"));
        localeStr = localeStr.substring(localeStr.lastIndexOf("/") + 1);
        inStream = res.getInputStream();
        Properties properties = new Properties();
        properties.load(inStream);
        Integer level = Integer.valueOf(Integer.parseInt(properties.getProperty("level", "0")));
        LocaleLiteralManager localeLiteralManager = I18nManager.getLocalManager(localeStr, true);
        Enumeration keys = properties.propertyNames();
        do
        {
            if(!keys.hasMoreElements())
                break;
            String key = keys.nextElement().toString();
            String value = properties.getProperty(key);
            LocaleLiteral locale = localeLiteralManager.getLiteralRelation(key);
            if(locale == null || locale.getLevel() < level.intValue())
                localeLiteralManager.putLiteralRelation(key, value, level.intValue());
        } while(true);
        properties.clear();
        inStream.close();
        continue; /* Loop/switch isn't completed */
        IOException e;
        e;
        LogUtils.error(com/sinitek/sirm/common/support/i18n/I18nSupport, e);
        inStream.close();
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        inStream.close();
        throw exception;
        i$++;
          goto _L3
_L2:
        Locale defaultLocale = null;
        String localeStr = LocalSetting.getSetting("language.default");
        if(localeStr != null)
            defaultLocale = LocaleUtil.toLocale(localeStr);
        if(defaultLocale == null)
            defaultLocale = Locale.getDefault();
        I18nManager.setDefaultLocale(defaultLocale);
        break MISSING_BLOCK_LABEL_316;
        IOException e;
        e;
        e.printStackTrace();
        isInitialized = true;
        return;
    }

    public void destroy(IAppLoaderContext context)
    {
        isInitialized = false;
    }

    private static String configFilePattern = "classpath*:locale/**/*.properties";
    private boolean isInitialized;

}
