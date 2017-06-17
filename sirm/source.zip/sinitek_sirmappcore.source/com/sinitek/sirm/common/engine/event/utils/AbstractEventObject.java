// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractEventObject.java

package com.sinitek.sirm.common.engine.event.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Referenced classes of package com.sinitek.sirm.common.engine.event.utils:
//            IEventTester

public abstract class AbstractEventObject
    implements Serializable
{
    public class SimpleEventTester
        implements IEventTester
    {

        public void writeMessage(Object message)
        {
            this.message.add((new StringBuilder()).append(message).append("").toString());
            if(isWriteLog)
                logger.debug(message);
        }

        public List getAllMessage()
        {
            return message;
        }

        public void setWriteLog(boolean isWrite)
        {
            isWriteLog = isWrite;
        }

        public void setLogClass(Class clazz)
        {
            logger = LogFactory.getLog(clazz);
        }

        private List message;
        private Log logger;
        private boolean isWriteLog;
        final AbstractEventObject this$0;

        public SimpleEventTester()
        {
            this$0 = AbstractEventObject.this;
            super();
            message = new FastList();
            logger = LogFactory.getLog(com/sinitek/sirm/common/engine/event/utils/AbstractEventObject$SimpleEventTester);
            isWriteLog = true;
        }
    }


    public AbstractEventObject(Object source)
    {
        this.source = null;
        eventCode = null;
        dataMap = new FastMap();
        this.source = source;
    }

    public AbstractEventObject()
    {
        source = null;
        eventCode = null;
        dataMap = new FastMap();
    }

    public void bindData(Object key, Object value)
    {
        dataMap.put(key, value);
    }

    public Object getData(Object key)
    {
        return dataMap.get(key);
    }

    public Map getAllData()
    {
        return new FastMap(dataMap);
    }

    public IEventTester getEventTester()
    {
        if(tester == null)
            tester = new SimpleEventTester();
        return tester;
    }

    public void setEventTester(IEventTester tester)
    {
        this.tester = tester;
    }

    public Object getSource()
    {
        return source;
    }

    public void setSource(Object source)
    {
        this.source = source;
    }

    public String getEventCode()
    {
        return eventCode;
    }

    public void setEventCode(String eventCode)
    {
        this.eventCode = eventCode;
    }

    protected Object source;
    private String eventCode;
    private FastMap dataMap;
    private IEventTester tester;
}
