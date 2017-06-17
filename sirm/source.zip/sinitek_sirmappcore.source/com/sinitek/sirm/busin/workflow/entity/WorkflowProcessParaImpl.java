// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkflowProcessParaImpl.java

package com.sinitek.sirm.busin.workflow.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.workflow.entity:
//            IWorkflowProcessPara

public class WorkflowProcessParaImpl extends MetaObjectImpl
    implements IWorkflowProcessPara
{

    public WorkflowProcessParaImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("WFPROCESSPARA"));
    }

    public WorkflowProcessParaImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getTypeId()
    {
        return (Integer)get("TypeId");
    }

    public void setTypeId(Integer value)
    {
        put("TypeId", value);
    }

    public String getCodeName()
    {
        return (String)get("CodeName");
    }

    public void setCodeName(String value)
    {
        put("CodeName", value);
    }

    public String getShowName()
    {
        return (String)get("ShowName");
    }

    public void setShowName(String value)
    {
        put("ShowName", value);
    }

    public Integer getSort()
    {
        return (Integer)get("Sort");
    }

    public void setSort(Integer value)
    {
        put("Sort", value);
    }

    public Integer getKind()
    {
        return (Integer)get("Kind");
    }

    public void setKind(Integer value)
    {
        put("Kind", value);
    }

    public String getBaseValue()
    {
        return (String)get("BaseValue");
    }

    public void setBaseValue(String value)
    {
        put("BaseValue", value);
    }

    public Integer getColumnId()
    {
        return (Integer)get("ColumnId");
    }

    public void setColumnId(Integer value)
    {
        put("ColumnId", value);
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
