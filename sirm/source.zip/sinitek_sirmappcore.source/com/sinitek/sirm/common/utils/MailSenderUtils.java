// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailSenderUtils.java

package com.sinitek.sirm.common.utils;

import com.sinitek.sirm.busin.routine.utils.MessageInfo;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import java.util.List;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.sinitek.sirm.common.utils:
//            MailSender

public class MailSenderUtils
{

    public MailSenderUtils()
    {
    }

    public static void sendMail(String to, String subject, String content, List attachments)
    {
        String fromSys = SettingUtils.getStringValue("COMMON", "MAIL_FROMSYS");
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setSenderEmail(fromSys);
        messageInfo.setReceiversEmail(to.split(","));
        messageInfo.setTitle(subject);
        messageInfo.setContent(content);
        messageInfo.setSeparate(true);
        messageInfo.setAttachments(attachments);
        sendMail(messageInfo);
    }

    public static void sendMail(String to, String subject, String content, List attachments, List filenames)
    {
        String fromSys = SettingUtils.getStringValue("COMMON", "MAIL_FROMSYS");
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setSenderEmail(fromSys);
        messageInfo.setReceiversEmail(to.split(","));
        messageInfo.setTitle(subject);
        messageInfo.setContent(content);
        messageInfo.setSeparate(true);
        messageInfo.setAttachments(attachments);
        messageInfo.setFileNames(filenames);
        sendMail(messageInfo);
    }

    public static void sendMail(String form, String to, String subject, String content, List attachments, List filenames)
    {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setSenderEmail(form);
        messageInfo.setReceiversEmail(to.split(","));
        messageInfo.setTitle(subject);
        messageInfo.setContent(content);
        messageInfo.setSeparate(true);
        messageInfo.setAttachments(attachments);
        messageInfo.setFileNames(filenames);
        sendMail(messageInfo);
    }

    public static void sendMail(MessageInfo messageInfo)
    {
        String host = SettingUtils.getStringValue("COMMON", "MAIL_SMTPSERVER");
        String username = SettingUtils.getStringValue("COMMON", "MAIL_SMTPUSER");
        String password = SettingUtils.getStringValue("COMMON", "MAIL_SMTPPWD");
        MailSender mailSender = new MailSender(host, username, password);
        if(StringUtils.isNotBlank(messageInfo.getSenderEmail()) && messageInfo.getReceiversEmail() != null)
            mailSender.sendMail(messageInfo);
    }
}
