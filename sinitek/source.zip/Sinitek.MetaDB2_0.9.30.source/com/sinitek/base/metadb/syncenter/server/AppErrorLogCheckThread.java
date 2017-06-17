// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AppErrorLogCheckThread.java

package com.sinitek.base.metadb.syncenter.server;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.BaseDataPack;
import com.sinitek.base.metadb.syncenter.common.DBConnectionFactory;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.TimerTask;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.server:
//            SynServerConfig, ServerDealThreadGroup

public class AppErrorLogCheckThread extends TimerTask
{

    public AppErrorLogCheckThread(SynServerConfig config, ServerDealThreadGroup threadGroup)
    {
        this.config = config;
        this.threadGroup = threadGroup;
    }

    public void run()
    {
        Connection connection;
        LOGGER.debug("\u5F00\u59CB\u68C0\u67E5MetaDB\u5E94\u7528\u6CA1\u6709\u6210\u529F\u9001\u8FBE\u540C\u6B65\u4E2D\u5FC3\u7684\u5F85\u540C\u6B65\u6570\u636E");
        connection = null;
        connection = config.getConnectionFactory().getConnection();
        connection.setReadOnly(false);
        String sql = "select appkey, ipaddress, appname, port, datapack, logid from SYNC_APPERRORLOG for update ";
        ResultSet rs = connection.createStatement(1004, 1008).executeQuery(sql);
        do
        {
            if(!rs.next())
                break;
            boolean dealSuccessFlag = false;
            int logId = rs.getInt("LOGID");
            if(LOGGER.isDebugEnabled())
            {
                String appKey = rs.getString("APPKEY");
                String appIpAddress = rs.getString("IPADDRESS");
                String appName = rs.getString("APPNAME");
                int port = rs.getInt("PORT");
                LOGGER.debug((new StringBuilder()).append("MetaDB\u5B9E\u4F8B[").append(appKey).append("]\uFF0CAPP\u540D\u79F0[").append(appName).append("]\uFF0C\u76D1\u542C\u5730\u5740\uFF1A[").append(appIpAddress).append(":").append(port).append("]\u5B58\u5728\u6CA1\u6709\u6210\u529F\u9001\u8FBE\u540C\u6B65\u4E2D\u5FC3\u7684\u6570\u636E\u62A5\u6587\uFF0C\u62A5\u6587LOGID[").append(logId).append("]").toString());
            }
            Blob blob = rs.getBlob("DATAPACK");
            InputStream is = blob.getBinaryStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for(int b = is.read(); b != -1; b = is.read())
                baos.write(b);

            is.close();
            byte datapackBytes[] = baos.toByteArray();
            try
            {
                com.sinitek.base.metadb.syncenter.common.IDataPack dataPack = new BaseDataPack(datapackBytes);
                threadGroup.addDataPackToDeal(dataPack);
                dealSuccessFlag = true;
            }
            catch(Exception ex)
            {
                LOGGER.warn((new StringBuilder()).append("\u51C6\u5907\u6D3E\u53D1\u672A\u6210\u529F\u6295\u9012\u5230\u540C\u6B65\u4E2D\u5FC3\u7684\u6570\u636E\u62A5\u6587\u5931\u8D25\uFF0C\u62A5\u6587LOGID[").append(logId).append("]").toString());
            }
            if(dealSuccessFlag)
            {
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u6D3E\u53D1\u672A\u6210\u529F\u6295\u9012\u5230\u540C\u6B65\u4E2D\u5FC3\u7684\u6570\u636E\u62A5\u6587\u6210\u529F\uFF0C\u62A5\u6587LOGID[").append(logId).append("]\uFF0C\u51C6\u5907\u5220\u9664\u8BE5\u6570\u636E").toString());
                rs.deleteRow();
            }
        } while(true);
        connection.commit();
        if(connection != null)
            try
            {
                connection.close();
            }
            catch(SQLException e)
            {
                LOGGER.warn("\u5173\u95ED\u68C0\u67E5MetaDB\u5E94\u7528\u6CA1\u6709\u6210\u529F\u9001\u8FBE\u540C\u6B65\u4E2D\u5FC3\u7684\u6570\u636E\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
            }
        break MISSING_BLOCK_LABEL_473;
        Exception ex;
        ex;
        LOGGER.error("\u68C0\u67E5MetaDB\u5E94\u7528\u6CA1\u6709\u6210\u529F\u9001\u8FBE\u540C\u6B65\u4E2D\u5FC3\u7684\u5F85\u540C\u6B65\u6570\u636E\u5931\u8D25", ex);
        if(connection != null)
            try
            {
                connection.close();
            }
            catch(SQLException e)
            {
                LOGGER.warn("\u5173\u95ED\u68C0\u67E5MetaDB\u5E94\u7528\u6CA1\u6709\u6210\u529F\u9001\u8FBE\u540C\u6B65\u4E2D\u5FC3\u7684\u6570\u636E\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
            }
        break MISSING_BLOCK_LABEL_473;
        Exception exception;
        exception;
        if(connection != null)
            try
            {
                connection.close();
            }
            catch(SQLException e)
            {
                LOGGER.warn("\u5173\u95ED\u68C0\u67E5MetaDB\u5E94\u7528\u6CA1\u6709\u6210\u529F\u9001\u8FBE\u540C\u6B65\u4E2D\u5FC3\u7684\u6570\u636E\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25", e);
            }
        throw exception;
        LOGGER.debug("\u68C0\u67E5MetaDB\u5E94\u7528\u6CA1\u6709\u6210\u529F\u9001\u8FBE\u540C\u6B65\u4E2D\u5FC3\u7684\u5F85\u540C\u6B65\u6570\u636E\u5B8C\u6210");
        return;
    }

    private static Logger LOGGER;
    private SynServerConfig config;
    private ServerDealThreadGroup threadGroup;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }
}
