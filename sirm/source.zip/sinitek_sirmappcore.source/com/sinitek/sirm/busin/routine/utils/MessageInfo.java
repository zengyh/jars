// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageInfo.java

package com.sinitek.sirm.busin.routine.utils;

import java.util.ArrayList;
import java.util.List;

public class MessageInfo
{

    public MessageInfo()
    {
        senderEmail = null;
        receiversEmail = new String[0];
        copiesEmail = null;
        title = null;
        content = null;
        attachments = new ArrayList();
        fileNames = new ArrayList();
        separate = true;
    }

    public String getSenderEmail()
    {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail)
    {
        this.senderEmail = senderEmail;
    }

    public String[] getReceiversEmail()
    {
        return receiversEmail;
    }

    public void setReceiversEmail(String receiversEmail[])
    {
        this.receiversEmail = receiversEmail;
    }

    public String getCopiesEmail()
    {
        return copiesEmail;
    }

    public void setCopiesEmail(String copiesEmail)
    {
        this.copiesEmail = copiesEmail;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public List getAttachments()
    {
        return attachments;
    }

    public void setAttachments(List attachments)
    {
        this.attachments = attachments;
    }

    public List getFileNames()
    {
        return fileNames;
    }

    public void setFileNames(List fileNames)
    {
        this.fileNames = fileNames;
    }

    public boolean getSeparate()
    {
        return separate;
    }

    public void setSeparate(boolean separate)
    {
        this.separate = separate;
    }

    private String senderEmail;
    private String receiversEmail[];
    private String copiesEmail;
    private String title;
    private String content;
    private List attachments;
    private List fileNames;
    private boolean separate;
}
