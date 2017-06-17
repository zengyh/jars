// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IAttachment.java

package com.sinitek.sirm.common.attachment.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import com.sinitek.base.metadb.IStreamValue;

public interface IAttachment
    extends IMetaObjectImpl
{

    public abstract String getSourceEntity();

    public abstract void setSourceEntity(String s);

    public abstract Integer getType();

    public abstract void setType(Integer integer);

    public abstract Integer getSourceId();

    public abstract void setSourceId(Integer integer);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract Double getContentSize();

    public abstract void setContentSize(Double double1);

    public abstract Integer getPageCount();

    public abstract void setPageCount(Integer integer);

    public abstract Integer getConvertStatus();

    public abstract void setConvertStatus(Integer integer);

    public abstract String getDigest();

    public abstract void setDigest(String s);

    public abstract Integer getStoreType();

    public abstract void setStoreType(Integer integer);

    public abstract String getStorePath();

    public abstract void setStorePath(String s);

    public abstract String getFileType();

    public abstract void setFileType(String s);

    public abstract IStreamValue getContent();

    public abstract void setContent(IStreamValue istreamvalue);

    public abstract Integer getConvertResult();

    public abstract void setConvertResult(Integer integer);

    public abstract Integer getSendFlag();

    public abstract void setSendFlag(Integer integer);

    public abstract Integer getConvertFlag();

    public abstract void setConvertFlag(Integer integer);

    public abstract Integer getConvertId();

    public abstract void setConvertId(Integer integer);

    public static final String ENTITY_NAME = "ATTACHMENT";
}
