// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataDealThread.java

package com.sinitek.base.metadb.syncenter.server;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.*;
import oracle.sql.BLOB;
import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.UnboundedFifoBuffer;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.server:
//            SynServerConfig

public class DataDealThread
    implements Runnable
{

    public DataDealThread(MetaDBInstanceInfo instanceInfo, SynServerConfig serverConfig)
    {
        runflag = true;
        buffer = new UnboundedFifoBuffer();
        this.instanceInfo = instanceInfo;
        this.serverConfig = serverConfig;
    }

    public synchronized void exit()
    {
        runflag = false;
        notifyAll();
    }

    public synchronized void addDataToDeal(IDataPack dataPack)
    {
        buffer.add(dataPack);
        notifyAll();
    }

    public void updateInstanceInfo(MetaDBInstanceInfo instanceInfo)
    {
        String origIpAddress = this.instanceInfo.getInstanceIpAddress();
        this.instanceInfo = instanceInfo;
        this.instanceInfo.setInstanceIpAddress(origIpAddress);
    }

    public MetaDBInstanceInfo getInstanceInfo()
    {
        return instanceInfo;
    }

    public void run()
    {
        do
        {
            if(!runflag)
                break;
            synchronized(this)
            {
                try
                {
                    for(; buffer.isEmpty() && runflag; wait());
                }
                catch(InterruptedException e)
                {
                    LOGGER.debug((new StringBuilder()).append("MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u7684\u53D1\u9001\u7EBF\u7A0B\u5F3A\u5236\u9000\u51FAwait\u72B6\u6001\uFF0C\u51C6\u5907\u9000\u51FA").toString(), e);
                    runflag = false;
                }
            }
            if(!runflag)
            {
                LOGGER.debug((new StringBuilder()).append("MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u7684\u53D1\u9001\u7EBF\u7A0B\u5373\u5C06\u7ED3\u675F").toString());
                break;
            }
            IDataPack dataPack = (IDataPack)buffer.remove();
            boolean sendSuccess = false;
            Socket socket = new Socket();
            try
            {
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u8FDE\u63A5MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]").toString());
                socket.connect(new InetSocketAddress(instanceInfo.getInstanceIpAddress(), instanceInfo.getPort()), 10000);
                if(LOGGER.isTraceEnabled())
                    LOGGER.trace((new StringBuilder()).append("\u8FDE\u63A5MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u6210\u529F").toString());
                OutputStream os = socket.getOutputStream();
                os.write(dataPack.getBytes());
                os.flush();
                socket.shutdownOutput();
                if(LOGGER.isTraceEnabled())
                    LOGGER.trace((new StringBuilder()).append("\u5BF9MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u7684\u8F93\u51FA\u6D41\u5199\u5165\u6210\u529F").toString());
                InputStream is = socket.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream(192);
                int b;
                for(b = is.read(); b != -1 && baos.size() < 192; b = is.read())
                    baos.write(b);

                if(baos.size() < 192)
                    LOGGER.error((new StringBuilder()).append("\u5BF9MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u53D1\u9001\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u540E\u6536\u5230\u7684\u8FD4\u56DE\u62A5\u6587\u957F\u5EA6\u4E0D\u8DB3192\u4F4D\uFF0C\u5B9E\u9645\u4E3A[").append(baos.size()).append("]\u4F4D\uFF0C\u5224\u4E3A\u975E\u6CD5\u8FD4\u56DE\u62A5\u6587").toString());
                else
                if(b != -1)
                {
                    LOGGER.error((new StringBuilder()).append("\u5BF9MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u53D1\u9001\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u540E\u6536\u5230\u7684\u8FD4\u56DE\u62A5\u6587\u957F\u5EA6\u8D85\u8FC7192\u4F4D\uFF0C\u5224\u4E3A\u975E\u6CD5\u8FD4\u56DE\u62A5\u6587").toString());
                } else
                {
                    if(LOGGER.isTraceEnabled())
                        LOGGER.trace((new StringBuilder()).append("\u5BF9MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u53D1\u9001\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u540E\u6536\u5230\u7684\u8FD4\u56DE\u62A5\u6587\u957F\u5EA6\u4E3A192\u4F4D\uFF0C\u51C6\u5907\u5224\u65AD\u62A5\u6587\u7F16\u53F7\u662F\u5426\u5339\u914D").toString());
                    String backPackCode = (new String(baos.toByteArray(), 12, 20)).trim();
                    if(!dataPack.getPackCode().equals(backPackCode))
                    {
                        LOGGER.error((new StringBuilder()).append("\u5BF9MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u53D1\u9001\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u540E\u6536\u5230\u7684\u8FD4\u56DE\u62A5\u6587\u4E2D\u7684\u62A5\u6587\u7F16\u53F7\u4E0D\u4E00\u81F4\uFF0C\u5B9E\u9645\u4E3A[").append(backPackCode).append("]\uFF0C\u5224\u4E3A\u975E\u6CD5\u8FD4\u56DE\u62A5\u6587").toString());
                    } else
                    {
                        if(LOGGER.isTraceEnabled())
                            LOGGER.trace((new StringBuilder()).append("\u5BF9MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u53D1\u9001\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u540E\u6536\u5230\u7684\u8FD4\u56DE\u62A5\u6587\u4E2D\u7684\u62A5\u6587\u7F16\u53F7\u4E00\u81F4\uFF0C\u5224\u4E3A\u6B63\u786E\u8FD4\u56DE\u62A5\u6587").toString());
                        sendSuccess = true;
                        if(LOGGER.isDebugEnabled())
                            LOGGER.debug((new StringBuilder()).append("\u8FDE\u63A5MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u6210\u529F").toString());
                    }
                }
                socket.shutdownInput();
            }
            catch(IOException e)
            {
                LOGGER.debug((new StringBuilder()).append("\u8FDE\u63A5MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u5931\u8D25").toString(), e);
            }
            finally
            {
                if(socket.isConnected())
                    try
                    {
                        socket.close();
                    }
                    catch(IOException e)
                    {
                        LOGGER.debug((new StringBuilder()).append("\u5173\u95ED\u8FDE\u63A5MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u7684Socket\u5BF9\u8C61\u5931\u8D25").toString(), e);
                    }
            }
            if(!sendSuccess)
            {
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u8FDE\u63A5MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u53D1\u9001\u6570\u636E\u62A5\u6587[").append(dataPack.getPackCode()).append("]\u5931\u8D25\uFF0C\u51C6\u5907\u5411\u6570\u636E\u5E93\u4E2D\u5199\u5165\u540C\u6B65\u6570\u636E").toString());
                try
                {
                    writeDataPackToDB(dataPack);
                }
                catch(SQLException sqle)
                {
                    LOGGER.warn((new StringBuilder()).append("\u5BF9MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u5199\u5165\u6570\u636E\u5E93\u540C\u6B65\u6570\u636E\u5931\u8D25\uFF0C\u53EF\u80FD\u662F\u5931\u8D25\u540E\u56DE\u6EDA\u4E0D\u6210\u529F\u6216\u5173\u95ED\u6570\u636E\u5E93\u8FDE\u63A5\u5931\u8D25").toString());
                }
            }
        } while(true);
        LOGGER.debug((new StringBuilder()).append("MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u7684\u53D1\u9001\u7EBF\u7A0B\u9000\u51FA").toString());
    }

    private void writeDataPackToDB(IDataPack dataPack)
        throws SQLException
    {
        Connection connection = null;
        try
        {
            connection = serverConfig.getConnectionFactory().getConnection();
            String getIdSql = "select SEQ_CENTERERRORLOG.nextval from dual";
            ResultSet rs = connection.createStatement().executeQuery(getIdSql);
            rs.next();
            long id = rs.getLong(1);
            String sql = "insert into SYNC_CENTERERRORLOG\n( appkey, appname, ipaddress, port, logtime, logid, datapack )\nvalues\n(?,?,?,?, sysdate, ?, empty_blob() )";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, instanceInfo.getKey());
            preparedStatement.setString(2, instanceInfo.getAppName());
            preparedStatement.setString(3, instanceInfo.getInstanceIpAddress());
            preparedStatement.setInt(4, instanceInfo.getPort());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
            rs = connection.createStatement().executeQuery((new StringBuilder()).append("select datapack from SYNC_CENTERERRORLOG where logid=").append(id).append(" for update").toString());
            if(rs.next())
            {
                BLOB blob = (BLOB)rs.getBlob(1);
                OutputStream os = blob.setBinaryStream(0L);
                os.write(dataPack.getBytes());
                os.flush();
                os.close();
            }
            connection.commit();
        }
        catch(Exception ex)
        {
            LOGGER.error((new StringBuilder()).append("\u5BF9MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u5199\u5165\u6570\u636E\u5E93\u540C\u6B65\u6570\u636E\u5931\u8D25").toString(), ex);
            if(connection != null)
                connection.rollback();
        }
        finally
        {
            if(connection != null)
                connection.close();
        }
    }

    private MetaDBInstanceInfo instanceInfo;
    private boolean runflag;
    private Buffer buffer;
    private SynServerConfig serverConfig;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }
}
