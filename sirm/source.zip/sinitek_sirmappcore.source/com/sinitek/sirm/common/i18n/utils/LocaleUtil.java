// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocaleUtil.java

package com.sinitek.sirm.common.i18n.utils;

import java.util.Locale;

public class LocaleUtil
{

    public LocaleUtil()
    {
    }

    public static Locale toLocale(String str)
    {
        if(str == null)
            return DEFAULT_LOCALE;
        int len = str.length();
        if(len != 2 && len != 5 && len < 7)
            return DEFAULT_LOCALE;
        if(len == 2)
            return new Locale(str.toLowerCase(), "");
        if(str.charAt(2) != '_')
            return DEFAULT_LOCALE;
        if(len == 5)
            return new Locale(str.substring(0, 2).toLowerCase(), str.substring(3, 5).toUpperCase());
        int s = str.indexOf("_");
        int e = str.lastIndexOf("_");
        if(s > 0 && e > 0 && e > s)
        {
            String language = str.substring(0, s);
            String country = str.substring(s + 1, e);
            String variant = str.substring(e + 1, len);
            language = language.toLowerCase();
            country = country.toUpperCase();
            variant = variant.toUpperCase();
            return new Locale(language, country, variant);
        } else
        {
            return DEFAULT_LOCALE;
        }
    }

    public static final Locale DEFAULT_LOCALE = new Locale("zh", "CN");

}
