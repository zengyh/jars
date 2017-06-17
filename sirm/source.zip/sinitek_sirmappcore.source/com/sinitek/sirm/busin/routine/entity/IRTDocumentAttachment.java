// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRTDocumentAttachment.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IRTDocumentAttachment
    extends IMetaObjectImpl
{

    public abstract String getAttachmentname();

    public abstract void setAttachmentname(String s);

    public abstract Integer getDocumentid();

    public abstract void setDocumentid(Integer integer);

    public abstract Date getCreatetime();

    public abstract void setCreatetime(Date date);

    public abstract String getDocumentVersion();

    public abstract void setDocumentVersion(String s);

    public abstract Integer getAttachmentid();

    public abstract void setAttachmentid(Integer integer);

    public abstract String getAttachmentername();

    public abstract void setAttachmentername(String s);

    public abstract Integer getGroupid();

    public abstract void setGroupid(Integer integer);

    public abstract String getRemarks();

    public abstract void setRemarks(String s);

    public abstract String getAttachmenter();

    public abstract void setAttachmenter(String s);

    public static final String ENTITY_NAME = "RTDOCUMENTATTACHMENT";
}
