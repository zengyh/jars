// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ControlClient.java

package com.sinitek.base.control.client;

import com.sinitek.base.control.*;

// Referenced classes of package com.sinitek.base.control.client:
//            ControlSender, ServerInfoConfig, ServerInfo

public abstract class ControlClient
{
    private static class ControlClientImpl extends ControlClient
    {

        public ControlClientImpl(String serverAddress, int serverPort)
        {
            this.serverAddress = serverAddress;
            this.serverPort = serverPort;
        }
    }


    public ControlClient()
    {
    }

    public IHandleResult sendControl(String name, IRequest request)
        throws HandleException
    {
        return ControlSender.send(serverAddress, serverPort, name, request);
    }

    public static ControlClient createClient(String serverName)
        throws HandleException
    {
        ServerInfo info = ServerInfoConfig.getInstance().getServerInfo(serverName);
        return createClient(info.getServerAddress(), info.getServerPort());
    }

    public static ControlClient createClient(String serverIp, int port)
    {
        return new ControlClientImpl(serverIp, port);
    }

    protected String serverAddress;
    protected int serverPort;
}
