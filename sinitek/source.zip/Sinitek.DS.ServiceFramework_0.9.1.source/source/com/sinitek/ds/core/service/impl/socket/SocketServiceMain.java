// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SocketServiceMain.java

package com.sinitek.ds.core.service.impl.socket;

import com.sinitek.base.control.server.ControlServer;
import com.sinitek.base.control.server.ServerConfig;

public class SocketServiceMain
{

    public SocketServiceMain()
    {
    }

    public static void main(String args[])
    {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        java.net.URL configUrl = loader.getResource("socketserver_config.xml");
        if(configUrl == null)
            configUrl = loader.getResource("com/sinitek/ds/core/service/impl/socket/socketserver_config.xml");
        ServerConfig config = new ServerConfig(configUrl);
        ControlServer server = new ControlServer();
        server.startServer(config);
    }

    private static final String DEFAULT_CONFIG_FILE = "com/sinitek/ds/core/service/impl/socket/socketserver_config.xml";
}
