// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynClient.java

package com.sinitek.base.metadb.syncenter.client;

import com.sinitek.base.common.SinitekDES;
import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.BlockingBuffer;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.client:
//            SynClientConfig, SynCenterClientException, SynClientListenThread, DataDealThread, 
//            DataSendThread, SynClientStatusCheckThread, DataSendUtils, CenterErrorLogCheckThread, 
//            ISynClientListener

public class SynClient
{

    public SynClient(Properties syncProperties, ISynClientListener listener, DataSource localDataSource)
    {
        clientConfig = new SynClientConfig();
        String serverListStr = syncProperties.getProperty("sync.serverlist", "");
        if(StringUtils.isBlank(serverListStr))
            throw new SynCenterClientException("0001");
        try
        {
            InetSocketAddress serverAddress[] = parseServerAddress(serverListStr);
            if(LOGGER.isDebugEnabled())
            {
                LOGGER.debug("\u914D\u7F6E\u7684\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668\u4FE1\u606F\u5982\u4E0B\uFF1A");
                for(int i = 0; i < serverAddress.length; i++)
                    LOGGER.debug((new StringBuilder()).append("\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668[").append(i + 1).append("]:[").append(serverAddress[i].toString()).append("]").toString());

            }
            clientConfig.setServerAddress(serverAddress);
        }
        catch(Exception ex)
        {
            throw new SynCenterClientException("0002", ex, new Object[] {
                serverListStr
            });
        }
        String connTypeStr = syncProperties.getProperty("sync.dbconnecttype", "");
        if(StringUtils.isEmpty(connTypeStr))
            throw new SynCenterClientException("0003");
        DBConnectType connectType = (DBConnectType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/syncenter/common/DBConnectType, connTypeStr);
        if(LOGGER.isDebugEnabled())
            LOGGER.debug((new StringBuilder()).append("\u914D\u7F6E\u7684\u540C\u6B65\u4E2D\u5FC3\u6570\u636E\u5E93\u8FDE\u63A5\u65B9\u5F0F\u4E3A\uFF1A[").append(connectType.getEnumItemInfo()).append("]").toString());
        DBConnectionFactory connectionFactory = new DBConnectionFactory();
        connectionFactory.setDbConnectType(connectType);
        if(connectType == DBConnectType.JNDI)
        {
            String jndiString = syncProperties.getProperty("sync.jndi.jndiname", "");
            if(StringUtils.isEmpty(jndiString))
                throw new SynCenterClientException("0004");
            connectionFactory.setJndi(jndiString);
            LOGGER.debug((new StringBuilder()).append("MetaDB\u540C\u6B65\u4E2D\u5FC3\u5BA2\u6237\u7AEF\u914D\u7F6E\u4F7F\u7528\u7684JNDI\u540D\u79F0\u4E3A\uFF1A[").append(jndiString).append("]").toString());
        } else
        if(connectType == DBConnectType.EXT_DATASOURCE)
        {
            String dataSourceName = syncProperties.getProperty("sync.ext_ds.name", "");
            if(StringUtils.isEmpty(dataSourceName))
                throw new SynCenterClientException("0005");
            connectionFactory.setExtDataSourceName(dataSourceName);
            LOGGER.debug((new StringBuilder()).append("MetaDB\u540C\u6B65\u4E2D\u5FC3\u5BA2\u6237\u7AEF\u914D\u7F6E\u4F7F\u7528\u7684\u5916\u90E8\u6570\u636E\u6E90\u540D\u79F0\u4E3A\uFF1A[").append(dataSourceName).append("]").toString());
        } else
        if(connectType == DBConnectType.DIRECT_CONN)
        {
            String driverClassName = syncProperties.getProperty("sync.directconn.driverClassName", "");
            if(StringUtils.isBlank(driverClassName))
                throw new SynCenterClientException("0006", new Object[] {
                    "\u6CA1\u6709\u914D\u7F6Esync.directconn.driverClassName\uFF0C\u8BE5\u53C2\u6570\u5728\u4F7F\u7528DIRECT_CONN\u76F4\u8FDE\u6A21\u5F0F\u4E0B\u5FC5\u987B\uFF0C\u8868\u793A\u6570\u636E\u5E93\u9A71\u52A8\u5668\u7C7B\u540D"
                });
            LOGGER.debug((new StringBuilder()).append("\u914D\u7F6E\u7684\u6570\u636E\u5E93\u9A71\u52A8\u5668\u7C7B\u540D\u4E3A\uFF1A[").append(driverClassName).append("]").toString());
            String urlString = syncProperties.getProperty("sync.directconn.url", "");
            if(StringUtils.isBlank(urlString))
                throw new SynCenterClientException("0006", new Object[] {
                    "\u6CA1\u6709\u914D\u7F6Esync.directconn.url\uFF0C\u8BE5\u53C2\u6570\u5728\u4F7F\u7528DIRECT_CONN\u76F4\u8FDE\u6A21\u5F0F\u4E0B\u5FC5\u987B\uFF0C\u8868\u793A\u6570\u636E\u5E93\u8FDE\u63A5\u5B57\u7B26\u4E32"
                });
            LOGGER.debug((new StringBuilder()).append("\u914D\u7F6E\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u5B57\u7B26\u4E32\u4E3A\uFF1A[").append(urlString).append("]").toString());
            String userName = syncProperties.getProperty("sync.directconn.username", "");
            if(StringUtils.isBlank(userName))
                throw new SynCenterClientException("0006", new Object[] {
                    "\u6CA1\u6709\u914D\u7F6Esync.directconn.username\uFF0C\u8BE5\u53C2\u6570\u5728\u4F7F\u7528DIRECT_CONN\u76F4\u8FDE\u6A21\u5F0F\u4E0B\u5FC5\u987B\uFF0C\u8868\u793A\u6570\u636E\u5E93\u7528\u6237\u540D"
                });
            LOGGER.debug((new StringBuilder()).append("\u914D\u7F6E\u7684\u6570\u636E\u5E93\u7528\u6237\u540D\u4E3A\uFF1A[").append(userName).append("]").toString());
            String password = syncProperties.getProperty("sync.directconn.password", "");
            if(StringUtils.isBlank(password))
                throw new SynCenterClientException("0006", new Object[] {
                    "\u6CA1\u6709\u914D\u7F6Esync.directconn.password\uFF0C\u8BE5\u53C2\u6570\u5728\u4F7F\u7528DIRECT_CONN\u76F4\u8FDE\u6A21\u5F0F\u4E0B\u5FC5\u987B\uFF0C\u8868\u793A\u6570\u636E\u5E93\u7528\u6237\u5BC6\u7801"
                });
            if(password.toUpperCase().startsWith("SINITEK_ENC|"))
            {
                LOGGER.debug("\u914D\u7F6E\u7684\u6570\u636E\u5E93\u5BC6\u7801\u4E3A\u5BC6\u6587\u6A21\u5F0F");
                password = password.substring(12);
                password = SinitekDES.decrypt(password);
            } else
            {
                LOGGER.debug("\u914D\u7F6E\u7684\u6570\u636E\u5E93\u5BC6\u7801\u4E3A\u660E\u6587\u6A21\u5F0F");
            }
            connectionFactory.setDriverClassName(driverClassName);
            connectionFactory.setUserName(userName);
            connectionFactory.setPassword(password);
            connectionFactory.setUrl(urlString);
        } else
        if(connectType == DBConnectType.LOCAL)
        {
            if(localDataSource == null)
                throw new SynCenterClientException("0006", new Object[] {
                    "\u5728\u4F7F\u7528LOCAL\u6A21\u5F0F\u4E0B\u5FC5\u987B\u672C\u5730\u6570\u636E\u6E90\u4E0D\u80FD\u4E3A\u7A7A"
                });
            connectionFactory.setLocalDataSource(localDataSource);
        } else
        {
            throw new SynCenterClientException("0007", new Object[] {
                connTypeStr
            });
        }
        try
        {
            connectionFactory.testConnection();
        }
        catch(SQLException e)
        {
            throw new SynCenterClientException("0008", e);
        }
        clientConfig.setConnectionFactory(connectionFactory);
        String appName = syncProperties.getProperty("sync.appname", "");
        if(StringUtils.isEmpty(appName))
            throw new SynCenterClientException("0009");
        String localAddress = getLocalIpAddress();
        if(localAddress == null)
        {
            localAddress = "127.0.0.1";
            LOGGER.warn("\u672C\u5730\u65E0\u6CD5\u83B7\u5F97\u8054\u7F51\u72B6\u6001\u7684IP\u5730\u5740\uFF0C\u4F7F\u7528[127.0.0.1]\u66FF\u4EE3");
        } else
        {
            LOGGER.debug((new StringBuilder()).append("\u68C0\u6D4B\u5230\u672C\u5730IP\u5730\u5740[").append(localAddress).append("]").toString());
        }
        String appKey = (new StringBuilder()).append(appName).append("_").append(localAddress).toString();
        LOGGER.debug((new StringBuilder()).append("\u4F7F\u7528\u5E94\u7528KEY[").append(appKey).append("]").toString());
        clientConfig.setAppKey(appKey);
        clientConfig.setAppName(appName);
        clientConfig.setLocalAddress(localAddress);
        String strPort = syncProperties.getProperty("sync.listenport");
        if(StringUtils.isNotEmpty(strPort))
            try
            {
                int port = Integer.parseInt("strPort");
                clientConfig.setListernPort(port);
            }
            catch(NumberFormatException nfe)
            {
                throw new SynCenterClientException("0011", nfe, new Object[] {
                    strPort
                });
            }
        Buffer dataBuffer = BlockingBuffer.decorate(new UnboundedFifoBuffer());
        Thread listenThread = new Thread(new SynClientListenThread(clientConfig, dataBuffer), "\u540C\u6B65\u4E2D\u5FC3\u76D1\u542C\u7EBF\u7A0B");
        listenThread.setDaemon(true);
        listenThread.start();
        SynClientStatusCheckThread.startCheck(clientConfig);
        int regCount = clientConfig.getServerAddress().length;
        for(int i = 0; i < clientConfig.getServerAddress().length; i++)
        {
            IDataPack helloPack = BaseDataPack.createHelloPack(clientConfig.getAppKey());
            InetSocketAddress serverAddress = clientConfig.getServerAddress()[i];
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u5411\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668[").append(serverAddress.getHostName()).append(":").append(serverAddress.getPort()).append("]\u53D1\u9001\u63E1\u624B\u4FE1\u53F7").toString());
            if(DataSendUtils.sendData(helloPack, serverAddress))
            {
                regCount--;
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u5411\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668[").append(serverAddress.getHostName()).append(":").append(serverAddress.getPort()).append("]\u53D1\u9001\u63E1\u624B\u4FE1\u53F7\u6210\u529F").toString());
                continue;
            }
            if(LOGGER.isDebugEnabled())
                LOGGER.debug((new StringBuilder()).append("\u5411\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668[").append(serverAddress.getHostName()).append(":").append(serverAddress.getPort()).append("]\u53D1\u9001\u63E1\u624B\u4FE1\u53F7\u5931\u8D25").toString());
        }

        if(regCount == clientConfig.getServerAddress().length)
            throw new SynCenterClientException("0016");
        if(regCount > 0)
        {
            LOGGER.warn((new StringBuilder()).append("\u5C1A\u6709[").append(regCount).append("]\u4E2A\u540C\u6B65\u4E2D\u5FC3\u672A\u6210\u529F\u7B7E\u5230\uFF0C\u8FDB\u5165\u88AB\u52A8\u72B6\u6001\u540C\u6B65\u6D41\u7A0B\uFF0C\u7B49\u5F852\u5206\u949F\u540E\u7EE7\u7EED").toString());
            try
            {
                Thread.sleep(0x1d4c0L);
            }
            catch(InterruptedException e)
            {
                LOGGER.error("\u5728\u72B6\u6001\u88AB\u52A8\u540C\u6B65\u6D41\u7A0B\u4E2D\uFF0C\u7CFB\u7EDF\u88AB\u610F\u5916\u4E2D\u65AD", e);
                throw new SynCenterClientException("0013", e);
            }
            LOGGER.debug("\u72B6\u6001\u88AB\u52A8\u540C\u6B65\u6D41\u7A0B\u5B8C\u6210");
        }
        dataBuffer.clear();
        Thread dealThread = new Thread(new DataDealThread(dataBuffer, listener), "\u62A5\u6587\u5904\u7406\u7EBF\u7A0B");
        dealThread.setDaemon(true);
        dealThread.start();
        LOGGER.debug("\u62A5\u6587\u5904\u7406\u7EBF\u7A0B\u542F\u52A8");
        CenterErrorLogCheckThread.startCheck(dataBuffer, clientConfig);
        sendBuffer = BlockingBuffer.decorate(new UnboundedFifoBuffer());
        Thread sendThread = new Thread(new DataSendThread(sendBuffer, clientConfig), "\u6570\u636E\u62A5\u6587\u53D1\u9001\u7EBF\u7A0B");
        sendThread.setDaemon(true);
        sendThread.start();
        LOGGER.debug("\u62A5\u6587\u53D1\u9001\u7EBF\u7A0B\u542F\u52A8");
    }

    static String getLocalIpAddress()
    {
        try
        {
            return InetAddress.getLocalHost().getHostName();
        }
        catch(Exception e)
        {
            throw new SynCenterClientException("0010", e);
        }
    }

    public void sendData(IDataPack dataPack)
    {
        sendBuffer.add(dataPack);
    }

    public String getAppKey()
    {
        return clientConfig.getAppKey();
    }

    private InetSocketAddress[] parseServerAddress(String serverListStr)
    {
        String addressStrs[] = serverListStr.split("[;]");
        InetSocketAddress ret[] = new InetSocketAddress[addressStrs.length];
        for(int i = 0; i < addressStrs.length; i++)
            if(StringUtils.isNotBlank(addressStrs[i]))
            {
                String addressStr[] = addressStrs[i].split("[:]");
                String address = addressStr[0].trim();
                int port = Integer.parseInt(addressStr[1].trim());
                ret[i] = new InetSocketAddress(address, port);
            }

        return ret;
    }

    private static Logger LOGGER;
    private Buffer sendBuffer;
    private SynClientConfig clientConfig;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }
}
