// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageSendContext.java

package com.sinitek.sirm.busin.routine.utils;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.sinitek.sirm.busin.routine.utils:
//            MessageContext

public class MessageSendContext extends MessageContext
{

    public MessageSendContext()
    {
        title = null;
        content = null;
        receivers = new ArrayList();
        operateurl = null;
        directloginflag = 1;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List getReceivers()
    {
        return receivers;
    }

    public void setReceivers(List receivers)
    {
        this.receivers = receivers;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getOperateurl()
    {
        return operateurl;
    }

    public void setOperateurl(String operateurl)
    {
        this.operateurl = operateurl;
    }

    public int getDirectloginflag()
    {
        return directloginflag;
    }

    public void setDirectloginflag(int directloginflag)
    {
        this.directloginflag = directloginflag;
    }

    public int getTemplatetypeid()
    {
        return templatetypeid;
    }

    public void setTemplatetypeid(int templatetypeid)
    {
        this.templatetypeid = templatetypeid;
    }

    public int getTemplatetype()
    {
        return templatetype;
    }

    public void setTemplatetype(int templatetype)
    {
        this.templatetype = templatetype;
    }

    public int getExampleownerid()
    {
        return exampleownerid;
    }

    public void setExampleownerid(int exampleownerid)
    {
        this.exampleownerid = exampleownerid;
    }

    private String title;
    private String content;
    private List receivers;
    private String operateurl;
    private int directloginflag;
    private int exampleownerid;
    private int templatetypeid;
    private int templatetype;
}
