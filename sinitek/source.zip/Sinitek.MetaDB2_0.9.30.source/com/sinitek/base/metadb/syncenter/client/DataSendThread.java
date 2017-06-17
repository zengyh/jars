// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSendThread.java

package com.sinitek.base.metadb.syncenter.client;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.DBConnectionFactory;
import com.sinitek.base.metadb.syncenter.common.IDataPack;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;
import oracle.sql.BLOB;
import org.apache.commons.collections.Buffer;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.client:
//            SynClientConfig, DataSendUtils

public class DataSendThread
    implements Runnable
{

    public DataSendThread(Buffer sendBuffer, SynClientConfig clientConfig)
    {
        this.sendBuffer = sendBuffer;
        this.clientConfig = clientConfig;
        failCountArray = new int[clientConfig.getServerAddress().length];
    }

    public void run()
    {
_L2:
        IDataPack dataPack;
        Connection connection;
        boolean successFlag;
        do
        {
            dataPack = (IDataPack)sendBuffer.remove();
            InetSocketAddress address = getCurrentServer();
            InetSocketAddress firstUseAddress = address;
            successFlag = false;
            do
            {
                if(successFlag || address == null)
                    break;
                successFlag = DataSendUtils.sendData(dataPack, address);
                if(!successFlag)
                {
                    address = getNextServer();
                    if(address == firstUseAddress)
                        address = null;
                }
            } while(true);
        } while(successFlag);
        LOGGER.debug("\u5411\u6240\u6709\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668\u53D1\u9001\u6570\u636E\u62A5\u6587\u5747\u5931\u8D25\uFF0C\u5C1D\u8BD5\u5411\u6570\u636E\u5E93\u670D\u52A1\u5668\u5199\u5165\u6570\u636E\u62A5\u6587");
        connection = null;
        connection = clientConfig.getConnectionFactory().getConnection();
        String getIdSql = "select seq_apperrorlog.nextval from dual";
        ResultSet rs = connection.createStatement().executeQuery(getIdSql);
        rs.next();
        int id = rs.getInt(1);
        rs.close();
        String insertSql = "insert into sync_apperrorlog\n( logid, appkey, appname, ipaddress, port, logtime, datapack )\nvalues\n( ?, ?, ?, ?, ?, sysdate, empty_blob())";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, clientConfig.getAppKey());
        preparedStatement.setString(3, clientConfig.getAppName());
        preparedStatement.setString(4, clientConfig.getLocalAddress());
        preparedStatement.setInt(5, clientConfig.getListernPort());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        rs = connection.createStatement().executeQuery((new StringBuilder()).append("select datapack from sync_apperrorlog where logid=").append(id).append(" for update").toString());
        if(rs.next())
        {
            BLOB blob = (BLOB)rs.getBlob(1);
            OutputStream os = blob.setBinaryStream(0L);
            os.write(dataPack.getBytes());
            os.flush();
            os.close();
        }
        connection.commit();
        LOGGER.debug("\u5C1D\u8BD5\u5411\u6570\u636E\u5E93\u670D\u52A1\u5668\u5199\u5165\u6570\u636E\u62A5\u6587\u6210\u529F");
        if(connection != null)
            try
            {
                connection.close();
            }
            catch(SQLException e)
            {
                LOGGER.warn("\u5173\u95ED\u540C\u6B65\u4E2D\u5FC3\u5BA2\u6237\u7AEF\u5C06\u672A\u6210\u529F\u63D0\u4EA4\u5230\u540C\u6B65\u4E2D\u5FC3\u7684\u540C\u6B65\u62A5\u6587\u5199\u5165\u6570\u636E\u5E93\u7684\u8FDE\u63A5\u5931\u8D25", e);
            }
        continue; /* Loop/switch isn't completed */
        Exception ex;
        ex;
        LOGGER.error((new StringBuilder()).append("\u5C06\u6570\u636E\u62A5\u6587[").append(dataPack.getApplicationCode()).append("]\u5199\u5165\u6570\u636E\u5E93\u5931\u8D25").toString(), ex);
        if(connection != null)
            try
            {
                connection.close();
            }
            catch(SQLException e)
            {
                LOGGER.warn("\u5173\u95ED\u540C\u6B65\u4E2D\u5FC3\u5BA2\u6237\u7AEF\u5C06\u672A\u6210\u529F\u63D0\u4EA4\u5230\u540C\u6B65\u4E2D\u5FC3\u7684\u540C\u6B65\u62A5\u6587\u5199\u5165\u6570\u636E\u5E93\u7684\u8FDE\u63A5\u5931\u8D25", e);
            }
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        if(connection != null)
            try
            {
                connection.close();
            }
            catch(SQLException e)
            {
                LOGGER.warn("\u5173\u95ED\u540C\u6B65\u4E2D\u5FC3\u5BA2\u6237\u7AEF\u5C06\u672A\u6210\u529F\u63D0\u4EA4\u5230\u540C\u6B65\u4E2D\u5FC3\u7684\u540C\u6B65\u62A5\u6587\u5199\u5165\u6570\u636E\u5E93\u7684\u8FDE\u63A5\u5931\u8D25", e);
            }
        throw exception;
    }

    private InetSocketAddress getCurrentServer()
    {
        return clientConfig.getServerAddress()[currntServerIndex];
    }

    private synchronized InetSocketAddress getNextServer()
    {
        int _current = currntServerIndex;
        failCountArray[currntServerIndex]++;
        int availableServerCount = clientConfig.getServerAddress().length;
        if(failCountArray[currntServerIndex] >= 3)
        {
            if(LOGGER.isDebugEnabled())
            {
                InetSocketAddress currentAddress = clientConfig.getServerAddress()[_current];
                LOGGER.debug((new StringBuilder()).append("\u5F53\u524D\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668[").append(currentAddress.getHostName()).append(":").append(currentAddress.getPort()).append("]").append("\u5DF2\u7ECF\u8FDE\u7EED3\u6B21\u672A\u80FD\u6210\u529F\u53D1\u9001\u6570\u636E\uFF0C\u5207\u6362\u5230\u4E0B\u4E00\u4E2A\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668\u4F5C\u4E3A\u9996\u9009\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668").toString());
            }
            failCountArray[currntServerIndex] = 0;
            currntServerIndex++;
            if(currntServerIndex == availableServerCount)
                currntServerIndex = 0;
            if(LOGGER.isDebugEnabled())
            {
                InetSocketAddress currentAddress = clientConfig.getServerAddress()[currntServerIndex];
                LOGGER.debug((new StringBuilder()).append("\u5F53\u524D\u9996\u5148\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668[").append(currentAddress.getHostName()).append(":").append(currentAddress.getPort()).append("]").toString());
            }
        }
        if(++_current == availableServerCount)
            _current = 0;
        return clientConfig.getServerAddress()[_current];
    }

    private Buffer sendBuffer;
    private SynClientConfig clientConfig;
    private int failCountArray[];
    private int currntServerIndex;
    private static Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }
}
