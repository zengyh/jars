// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcess

public class WorkflowProcessImpl extends MetaObjectImpl
    implements IWorkflowProcess
{

    public WorkflowProcessImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESS"));
    }

    public WorkflowProcessImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getPhoneshow()
    {
        return (Integer)get("phoneshow");
    }

    public void setPhoneshow(Integer value)
    {
        put("phoneshow", value);
    }

    public Integer getClaimType()
    {
        return (Integer)get("claimType");
    }

    public void setClaimType(Integer value)
    {
        put("claimType", value);
    }

    public String getSysCode()
    {
        return (String)get("SysCode");
    }

    public void setSysCode(String value)
    {
        put("SysCode", value);
    }

    public Integer getSysVersion()
    {
        return (Integer)get("SysVersion");
    }

    public void setSysVersion(Integer value)
    {
        put("SysVersion", value);
    }

    public Integer getProcessType()
    {
        return (Integer)get("ProcessType");
    }

    public void setProcessType(Integer value)
    {
        put("ProcessType", value);
    }

    public String getProcessBrief()
    {
        return (String)get("ProcessBrief");
    }

    public void setProcessBrief(String value)
    {
        put("ProcessBrief", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("Status");
    }

    public void setStatus(Integer value)
    {
        put("Status", value);
    }

    public String getName()
    {
        return (String)get("Name");
    }

    public void setName(String value)
    {
        put("Name", value);
    }

    public Integer getSort()
    {
        return (Integer)get("Sort");
    }

    public void setSort(Integer value)
    {
        put("Sort", value);
    }

    public Integer getProcessVersion()
    {
        return (Integer)get("ProcessVersion");
    }

    public void setProcessVersion(Integer value)
    {
        put("ProcessVersion", value);
    }

    public String getProcessCode()
    {
        return (String)get("ProcessCode");
    }

    public void setProcessCode(String value)
    {
        put("ProcessCode", value);
    }

    public String getSpecialMark()
    {
        return (String)get("SpecialMark");
    }

    public void setSpecialMark(String value)
    {
        put("SpecialMark", value);
    }
}
