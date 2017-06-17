// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageContext.java

package com.sinitek.sirm.busin.routine.utils;

import java.util.*;

public class MessageContext
{

    public MessageContext()
    {
        code = null;
        receivers = new ArrayList();
        params = new HashMap();
        mailFrom = null;
        attachments = new ArrayList();
        sendId = null;
    }

    public String getSendId()
    {
        return sendId;
    }

    public void setSendId(String sendId)
    {
        this.sendId = sendId;
    }

    public void addParam(String name, Object value)
    {
        params.put(name, value);
    }

    public void setParams(Map params)
    {
        this.params = params;
    }

    public Object getParam(String name)
    {
        return params.get(name);
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public List getReceivers()
    {
        return receivers;
    }

    public void setReceivers(List receivers)
    {
        this.receivers = receivers;
    }

    public Map getParams()
    {
        return params;
    }

    public List getAttachments()
    {
        return attachments;
    }

    public void setAttachments(List attachments)
    {
        this.attachments = attachments;
    }

    public String getMailFrom()
    {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom)
    {
        this.mailFrom = mailFrom;
    }

    private String code;
    private List receivers;
    private Map params;
    private String mailFrom;
    private List attachments;
    private String sendId;
}
