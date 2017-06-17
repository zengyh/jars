// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTDirectoryAppendImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            IRTDirectoryAppend

public class RTDirectoryAppendImpl extends MetaObjectImpl
    implements IRTDirectoryAppend
{

    public RTDirectoryAppendImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("RTDIRECTORYAPPEND"));
    }

    public RTDirectoryAppendImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getIssearch()
    {
        return (Integer)get("Issearch");
    }

    public void setIssearch(Integer value)
    {
        put("Issearch", value);
    }

    public Integer getAllowapplyautor()
    {
        return (Integer)get("Allowapplyautor");
    }

    public void setAllowapplyautor(Integer value)
    {
        put("Allowapplyautor", value);
    }

    public Integer getAllowsubapplyauthor()
    {
        return (Integer)get("Allowsubapplyauthor");
    }

    public void setAllowsubapplyauthor(Integer value)
    {
        put("Allowsubapplyauthor", value);
    }

    public Integer getWorkflowid()
    {
        return (Integer)get("Workflowid");
    }

    public void setWorkflowid(Integer value)
    {
        put("Workflowid", value);
    }

    public Integer getDirectoryid()
    {
        return (Integer)get("Directoryid");
    }

    public void setDirectoryid(Integer value)
    {
        put("Directoryid", value);
    }
}
