// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageRemindSender.java

package com.sinitek.sirm.busin.routine.utils;

import com.sinitek.sirm.busin.routine.entity.*;
import com.sinitek.sirm.busin.routine.service.ISendMessageService;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.busin.routine.utils:
//            MessageReceiver, MessageUtils, IMessageSender, MessageSendContext

public class MessageRemindSender
    implements IMessageSender
{

    public MessageRemindSender()
    {
    }

    public String getContent(String cont, Map map)
    {
        return StringUtil.replaceVariables(cont, map);
    }

    public static String getName(String val, Map map)
    {
        String b = val.substring(2, val.length() - 1);
        if(!"".equals(map.get(b)) && null != map.get(b))
            return (String)map.get(b);
        else
            return "";
    }

    public void send(MessageSendContext context)
    {
        String sendid = SettingUtils.getStringValue("COMMON", "ADMINUSER");
        Map map = context.getParams();
        String cont = context.getContent();
        String title = context.getTitle();
        try
        {
            cont = getContent(context.getContent(), context.getParams());
            title = getContent(context.getTitle(), context.getParams());
        }
        catch(NullPointerException ex)
        {
            LOGGER.error("\u53D1\u9001\u6D88\u606F\u53D8\u91CF\u66FF\u6362\u4E3A\u7A7A\uFF1A", ex);
        }
        catch(Exception ex)
        {
            LOGGER.error("\u53D1\u9001\u6D88\u606F\u53D8\u91CF\u9519\u8BEF\uFF1A", ex);
        }
        List mrlist = context.getReceivers();
        Iterator i$ = mrlist.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            MessageReceiver receiver = (MessageReceiver)i$.next();
            try
            {
                boolean isok = null != cont && !"".equals(cont) && null != title && !"".equals(title) && null != receiver.getEmpId() && !"".equals(receiver.getEmpId()) && isNumber(receiver.getEmpId());
                if(isok)
                {
                    ISendMessage sm = new SendMessageImpl();
                    sm.setSender(NumberTool.safeToInteger(sendid, Integer.valueOf(0)));
                    sm.setSendTime(new Date());
                    sm.setTimingFlag(Integer.valueOf(0));
                    sm.setTitle(title);
                    sm.setContent(cont);
                    sm.setReceiver(receiver.getEmpId());
                    sm.setRelapseFlag(Integer.valueOf(0));
                    sm.setSendStatus(Integer.valueOf(1));
                    sm.setSendMode("1");
                    List _receivemesssageList = new ArrayList();
                    IReceiveMessage _reveivemessage = new ReceiveMessageImpl();
                    _reveivemessage.setReceiver(Integer.valueOf(Integer.parseInt(receiver.getEmpId())));
                    _reveivemessage.setStatus(Integer.valueOf(0));
                    _reveivemessage.setSendMode(Integer.valueOf(1));
                    _receivemesssageList.add(_reveivemessage);
                    CommonServiceFactory.getSendMessageService().saveSendMessage(sm, new ArrayList(), new ArrayList(), _receivemesssageList);
                }
            }
            catch(Exception ex)
            {
                LOGGER.error("\u53D1\u9001\u7CFB\u7EDF\u6D88\u606F\u9519\u8BEF\uFF1A", ex);
            }
        } while(true);
    }

    public static boolean isNumber(String str)
    {
        return str.matches("[\\d]+");
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/routine/utils/MessageUtils);

}
