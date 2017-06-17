// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerDealThreadGroup.java

package com.sinitek.base.metadb.syncenter.server;

import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.*;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.server:
//            DataDealThread, SynCenterServerException, SynServerConfig

public class ServerDealThreadGroup
{

    public ServerDealThreadGroup(SynServerConfig synServerConfig)
    {
        this.synServerConfig = synServerConfig;
        checkDealThreads();
    }

    public synchronized void addDataPackToDeal(IDataPack dataPack)
    {
        if(!dataPack.getApplicationCode().equalsIgnoreCase("HELLO"))
        {
            synchronized(this)
            {
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u5411[").append(dealThreadMap.size()).append("]\u4E2A\u5904\u7406\u7EBF\u7A0B\u6D3E\u53D1\u6570\u636E").toString());
                Iterator iterator = dealThreadMap.entrySet().iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                    if(!dataPack.getSenderAppKey().equals(entry.getKey()))
                    {
                        DataDealThread thread = (DataDealThread)entry.getValue();
                        thread.addDataToDeal(dataPack);
                    }
                } while(true);
            }
        } else
        {
            checkDealThreads();
            synchronized(this)
            {
                DataDealThread dealThread = (DataDealThread)dealThreadMap.get(dataPack.getSenderAppKey());
                if(dealThread != null)
                {
                    MetaDBInstanceInfo instanceInfo = dealThread.getInstanceInfo();
                    if(LOGGER.isDebugEnabled())
                        LOGGER.debug((new StringBuilder()).append("\u5BF9MetaDB\u5B9E\u4F8B[").append(instanceInfo.getKey()).append("]\uFF0CAPP\u540D\u79F0[").append(instanceInfo.getAppName()).append("]\u6807\u8BB0\u5B9E\u9645IP\u5730\u5740\uFF1A[").append(dataPack.getSenderAddress()).append("]").toString());
                    instanceInfo.setInstanceIpAddress(dataPack.getSenderAddress());
                }
            }
        }
    }

    public void checkDealThreads()
    {
        Connection connection;
        Map onlineMap;
        LOGGER.debug("\u5F00\u59CB\u67E5\u8BE2\u6570\u636E\u5E93\u4E2D\u8BB0\u5F55\u7684\u5728\u7EBFMetaDB\u5B9E\u4F8B\u6570\u636E");
        connection = null;
        onlineMap = new HashMap();
        try
        {
            connection = synServerConfig.getConnectionFactory().getConnection();
            String sql = "select t.*, (systimestamp - t.lastupdatetime) as d  from SYNC_METADBINSTINFO t\nwhere (systimestamp - t.lastupdatetime)<'000000000 00:02:00.000000'";
            Statement statement = connection.createStatement();
            ResultSet rs;
            MetaDBInstanceInfo info;
            for(rs = statement.executeQuery(sql); rs.next(); onlineMap.put(info.getKey(), info))
            {
                info = new MetaDBInstanceInfo();
                info.setKey(rs.getString("APPKEY"));
                info.setAppName(rs.getString("APPNAME"));
                info.setInstanceIpAddress(rs.getString("IPADDRESS"));
                info.setPort(rs.getInt("PORT"));
                info.setLastUpdateTime(rs.getTimestamp("LASTUPDATETIME"));
            }

            rs.close();
            statement.close();
        }
        catch(SQLException e)
        {
            throw new SynCenterServerException("0002", e);
        }
        if(connection != null)
            try
            {
                connection.close();
            }
            catch(SQLException e)
            {
                LOGGER.warn("\u5173\u95ED\u8BFB\u53D6\u5728\u7EBFMetaDB\u5B9E\u4F8B\u7684\u6570\u636E\u8FDE\u63A5\u5931\u8D25");
            }
        break MISSING_BLOCK_LABEL_235;
        Exception exception;
        exception;
        if(connection != null)
            try
            {
                connection.close();
            }
            catch(SQLException e)
            {
                LOGGER.warn("\u5173\u95ED\u8BFB\u53D6\u5728\u7EBFMetaDB\u5B9E\u4F8B\u7684\u6570\u636E\u8FDE\u63A5\u5931\u8D25");
            }
        throw exception;
        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug((new StringBuilder()).append("\u603B\u5171\u83B7\u5F97[").append(onlineMap.size()).append("]\u5728\u7EBFMetaDB\u5B9E\u4F8B").toString());
            MetaDBInstanceInfo instanceInfo;
            for(Iterator iterator = onlineMap.values().iterator(); iterator.hasNext(); LOGGER.debug((new StringBuilder()).append("MetaDB\u5B9E\u4F8B[").append(instanceInfo.getKey()).append("]\uFF0CAPP\u540D\u79F0[").append(instanceInfo.getAppName()).append("]\uFF0C\u76D1\u542C\u5730\u5740\uFF1A[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]").toString()))
                instanceInfo = (MetaDBInstanceInfo)iterator.next();

        }
        synchronized(this)
        {
            Map tempMap = new HashMap();
            tempMap.putAll(dealThreadMap);
            for(Iterator onlineIterator = onlineMap.entrySet().iterator(); onlineIterator.hasNext();)
            {
                java.util.Map.Entry onlineEntry = (java.util.Map.Entry)onlineIterator.next();
                DataDealThread dealThread = (DataDealThread)tempMap.get(onlineEntry.getKey());
                MetaDBInstanceInfo instanceInfo = (MetaDBInstanceInfo)onlineEntry.getValue();
                if(dealThread != null)
                {
                    if(LOGGER.isDebugEnabled())
                        LOGGER.debug((new StringBuilder()).append("MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u7684\u53D1\u9001\u7EBF\u7A0B\u5DF2\u7ECF\u5B58\u5728\uFF0C\u66F4\u65B0\u5B9E\u4F8B\u4FE1\u606F").toString());
                    dealThread.updateInstanceInfo(instanceInfo);
                    tempMap.remove(onlineEntry.getKey());
                } else
                {
                    if(LOGGER.isDebugEnabled())
                        LOGGER.debug((new StringBuilder()).append("MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u7684\u53D1\u9001\u7EBF\u7A0B\u4E0D\u5B58\u5728\u6216\u5DF2\u7ECF\u5173\u95ED\uFF0C\u521B\u5EFA\u65B0\u5904\u7406\u7EBF\u7A0B").toString());
                    DataDealThread _dealThread = new DataDealThread(instanceInfo, synServerConfig);
                    Thread thread = new Thread(_dealThread, (new StringBuilder()).append("\u5904\u7406\u7EBF\u7A0B_").append(instanceInfo.getKey()).toString());
                    thread.setDaemon(true);
                    thread.start();
                    dealThreadMap.put(onlineEntry.getKey(), _dealThread);
                }
            }

            Object key;
            for(Iterator killIterator = tempMap.keySet().iterator(); killIterator.hasNext(); dealThreadMap.remove(key))
            {
                key = killIterator.next();
                DataDealThread dealThread = (DataDealThread)dealThreadMap.get(key);
                if(LOGGER.isDebugEnabled())
                {
                    MetaDBInstanceInfo instanceInfo = dealThread.getInstanceInfo();
                    LOGGER.debug((new StringBuilder()).append("MetaDB\u5B9E\u4F8BKey[").append(instanceInfo.getKey()).append("]\uFF0C\u5730\u5740[").append(instanceInfo.getInstanceIpAddress()).append(":").append(instanceInfo.getPort()).append("]\u5904\u4E8E\u79BB\u7EBF\u72B6\u6001\uFF0C\u5411\u5904\u7406\u7EBF\u7A0B\u53D1\u9001\u7EC8\u6B62\u4FE1\u53F7").toString());
                }
                dealThread.exit();
            }

            notifyAll();
        }
        return;
    }

    private SynServerConfig synServerConfig;
    private static Logger LOGGER;
    private final Map dealThreadMap = new HashMap();

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }
}
