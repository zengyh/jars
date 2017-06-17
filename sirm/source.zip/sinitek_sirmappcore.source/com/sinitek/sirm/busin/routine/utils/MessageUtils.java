// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageUtils.java

package com.sinitek.sirm.busin.routine.utils;

import com.sinitek.sirm.common.exception.SirmAppException;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.busin.routine.utils:
//            IMessageSender, MessageEmailSender

public class MessageUtils
{

    public MessageUtils()
    {
    }

    public static IMessageSender getMessageEmailSender()
    {
        IMessageSender result = null;
        String str = SettingUtils.getStringValue("COMMON", "MSG_SENDMODE_1");
        if(str != null)
            try
            {
                result = (IMessageSender)Class.forName(str).newInstance();
            }
            catch(Exception e)
            {
                LOGGER.error("\u5B9E\u4F8B\u5316\u90AE\u4EF6\u53D1\u9001\u63A5\u53E3\u51FA\u9519\uFF0C\u8BF7\u68C0\u67E5[COMMON],[MSG_SENDMODE_1]\u53C2\u6570", e);
            }
        if(result == null)
            result = new MessageEmailSender();
        return result;
    }

    public static IMessageSender getMessageSMSSender()
    {
        IMessageSender result = null;
        String str = SettingUtils.getStringValue("COMMON", "MSG_SENDMODE_2");
        if(str != null)
            try
            {
                result = (IMessageSender)Class.forName(str).newInstance();
            }
            catch(Exception e)
            {
                LOGGER.error("\u5B9E\u4F8B\u5316\u53D1\u9001\u77ED\u4FE1\u63A5\u53E3\u51FA\u9519\uFF0C\u8BF7\u68C0\u67E5[COMMON],[MSG_SENDMODE_2]\u53C2\u6570", e);
            }
        return result;
    }

    public static IMessageSender getMessageRemindeSender()
    {
        IMessageSender result = null;
        String str = SettingUtils.getStringValue("COMMON", "MSG_SENDMODE_4");
        if(str != null)
            try
            {
                result = (IMessageSender)Class.forName(str).newInstance();
            }
            catch(Exception e)
            {
                LOGGER.error("\u5B9E\u4F8B\u5316\u7CFB\u7EDF\u6D88\u606F\u63A5\u53E3\u51FA\u9519\uFF0C\u8BF7\u68C0\u67E5[COMMON],[MSG_SENDMODE_4]\u53C2\u6570", e);
            }
        return result;
    }

    public static IMessageSender getMessageMobileSender()
    {
        IMessageSender result = null;
        String str = SettingUtils.getStringValue("COMMON", "MSG_SENDMODE_5");
        if(StringUtils.isBlank(str))
            str = "com.sinitek.sirm.busin.routine.utils.MessageMobileSender";
        if(str != null)
            try
            {
                result = (IMessageSender)Class.forName(str).newInstance();
            }
            catch(Exception e)
            {
                LOGGER.error("\u5B9E\u4F8B\u5316\u7CFB\u7EDF\u6D88\u606F\u63A5\u53E3\u51FA\u9519\uFF0C\u8BF7\u68C0\u67E5[COMMON],[MSG_SENDMODE_5]\u53C2\u6570", e);
                throw new SirmAppException((new StringBuilder()).append("Get MessageSender Failed![").append(str).append("]").toString(), "0004");
            }
        return result;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/routine/utils/MessageUtils);

}
