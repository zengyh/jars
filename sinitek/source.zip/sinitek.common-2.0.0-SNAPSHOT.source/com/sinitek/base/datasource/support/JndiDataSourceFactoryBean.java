// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JndiDataSourceFactoryBean.java

package com.sinitek.base.datasource.support;

import com.sinitek.base.datasource.config.impl.RemoteDataSourceConfig;
import java.util.*;

// Referenced classes of package com.sinitek.base.datasource.support:
//            AbstractDataSourceFactoryBean

public class JndiDataSourceFactoryBean extends AbstractDataSourceFactoryBean
{

    public JndiDataSourceFactoryBean()
    {
    }

    protected Map getLocalDataSourceConfig()
    {
        return Collections.EMPTY_MAP;
    }

    protected Map getRemoteDataSourceConfig()
    {
        Map ret = new HashMap();
        RemoteDataSourceConfig config = new RemoteDataSourceConfig();
        config.setDataSourceName(name);
        config.setJndi(jndi);
        config.setJndiProp(jndiProp);
        config.setNeedBind(false);
        ret.put(name, config);
        return ret;
    }

    public String getJndi()
    {
        return jndi;
    }

    public void setJndi(String jndi)
    {
        this.jndi = jndi;
    }

    public Properties getJndiProp()
    {
        return jndiProp;
    }

    public void setJndiProp(Properties jndiProp)
    {
        this.jndiProp = jndiProp;
    }

    public String jndi;
    public Properties jndiProp;
}
