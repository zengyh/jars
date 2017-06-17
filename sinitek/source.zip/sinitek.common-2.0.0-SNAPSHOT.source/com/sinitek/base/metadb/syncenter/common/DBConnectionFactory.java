// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DBConnectionFactory.java

package com.sinitek.base.metadb.syncenter.common;

import com.sinitek.base.datasource.DataSourceFactory;
import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;

// Referenced classes of package com.sinitek.base.metadb.syncenter.common:
//            SynCenterCommonException, DBConnectType

public class DBConnectionFactory
{

    public DBConnectionFactory()
    {
        isInit = false;
    }

    public Connection getConnection()
        throws SQLException
    {
        if(dbConnectType == DBConnectType.EXT_DATASOURCE)
        {
            DataSource ds = DataSourceFactory.getInstance().getDataSouce(extDataSourceName);
            return ds.getConnection();
        }
        if(dbConnectType == DBConnectType.DIRECT_CONN)
        {
            if(!isInit)
                try
                {
                    Class.forName(driverClassName);
                    isInit = true;
                }
                catch(ClassNotFoundException e)
                {
                    throw new SynCenterCommonException("0001", e, new Object[] {
                        driverClassName
                    });
                }
            Connection connection = DriverManager.getConnection(url, userName, password);
            connection.setAutoCommit(false);
            return connection;
        }
        if(dbConnectType == DBConnectType.JNDI)
            try
            {
                Context ctx = new InitialContext();
                Object remoteObj = ctx.lookup(jndi);
                DataSource dataSource = (DataSource)remoteObj;
                return dataSource.getConnection();
            }
            catch(NamingException e)
            {
                throw new SynCenterCommonException("0003", e, new Object[] {
                    jndi
                });
            }
        if(dbConnectType == DBConnectType.LOCAL)
            return localDataSource.getConnection();
        else
            throw new SynCenterCommonException("0002", new Object[] {
                dbConnectType
            });
    }

    public void testConnection()
        throws SQLException
    {
        Connection conn = null;
        conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select 1+1 from dual");
        rs.next();
        if(conn != null)
            conn.close();
        break MISSING_BLOCK_LABEL_58;
        Exception exception;
        exception;
        if(conn != null)
            conn.close();
        throw exception;
    }

    public DBConnectType getDbConnectType()
    {
        return dbConnectType;
    }

    public void setDbConnectType(DBConnectType dbConnectType)
    {
        this.dbConnectType = dbConnectType;
    }

    public String getExtDataSourceName()
    {
        return extDataSourceName;
    }

    public void setExtDataSourceName(String extDataSourceName)
    {
        this.extDataSourceName = extDataSourceName;
    }

    public String getDriverClassName()
    {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName)
    {
        this.driverClassName = driverClassName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getJndi()
    {
        return jndi;
    }

    public void setJndi(String jndi)
    {
        this.jndi = jndi;
    }

    public DataSource getLocalDataSource()
    {
        return localDataSource;
    }

    public void setLocalDataSource(DataSource localDataSource)
    {
        this.localDataSource = localDataSource;
    }

    private DBConnectType dbConnectType;
    private String extDataSourceName;
    private String driverClassName;
    private String userName;
    private String password;
    private String url;
    private boolean isInit;
    private String jndi;
    private DataSource localDataSource;
}
