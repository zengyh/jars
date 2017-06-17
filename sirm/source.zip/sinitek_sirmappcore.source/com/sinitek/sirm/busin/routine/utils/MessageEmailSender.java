// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageEmailSender.java

package com.sinitek.sirm.busin.routine.utils;

import com.sinitek.sirm.common.setting.utils.SettingUtils;
import com.sinitek.sirm.common.utils.MailSender;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.sinitek.sirm.busin.routine.utils:
//            MessageAttachment, MessageReceiver, MessageUtils, IMessageSender, 
//            MessageSendContext

public class MessageEmailSender
    implements IMessageSender
{

    public MessageEmailSender()
    {
    }

    public void send(MessageSendContext context)
    {
        String host = SettingUtils.getStringValue("COMMON", "MAIL_SMTPSERVER");
        String username = SettingUtils.getStringValue("COMMON", "MAIL_SMTPUSER");
        String password = SettingUtils.getStringValue("COMMON", "MAIL_SMTPPWD");
        String sysmail = SettingUtils.getStringValue("COMMON", "MAIL_FROMSYS");
        if(context.getMailFrom() != null)
            sysmail = context.getMailFrom();
        MailSender _mailSender = new MailSender();
        _mailSender.setSmtpServer(host);
        _mailSender.setSmtpUser(username);
        _mailSender.setSmtpPassword(password);
        String cont = context.getContent();
        String title = context.getTitle();
        try
        {
            cont = getContent(context.getContent(), context.getParams());
            title = getContent(context.getTitle(), context.getParams());
        }
        catch(NullPointerException ex)
        {
            LOGGER.error("\u53D1\u9001\u90AE\u4EF6\u53D8\u91CF\u66FF\u6362\u4E3A\u7A7A\uFF1A", ex);
        }
        catch(Exception ex)
        {
            LOGGER.error("\u53D1\u9001\u90AE\u4EF6\u53D8\u91CF\u9519\u8BEF\uFF1A", ex);
        }
        List list = context.getAttachments();
        List fileAttachments = new ArrayList();
        List sourceFileName = new ArrayList();
        MessageAttachment attachmentobj;
        for(Iterator i$ = list.iterator(); i$.hasNext(); fileAttachments.add(attachmentobj.getFileName()))
        {
            attachmentobj = (MessageAttachment)i$.next();
            sourceFileName.add(attachmentobj.getDisplayName());
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
                if(StringUtils.isNotBlank(receiver.getEmail()))
                    _mailSender.sendMail(sysmail, receiver.getEmail(), title, cont, fileAttachments, sourceFileName);
            }
            catch(Exception e)
            {
                LOGGER.error("\u6267\u884C\u53D1\u9001\u90AE\u4EF6\u65B9\u6CD5\u65F6\u9519\u8BEF\uFF1A", e);
            }
        } while(true);
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

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/busin/routine/utils/MessageUtils);

}
