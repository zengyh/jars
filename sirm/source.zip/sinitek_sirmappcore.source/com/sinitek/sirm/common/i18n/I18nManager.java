// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   I18nManager.java

package com.sinitek.sirm.common.i18n;

import com.sinitek.sirm.common.i18n.utils.LocaleUtil;
import com.sinitek.sirm.common.support.i18n.I18nSupport;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Set;
import javolution.util.FastMap;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.sirm.common.i18n:
//            LocaleLiteralManager

public class I18nManager
{

    public I18nManager()
    {
    }

    public static Locale getDefaultLocale()
    {
        return defaultLocale;
    }

    public static Locale[] getSupportLocale()
    {
        Set localeSet = literalMap.keySet();
        Locale locales[] = new Locale[localeSet.size()];
        localeSet.toArray(locales);
        return locales;
    }

    public static void setDefaultLocale(Locale defaultLocale)
    {
        defaultLocale = defaultLocale;
    }

    public static LocaleLiteralManager getLocalManager(String locale)
    {
        return getLocalManager(LocaleUtil.toLocale(locale), false);
    }

    public static LocaleLiteralManager getLocalManager(Locale locale, boolean isCreate)
    {
        LocaleLiteralManager localeLiteralManager = (LocaleLiteralManager)literalMap.get(locale);
        if(!isCreate && literalMap.size() == 0)
            (new I18nSupport()).initialize(null);
        if(isCreate && localeLiteralManager == null)
        {
            localeLiteralManager = new LocaleLiteralManager();
            literalMap.put(locale, localeLiteralManager);
        }
        return localeLiteralManager;
    }

    public static LocaleLiteralManager getLocalManager(String locale, boolean isCreate)
    {
        return getLocalManager(LocaleUtil.toLocale(locale), isCreate);
    }

    public static void putLiteralRelation(String locale, String key, String value)
    {
        LocaleLiteralManager localeLiteralManager = getLocalManager(LocaleUtil.toLocale(locale), true);
        localeLiteralManager.putLiteralRelation(key, value);
    }

    public static void putAllLiteralRelation(String locale, FastMap map)
    {
        getLocalManager(LocaleUtil.toLocale(locale), true).putAllLiteralRelation(map);
    }

    public static String getLiteralRelationValue(String locale, String key)
    {
        LocaleLiteralManager localeLiteralManager = getLocalManager(locale);
        return localeLiteralManager != null ? localeLiteralManager.getLiteralRelationValue(key) : null;
    }

    public static String getLiteralRelationValue(String locale, String key, Object objects[])
    {
        String value = getLiteralRelationValue(locale, key);
        if(StringUtils.isNotBlank(value))
            return MessageFormat.format(value, objects);
        else
            return null;
    }

    public static String getLiteralRelationValue(String locale, String key, String defaultValue)
    {
        String value = getLiteralRelationValue(locale, key);
        return value == null ? defaultValue : value;
    }

    protected static FastMap literalMap = new FastMap();
    protected static Locale defaultLocale;

}
