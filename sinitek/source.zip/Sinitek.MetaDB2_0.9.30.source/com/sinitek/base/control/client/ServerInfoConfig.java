// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerInfoConfig.java

package com.sinitek.base.control.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.control.client:
//            ServerInfo, ClientHandlerException

public class ServerInfoConfig
{

    private ServerInfoConfig()
    {
    }

    public static synchronized ServerInfoConfig getInstance()
    {
        if(instance == null)
        {
            instance = new ServerInfoConfig();
            instance.init();
        }
        return instance;
    }

    public ServerInfo getServerInfo(String serverName)
    {
        ServerInfo ret = (ServerInfo)serverInfoMap.get(serverName.toLowerCase());
        if(ret == null)
            throw new ClientHandlerException("0001", new Object[] {
                serverName
            });
        else
            return ret;
    }

    public void init()
    {
        InputStream is;
        Properties config;
        Exception exception;
        serverInfoMap = new HashedMap();
        URL configUrl = Thread.currentThread().getContextClassLoader().getResource("serverinfo.properties");
        if(configUrl == null)
            throw new ClientHandlerException("0002");
        is = null;
        config = new Properties();
        try
        {
            is = configUrl.openStream();
            config.load(is);
        }
        catch(Exception ex)
        {
            throw new ClientHandlerException("0003", ex);
        }
        finally
        {
            if(is == null) goto _L0; else goto _L0
        }
        if(is != null)
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                LOGGER.error("\u5173\u95ED\u914D\u7F6E\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
            }
        break MISSING_BLOCK_LABEL_125;
        try
        {
            is.close();
        }
        catch(IOException e)
        {
            LOGGER.error("\u5173\u95ED\u914D\u7F6E\u6587\u4EF6\u8F93\u5165\u6D41\u5931\u8D25", e);
        }
        throw exception;
        Enumeration en = config.propertyNames();
        do
        {
            if(!en.hasMoreElements())
                break;
            String propName = (String)en.nextElement();
            String _temp[] = propName.split("\\.");
            if(_temp.length == 2)
            {
                String name = _temp[0].trim().toLowerCase();
                ServerInfo info = (ServerInfo)serverInfoMap.get(name);
                if(info == null)
                {
                    info = new ServerInfo();
                    serverInfoMap.put(name, info);
                }
                String configName = _temp[1].trim();
                if("address".equalsIgnoreCase(configName))
                    info.setServerAddress(config.getProperty(propName));
                else
                if("port".equalsIgnoreCase(configName))
                    info.setServerPort(Integer.parseInt(config.getProperty(propName)));
            }
        } while(true);
        return;
    }

    private static ServerInfoConfig instance;
    private Map serverInfoMap;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/control/client/ServerInfoConfig);

}
