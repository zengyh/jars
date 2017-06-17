// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailSender.java

package com.sinitek.sirm.common.utils;

import com.sinitek.sirm.busin.routine.utils.MessageInfo;
import com.sinitek.sirm.common.exception.SirmAppException;
import com.sinitek.sirm.common.setting.utils.SettingUtils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;

public class MailSender
{

    public MailSender()
    {
        smtpPort = 25;
    }

    public MailSender(String smtpServer, String smtpUser, String smtpPassword)
    {
        smtpPort = 25;
        this.smtpServer = smtpServer;
        this.smtpUser = smtpUser;
        this.smtpPassword = smtpPassword;
        smtpPort = SettingUtils.getIntegerValue("COMMON", "MAIL_SMTPPORT", Integer.valueOf(25)).intValue();
    }

    public void sendMail(String from, String to, String subject, String content, List attachments)
    {
        sendMail(from, to, subject, content, attachments, null);
    }

    public void sendMail(String from, String to, String subject, String content, List attachments, List filenames)
    {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setSenderEmail(from);
        messageInfo.setReceiversEmail(to.split(","));
        messageInfo.setTitle(subject);
        messageInfo.setContent(content);
        messageInfo.setSeparate(true);
        messageInfo.setAttachments(attachments);
        messageInfo.setFileNames(filenames);
        sendMail(messageInfo);
    }

    public void sendMail(MessageInfo messageInfo)
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        if(!StringUtils.isEmpty(smtpUser) && !StringUtils.isEmpty(smtpPassword))
        {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.timeout", Integer.valueOf(0x2bf20));
            p.put("mail.smtp.connectiontimeout", Integer.valueOf(60000));
            mailSender.setPort(smtpPort);
            mailSender.setJavaMailProperties(p);
            mailSender.setUsername(smtpUser);
            mailSender.setPassword(smtpPassword);
        }
        mailSender.setDefaultEncoding("utf-8");
        mailSender.setHost(smtpServer);
        MimeMessage message = mailSender.createMimeMessage();
        String from = messageInfo.getSenderEmail();
        try
        {
            InternetAddress internetAddress = new InternetAddress();
            if(from.contains("<"))
                internetAddress.setPersonal(from.substring(0, from.indexOf("<")));
            message.setFrom(internetAddress);
        }
        catch(Exception e) { }
        MimeMailMessage mm = new MimeMailMessage(message);
        try
        {
            from = new String(new String(from.getBytes("UTF-8"), "ISO-8859-1"));
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        mm.setFrom(from);
        mm.setSubject(messageInfo.getTitle());
        if(messageInfo.getCopiesEmail() != null && !"".equals(messageInfo.getCopiesEmail()))
            try
            {
                InternetAddress addresses[] = InternetAddress.parse(messageInfo.getCopiesEmail());
                message.setRecipients(javax.mail.Message.RecipientType.CC, addresses);
            }
            catch(AddressException e)
            {
                LOGGER.error("copyAddress error", e);
                throw new SirmAppException("copyAddress error", "0002");
            }
            catch(MessagingException e)
            {
                LOGGER.error("copyReceiver error", e);
                throw new SirmAppException("copyReceiver error", "0002");
            }
        if(messageInfo.getSeparate())
        {
            String receiversEmail[] = messageInfo.getReceiversEmail();
            for(int j = 0; j < receiversEmail.length; j++)
            {
                mm.setTo(receiversEmail[j]);
                try
                {
                    Multipart mp = new MimeMultipart();
                    MimeBodyPart mbp1 = new MimeBodyPart();
                    mbp1.setContent(messageInfo.getContent(), "text/html;charset=utf-8");
                    mp.addBodyPart(mbp1);
                    if(messageInfo.getAttachments() != null && messageInfo.getAttachments().size() > 0)
                    {
                        int i = 0;
                        for(Iterator i$ = messageInfo.getAttachments().iterator(); i$.hasNext(); i++)
                        {
                            Object Attachment = (String)i$.next();
                            String fileAttachment = (new StringBuilder()).append(Attachment).append("").toString();
                            if(StringUtils.isBlank(fileAttachment))
                                continue;
                            File file = new File(fileAttachment);
                            MimeBodyPart mbp2 = new MimeBodyPart();
                            DataSource source = new FileDataSource(file);
                            mbp2.setDataHandler(new DataHandler(source));
                            String h = null;
                            if(messageInfo.getFileNames() != null && messageInfo.getFileNames().size() > 0)
                                h = MimeUtility.encodeText((String)messageInfo.getFileNames().get(i));
                            else
                                h = MimeUtility.encodeText(file.getName());
                            mbp2.setFileName(h);
                            mp.addBodyPart(mbp2);
                        }

                    }
                    message.setContent(mp);
                    mailSender.send(message);
                }
                catch(MessagingException ex)
                {
                    LOGGER.error("send mail failed!", ex);
                    throw new SirmAppException("send mail failed!", "0002");
                }
                catch(UnsupportedEncodingException ex)
                {
                    LOGGER.error("send mail failed!", ex);
                    throw new SirmAppException("send mail failed!", "0003");
                }
                catch(Exception ex)
                {
                    LOGGER.error("send mail failed!", ex);
                }
            }

        } else
        {
            mm.setTo(messageInfo.getReceiversEmail());
            try
            {
                Multipart mp = new MimeMultipart();
                MimeBodyPart mbp1 = new MimeBodyPart();
                mbp1.setContent(messageInfo.getContent(), "text/html;charset=utf-8");
                mp.addBodyPart(mbp1);
                if(messageInfo.getAttachments() != null && messageInfo.getAttachments().size() > 0)
                {
                    int i = 0;
                    for(Iterator i$ = messageInfo.getAttachments().iterator(); i$.hasNext(); i++)
                    {
                        Object Attachment = (String)i$.next();
                        String fileAttachment = (new StringBuilder()).append(Attachment).append("").toString();
                        if(StringUtils.isBlank(fileAttachment))
                            continue;
                        File file = new File(fileAttachment);
                        MimeBodyPart mbp2 = new MimeBodyPart();
                        DataSource source = new FileDataSource(file);
                        mbp2.setDataHandler(new DataHandler(source));
                        String h = null;
                        if(messageInfo.getFileNames() != null && messageInfo.getFileNames().size() > 0)
                            h = MimeUtility.encodeText((String)messageInfo.getFileNames().get(i));
                        else
                            h = MimeUtility.encodeText(file.getName());
                        mbp2.setFileName(h);
                        mp.addBodyPart(mbp2);
                    }

                }
                message.setContent(mp);
                mailSender.send(message);
            }
            catch(MessagingException ex)
            {
                LOGGER.error("send mail failed!", ex);
                throw new SirmAppException("send mail failed!", "0002");
            }
            catch(UnsupportedEncodingException ex)
            {
                LOGGER.error("send mail failed!", ex);
                throw new SirmAppException("send mail failed!", "0003");
            }
            catch(Exception ex)
            {
                LOGGER.error("send mail failed!", ex);
            }
        }
    }

    public String getSmtpServer()
    {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer)
    {
        this.smtpServer = smtpServer;
    }

    public String getSmtpUser()
    {
        return smtpUser;
    }

    public void setSmtpUser(String smtpUser)
    {
        this.smtpUser = smtpUser;
    }

    public String getSmtpPassword()
    {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword)
    {
        this.smtpPassword = smtpPassword;
    }

    public int getSmtpPort()
    {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort)
    {
        this.smtpPort = smtpPort;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/utils/MailSender);
    private String smtpServer;
    private String smtpUser;
    private String smtpPassword;
    private int smtpPort;

}
