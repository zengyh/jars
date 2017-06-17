// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckoutConnectionThread.java

package com.sinitek.base.datasource.impl;

import com.sinitek.base.datasource.*;
import com.sinitek.base.datasource.config.IDataSourceStrategyConfig;
import com.sinitek.base.datasource.config.IRemoteDataSourceConfig;
import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.datasource.impl:
//            StrongRemoteDataSource, DataSourceProviderImpl

public class CheckoutConnectionThread extends Thread
{

    public CheckoutConnectionThread(DataSource originalDataSource, IStrongDataSource strongDataSource)
    {
        this.originalDataSource = originalDataSource;
        this.strongDataSource = strongDataSource;
        LOGGER.info((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(strongDataSource.getDataSourceName()).append("]\u5B9A\u65F6\u68C0\u67E5\u8FDE\u63A5\u673A\u5236\u5F00\u59CB\u542F\u52A8\uFF01").toString());
        setDaemon(true);
        start();
    }

    public void run()
    {
        IDataSourceStrategyConfig strategyConfig = DataSourceProviderImpl.getInstance().getDataSourceStrategy();
        String checkoutConnectionSql = strategyConfig.getCheckoutConnectionSql();
        long checkoutConnectionInterval = strategyConfig.getCheckoutConnectionInterval();
        for(boolean isrun = true; isrun;)
        {
            if(!checkoutConnection(checkoutConnectionSql))
            {
                strongDataSource.startReconnect();
                return;
            }
            try
            {
                LOGGER.debug((new StringBuilder()).append("\u7B49\u5F85[").append(checkoutConnectionInterval).append("]\u6BEB\u79D2\u540E\u5C06\u518D\u6B21\u68C0\u67E5\u6570\u636E\u6E90\uFF1A[").append(strongDataSource.getDataSourceName()).append("]").toString());
                Thread.sleep(checkoutConnectionInterval);
            }
            catch(InterruptedException e)
            {
                LOGGER.error((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(strongDataSource.getDataSourceName()).append("]\u7B49\u5F85\u8FDE\u63A5\u91CD\u8BD5\u65F6\u53D1\u751F\u9519\u8BEF\uFF1A").append(e.getMessage()).append("\u3002\u68C0\u67E5\u7EBF\u7A0B\u9000\u51FA").toString(), e);
                isrun = false;
            }
        }

    }

    private boolean checkoutConnection(String checkoutConnectionSql)
    {
        Connection conn;
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(strongDataSource.getDataSourceName()).append("]\u5F00\u59CB\u68C0\u67E5\u8FDE\u63A5\u662F\u5426\u6B63\u5E38\uFF01").toString());
        conn = null;
        Statement statement;
        boolean isNeedNamePwd = false;
        if(strongDataSource instanceof StrongRemoteDataSource)
        {
            StrongRemoteDataSource remoteDataSource = (StrongRemoteDataSource)strongDataSource;
            if(remoteDataSource.getRemoteDataSourceConfig() != null)
            {
                String userName = remoteDataSource.getRemoteDataSourceConfig().getUserName();
                String passWord = remoteDataSource.getRemoteDataSourceConfig().getPassword();
                if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(passWord))
                {
                    isNeedNamePwd = true;
                    conn = originalDataSource.getConnection(userName, passWord);
                }
            }
        }
        if(!isNeedNamePwd)
            conn = originalDataSource.getConnection();
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(strongDataSource.getDataSourceName()).append("]\u83B7\u53D6Connection\u6210\u529F\uFF01").toString());
        if(StringUtils.isBlank(checkoutConnectionSql))
        {
            LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(strongDataSource.getDataSourceName()).append("]\u8FDE\u63A5\u7B56\u7565\u4E2DcheckoutConnectionSql\u4E3A\u7A7A\uFF1A[").append(checkoutConnectionSql).append("]\uFF0C\u5C06\u4E0D\u6267\u884Csql\u8BED\u53E5\u9A8C\u8BC1\uFF01").toString());
            break MISSING_BLOCK_LABEL_657;
        }
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(strongDataSource.getDataSourceName()).append("]\u8FDE\u63A5\u7B56\u7565\u4E2DcheckoutConnectionSql\u4E3A\uFF1A[").append(checkoutConnectionSql).append("]\uFF0C\u5C06\u6267\u884Csql\u8BED\u53E5\u9A8C\u8BC1\uFF01").toString());
        statement = null;
        statement = conn.createStatement();
        statement.execute(checkoutConnectionSql);
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(strongDataSource.getDataSourceName()).append("]\u6267\u884C\u9A8C\u8BC1Sql\uFF1A[").append(checkoutConnectionSql).append("]\u6210\u529F\uFF01").toString());
        Exception e;
        try
        {
            if(statement != null)
                statement.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(strongDataSource.getDataSourceName()).append("]\u7684Statement\u65F6\u5931\u8D25").toString(), e);
        }
        break MISSING_BLOCK_LABEL_657;
        e;
        boolean flag1;
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(strongDataSource.getDataSourceName()).append("]\u5C1D\u8BD5\u6267\u884Csql\uFF1A[").append(checkoutConnectionSql).append("]\u65F6\u5931\u8D25\uFF01").append(e.getMessage()).toString(), e);
        flag1 = false;
        try
        {
            if(statement != null)
                statement.close();
        }
        catch(SQLException e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(strongDataSource.getDataSourceName()).append("]\u7684Statement\u65F6\u5931\u8D25").toString(), e);
        }
        try
        {
            if(null != conn && !conn.isClosed())
                conn.close();
        }
        catch(SQLException e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(strongDataSource.getDataSourceName()).append("]\u8FDE\u63A5\u65F6\u5931\u8D25").toString(), e);
        }
        return flag1;
        Exception exception;
        exception;
        try
        {
            if(statement != null)
                statement.close();
        }
        catch(SQLException e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(strongDataSource.getDataSourceName()).append("]\u7684Statement\u65F6\u5931\u8D25").toString(), e);
        }
        throw exception;
        boolean flag;
        LOGGER.debug((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(strongDataSource.getDataSourceName()).append("]\u68C0\u67E5\u8FDE\u63A5\u6210\u529F\uFF0C\u6570\u636E\u5E93\u8FDE\u63A5\u6B63\u5E38\uFF01").toString());
        flag = true;
        try
        {
            if(null != conn && !conn.isClosed())
                conn.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(strongDataSource.getDataSourceName()).append("]\u8FDE\u63A5\u65F6\u5931\u8D25").toString(), e);
        }
        return flag;
        Exception e;
        e;
        if(strongDataSource.isReconnectionStarted())
            throw new DataSourceException("0006", e, new Object[] {
                strongDataSource.getDataSourceName()
            });
        LOGGER.warn((new StringBuilder()).append("\u6570\u636E\u6E90\uFF1A[").append(strongDataSource.getDataSourceName()).append("]\u83B7\u53D6\u8FDE\u63A5\u5931\u8D25\uFF1A").append(e.getMessage()).toString(), e);
        flag = false;
        try
        {
            if(null != conn && !conn.isClosed())
                conn.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(strongDataSource.getDataSourceName()).append("]\u8FDE\u63A5\u65F6\u5931\u8D25").toString(), e);
        }
        return flag;
        Exception exception1;
        exception1;
        try
        {
            if(null != conn && !conn.isClosed())
                conn.close();
        }
        catch(SQLException e)
        {
            LOGGER.error((new StringBuilder()).append("\u5173\u95ED\u6570\u636E\u6E90[").append(strongDataSource.getDataSourceName()).append("]\u8FDE\u63A5\u65F6\u5931\u8D25").toString(), e);
        }
        throw exception1;
    }

    private DataSource originalDataSource;
    private IStrongDataSource strongDataSource;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/datasource/impl/CheckoutConnectionThread);

}
