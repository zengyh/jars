// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttachmentImpl.java

package com.sinitek.sirm.common.attachment.entity;

import com.sinitek.base.metadb.*;

// Referenced classes of package com.sinitek.sirm.common.attachment.entity:
//            IAttachment

public class AttachmentImpl extends MetaObjectImpl
    implements IAttachment
{

    public AttachmentImpl()
    {
        this(MetaDBContextFactory.getInstance().getEntity("ATTACHMENT"));
    }

    public AttachmentImpl(IEntity entity)
    {
        super(entity);
    }

    public String getSourceEntity()
    {
        return (String)get("sourceEntity");
    }

    public void setSourceEntity(String value)
    {
        put("sourceEntity", value);
    }

    public Integer getType()
    {
        return (Integer)get("type");
    }

    public void setType(Integer value)
    {
        put("type", value);
    }

    public Integer getSourceId()
    {
        return (Integer)get("sourceId");
    }

    public void setSourceId(Integer value)
    {
        put("sourceId", value);
    }

    public String getName()
    {
        return (String)get("name");
    }

    public void setName(String value)
    {
        put("name", value);
    }

    public Double getContentSize()
    {
        return (Double)get("contentSize");
    }

    public void setContentSize(Double value)
    {
        put("contentSize", value);
    }

    public Integer getPageCount()
    {
        return (Integer)get("pageCount");
    }

    public void setPageCount(Integer value)
    {
        put("pageCount", value);
    }

    public Integer getConvertStatus()
    {
        return (Integer)get("convertStatus");
    }

    public void setConvertStatus(Integer value)
    {
        put("convertStatus", value);
    }

    public String getDigest()
    {
        return (String)get("digest");
    }

    public void setDigest(String value)
    {
        put("digest", value);
    }

    public Integer getStoreType()
    {
        return (Integer)get("storeType");
    }

    public void setStoreType(Integer value)
    {
        put("storeType", value);
    }

    public String getStorePath()
    {
        return (String)get("storePath");
    }

    public void setStorePath(String value)
    {
        put("storePath", value);
    }

    public String getFileType()
    {
        return (String)get("fileType");
    }

    public void setFileType(String value)
    {
        put("fileType", value);
    }

    public IStreamValue getContent()
    {
        return (IStreamValue)get("content");
    }

    public void setContent(IStreamValue value)
    {
        put("content", value);
    }

    public Integer getConvertResult()
    {
        return (Integer)get("convertResult");
    }

    public void setConvertResult(Integer value)
    {
        put("convertResult", value);
    }

    public Integer getSendFlag()
    {
        return (Integer)get("sendFlag");
    }

    public void setSendFlag(Integer value)
    {
        put("sendFlag", value);
    }

    public Integer getConvertFlag()
    {
        return (Integer)get("convertFlag");
    }

    public void setConvertFlag(Integer value)
    {
        put("convertFlag", value);
    }

    public Integer getConvertId()
    {
        return (Integer)get("convertId");
    }

    public void setConvertId(Integer value)
    {
        put("convertId", value);
    }
}
