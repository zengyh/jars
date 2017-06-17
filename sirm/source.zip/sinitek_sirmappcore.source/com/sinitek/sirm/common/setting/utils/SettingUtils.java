// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SettingUtils.java

package com.sinitek.sirm.common.setting.utils;

import com.sinitek.base.metadb.MetaDBContextFactory;
import com.sinitek.base.metadb.cache.IMetaDBCacheContext;
import com.sinitek.sirm.common.setting.entity.ISetting;
import com.sinitek.sirm.common.support.LocalSetting;
import com.sinitek.sirm.common.utils.EncryptUtil;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.sirm.common.setting.utils:
//            SettingMetaDBPlugin

public class SettingUtils
{

    public SettingUtils()
    {
    }

    public static String getStringValue(String module, String name)
    {
        ISetting _setting = getSetting(module, name);
        return _setting != null ? _setting.getValue() : null;
    }

    public static String getStringValue(String module, String name, String dv)
    {
        String value = getStringValue(module, name);
        return StringUtils.isBlank(value) ? dv : value;
    }

    public static Integer getIntegerValue(String module, String name)
    {
        ISetting _setting = getSetting(module, name);
        return _setting != null && _setting.getValue() != null && !"".equals(_setting.getValue().trim()) && !"null".equals(_setting.getValue().trim()) ? Integer.valueOf(_setting.getValue()) : null;
    }

    public static Integer getIntegerValue(String module, String name, Integer dv)
    {
        Integer value = getIntegerValue(module, name);
        return value != null ? value : dv;
    }

    public static Double getDoubleValue(String module, String name)
    {
        ISetting _setting = getSetting(module, name);
        return _setting != null && _setting.getValue() != null && !"".equals(_setting.getValue().trim()) && !"null".equals(_setting.getValue().trim()) ? Double.valueOf(_setting.getValue()) : null;
    }

    public static Double getDoubleValue(String module, String name, Double dv)
    {
        Double value = getDoubleValue(module, name);
        return value != null ? value : dv;
    }

    public static Date getDateValue(String module, String name, String format)
    {
        Date r = null;
        ISetting _setting = getSetting(module, name);
        if(_setting != null)
            try
            {
                r = (new SimpleDateFormat(format)).parse(_setting.getValue());
            }
            catch(Exception ex) { }
        return r;
    }

    public static Date getDateValue(String module, String name, String format, Date dv)
    {
        Date r = getDateValue(module, name, format);
        return r != null ? r : dv;
    }

    public static Map findSettingsByModule(String module)
    {
        if(settingMetaDBPlugin == null)
            synchronized(com/sinitek/sirm/common/setting/utils/SettingUtils)
            {
                if(settingMetaDBPlugin == null)
                {
                    SettingMetaDBPlugin plugin = new SettingMetaDBPlugin();
                    MetaDBContextFactory.getInstance().getCacheContext().registCachePlugin(plugin);
                    settingMetaDBPlugin = plugin;
                }
            }
        Map mapping = (Map)settingMetaDBPlugin.getSettingMap().get(module);
        return mapping != null ? new HashMap(mapping) : new HashMap();
    }

    public static String getLocalSetting(String name)
    {
        return LocalSetting.getSetting(name);
    }

    public static String getLocalSetting(String name, String dv)
    {
        return LocalSetting.getSetting(name, dv);
    }

    public static ISetting getSetting(String module, String name)
    {
        ISetting _result = null;
        Map _map = findSettingsByModule(module);
        if(_map != null)
        {
            _result = (ISetting)_map.get(name);
            if(_result != null && _result.getEncryptionFlag() != null && _result.getEncryptionFlag().intValue() == 1)
                _result.setValue(EncryptUtil.decryptDES(_result.getValue(), "20121221"));
        }
        return _result;
    }

    private static SettingMetaDBPlugin settingMetaDBPlugin = null;

}
