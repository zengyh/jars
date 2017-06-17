// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageTemplateImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            IMessageTemplate

public class MessageTemplateImpl extends MetaObjectImpl
    implements IMessageTemplate
{

    public MessageTemplateImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("MESSAGETEMPLATE"));
    }

    public MessageTemplateImpl(IEntity entity)
    {
        super(entity);
    }

    public String getCode()
    {
        return (String)get("Code");
    }

    public void setCode(String value)
    {
        put("Code", value);
    }

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }

    public String getContent()
    {
        return (String)get("Content");
    }

    public void setContent(String value)
    {
        put("Content", value);
    }

    public Integer getSendMode()
    {
        return (Integer)get("SendMode");
    }

    public void setSendMode(Integer value)
    {
        put("SendMode", value);
    }

    public String getTitle()
    {
        return (String)get("Title");
    }

    public void setTitle(String value)
    {
        put("Title", value);
    }

    public String getSmsContent()
    {
        return (String)get("SmsContent");
    }

    public void setSmsContent(String value)
    {
        put("SmsContent", value);
    }

    public String getRemindContent()
    {
        return (String)get("RemindContent");
    }

    public void setRemindContent(String value)
    {
        put("RemindContent", value);
    }

    public Integer getForceflag()
    {
        return (Integer)get("Forceflag");
    }

    public void setForceflag(Integer value)
    {
        put("Forceflag", value);
    }

    public Integer getCatagory()
    {
        return (Integer)get("Catagory");
    }

    public void setCatagory(Integer value)
    {
        put("Catagory", value);
    }

    public String getRemark()
    {
        return (String)get("Remark");
    }

    public void setRemark(String value)
    {
        put("Remark", value);
    }

    public Integer getProcesstype()
    {
        return (Integer)get("Processtype");
    }

    public void setProcesstype(Integer value)
    {
        put("Processtype", value);
    }

    public String getMobileContent()
    {
        return (String)get("MobileContent");
    }

    public void setMobileContent(String value)
    {
        put("MobileContent", value);
    }

    public Integer getTemplatetype()
    {
        return (Integer)get("Templatetype");
    }

    public void setTemplatetype(Integer value)
    {
        put("Templatetype", value);
    }
}
