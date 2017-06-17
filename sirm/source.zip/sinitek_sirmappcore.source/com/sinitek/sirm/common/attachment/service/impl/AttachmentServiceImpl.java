// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttachmentServiceImpl.java

package com.sinitek.sirm.common.attachment.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.IStreamValue;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.attachment.entity.*;
import com.sinitek.sirm.common.attachment.service.IAttachmentService;
import com.sinitek.sirm.common.exception.SirmAppException;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;
import org.apache.log4j.Logger;

public class AttachmentServiceImpl extends AbstractMetaDBContextSupport
    implements IAttachmentService
{

    public AttachmentServiceImpl()
    {
    }

    public void saveAttachment(IAttachment attachment)
    {
        if(attachment instanceof AttachmentImpl)
            (new AttachmentMO(attachment)).save();
        else
            attachment.save();
    }

    public void saveAttachmentList(int sourceId, String entityName, List attachmentList)
    {
        if(attachmentList != null && attachmentList.size() > 0)
        {
            IAttachment _attachment;
            for(Iterator i$ = attachmentList.iterator(); i$.hasNext(); saveAttachment(_attachment))
            {
                _attachment = (IAttachment)i$.next();
                _attachment.setSourceId(Integer.valueOf(sourceId));
                _attachment.setSourceEntity(entityName);
                _attachment.setStoreType(Integer.valueOf(0));
            }

        }
    }

    public List findAttachmentList(String sourceEntity, int sourceId)
    {
        List result = new ArrayList();
        String _sql = "sourceEntity=:sourceEntity and sourceId=:sourceId";
        Map _map = new HashMap();
        _map.put("sourceEntity", sourceEntity);
        _map.put("sourceId", Integer.valueOf(sourceId));
        List list = getMetaDBContext().query("ATTACHMENT", _sql, _map);
        IAttachment attachment;
        for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(new AttachmentMO(attachment)))
            attachment = (IAttachment)i$.next();

        return result;
    }

    public List findAttachmentList(String sourceEntity, int sourceId, int type)
    {
        List result = new ArrayList();
        String _sql = "sourceEntity=:sourceEntity and sourceId=:sourceId";
        Map _map = new HashMap();
        _map.put("sourceEntity", sourceEntity);
        _map.put("sourceId", Integer.valueOf(sourceId));
        if(type > 0)
        {
            _map.put("type", Integer.valueOf(type));
            _sql = (new StringBuilder()).append(_sql).append(" and type=:type").toString();
        }
        List list = getMetaDBContext().query("ATTACHMENT", _sql, _map);
        IAttachment attachment;
        for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(new AttachmentMO(attachment)))
            attachment = (IAttachment)i$.next();

        return result;
    }

    public List findAttachmentList(Map params)
    {
        List result = new ArrayList();
        Map sp = new HashMap();
        StringBuffer sb = new StringBuffer(" 1=1 ");
        String sourceentity = StringUtil.safeToString(params.get("sourceentity"), null);
        if(sourceentity != null)
        {
            sb.append(" and sourceEntity=:sourceEntity ");
            sp.put("sourceEntity", sourceentity);
        }
        Integer sourceid = NumberTool.safeToInteger(params.get("sourceid"), null);
        if(sourceid != null && sourceid.intValue() > 0)
        {
            sb.append(" and sourceId=:sourceId ");
            sp.put("sourceId", sourceid);
        }
        Integer type = NumberTool.safeToInteger(params.get("type"), null);
        if(type != null)
        {
            sb.append(" and type=:type ");
            sp.put("type", type);
        }
        List list = getMetaDBContext().query("ATTACHMENT", sb.toString(), sp);
        IAttachment attachment;
        for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(new AttachmentMO(attachment)))
            attachment = (IAttachment)i$.next();

        return result;
    }

    public IMetaDBQuery findAttachmentInfoList(String sourceEntity, int sourceId)
    {
        String sql = (new StringBuilder()).append("SELECT OBJID,SOURCEENTITY,SOURCEID,NAME,TYPE,\nCONTENTSIZE,PAGECOUNT,CONVERTSTATUS,DIGEST,STORETYPE,\nSTOREPATH,FILETYPE,CREATETIMESTAMP,\nUPDATETIMESTAMP,VERSION,ENTITYNAME FROM SIRM_ATTACHMENT WHERE SOURCEENTITY='").append(sourceEntity).append("' and sourceId=").append(sourceId).toString();
        return getMetaDBContext().createSqlQuery(sql);
    }

    public InputStream getAttachmentAsInputStreamById(int id)
        throws SQLException
    {
        InputStream result = null;
        IAttachment attachment = getAttachmentById(id);
        if(attachment != null)
            try
            {
                result = attachment.getContent().getInputStream();
            }
            catch(IOException ex)
            {
                LOGGER.error("getattachmentasinputstreambyid failed.", ex);
                throw new SirmAppException((new StringBuilder()).append("get attachment failed![id:").append(id).append("]").toString(), "0001");
            }
        return result;
    }

    public IAttachment getAttachmentById(int id)
    {
        IAttachment attachment = (IAttachment)getMetaDBContext().get("ATTACHMENT", id);
        return attachment != null ? new AttachmentMO(attachment) : null;
    }

    public Map getAttachmentMapById(int id)
    {
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery((new StringBuilder()).append("SELECT OBJID,NAME,FILETYPE,STORETYPE,STOREPATH  FROM SIRM_ATTACHMENT WHERE OBJID=").append(id).toString());
        List _list = _query.getResult();
        if(_list == null || _list.size() < 1)
            return null;
        else
            return (Map)_list.get(0);
    }

    public int getAttachmentCount(String sourceEntity, int sourceId)
    {
        IMetaDBQuery _query = findAttachmentInfoList(sourceEntity, sourceId);
        if(_query != null)
            return _query.getResultCount();
        else
            return 0;
    }

    public void removeAttachment(int id)
    {
        IAttachment _attachment = getAttachmentById(id);
        removeAttachment(_attachment);
    }

    public void removeAttachment(IAttachment attachment)
    {
        if(attachment instanceof AttachmentImpl)
            (new AttachmentMO(attachment)).remove();
        else
            attachment.remove();
    }

    public int removeAttachmentList(String ids, String sourceEntity, int sourceId)
    {
        if(ids != null && ids.trim().length() > 0)
            removeAttachmentList(ids.split(","));
        return getAttachmentCount(sourceEntity, sourceId);
    }

    public void removeAttachmentList(String ids[])
    {
        if(ids != null && ids.length > 0)
        {
            for(int i = 0; i < ids.length; i++)
            {
                String id = ids[i];
                if(id.trim().length() > 0)
                    removeAttachment(Integer.parseInt(id.trim()));
            }

        }
    }

    public void removeAttachmentTohis(int id)
    {
        IAttachment _attachment = getAttachmentById(id);
        if(_attachment != null)
        {
            _attachment.setSourceEntity((new StringBuilder()).append(_attachment.getSourceEntity()).append("_HIS").toString());
            _attachment.save();
        }
    }

    public void removeAttachment(String sourceEntity, int sourceId, String fileType)
    {
        Map params = new HashMap();
        params.put("sourceentity", sourceEntity);
        params.put("sourceid", Integer.valueOf(sourceId));
        params.put("filetype", fileType);
        List list = getMetaDBContext().query(com/sinitek/sirm/common/attachment/entity/IAttachment, "sourceentity=:sourceentity and sourceid=:sourceid and filetype=:filetype", params);
        Iterator i$ = list.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IAttachment attachment = (IAttachment)i$.next();
            if(attachment != null)
                attachment.remove();
        } while(true);
    }

    public IAttachment getAttachment(String sourceEntity, int sourceId, String fileType)
    {
        IAttachment attachment = null;
        Map params = new HashMap();
        params.put("sourceentity", sourceEntity);
        params.put("sourceid", Integer.valueOf(sourceId));
        params.put("filetype", fileType);
        List list = getMetaDBContext().query(com/sinitek/sirm/common/attachment/entity/IAttachment, "sourceentity=:sourceentity and sourceid=:sourceid and filetype=:filetype", params);
        if(!StringUtil.isEmpty(list))
            attachment = new AttachmentMO((IAttachment)list.get(0));
        return attachment;
    }

    private final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/common/attachment/service/impl/AttachmentServiceImpl);
}
