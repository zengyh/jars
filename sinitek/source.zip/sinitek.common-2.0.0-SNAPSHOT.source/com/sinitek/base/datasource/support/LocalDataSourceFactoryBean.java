// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocalDataSourceFactoryBean.java

package com.sinitek.base.datasource.support;

import com.sinitek.base.datasource.config.impl.LocalDataSourceConfig;
import java.util.*;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.base.datasource.support:
//            AbstractDataSourceFactoryBean

public class LocalDataSourceFactoryBean extends AbstractDataSourceFactoryBean
{

    public LocalDataSourceFactoryBean()
    {
    }

    protected Map getLocalDataSourceConfig()
    {
        Map ret = new HashMap();
        LocalDataSourceConfig config = new LocalDataSourceConfig();
        config.setDataSourceName(name);
        config.setDriverClassName(driverClassName);
        config.setNeedBind(false);
        config.setPassword(password);
        String temp = StringUtils.isEmpty(provider) ? "c3p0" : provider;
        config.setProvider(temp);
        config.setUrl(url);
        config.setUserName(userName);
        ret.put(name, config);
        return ret;
    }

    protected Map getRemoteDataSourceConfig()
    {
        return Collections.EMPTY_MAP;
    }

    public String getDriverClassName()
    {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName)
    {
        this.driverClassName = driverClassName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getProvider()
    {
        return provider;
    }

    public void setProvider(String provider)
    {
        this.provider = provider;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    private String provider;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
}
