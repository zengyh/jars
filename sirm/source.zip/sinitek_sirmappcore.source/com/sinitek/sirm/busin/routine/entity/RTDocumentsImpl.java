// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTDocumentsImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            IRTDocuments

public class RTDocumentsImpl extends MetaObjectImpl
    implements IRTDocuments
{

    public RTDocumentsImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("RTDOCUMENTS"));
    }

    public RTDocumentsImpl(IEntity entity)
    {
        super(entity);
    }

    public Integer getDirectoryId()
    {
        return (Integer)get("directoryId");
    }

    public void setDirectoryId(Integer value)
    {
        put("directoryId", value);
    }

    public String getDocumenter()
    {
        return (String)get("documenter");
    }

    public void setDocumenter(String value)
    {
        put("documenter", value);
    }

    public String getDocumentName()
    {
        return (String)get("documentName");
    }

    public void setDocumentName(String value)
    {
        put("documentName", value);
    }

    public String getDocumentContent()
    {
        return (String)get("documentContent");
    }

    public void setDocumentContent(String value)
    {
        put("documentContent", value);
    }

    public Integer getStatus()
    {
        return (Integer)get("status");
    }

    public void setStatus(Integer value)
    {
        put("status", value);
    }

    public Date getCreatetime()
    {
        return (Date)get("createtime");
    }

    public void setCreatetime(Date value)
    {
        put("createtime", value);
    }
}
