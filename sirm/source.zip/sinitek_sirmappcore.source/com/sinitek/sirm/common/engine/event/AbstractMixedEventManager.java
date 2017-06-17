// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractMixedEventManager.java

package com.sinitek.sirm.common.engine.event;

import com.sinitek.sirm.common.engine.event.utils.ListenerManager;

// Referenced classes of package com.sinitek.sirm.common.engine.event:
//            EventManager

public abstract class AbstractMixedEventManager extends EventManager
{

    private EventManager getEventManager()
    {
        if(manager == null)
            synchronized(lock)
            {
                if(manager == null)
                    manager = EventManager.getInstance();
            }
        return manager;
    }

    protected AbstractMixedEventManager()
    {
    }

    protected abstract ListenerManager getSelfListenerMap();

    protected ListenerManager getListenerManager()
    {
        ListenerManager managerMap = EventManager.getInstance().getListenerManager();
        ListenerManager tempManager = new ListenerManager();
        tempManager.putListenerManager(managerMap);
        ListenerManager selfManager = getSelfListenerMap();
        if(selfManager != null)
            tempManager.putListenerManager(selfManager);
        return tempManager;
    }

    private static EventManager manager;
    private static final byte lock[] = new byte[0];

}
