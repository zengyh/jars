// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerHookThread.java

package com.sinitek.base.synserver.server;


// Referenced classes of package com.sinitek.base.synserver.server:
//            SynServer

public class ServerHookThread extends Thread
{

    public ServerHookThread(SynServer server)
    {
        this.server = server;
    }

    public void run()
    {
        server.stopServer();
    }

    private SynServer server;
}
