// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageSMSSender.java

package com.sinitek.sirm.busin.routine.utils;

import com.sinitek.sirm.common.utils.StringUtil;
import java.util.Map;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.busin.routine.utils:
//            IMessageSender, MessageSendContext

public class MessageSMSSender
    implements IMessageSender
{

    public MessageSMSSender()
    {
    }

    public void send(MessageSendContext context)
    {
        String SMSContent = getContent(context.getContent(), context.getParams());
        LOGGER.info((new StringBuilder()).append("SMS:").append(SMSContent).toString());
    }

    public String getContent(String cont, Map map)
    {
        return StringUtil.replaceVariables(cont, map);
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/routine/utils/MessageSMSSender);

}
