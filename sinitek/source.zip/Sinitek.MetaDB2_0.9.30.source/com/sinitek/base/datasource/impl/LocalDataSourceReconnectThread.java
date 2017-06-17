// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocalDataSourceReconnectThread.java

package com.sinitek.base.datasource.impl;

import com.sinitek.base.datasource.IDataSourceProvider;
import com.sinitek.base.datasource.ILocalStrongDataSource;
import com.sinitek.base.datasource.config.IDataSourceStrategyConfig;
import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.datasource.impl:
//            DataSourceProviderImpl

public class LocalDataSourceReconnectThread extends Thread
{

    public LocalDataSourceReconnectThread(DataSource originalDataSource, ILocalStrongDataSource localStrongDataSource)
    {
        totalConnTimes = 0;
        this.originalDataSource = originalDataSource;
        this.localStrongDataSource = localStrongDataSource;
        LOGGER.info((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u91CD\u8FDE\u673A\u5236\u5F00\u59CB\u542F\u52A8\uFF01").toString());
        setDaemon(true);
        start();
    }

    public void run()
    {
        IDataSourceStrategyConfig strategyConfig = DataSourceProviderImpl.getInstance().getDataSourceStrategy();
        String checkoutConnectionSql = strategyConfig.getCheckoutConnectionSql();
        long firstReconnectionInterval = strategyConfig.getFirstReconnectionInterval();
        int firstReconnectionTimes = strategyConfig.getFirstReconnectionTimes();
        long longTimeReconnectionInterval = strategyConfig.getLongTimeReconnectionInterval();
        int longTimeReconnectionTimes = strategyConfig.getLongTimeReconnectionTimes();
        if(firstReconnectionTimes > 0)
        {
            LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u5F00\u59CB\u7B2C\u4E00\u8F6E\u81EA\u52A8\u91CD\u8FDE\uFF0C\u6839\u636E\u914D\u7F6E\u7684\u7B56\u7565\uFF0C").append("\u5C06\u91CD\u8FDE[").append(firstReconnectionTimes).append("]\u6B21\uFF01").toString());
            for(int i = 0; i < firstReconnectionTimes; i++)
                if(reconnect(firstReconnectionInterval, checkoutConnectionSql))
                {
                    reset();
                    return;
                }

        } else
        {
            LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u5F00\u59CB\u7B2C\u4E00\u8F6E\u81EA\u52A8\u91CD\u8FDE\uFF0C\u6839\u636E\u914D\u7F6E\u7684\u7B56\u7565\uFF0C").append("\u91CD\u8FDE\u6B21\u6570[").append(firstReconnectionTimes).append("]\u5C0F\u4E8E\u96F6\uFF0C\u5C06\u65E0\u9650\u91CD\u8FDE\uFF01").toString());
            while(!reconnect(firstReconnectionInterval, checkoutConnectionSql)) ;
            reset();
            return;
        }
        LOGGER.info((new StringBuilder()).append("\u7B2C\u4E00\u8F6E\u91CD\u8FDE\u7ED3\u675F\uFF0C\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u8FDE\u63A5\u5931\u8D25\uFF0C\u5C06\u5F00\u59CB\u7B2C\u4E8C\u8F6E\u91CD\u8FDE\uFF01").toString());
        if(longTimeReconnectionTimes > 0)
        {
            LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u5F00\u59CB\u7B2C\u4E8C\u8F6E\u81EA\u52A8\u91CD\u8FDE\uFF0C\u6839\u636E\u914D\u7F6E\u7684\u7B56\u7565\uFF0C").append("\u5C06\u91CD\u8FDE[").append(longTimeReconnectionTimes).append("]\u6B21\uFF01").toString());
            for(int i = 0; i < longTimeReconnectionTimes; i++)
                if(reconnect(longTimeReconnectionInterval, checkoutConnectionSql))
                {
                    reset();
                    return;
                }

        } else
        {
            LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u5F00\u59CB\u7B2C\u4E8C\u8F6E\u81EA\u52A8\u91CD\u8FDE\uFF0C\u6839\u636E\u914D\u7F6E\u7684\u7B56\u7565\uFF0C").append("\u91CD\u8FDE\u6B21\u6570[").append(longTimeReconnectionTimes).append("]\u5C0F\u4E8E\u96F6\uFF0C\u5C06\u65E0\u9650\u91CD\u8FDE\uFF01").toString());
            while(!reconnect(longTimeReconnectionInterval, checkoutConnectionSql)) ;
            reset();
            return;
        }
        LOGGER.info((new StringBuilder()).append("\u7B2C\u4E8C\u8F6E\u91CD\u8FDE\u7ED3\u675F\uFF0C\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u8FDE\u63A5\u5931\u8D25\uFF01").toString());
    }

    private boolean reconnect(long interval, String checkoutConnectionSql)
    {
        Connection conn;
        totalConnTimes++;
        conn = null;
        Statement statement;
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u6B63\u5728\u5C1D\u8BD5\u7B2C[").append(totalConnTimes).append("]\u6B21\u91CD\u8FDE\uFF01").toString());
        conn = originalDataSource.getConnection();
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u83B7\u53D6Connection\u6210\u529F\uFF01").toString());
        if(StringUtils.isBlank(checkoutConnectionSql))
        {
            LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u8FDE\u63A5\u7B56\u7565\u4E2DcheckoutConnectionSql\u4E3A\u7A7A\uFF1A[").append(checkoutConnectionSql).append("]\uFF0C\u5C06\u4E0D\u6267\u884Csql\u8BED\u53E5\u9A8C\u8BC1\uFF01").toString());
            break MISSING_BLOCK_LABEL_595;
        }
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u8FDE\u63A5\u7B56\u7565\u4E2DcheckoutConnectionSql\u4E3A\uFF1A[").append(checkoutConnectionSql).append("]\uFF0C\u5C06\u6267\u884Csql\u8BED\u53E5\u9A8C\u8BC1\uFF01").toString());
        statement = null;
        statement = conn.createStatement();
        statement.execute(checkoutConnectionSql);
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u6267\u884C\u9A8C\u8BC1Sql\uFF1A[").append(checkoutConnectionSql).append("]\u6210\u529F\uFF01").toString());
        SQLException e;
        try
        {
            if(statement != null)
                statement.close();
        }
        // Misplaced declaration of an exception variable
        catch(SQLException e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(localStrongDataSource.getDataSourceName()).append("]\u7684Statement\u65F6\u5931\u8D25").toString(), e);
        }
        break MISSING_BLOCK_LABEL_595;
        e;
        boolean flag2;
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u5C1D\u8BD5\u6267\u884Csql\uFF1A[").append(checkoutConnectionSql).append("]\u65F6\u5931\u8D25\uFF01").append(e.getMessage()).toString(), e);
        flag2 = false;
        try
        {
            if(statement != null)
                statement.close();
        }
        catch(SQLException e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(localStrongDataSource.getDataSourceName()).append("]\u7684Statement\u65F6\u5931\u8D25").toString(), e);
        }
        try
        {
            if(conn != null && !conn.isClosed())
                conn.close();
        }
        catch(SQLException e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(localStrongDataSource.getDataSourceName()).append("]\u7684Connection\u65F6\u5931\u8D25").toString(), e);
        }
        return flag2;
        Exception exception;
        exception;
        try
        {
            if(statement != null)
                statement.close();
        }
        catch(SQLException e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(localStrongDataSource.getDataSourceName()).append("]\u7684Statement\u65F6\u5931\u8D25").toString(), e);
        }
        throw exception;
        boolean flag;
        LOGGER.info((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u91CD\u65B0\u8FDE\u63A5\u6210\u529F\uFF01").toString());
        flag = true;
        try
        {
            if(conn != null && !conn.isClosed())
                conn.close();
        }
        // Misplaced declaration of an exception variable
        catch(SQLException e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(localStrongDataSource.getDataSourceName()).append("]\u7684Connection\u65F6\u5931\u8D25").toString(), e);
        }
        return flag;
        Exception e;
        e;
        boolean flag1;
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u5C1D\u8BD5\u8FDE\u63A5\u5931\u8D25\uFF0C\u7B49\u5F85[").append(interval).append("]\u6BEB\u79D2\u540E\u5C06\u518D\u6B21\u5C1D\u8BD5\u91CD\u8FDE").toString(), e);
        try
        {
            Thread.sleep(interval);
        }
        catch(InterruptedException ie)
        {
            LOGGER.error((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(localStrongDataSource.getDataSourceName()).append("]\u7B49\u5F85\u8FDE\u63A5\u91CD\u8BD5\u65F6\u53D1\u751F\u9519\u8BEF\uFF1A").append(ie.getMessage()).toString(), ie);
        }
        flag1 = false;
        try
        {
            if(conn != null && !conn.isClosed())
                conn.close();
        }
        catch(SQLException e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(localStrongDataSource.getDataSourceName()).append("]\u7684Connection\u65F6\u5931\u8D25").toString(), e);
        }
        return flag1;
        Exception exception1;
        exception1;
        try
        {
            if(conn != null && !conn.isClosed())
                conn.close();
        }
        catch(SQLException e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(localStrongDataSource.getDataSourceName()).append("]\u7684Connection\u65F6\u5931\u8D25").toString(), e);
        }
        throw exception1;
    }

    private void reset()
    {
        localStrongDataSource.startCheckoutConnection();
    }

    private DataSource originalDataSource;
    private ILocalStrongDataSource localStrongDataSource;
    private int totalConnTimes;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/datasource/impl/LocalDataSourceReconnectThread);

}
