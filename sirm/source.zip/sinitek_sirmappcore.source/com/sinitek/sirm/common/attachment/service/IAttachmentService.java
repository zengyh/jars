// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IAttachmentService.java

package com.sinitek.sirm.common.attachment.service;

import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.attachment.entity.IAttachment;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IAttachmentService
{

    public abstract void saveAttachment(IAttachment iattachment);

    public abstract void saveAttachmentList(int i, String s, List list);

    public abstract List findAttachmentList(String s, int i);

    public abstract List findAttachmentList(String s, int i, int j);

    public abstract List findAttachmentList(Map map);

    public abstract IMetaDBQuery findAttachmentInfoList(String s, int i);

    public abstract InputStream getAttachmentAsInputStreamById(int i)
        throws SQLException;

    public abstract IAttachment getAttachmentById(int i);

    public abstract Map getAttachmentMapById(int i);

    public abstract void removeAttachment(int i);

    public abstract void removeAttachment(IAttachment iattachment);

    public abstract int removeAttachmentList(String s, String s1, int i);

    public abstract void removeAttachmentList(String as[]);

    public abstract int getAttachmentCount(String s, int i);

    public abstract void removeAttachmentTohis(int i);

    public abstract void removeAttachment(String s, int i, String s1);

    public abstract IAttachment getAttachment(String s, int i, String s1);
}
