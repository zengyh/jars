// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynClientStatusCheckThread.java

package com.sinitek.base.metadb.syncenter.client;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.DBConnectionFactory;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.client:
//            SynCenterClientException, SynClientConfig

public class SynClientStatusCheckThread
{
    static final class CheckThread extends TimerTask
    {

        public void run()
        {
            Connection conn;
            SynClientStatusCheckThread.LOGGER.debug("\u5F00\u59CB\u5411\u6570\u636E\u5E93\u5237\u65B0\u5B9E\u4F8B\u72B6\u6001");
            conn = null;
            conn = clientConfig.getConnectionFactory().getConnection();
            String updateSql = "update SYNC_METADBINSTINFO set lastupdatetime=systimestamp \nwhere appkey=?";
            PreparedStatement updateStatement = conn.prepareStatement(updateSql);
            updateStatement.setString(1, clientConfig.getAppKey());
            if(updateStatement.executeUpdate() == 0)
            {
                String insertSql = "insert into SYNC_METADBINSTINFO( appkey, appname, ipaddress, port, lastupdatetime)\nvalues ( ?, ?, ?, ?, systimestamp)";
                PreparedStatement insertStatement = conn.prepareStatement(insertSql);
                insertStatement.setString(1, clientConfig.getAppKey());
                insertStatement.setString(2, clientConfig.getAppName());
                insertStatement.setString(3, clientConfig.getLocalAddress());
                insertStatement.setInt(4, clientConfig.getListernPort());
                insertStatement.executeUpdate();
            }
            conn.commit();
            SynClientStatusCheckThread.LOGGER.debug("\u5411\u6570\u636E\u5E93\u5237\u65B0\u5B9E\u4F8B\u72B6\u6001\u6210\u529F");
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    SynClientStatusCheckThread.LOGGER.warn("\u5411\u6570\u636E\u5E93\u5199\u5165MetaDB\u5B9E\u4F8B\u72B6\u6001\u4FE1\u606F\u65F6\u5173\u95ED\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
                }
            break MISSING_BLOCK_LABEL_243;
            Exception ex;
            ex;
            SynClientStatusCheckThread.LOGGER.error("\u5411\u6570\u636E\u5E93\u5237\u65B0\u5B9E\u4F8B\u72B6\u6001\u5931\u8D25", ex);
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    SynClientStatusCheckThread.LOGGER.warn("\u5411\u6570\u636E\u5E93\u5199\u5165MetaDB\u5B9E\u4F8B\u72B6\u6001\u4FE1\u606F\u65F6\u5173\u95ED\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
                }
            break MISSING_BLOCK_LABEL_243;
            Exception exception;
            exception;
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    SynClientStatusCheckThread.LOGGER.warn("\u5411\u6570\u636E\u5E93\u5199\u5165MetaDB\u5B9E\u4F8B\u72B6\u6001\u4FE1\u606F\u65F6\u5173\u95ED\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
                }
            throw exception;
        }

        final SynClientConfig clientConfig;

        CheckThread(SynClientConfig clientConfig)
        {
            this.clientConfig = clientConfig;
        }
    }


    public SynClientStatusCheckThread()
    {
    }

    public static void startCheck(SynClientConfig clientConfig)
    {
        Connection conn;
        Exception exception;
        conn = null;
        try
        {
            conn = clientConfig.getConnectionFactory().getConnection();
            String sql = "select * from SYNC_METADBINSTINFO where appkey=? for update";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            String appKey = clientConfig.getAppKey();
            preparedStatement.setString(1, appKey);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
            {
                String updateSql = "update SYNC_METADBINSTINFO set lastupdatetime=systimestamp,appname=?,ipaddress=?,port=?\nwhere appkey=?";
                PreparedStatement updateStatement = conn.prepareStatement(updateSql);
                updateStatement.setString(1, clientConfig.getAppName());
                updateStatement.setString(2, clientConfig.getLocalAddress());
                updateStatement.setInt(3, clientConfig.getListernPort());
                updateStatement.setString(4, appKey);
                updateStatement.executeUpdate();
            } else
            {
                String updateSql = "insert into SYNC_METADBINSTINFO( appkey, appname, ipaddress, port, lastupdatetime)\nvalues ( ?, ?, ?, ?, systimestamp)";
                PreparedStatement insertStatement = conn.prepareStatement(updateSql);
                insertStatement.setString(1, appKey);
                insertStatement.setString(2, clientConfig.getAppName());
                insertStatement.setString(3, clientConfig.getLocalAddress());
                insertStatement.setInt(4, clientConfig.getListernPort());
                insertStatement.executeUpdate();
            }
            conn.commit();
            LOGGER.debug("\u5B8C\u6210\u9996\u6B21\u5411\u6570\u636E\u5E93\u5199\u5165\u5B9E\u4F8B\u72B6\u6001");
        }
        catch(Exception ex)
        {
            throw new SynCenterClientException("0012", ex);
        }
        finally
        {
            if(conn == null) goto _L0; else goto _L0
        }
        if(conn != null)
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                LOGGER.warn("\u9996\u6B21\u5411\u6570\u636E\u5E93\u5199\u5165MetaDB\u5B9E\u4F8B\u72B6\u6001\u4FE1\u606F\u65F6\u5173\u95ED\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
            }
        break MISSING_BLOCK_LABEL_275;
        try
        {
            conn.close();
        }
        catch(SQLException e)
        {
            LOGGER.warn("\u9996\u6B21\u5411\u6570\u636E\u5E93\u5199\u5165MetaDB\u5B9E\u4F8B\u72B6\u6001\u4FE1\u606F\u65F6\u5173\u95ED\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
        }
        throw exception;
        Timer checkTimer = new Timer("MetaDB\u5B9E\u4F8B\u72B6\u6001\u767B\u8BB0\u7EBF\u7A0B", true);
        checkTimer.schedule(new CheckThread(clientConfig), 60000L, 60000L);
        return;
    }

    private static Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }

}
