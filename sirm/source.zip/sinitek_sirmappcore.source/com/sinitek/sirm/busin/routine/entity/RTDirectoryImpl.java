// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTDirectoryImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            IRTDirectory

public class RTDirectoryImpl extends MetaObjectImpl
    implements IRTDirectory
{

    public RTDirectoryImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("RTDIRECTORY"));
    }

    public RTDirectoryImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getSort()
    {
        return (Integer)get("sort");
    }

    public void setSort(Integer value)
    {
        put("sort", value);
    }

    public String getDirectoryer()
    {
        return (String)get("directoryer");
    }

    public void setDirectoryer(String value)
    {
        put("directoryer", value);
    }

    public String getDirectoryname()
    {
        return (String)get("directoryname");
    }

    public void setDirectoryname(String value)
    {
        put("directoryname", value);
    }

    public Integer getParentid()
    {
        return (Integer)get("parentid");
    }

    public void setParentid(Integer value)
    {
        put("parentid", value);
    }

    public String getDirectorycontent()
    {
        return (String)get("directorycontent");
    }

    public void setDirectorycontent(String value)
    {
        put("directorycontent", value);
    }

    public Integer getDirectorytype()
    {
        return (Integer)get("directorytype");
    }

    public void setDirectorytype(Integer value)
    {
        put("directorytype", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }
}
