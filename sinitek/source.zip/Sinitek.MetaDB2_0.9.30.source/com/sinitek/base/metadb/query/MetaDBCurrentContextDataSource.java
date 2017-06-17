// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetaDBCurrentContextDataSource.java

package com.sinitek.base.metadb.query;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.MetaDBContextFactory;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.sql.*;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.hibernate.Session;

public class MetaDBCurrentContextDataSource
    implements DataSource
{
    private static class ConnectionInvocationHandler
        implements InvocationHandler
    {

        public Object invoke(Object proxy, Method method, Object args[])
            throws Throwable
        {
            if(!"close".equals(method.getName()))
            {
                return method.invoke(currentContext.getSession().connection(), args);
            } else
            {
                currentContext.close();
                return null;
            }
        }

        private IMetaDBContext currentContext;

        private ConnectionInvocationHandler(IMetaDBContext currentContext)
        {
            this.currentContext = currentContext;
        }

    }


    public MetaDBCurrentContextDataSource()
    {
        out = new PrintWriter(System.out);
    }

    public int getLoginTimeout()
        throws SQLException
    {
        return 0;
    }

    public Logger getParentLogger()
        throws SQLFeatureNotSupportedException
    {
        return null;
    }

    public void setLoginTimeout(int i)
        throws SQLException
    {
    }

    public PrintWriter getLogWriter()
        throws SQLException
    {
        return out;
    }

    public void setLogWriter(PrintWriter out)
        throws SQLException
    {
        this.out = out;
    }

    public Connection getConnection()
        throws SQLException
    {
        IMetaDBContext currentContext = MetaDBContextFactory.getInstance().createContext(MetaDBContextFactory.CURRENT_ONLY);
        return (Connection)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {
            java/sql/Connection
        }, new ConnectionInvocationHandler(currentContext));
    }

    public Connection getConnection(String username, String password)
        throws SQLException
    {
        return getConnection();
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

    private PrintWriter out;
}
