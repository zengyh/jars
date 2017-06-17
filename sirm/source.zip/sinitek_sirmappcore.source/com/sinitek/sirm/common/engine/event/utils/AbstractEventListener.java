// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractEventListener.java

package com.sinitek.sirm.common.engine.event.utils;

import java.util.EventListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Referenced classes of package com.sinitek.sirm.common.engine.event.utils:
//            AbstractEventObject, IEventTester, IEventProcess

public abstract class AbstractEventListener
    implements EventListener
{

    public AbstractEventListener(String eventName, Object source)
    {
        eventProcess = null;
        if(source == null)
            throw new IllegalArgumentException("null eventName");
        if(eventName == null)
        {
            throw new IllegalArgumentException("null source");
        } else
        {
            this.source = source;
            this.eventName = eventName;
            return;
        }
    }

    public IEventProcess getEventProcess()
    {
        return eventProcess;
    }

    public void setEventProcess(IEventProcess eventProcess)
    {
        this.eventProcess = eventProcess;
    }

    public void dispatch(AbstractEventObject eventObject)
    {
        if(eventProcess != null)
            try
            {
                eventObject.getEventTester().setLogClass(eventProcess.getClass());
                eventProcess.process(eventObject);
            }
            catch(Exception e)
            {
                logger.error("Event process Fails:", e);
            }
    }

    public String getEventName()
    {
        return eventName;
    }

    public Object getSource()
    {
        return source;
    }

    public String getActionCode()
    {
        return actionCode;
    }

    public void setActionCode(String actionCode)
    {
        this.actionCode = actionCode;
    }

    public String getActionLocation()
    {
        return actionLocation;
    }

    public void setActionLocation(String actionLocation)
    {
        this.actionLocation = actionLocation;
    }

    public int getSyncFlag()
    {
        return syncFlag;
    }

    public void setSyncFlag(int syncFlag)
    {
        this.syncFlag = syncFlag;
    }

    private String eventName;
    private Object source;
    final byte lock[] = new byte[0];
    private IEventProcess eventProcess;
    private String actionCode;
    private String actionLocation;
    private int syncFlag;
    private static final Log logger = LogFactory.getLog(com/sinitek/sirm/common/engine/event/utils/AbstractEventListener);

}
