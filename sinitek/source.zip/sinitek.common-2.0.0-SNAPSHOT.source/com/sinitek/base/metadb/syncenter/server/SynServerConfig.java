// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SynServerConfig.java

package com.sinitek.base.metadb.syncenter.server;

import com.sinitek.base.common.SinitekDES;
import com.sinitek.base.enumsupport.EnumException;
import com.sinitek.base.enumsupport.EnumItemContext;
import com.sinitek.base.metadb.MetaDBLoggerFactory;
import com.sinitek.base.metadb.syncenter.common.*;
import java.io.*;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.metadb.syncenter.server:
//            SynCenterServerException

public class SynServerConfig
{

    public SynServerConfig()
    {
    }

    public static SynServerConfig loadConfig(String cfgFile)
        throws SynCenterException
    {
        File _cfgFile;
        InputStream inputStream;
        Properties cfgProp;
        _cfgFile = new File(cfgFile);
        if(!_cfgFile.exists())
            throw new SynCenterServerException("0001", new Object[] {
                (new StringBuilder()).append("\u540C\u6B65\u4E2D\u5FC3\u670D\u52A1\u5668\u7AEF\u914D\u7F6E\u6587\u4EF6[").append(cfgFile).append("]\u4E0D\u5B58\u5728").toString()
            });
        LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u8BFB\u53D6\u914D\u7F6E\u6587\u4EF6[").append(cfgFile).append("]").toString());
        inputStream = null;
        cfgProp = new Properties();
        try
        {
            inputStream = new FileInputStream(_cfgFile);
            cfgProp.load(inputStream);
        }
        catch(Exception ex)
        {
            throw new SynCenterServerException("0001", ex, new Object[] {
                (new StringBuilder()).append("\u8BFB\u53D6\u914D\u7F6E\u6587\u4EF6[").append(cfgFile).append("]\u5931\u8D25").toString()
            });
        }
        if(inputStream != null)
            try
            {
                inputStream.close();
            }
            catch(IOException e)
            {
                LOGGER.warn((new StringBuilder()).append("\u5173\u95ED\u914D\u7F6E\u6587\u4EF6[").append(cfgFile).append("]\u5931\u8D25").toString(), e);
            }
        break MISSING_BLOCK_LABEL_254;
        Exception exception;
        exception;
        if(inputStream != null)
            try
            {
                inputStream.close();
            }
            catch(IOException e)
            {
                LOGGER.warn((new StringBuilder()).append("\u5173\u95ED\u914D\u7F6E\u6587\u4EF6[").append(cfgFile).append("]\u5931\u8D25").toString(), e);
            }
        throw exception;
        SynServerConfig ret = new SynServerConfig();
        String _listenAddress = cfgProp.getProperty("synserver.listenaddress", "");
        LOGGER.debug((new StringBuilder()).append("\u914D\u7F6E\u7684\u76D1\u542CIP\u5730\u5740\uFF1A[").append(_listenAddress).append("]").toString());
        ret.listenIpAddress = _listenAddress;
        String _listenPort = cfgProp.getProperty("synserver.listenport", "");
        if(StringUtils.isBlank(_listenPort))
            throw new SynCenterServerException("0001", new Object[] {
                "\u6CA1\u6709\u914D\u7F6Esynserver.listenport\u53C2\u6570\uFF0C\u8BE5\u53C2\u6570\u8868\u793A\u76D1\u542C\u7AEF\u53E3"
            });
        if(!StringUtils.isNumeric(_listenPort) || Integer.parseInt(_listenPort) > 65535 || Integer.parseInt(_listenPort) < 1)
            throw new SynCenterServerException("0001", new Object[] {
                (new StringBuilder()).append("\u914D\u7F6E\u7684\u76D1\u542C\u7AEF\u53E3[").append(_listenPort).append("]\u503C\u975E\u6CD5").toString()
            });
        ret.listenPort = Integer.parseInt(_listenPort);
        LOGGER.debug((new StringBuilder()).append("\u914D\u7F6E\u7684\u76D1\u542C\u7AEF\u53E3\uFF1A[").append(_listenPort).append("]").toString());
        String _dbConnType = cfgProp.getProperty("synserver.dbconnecttype", null);
        if(StringUtils.isBlank(_dbConnType))
            throw new SynCenterServerException("0001", new Object[] {
                "\u6CA1\u6709\u914D\u7F6Esynserver.dbconnecttype\uFF0C\u8BE5\u53C2\u6570\u8868\u793A\u6570\u636E\u5E93\u8FDE\u63A5\u6A21\u5F0F"
            });
        try
        {
            ret.databaseConnectType = (DBConnectType)EnumItemContext.getInstance().getEnumItem(com/sinitek/base/metadb/syncenter/common/DBConnectType, _dbConnType);
        }
        catch(EnumException ee)
        {
            throw new SynCenterServerException("0001", ee, new Object[] {
                (new StringBuilder()).append("\u914D\u7F6E\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u65B9\u5F0Fsynserver.dbconnecttype\u503C[").append(_dbConnType).append("]\u4E0D\u5408\u6CD5").toString()
            });
        }
        LOGGER.debug((new StringBuilder()).append("\u914D\u7F6E\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u6A21\u5F0F\uFF1A[").append(ret.databaseConnectType.getEnumItemInfo()).append("]").toString());
        DBConnectionFactory _factory = new DBConnectionFactory();
        if(ret.databaseConnectType == DBConnectType.EXT_DATASOURCE)
        {
            String _dsName = cfgProp.getProperty("synserver.extdatasourcename", "");
            if(StringUtils.isBlank(_dsName))
                throw new SynCenterServerException("0001", new Object[] {
                    "\u6CA1\u6709\u914D\u7F6Esynserver.extdatasourcename\uFF0C\u8BE5\u53C2\u6570\u5728\u4F7F\u7528\u5916\u90E8\u6570\u636E\u6E90\u6A21\u5F0F\u4E0B\u5FC5\u987B\uFF0C\u8868\u793A\u5916\u90E8\u6570\u636E\u6E90\u540D\u79F0"
                });
            LOGGER.debug((new StringBuilder()).append("\u914D\u7F6E\u7684\u5916\u90E8\u6570\u636E\u6E90\u540D\u79F0\u4E3A\uFF1A[").append(_dsName).append("]").toString());
            _factory.setDbConnectType(ret.databaseConnectType);
            _factory.setExtDataSourceName(_dsName);
        } else
        if(ret.databaseConnectType == DBConnectType.DIRECT_CONN)
        {
            String driverClassName = cfgProp.getProperty("synserver.driverclassname", "");
            if(StringUtils.isBlank(driverClassName))
                throw new SynCenterServerException("0001", new Object[] {
                    "\u6CA1\u6709\u914D\u7F6Esynserver.driverclassname\uFF0C\u8BE5\u53C2\u6570\u5728\u4F7F\u7528\u672C\u5730\u914D\u7F6E\u76F4\u8FDE\u6A21\u5F0F\u4E0B\u5FC5\u987B\uFF0C\u8868\u793A\u6570\u636E\u5E93\u9A71\u52A8\u5668\u7C7B\u540D"
                });
            LOGGER.debug((new StringBuilder()).append("\u914D\u7F6E\u7684\u6570\u636E\u5E93\u9A71\u52A8\u5668\u7C7B\u540D\u4E3A\uFF1A[").append(driverClassName).append("]").toString());
            String urlString = cfgProp.getProperty("synserver.url", "");
            if(StringUtils.isBlank(urlString))
                throw new SynCenterServerException("0001", new Object[] {
                    "\u6CA1\u6709\u914D\u7F6Esynserver.url\uFF0C\u8BE5\u53C2\u6570\u5728\u4F7F\u7528\u672C\u5730\u914D\u7F6E\u76F4\u8FDE\u6A21\u5F0F\u4E0B\u5FC5\u987B\uFF0C\u8868\u793A\u6570\u636E\u5E93\u8FDE\u63A5\u5B57\u7B26\u4E32"
                });
            LOGGER.debug((new StringBuilder()).append("\u914D\u7F6E\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u5B57\u7B26\u4E32\u4E3A\uFF1A[").append(urlString).append("]").toString());
            String userName = cfgProp.getProperty("synserver.databaseuser", "");
            if(StringUtils.isBlank(userName))
                throw new SynCenterServerException("0001", new Object[] {
                    "\u6CA1\u6709\u914D\u7F6Esynserver.databaseuser\uFF0C\u8BE5\u53C2\u6570\u5728\u4F7F\u7528\u672C\u5730\u914D\u7F6E\u76F4\u8FDE\u6A21\u5F0F\u4E0B\u5FC5\u987B\uFF0C\u8868\u793A\u6570\u636E\u5E93\u7528\u6237\u540D"
                });
            LOGGER.debug((new StringBuilder()).append("\u914D\u7F6E\u7684\u6570\u636E\u5E93\u7528\u6237\u540D\u4E3A\uFF1A[").append(userName).append("]").toString());
            String password = cfgProp.getProperty("synserver.databasepassword", "");
            if(StringUtils.isBlank(password))
                throw new SynCenterServerException("0001", new Object[] {
                    "\u6CA1\u6709\u914D\u7F6Esynserver.databasepassword\uFF0C\u8BE5\u53C2\u6570\u5728\u4F7F\u7528\u672C\u5730\u914D\u7F6E\u76F4\u8FDE\u6A21\u5F0F\u4E0B\u5FC5\u987B\uFF0C\u8868\u793A\u6570\u636E\u5E93\u7528\u6237\u5BC6\u7801"
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
            _factory.setDbConnectType(ret.databaseConnectType);
            _factory.setDriverClassName(driverClassName);
            _factory.setUserName(userName);
            _factory.setPassword(password);
            _factory.setUrl(urlString);
        } else
        {
            throw new SynCenterServerException("0001", new Object[] {
                (new StringBuilder()).append("\u4E0D\u652F\u6301\u7684\u6570\u636E\u5E93\u8FDE\u63A5\u65B9\u5F0F[").append(ret.getDatabaseConnectType()).append("]").toString()
            });
        }
        LOGGER.debug("\u51C6\u5907\u6D4B\u8BD5\u6570\u636E\u5E93\u8FDE\u63A5");
        try
        {
            _factory.testConnection();
            ret.connectionFactory = _factory;
        }
        catch(Exception ex)
        {
            throw new SynCenterServerException("0002", ex);
        }
        LOGGER.debug("\u6D4B\u8BD5\u6570\u636E\u5E93\u8FDE\u63A5\u6210\u529F");
        LOGGER.debug((new StringBuilder()).append("\u8BFB\u53D6\u914D\u7F6E\u6587\u4EF6[").append(cfgFile).append("]\u5B8C\u6210").toString());
        return ret;
    }

    public String getListenIpAddress()
    {
        return listenIpAddress;
    }

    public int getListenPort()
    {
        return listenPort;
    }

    public DBConnectType getDatabaseConnectType()
    {
        return databaseConnectType;
    }

    public DBConnectionFactory getConnectionFactory()
    {
        return connectionFactory;
    }

    private String listenIpAddress;
    private int listenPort;
    private DBConnectType databaseConnectType;
    private DBConnectionFactory connectionFactory;
    private static final Logger LOGGER;

    static 
    {
        LOGGER = MetaDBLoggerFactory.LOGGER_SYN;
    }
}
