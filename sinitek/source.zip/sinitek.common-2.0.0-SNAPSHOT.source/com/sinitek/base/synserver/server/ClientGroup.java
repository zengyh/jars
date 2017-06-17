// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientGroup.java

package com.sinitek.base.synserver.server;

import com.sinitek.base.synserver.DataPackUtil;
import com.sinitek.base.synserver.IDataPack;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.base.synserver.server:
//            ClientBean, ServerWorkThread

public class ClientGroup
{

    public ClientGroup()
    {
        clientMap = new Hashtable();
    }

    public synchronized void registClient(ClientBean clientBean, ServerWorkThread workThread)
    {
        clientMap.put(clientBean, workThread);
    }

    public synchronized void removeClient(ClientBean clientBean)
    {
        clientMap.remove(clientBean);
    }

    public synchronized void broadcastAppPack(IDataPack pack, ClientBean from)
    {
        List _temp = new ArrayList(clientMap.keySet());
        Iterator iter = _temp.iterator();
        do
        {
            if(!iter.hasNext())
                break;
            ClientBean clientBean = (ClientBean)iter.next();
            ServerWorkThread workThread = (ServerWorkThread)clientMap.get(clientBean);
            if(workThread != null && !clientBean.equals(from))
            {
                if(LOGGER.isDebugEnabled())
                    LOGGER.debug((new StringBuilder()).append("\u51C6\u5907\u5411[").append(clientBean.getClientIp()).append("]:[").append(clientBean.getClientPort()).append("]\u5E7F\u64AD\u6570\u636E\u5305\uFF0C\u7F16\u53F7[").append(pack.getPackCode()).append("]").toString());
                workThread.addSendPack(pack);
            }
        } while(true);
    }

    public void sendExit()
    {
        List _temp = new ArrayList(clientMap.keySet());
        Iterator iter = _temp.iterator();
        do
        {
            if(!iter.hasNext())
                break;
            ClientBean clientBean = (ClientBean)iter.next();
            ServerWorkThread workThread = (ServerWorkThread)clientMap.get(clientBean);
            if(workThread != null)
            {
                exitCount++;
                workThread.addSendPack(DataPackUtil.createExitPack());
            }
        } while(true);
    }

    public List getAllClient()
    {
        return new ArrayList(clientMap.keySet());
    }

    public synchronized void markExit(ClientBean bean)
    {
        if(LOGGER.isInfoEnabled())
            LOGGER.info((new StringBuilder()).append("\u9488\u5BF9").append(bean.getInfo()).append("\u5DF2\u7ECF\u7EC8\u6B62\u6570\u636E\u63A5\u6536").toString());
        exitCount--;
        notifyAll();
    }

    public synchronized void waitAllExit()
    {
        try
        {
            while(exitCount > 0) 
                wait();
        }
        catch(InterruptedException e)
        {
            LOGGER.warn("\u7B49\u5F85\u6240\u6709\u5BA2\u6237\u7AEF\u6536\u5230Goodbye\u4FE1\u53F7\u88AB\u6253\u65AD", e);
        }
    }

    private Map clientMap;
    private static final Logger LOGGER = Logger.getLogger(com/sinitek/base/synserver/server/ClientGroup);
    private int exitCount;

}
