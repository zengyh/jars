// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRTDocuments.java

package com.sinitek.sirm.busin.routine.entity;

import com.sinitek.base.metadb.IMetaObjectImpl;
import java.util.Date;

public interface IRTDocuments
    extends IMetaObjectImpl
{

    public abstract Integer getDirectoryId();

    public abstract void setDirectoryId(Integer integer);

    public abstract String getDocumenter();

    public abstract void setDocumenter(String s);

    public abstract String getDocumentName();

    public abstract void setDocumentName(String s);

    public abstract String getDocumentContent();

    public abstract void setDocumentContent(String s);

    public abstract Integer getStatus();

    public abstract void setStatus(Integer integer);

    public abstract Date getCreatetime();

    public abstract void setCreatetime(Date date);

    public static final String ENTITY_NAME = "RTDOCUMENTS";
}
