// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrongDataSource.java

package com.sinitek.base.datasource.impl;

import com.sinitek.base.datasource.DataSourceException;
import com.sinitek.base.datasource.IStrongDataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.*;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.datasource.impl:
//            CheckoutConnectionThread

public abstract class StrongDataSource
    implements IStrongDataSource, DataSource
{

    public StrongDataSource()
    {
        checkoutConnectionStarted = false;
        reconnectionStarted = false;
    }

    public void bindDataSource(String jndiName, DataSource ds, boolean needCheckRepeat)
        throws DataSourceException, NamingException
    {
        Context context = new InitialContext();
        try
        {
            context.bind(jndiName, ds);
            LOGGER.info((new StringBuilder()).append("bind\u6570\u636E\u6E90:[").append(getDataSourceName()).append("]\u5230jndiName:[").append(jndiName).append("]\u6210\u529F\uFF01").toString());
        }
        catch(NamingException e)
        {
            if(needCheckRepeat)
                throw new DataSourceException("0012", e, new Object[] {
                    getDataSourceName()
                });
            context.rebind(jndiName, ds);
            LOGGER.info((new StringBuilder()).append("rebind\u6570\u636E\u6E90:[").append(getDataSourceName()).append("]\u5230jndiName:[").append(jndiName).append("]\u6210\u529F\uFF01").toString());
        }
    }

    public synchronized void startCheckoutConnection()
    {
        if(!checkoutConnectionStarted)
        {
            new CheckoutConnectionThread(getOriDataSource(), this);
            checkoutConnectionStarted = true;
            reconnectionStarted = false;
        }
    }

    public synchronized void reconnectionOver()
    {
        reconnectionStarted = false;
    }

    public boolean isReconnectionStarted()
    {
        return reconnectionStarted;
    }

    public Connection getConnection(String username, String password)
        throws SQLException
    {
        return getOriDataSource().getConnection(username, password);
    }

    public int getLoginTimeout()
        throws SQLException
    {
        return getOriDataSource().getLoginTimeout();
    }

    public void setLoginTimeout(int seconds)
        throws SQLException
    {
        getOriDataSource().setLoginTimeout(seconds);
    }

    public PrintWriter getLogWriter()
        throws SQLException
    {
        return getOriDataSource().getLogWriter();
    }

    public void setLogWriter(PrintWriter out)
        throws SQLException
    {
        getOriDataSource().setLogWriter(out);
    }

    public Connection getConnection()
        throws SQLException
    {
        return getOriDataSource().getConnection();
        SQLException e;
        e;
        StrongDataSource strongdatasource = this;
        JVM INSTR monitorenter ;
        LOGGER.warn((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(getDataSourceName()).append("]\u83B7\u53D6\u8FDE\u63A5\u5931\u8D25\uFF1A").append(e.getMessage()).toString(), e);
        startReconnect();
        throw new DataSourceException("0006", e, new Object[] {
            getDataSourceName()
        });
    }

    public Object unwrap(Class iface)
        throws SQLException
    {
        return null;
    }

    public boolean isWrapperFor(Class iface)
        throws SQLException
    {
        return false;
    }

    protected boolean checkoutConnectionStarted;
    protected boolean reconnectionStarted;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/datasource/impl/StrongDataSource);

}
