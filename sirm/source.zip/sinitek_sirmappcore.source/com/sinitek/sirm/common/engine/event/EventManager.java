// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventManager.java

package com.sinitek.sirm.common.engine.event;

import com.sinitek.sirm.common.engine.event.utils.AbstractEventListener;
import com.sinitek.sirm.common.engine.event.utils.AbstractEventObject;
import com.sinitek.sirm.common.engine.event.utils.IEventProcess;
import com.sinitek.sirm.common.engine.event.utils.ListenerManager;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EventManager
    implements Serializable
{
    static class testEventProcess3
        implements IEventProcess
    {

        public void process(AbstractEventObject e)
        {
            System.out.println("process2");
        }

        testEventProcess3()
        {
        }
    }

    static class testEventProcess2
        implements IEventProcess
    {

        public void process(AbstractEventObject e)
        {
            System.out.println("process1");
        }

        testEventProcess2()
        {
        }
    }

    static class testEventProcess
        implements IEventProcess
    {

        public void process(AbstractEventObject e)
        {
            System.out.println("process0");
        }

        testEventProcess()
        {
        }
    }


    protected EventManager()
    {
    }

    public static EventManager getInstance()
    {
        if(instance == null)
            synchronized(lock)
            {
                if(instance == null)
                    instance = new EventManager();
            }
        return instance;
    }

    public transient void addEventListener(AbstractEventListener listeners[])
    {
        getListenerManager().addEventListener(listeners);
    }

    public transient void removeEventListener(AbstractEventListener listeners[])
    {
        getListenerManager().removeEventListener(listeners);
    }

    public List dispatchEvent(String name, Object source)
    {
        return getListenerManager().dispatchEvent(name, source);
    }

    public List dispatchEvent(String name, Object source, AbstractEventObject eventObject)
    {
        return getListenerManager().dispatchEvent(name, source, eventObject);
    }

    public List dispatchEvent(String name, AbstractEventObject eventObject)
    {
        return dispatchEvent(name, this, eventObject);
    }

    public List dispatchEvent(String name)
    {
        return dispatchEvent(name, this);
    }

    protected ListenerManager getListenerManager()
    {
        if(listenerManager == null)
            synchronized(lock)
            {
                if(listenerManager == null)
                    listenerManager = new ListenerManager();
            }
        return listenerManager;
    }

    private static final Log logger = LogFactory.getLog(com/sinitek/sirm/common/engine/event/EventManager);
    private static EventManager instance;
    private ListenerManager listenerManager;
    private static final byte lock[] = new byte[0];

}
