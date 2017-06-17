// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleEventTester.java

package com.sinitek.sirm.common.engine.event.utils;

import java.util.List;
import javolution.util.FastList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Referenced classes of package com.sinitek.sirm.common.engine.event.utils:
//            IEventTester

public class SimpleEventTester
    implements IEventTester
{

    public SimpleEventTester()
    {
        message = new FastList();
        logger = LogFactory.getLog(com/sinitek/sirm/common/engine/event/utils/SimpleEventTester);
        isWriteLog = true;
    }

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
}
