// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocalSetting.java

package com.sinitek.sirm.common.support;

import com.sinitek.sirm.common.loader.utils.AppLoader;
import com.sinitek.sirm.common.loader.utils.IAppLoaderContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class LocalSetting
    implements AppLoader
{

    public LocalSetting()
    {
        _isInitialized = false;
    }

    public boolean isInitialized()
    {
        return _isInitialized;
    }

    public void initialize(IAppLoaderContext context)
    {
        InputStream fin = context.getResourceAsStream("/WEB-INF/application.properties");
        if(fin != null)
            try
            {
                settings.load(fin);
            }
            catch(IOException ex)
            {
                LOGGER.warn("load LocalSetting failed!", ex);
            }
    }

    public static void load(Properties properties)
    {
        settings.putAll(properties);
    }

    public void destroy(IAppLoaderContext context)
    {
        settings.clear();
        _isInitialized = false;
    }

    public static String getSetting(String name)
    {
        return settings.getProperty(name, "");
    }

    public static String getSetting(String name, String dv)
    {
        return settings.getProperty(name, dv);
    }

    public static boolean getSettingAsBoolean(String name, boolean dv)
    {
        boolean result = dv;
        try
        {
            result = Boolean.valueOf(settings.getProperty(name, String.valueOf(dv))).booleanValue();
        }
        catch(Exception ex) { }
        return result;
    }

    public static int getSettingAsInteger(String name, int dv)
    {
        int result = dv;
        try
        {
            result = Integer.valueOf(settings.getProperty(name, String.valueOf(dv))).intValue();
        }
        catch(Exception ex) { }
        return result;
    }

    public static boolean getSSOEnabled()
    {
        return getSettingAsBoolean("sso.enable", false);
    }

    public static boolean getSSOMainhost()
    {
        return getSettingAsBoolean("sso.mainhost", false);
    }

    public static int getUserchecktimeout()
    {
        return getSettingAsInteger("userchecktimeout", 30);
    }

    public static boolean getCSRFEnabled()
    {
        return getSettingAsBoolean("security.csrf.enable", false);
    }

    public static String getDeployHome()
    {
        return getSetting("app.deployhome", null);
    }

    public static String getLogHome()
    {
        return getSetting("app.loghome", null);
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/support/LocalSetting);
    private static Properties settings = new Properties();
    private boolean _isInitialized;

}
