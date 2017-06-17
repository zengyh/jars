// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DBMixedEventManager.java

package com.sinitek.sirm.common.engine.event;

import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.engine.event.service.IEventManagerService;
import com.sinitek.sirm.common.engine.event.utils.AbstractEventListener;
import com.sinitek.sirm.common.engine.event.utils.ListenerManager;

// Referenced classes of package com.sinitek.sirm.common.engine.event:
//            AbstractMixedEventManager

public class DBMixedEventManager extends AbstractMixedEventManager
{

    public DBMixedEventManager()
    {
    }

    public static DBMixedEventManager getInstance()
    {
        if(instance == null)
            synchronized(lock)
            {
                if(instance == null)
                    instance = new DBMixedEventManager();
            }
        return instance;
    }

    protected ListenerManager getSelfListenerMap()
    {
        if(listenerCache == null)
            synchronized(lock)
            {
                if(listenerCache == null)
                    listenerCache = getManagerService().getListenerManager();
            }
        return listenerCache;
    }

    private static IEventManagerService getManagerService()
    {
        if(managerService == null)
            synchronized(lock)
            {
                if(managerService == null)
                    managerService = CommonServiceFactory.getEventManagerService();
            }
        return managerService;
    }

    public transient void addEventListener(AbstractEventListener listeners[])
    {
        listenerCache.addEventListener(listeners);
    }

    public transient void removeEventListener(AbstractEventListener listeners[])
    {
        listenerCache.removeEventListener(listeners);
    }

    public void refreshCache()
    {
        synchronized(lock)
        {
            listenerCache = null;
        }
    }

    private static IEventManagerService managerService;
    private ListenerManager listenerCache;
    private static final byte lock[] = new byte[0];
    private static DBMixedEventManager instance;

}
