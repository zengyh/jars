// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRTDocumentsService.java

package com.sinitek.sirm.busin.routine.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.busin.routine.entity.*;
import java.util.*;

public interface IRTDocumentsService
{

    public abstract List findAllDocuments(int i, int j, String s);

    public abstract IRTDocuments getDocumentById(int i);

    public abstract void saveDocument(IRTDocuments irtdocuments);

    public abstract IMetaDBQuery findAttachmentsById(int i, int j);

    public abstract IMetaDBQuery findAllAttachmentsById(int i);

    public abstract void saveDocumentAttachment(IRTDocumentAttachment irtdocumentattachment);

    public abstract IRTDocumentAttachment getDocumentAttachmentById(int i);

    public abstract IRTDocumentAttachment getDocumentAttachmentByDocumentId(int i);

    public abstract IRTDocumentAttachment getAttachmentOrderVersion(int i, int j);

    public abstract void removeDocumentAttachmentById(int i);

    public abstract IMetaDBQuery getChildNodeList(Integer integer, Integer integer1);

    public abstract IMetaDBQuery getChildFileList(Integer integer, Integer integer1);

    public abstract IMetaDBQuery getDocumentsByOrgid(String s);

    public abstract IMetaDBQuery getDocumentAttachmentsByOrgid(String s);

    public abstract IMetaDBQuery findAllDocuments2(int i, int j, Date date, Date date1);

    public abstract IMetaDBQuery findAllDocumentByOrgidAndDirid(String s, int i);

    public abstract List findDocumentsByDirectoryIds(Object aobj[]);

    public abstract IMetaDBQuery findAllCompanyDoc(Map map);

    public abstract void saveDocumentByPath(int i, String s, int j, List list, int k, String s1);

    public abstract IRTDirectory loadDirectoryByPath(String s, int i, int j, String s1);

    public abstract IRTDocumentAttachment getDocumentAttachmentDocIdVersion(int i, int j);

    public abstract IRTDocuments getDocumentByDirIdName(int i, String s);
}
