// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrongJNDIDataSource.java

package com.sinitek.base.datasource.impl;

import com.sinitek.base.datasource.config.IRemoteDataSourceConfig;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.datasource.impl:
//            StrongRemoteDataSource

public class StrongJNDIDataSource extends StrongRemoteDataSource
{

    public StrongJNDIDataSource(IRemoteDataSourceConfig remoteDataSourceConfig)
    {
        this.remoteDataSourceConfig = remoteDataSourceConfig;
        try
        {
            init(remoteDataSourceConfig.isNeedBind());
        }
        catch(Exception e)
        {
            startReconnect();
        }
    }

    public void createOriDataSource()
    {
        try
        {
            Map jndiPropMap = remoteDataSourceConfig.getJndiProp();
            Hashtable env = null;
            if(null != jndiPropMap && !jndiPropMap.isEmpty())
            {
                env = new Hashtable();
                java.util.Map.Entry propEntry;
                for(Iterator jndiPropIterator = jndiPropMap.entrySet().iterator(); jndiPropIterator.hasNext(); env.put(propEntry.getKey(), propEntry.getValue()))
                    propEntry = (java.util.Map.Entry)jndiPropIterator.next();

            }
            Context context = new InitialContext(env);
            ds = (DataSource)context.lookup(remoteDataSourceConfig.getJndi());
        }
        catch(NamingException e)
        {
            LOGGER.error((new StringBuilder()).append("\u4F01\u56FE\u521B\u5EFAjndi\u540D\u4E3A[").append(remoteDataSourceConfig.getJndi()).append("]\u7684\u6570\u636E\u6E90[").append(getDataSourceName()).append("]\u65F6\u51FA\u9519\uFF01\uFF1A").append(e.getMessage()).toString(), e);
            startReconnect();
        }
        catch(ClassCastException e)
        {
            LOGGER.error((new StringBuilder()).append("\u4F01\u56FE\u521B\u5EFAjndi\u540D\u4E3A[").append(remoteDataSourceConfig.getJndi()).append("]\u7684\u6570\u636E\u6E90[").append(getDataSourceName()).append("]\u65F6\u51FA\u9519\uFF01\uFF1A").append(e.getMessage()).toString(), e);
            startReconnect();
        }
    }

    public Connection getConnection()
        throws SQLException
    {
        if(StringUtils.isNotBlank(remoteDataSourceConfig.getUserName()) && StringUtils.isNotBlank(remoteDataSourceConfig.getPassword()))
            return super.getConnection(remoteDataSourceConfig.getUserName(), remoteDataSourceConfig.getPassword());
        else
            return super.getConnection();
    }

    public DataSource getAndResetDataSource()
        throws NamingException, ClassCastException
    {
        bindDataSource(getDataSourceName(), ds, false);
        return ds;
    }

    public String getDataSourceName()
    {
        return remoteDataSourceConfig.getDataSourceName();
    }

    public DataSource getOriDataSource()
    {
        return ds;
    }

    public IRemoteDataSourceConfig getRemoteDataSourceConfig()
    {
        return remoteDataSourceConfig;
    }

    public boolean getNeedBind()
    {
        return remoteDataSourceConfig.isNeedBind();
    }

    public java.util.logging.Logger getParentLogger()
        throws SQLFeatureNotSupportedException
    {
        return null;
    }

    private DataSource ds;
    private IRemoteDataSourceConfig remoteDataSourceConfig;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/datasource/impl/StrongJNDIDataSource);

}
