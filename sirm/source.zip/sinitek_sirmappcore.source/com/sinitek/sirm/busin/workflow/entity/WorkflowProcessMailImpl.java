// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessMailImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessMail

public class WorkflowProcessMailImpl extends MetaObjectImpl
    implements IWorkflowProcessMail
{

    public WorkflowProcessMailImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSMAIL"));
    }

    public WorkflowProcessMailImpl(IEntity entity)
    {
        super(entity);
    }

    public String getMailContext()
    {
        return (String)get("MailContext");
    }

    public void setMailContext(String value)
    {
        put("MailContext", value);
    }

    public Integer getMailGoto()
    {
        return (Integer)get("MailGoto");
    }

    public void setMailGoto(Integer value)
    {
        put("MailGoto", value);
    }

    public Integer getMailGotoId()
    {
        return (Integer)get("MailGotoId");
    }

    public void setMailGotoId(Integer value)
    {
        put("MailGotoId", value);
    }

    public Integer getProcessId()
    {
        return (Integer)get("ProcessId");
    }

    public void setProcessId(Integer value)
    {
        put("ProcessId", value);
    }

    public Integer getStepId()
    {
        return (Integer)get("StepId");
    }

    public void setStepId(Integer value)
    {
        put("StepId", value);
    }

    public String getMailTitle()
    {
        return (String)get("MailTitle");
    }

    public void setMailTitle(String value)
    {
        put("MailTitle", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }
}
