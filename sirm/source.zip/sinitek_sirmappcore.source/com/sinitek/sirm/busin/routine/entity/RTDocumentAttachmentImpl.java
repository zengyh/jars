// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTDocumentAttachmentImpl.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.*;
import java.util.Date;

// Referenced classes of package com.sinitek.sirm.busin.routine.entity:
//            IRTDocumentAttachment

public class RTDocumentAttachmentImpl extends MetaObjectImpl
    implements IRTDocumentAttachment
{

    public RTDocumentAttachmentImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("RTDOCUMENTATTACHMENT"));
    }

    public RTDocumentAttachmentImpl(IEntity entity)
    {
        super(entity);
    }

    public String getAttachmentname()
    {
        return (String)get("attachmentname");
    }

    public void setAttachmentname(String value)
    {
        put("attachmentname", value);
    }

    public Integer getDocumentid()
    {
        return (Integer)get("documentid");
    }

    public void setDocumentid(Integer value)
    {
        put("documentid", value);
    }

    public Date getCreatetime()
    {
        return (Date)get("createtime");
    }

    public void setCreatetime(Date value)
    {
        put("createtime", value);
    }

    public String getDocumentVersion()
    {
        return (String)get("documentVersion");
    }

    public void setDocumentVersion(String value)
    {
        put("documentVersion", value);
    }

    public Integer getAttachmentid()
    {
        return (Integer)get("attachmentid");
    }

    public void setAttachmentid(Integer value)
    {
        put("attachmentid", value);
    }

    public String getAttachmentername()
    {
        return (String)get("attachmentername");
    }

    public void setAttachmentername(String value)
    {
        put("attachmentername", value);
    }

    public Integer getGroupid()
    {
        return (Integer)get("groupid");
    }

    public void setGroupid(Integer value)
    {
        put("groupid", value);
    }

    public String getRemarks()
    {
        return (String)get("remarks");
    }

    public void setRemarks(String value)
    {
        put("remarks", value);
    }

    public String getAttachmenter()
    {
        return (String)get("attachmenter");
    }

    public void setAttachmenter(String value)
    {
        put("attachmenter", value);
    }
}
