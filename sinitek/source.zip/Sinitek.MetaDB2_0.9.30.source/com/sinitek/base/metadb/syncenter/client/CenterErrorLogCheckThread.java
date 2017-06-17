// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CenterErrorLogCheckThread.java

package com.sinitek.base.metadb.syncenter.client;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.BaseDataPack;
import com.sinitek.base.metadb.syncenter.common.DBConnectionFactory;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.collections.Buffer;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.client:
//            SynCenterClientException, SynClientConfig

public class CenterErrorLogCheckThread
{
    static class CheckTask extends TimerTask
    {

        public void run()
        {
            Connection connection;
            CenterErrorLogCheckThread.LOGGER.debug("\u5F00\u59CB\u68C0\u67E5\u6570\u636E\u5E93\u4E2D\u540C\u6B65\u4E2D\u5FC3\u672A\u53D1\u5F80\u672C\u5E94\u7528\u7684\u540C\u6B65\u6570\u636E\u62A5\u6587");
            connection = null;
            connection = clientConfig.getConnectionFactory().getConnection();
            PreparedStatement stmt = connection.prepareStatement("select logid, logtime, datapack from SYNC_CENTERERRORLOG where appkey=? for update", 1004, 1008);
            stmt.setString(1, clientConfig.getAppKey());
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            while(rs.next()) 
            {
                count++;
                int logId = rs.getInt("LOGID");
                Date logTime = rs.getTimestamp("LOGTIME");
                if(CenterErrorLogCheckThread.LOGGER.isDebugEnabled())
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    CenterErrorLogCheckThread.LOGGER.debug((new StringBuilder()).append("\u5904\u7406LOGID[").append(logId).append("]\u7684\u540C\u6B65\u6570\u636E\uFF0C\u8BB0\u5F55\u65F6\u95F4\uFF1A[").append(sdf.format(logTime)).append("]").toString());
                }
                try
                {
                    InputStream is = rs.getBinaryStream("DATAPACK");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    for(int b = is.read(); b != -1; b = is.read())
                        baos.write(b);

                    com.sinitek.base.metadb.syncenter.common.IDataPack dataPack = new BaseDataPack(baos.toByteArray());
                    dataBuffer.add(dataPack);
                    is.close();
                    rs.deleteRow();
                }
                catch(Exception e)
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    CenterErrorLogCheckThread.LOGGER.error((new StringBuilder()).append("\u5904\u7406LOGID[").append(logId).append("]\u7684\u540C\u6B65\u6570\u636E\uFF0C\u8BB0\u5F55\u65F6\u95F4\uFF1A[").append(sdf.format(logTime)).append("]\u5931\u8D25").toString(), e);
                }
            }
            connection.commit();
            CenterErrorLogCheckThread.LOGGER.debug((new StringBuilder()).append("\u603B\u5171\u5904\u7406\u4E86[").append(count).append("]\u6761\u540C\u6B65\u6570\u636E").toString());
            CenterErrorLogCheckThread.LOGGER.debug("\u68C0\u67E5\u6570\u636E\u5E93\u4E2D\u540C\u6B65\u4E2D\u5FC3\u672A\u53D1\u5F80\u672C\u5E94\u7528\u7684\u540C\u6B65\u6570\u636E\u62A5\u6587\u5B8C\u6210");
            if(connection != null)
                try
                {
                    connection.close();
                }
                catch(SQLException e)
                {
                    CenterErrorLogCheckThread.LOGGER.warn("\u5173\u95ED\u6E05\u9664\u5386\u53F2\u540C\u6B65\u9519\u8BEF\u65E5\u5FD7\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
                }
            break MISSING_BLOCK_LABEL_448;
            Exception ex;
            ex;
            CenterErrorLogCheckThread.LOGGER.error("\u68C0\u67E5\u5386\u53F2\u540C\u6B65\u9519\u8BEF\u65E5\u5FD7\u64CD\u4F5C\u5931\u8D25", ex);
            if(connection != null)
                try
                {
                    connection.close();
                }
                catch(SQLException e)
                {
                    CenterErrorLogCheckThread.LOGGER.warn("\u5173\u95ED\u6E05\u9664\u5386\u53F2\u540C\u6B65\u9519\u8BEF\u65E5\u5FD7\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
                }
            break MISSING_BLOCK_LABEL_448;
            Exception exception;
            exception;
            if(connection != null)
                try
                {
                    connection.close();
                }
                catch(SQLException e)
                {
                    CenterErrorLogCheckThread.LOGGER.warn("\u5173\u95ED\u6E05\u9664\u5386\u53F2\u540C\u6B65\u9519\u8BEF\u65E5\u5FD7\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
                }
            throw exception;
        }

        private Buffer dataBuffer;
        private SynClientConfig clientConfig;

        CheckTask(Buffer dataBuffer, SynClientConfig clientConfig)
        {
            this.dataBuffer = dataBuffer;
            this.clientConfig = clientConfig;
        }
    }


    public CenterErrorLogCheckThread()
    {
    }

    public static void startCheck(Buffer dataBuffer, SynClientConfig clientConfig)
    {
        Connection connection;
        Exception exception;
        connection = null;
        try
        {
            connection = clientConfig.getConnectionFactory().getConnection();
            PreparedStatement stmt = connection.prepareStatement("delete from SYNC_CENTERERRORLOG where appkey=?");
            stmt.setString(1, clientConfig.getAppKey());
            int delCount = stmt.executeUpdate();
            LOGGER.debug((new StringBuilder()).append("\u603B\u5171\u5220\u9664\u4E86[").append(delCount).append("]\u6761\u5386\u53F2\u672A\u540C\u6B65\u6570\u636E").toString());
            connection.commit();
        }
        catch(Exception ex)
        {
            throw new SynCenterClientException("0015", ex);
        }
        finally
        {
            if(connection == null) goto _L0; else goto _L0
        }
        if(connection != null)
            try
            {
                connection.close();
            }
            catch(SQLException e)
            {
                LOGGER.warn("\u5173\u95ED\u6E05\u9664\u5386\u53F2\u540C\u6B65\u9519\u8BEF\u65E5\u5FD7\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
            }
        break MISSING_BLOCK_LABEL_143;
        try
        {
            connection.close();
        }
        catch(SQLException e)
        {
            LOGGER.warn("\u5173\u95ED\u6E05\u9664\u5386\u53F2\u540C\u6B65\u9519\u8BEF\u65E5\u5FD7\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
        }
        throw exception;
        Timer checkTimer = new Timer("\u68C0\u67E5\u672A\u540C\u6B65\u6570\u636E\u62A5\u6587\u7EBF\u7A0B", true);
        checkTimer.schedule(new CheckTask(dataBuffer, clientConfig), 0x1d4c0L, 0x1d4c0L);
        return;
    }

    private static Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }

}
