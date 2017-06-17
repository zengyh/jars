// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTDocumentsServiceImpl.java

package com.sinitek.sirm.busin.routine.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.busin.routine.entity.*;
import com.sinitek.sirm.busin.routine.service.*;
import com.sinitek.sirm.common.CommonServiceFactory;
import com.sinitek.sirm.common.attachment.entity.IAttachment;
import com.sinitek.sirm.common.attachment.service.IAttachmentService;
import com.sinitek.sirm.common.um.RequestUser;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.common.utils.TimeUtil;
import com.sinitek.sirm.common.web.RequestContext;
import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.sirm.org.busin.service.IOrgService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import com.sinitek.sirm.org.busin.service.impl.RightServiceImpl;
import com.sinitek.spirit.right.client.RightFactory;
import com.sinitek.spirit.right.core.*;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.util.*;

public class RTDocumentsServiceImpl extends AbstractMetaDBContextSupport
    implements IRTDocumentsService
{

    public RTDocumentsServiceImpl()
    {
    }

    public List findAllDocuments(int status, int directoryId, String documenter)
    {
        StringBuilder sql = new StringBuilder();
        Map _map = new HashMap();
        sql.append("select * from rt_documents where status=:status and directoryId=:directoryId");
        _map.put("status", Integer.valueOf(status));
        _map.put("directoryId", Integer.valueOf(directoryId));
        if(documenter != null && !"".equals(documenter))
        {
            sql.append(" and documenter=:documenter");
            _map.put("documenter", documenter);
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        iMetaDBQuery.setParameters(_map);
        return iMetaDBQuery.getResult();
    }

    public IRTDocuments getDocumentById(int id)
    {
        return (IRTDocuments)getMetaDBContext().get("RTDOCUMENTS", id);
    }

    public void saveDocument(IRTDocuments documents)
    {
        documents.save();
    }

    public IMetaDBQuery findAttachmentsById(int documentid, int groupid)
    {
        Map _map = new HashMap();
        StringBuilder stringBuilder = new StringBuilder("select r.*,a.filetype from rt_documentattachment r  left join sirm_attachment a     on a.objid = r.attachmentid where 1=1");
        if(documentid != 0)
        {
            _map.put("documentid", Integer.valueOf(documentid));
            stringBuilder.append(" and r.documentid=:documentid");
        }
        if(groupid != 0)
        {
            _map.put("groupid", Integer.valueOf(groupid));
            stringBuilder.append(" and r.groupid=:groupid");
        }
        stringBuilder.append(" order by r.documentversion desc");
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(stringBuilder.toString());
        if(iMetaDBQuery != null)
            iMetaDBQuery.setParameters(_map);
        return iMetaDBQuery;
    }

    public IMetaDBQuery findAllAttachmentsById(int documentid)
    {
        Map _map = new HashMap();
        _map.put("documentid", Integer.valueOf(documentid));
        String sql = "select r.*,a.filetype from rt_documentattachment r  left join sirm_attachment a     on a.objid = r.attachmentid  where r.objid in(select max(objid) from rt_documentattachment where documentid=:documentid group by groupid)";
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql);
        if(iMetaDBQuery != null)
            iMetaDBQuery.setParameters(_map);
        return iMetaDBQuery;
    }

    public void saveDocumentAttachment(IRTDocumentAttachment attachment)
    {
        attachment.save();
    }

    public IRTDocumentAttachment getDocumentAttachmentById(int objid)
    {
        return (IRTDocumentAttachment)getMetaDBContext().get("RTDOCUMENTATTACHMENT", objid);
    }

    public IRTDocumentAttachment getDocumentAttachmentByDocumentId(int objid)
    {
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTDOCUMENTATTACHMENT", " documentid=:documentid ");
        iMetaDBQuery.setParameter("documentid", Integer.valueOf(objid));
        List list = iMetaDBQuery.getResult();
        IRTDocumentAttachment documentAttachment = null;
        if(list != null && list.size() > 0)
            documentAttachment = (IRTDocumentAttachment)list.get(0);
        return documentAttachment;
    }

    public IRTDocumentAttachment getAttachmentOrderVersion(int objid, int groupid)
    {
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTDOCUMENTATTACHMENT", " documentid=:documentid and groupid=:groupid");
        iMetaDBQuery.setParameter("documentid", Integer.valueOf(objid));
        iMetaDBQuery.setParameter("groupid", Integer.valueOf(groupid));
        iMetaDBQuery.setOrderBy(" DocumentVersion desc");
        List list = iMetaDBQuery.getResult();
        IRTDocumentAttachment documentAttachment = null;
        if(list != null && list.size() > 0)
            documentAttachment = (IRTDocumentAttachment)list.get(0);
        return documentAttachment;
    }

    public void removeDocumentAttachmentById(int objid)
    {
        IRTDocumentAttachment attachment = getDocumentAttachmentById(objid);
        if(attachment != null)
            attachment.delete();
    }

    public IMetaDBQuery getChildNodeList(Integer objid, Integer status)
    {
        IMetaDBQuery metaDBQuery = null;
        String sqlcmd = "select * from rt_directory where parentId=:parentId and status=:status";
        Map params = new HashMap();
        params.put("parentId", objid);
        params.put("status", status);
        metaDBQuery = getMetaDBContext().createSqlQuery(sqlcmd);
        metaDBQuery.setParameters(params);
        return metaDBQuery;
    }

    public IMetaDBQuery getChildFileList(Integer objid, Integer status)
    {
        IMetaDBQuery metaDBQuery = null;
        String sqlcmd = "    select * from rt_documents where directoryId=:directoryId and status=:status";
        Map params = new HashMap();
        params.put("directoryId", objid);
        params.put("status", status);
        metaDBQuery = getMetaDBContext().createSqlQuery(sqlcmd);
        metaDBQuery.setParameters(params);
        return metaDBQuery;
    }

    public IMetaDBQuery getDocumentsByOrgid(String orgid)
    {
        IMetaDBQuery metaDBQuery = null;
        String sqlcmd = "select *\n from rt_documents\n where (objid in\n       (select authid\n           from rt_documentauth\n          where authentity = 'RTDOCUMENTS'\n            and (sharinger = :orgid or orgid = :orgid)) or\n       directoryid in\n       (select authid\n           from rt_documentauth\n          where authentity = 'RTDIRECTORY'\n            and (sharinger = :orgid or orgid = :orgid)) or\n       documenter = :orgid)\n   and status = 1";
        Map params = new HashMap();
        params.put("orgid", orgid);
        metaDBQuery = getMetaDBContext().createSqlQuery(sqlcmd);
        metaDBQuery.setParameters(params);
        return metaDBQuery;
    }

    public IMetaDBQuery getDocumentAttachmentsByOrgid(String orgid)
    {
        IMetaDBQuery metaDBQuery = null;
        String sqlcmd = "select *\n from rt_documentattachment a,rt_documents d\nwhere a.documentid=d.objid and\n   (d.objid in (select authid\n                  from rt_documentauth\n                 where authentity = 'RTDOCUMENTS'\n                   and sharinger = :orgid or orgid=:orgid)\n   or d.directoryid in (select authid\n                  from rt_documentauth\n                 where authentity = 'RTDIRECTORY'\n                   and sharinger = :orgid or orgid=:orgid)\n   or attachmenter=:orgid) and  d.status=1";
        Map params = new HashMap();
        params.put("orgid", orgid);
        metaDBQuery = getMetaDBContext().createSqlQuery(sqlcmd);
        metaDBQuery.setParameters(params);
        return metaDBQuery;
    }

    public IMetaDBQuery findAllDocuments2(int status, int directoryId, Date startDate, Date endDate)
    {
        StringBuilder sql = new StringBuilder();
        Map _map = new HashMap();
        sql.append("select * from rt_documents where status=:status ");
        if(directoryId != 0)
        {
            sql.append(" and directoryId=:directoryId\n");
            _map.put("directoryId", Integer.valueOf(directoryId));
        }
        if(startDate != null)
        {
            sql.append(" and createtime >= :startDate");
            _map.put("startDate", startDate);
        }
        if(endDate != null)
        {
            sql.append(" and createtime <= :endDate");
            _map.put("endDate", endDate);
        }
        _map.put("status", Integer.valueOf(status));
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        iMetaDBQuery.setParameters(_map);
        return iMetaDBQuery;
    }

    public IMetaDBQuery findAllDocumentByOrgidAndDirid(String orgid, int dirid)
    {
        StringBuffer sb = new StringBuffer("");
        sb.append((new StringBuilder()).append("WITH RIGHTAUTH AS\n (SELECT RDA.OBJID,\n         RDA.AUTHID,\n         RDA.BEGINTIME,\n         RDA.ENDTIME,\n         RDA.AUTHENTITY,\n         RDA.ORGID,\n         RDA.SHARINGER,\n         SR.RIGHTTYPE\n    FROM RT_DOCUMENTAUTH RDA, SPRT_RIGHTAUTH SR\n   WHERE SR.AUTHORGEXP = RDA.OBJID\n     AND ((RDA.BEGINTIME IS NULL AND RDA.ENDTIME IS NULL) OR\n         (RDA.BEGINTIME <= SYSDATE AND RDA.ENDTIME IS NULL) OR\n         (RDA.BEGINTIME IS NULL AND RDA.ENDTIME >= SYSDATE) OR\n         (RDA.BEGINTIME <= SYSDATE AND RDA.ENDTIME >= SYSDATE))\n     AND RDA.SHARINGER = '").append(orgid).append("')\n").append("SELECT T.OBJID,T.DOCUMENTER,\n").append(" (SELECT SO.ORGNAME\n").append("          FROM SPRT_ORGOBJECT SO\n").append("         WHERE SO.ORGID = T.DOCUMENTER) UPLOADER,\n").append("       T.DOCUMENTNAME,\n").append("       T.DOCUMENTCONTENT,\n").append("       TO_CHAR(CREATETIME, 'yyyy-MM-dd') UPLOADERTIME\n").append("  FROM (SELECT RD.OBJID,\n").append("               RD.DOCUMENTER,\n").append("               RD.DOCUMENTNAME,\n").append("               RD.DOCUMENTCONTENT,\n").append("               RD.CREATETIME,\n").append("               (SELECT COUNT(1) FROM RIGHTAUTH A WHERE A.AUTHID = RD.OBJID  AND a.authentity = 'RTDOCUMENTS') COUNT1,\n").append("               (SELECT COUNT(1) FROM RIGHTAUTH A1 WHERE A1.AUTHID = RD.DIRECTORYID AND a1.authentity = 'RTDIRECTORY') COUNT2\n").append("          FROM RT_DOCUMENTS RD\n").append("         WHERE rd.status = 1 and RD.DIRECTORYID = ").append(dirid).append(") T\n").append(" WHERE (T.DOCUMENTER = '").append(orgid).append("' OR NVL(T.COUNT1, 0) > 0\n").append("      or  NVL(T.COUNT2, 0) > 0)\n").toString());
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        return _query;
    }

    public List findDocumentsByDirectoryIds(Object directoryIds[])
    {
        StringBuilder sql = new StringBuilder(" 1=1 ");
        Map param = new HashMap();
        if(directoryIds != null && directoryIds.length > 0)
            QueryUtils.buildIn("directoryId", directoryIds, sql, param);
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTDOCUMENTS", sql.toString());
        iMetaDBQuery.setParameters(param);
        iMetaDBQuery.setDefaultOrderBy("createtime desc");
        return iMetaDBQuery.getResult();
    }

    public IMetaDBQuery findAllCompanyDoc(Map map)
    {
        StringBuffer sb = new StringBuffer("");
        Map param = new HashMap();
        sb.append("SELECT T.*, ROWNUM\n  FROM (SELECT RDC.OBJID,\n               RDC.DOCUMENTNAME,\n               RDC.DOCUMENTCONTENT,\n               RDC.CREATETIME,\n               RDD.DIRECTORYNAME,\n               RDD.OBJID AS DIRID\n          FROM RT_DIRECTORY RDD, RT_DOCUMENTS RDC\n         WHERE RDD.OBJID = RDC.DIRECTORYID\n           AND RDD.DIRECTORYTYPE = 1\n           AND RDD.STATUS = 1\n           AND RDC.STATUS = 1\n         ORDER BY RDC.CREATETIME DESC) T\n WHERE 1 = 1");
        String dirname = StringUtil.safeToString(map.get("dirname"), "");
        if(!"".equals(dirname))
            sb.append((new StringBuilder()).append(" AND T.DIRECTORYNAME LIKE '%").append(dirname).append("%'").toString());
        String docname = StringUtil.safeToString(map.get("docname"), "");
        if(!"".equals(docname))
            sb.append((new StringBuilder()).append("AND T.DOCUMENTNAME LIKE '%").append(docname).append("%'").toString());
        String upstarttime = StringUtil.safeToString(map.get("upstarttime"), "");
        if(!"".equals(upstarttime))
        {
            sb.append(" AND TO_CHAR(T.CREATETIME, 'yyyy-MM-dd') >= :upstarttime");
            param.put("upstarttime", upstarttime);
        }
        String upendtime = StringUtil.safeToString(map.get("upendtime"), "");
        if(!"".equals(upendtime))
        {
            sb.append(" AND TO_CHAR(T.CREATETIME, 'yyyy-MM-dd') <= :upendtime");
            param.put("upendtime", upendtime);
        }
        String rootDirId = StringUtil.safeToString(map.get("rootDirId"), "");
        if(!"".equals(rootDirId))
        {
            sb.append(" AND T.DIRID in (SELECT d.objid  FROM rt_directory d  start with d.objid=:rootDirId connect by prior   d.objid=d.parentid  )");
            param.put("rootDirId", rootDirId);
        }
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        _query.setParameters(param);
        return _query;
    }

    public void saveDocumentByPath(int rootDirId, String path, int dirType, List attacuhments, int version, String inputId)
    {
        IOrgService orgService = OrgServiceFactory.getOrgService();
        IRTDirectory dir = loadDirectoryByPath(path, dirType, rootDirId, inputId);
        IAttachmentService attachmentService = CommonServiceFactory.getAttachmentService();
        OrgObject orgObject = orgService.getOrgObjectById(inputId);
        int dirId = 0;
        if(dir == null)
            dirId = rootDirId;
        else
            dirId = dir.getId();
        if(attacuhments.size() == 0)
            return;
        IRTDocuments doc = null;
        doc = getDocumentByDirIdName(dirId, ((IAttachment)attacuhments.get(0)).getName());
        int groupId = 0;
        if(doc == null)
        {
            doc = new RTDocumentsImpl();
            doc.setCreatetime(new Date());
        } else
        {
            IRTDocumentAttachment docAtt = getDocumentAttachmentDocIdVersion(doc.getId(), 1);
            groupId = docAtt.getAttachmentid().intValue();
        }
        doc.setDirectoryId(Integer.valueOf(dirId));
        doc.setDocumenter(inputId);
        doc.setDocumentName(((IAttachment)attacuhments.get(0)).getName());
        doc.setStatus(Integer.valueOf(1));
        saveDocument(doc);
        IRTDocumentAttachment documentAttachment;
        for(Iterator i$ = attacuhments.iterator(); i$.hasNext(); saveDocumentAttachment(documentAttachment))
        {
            IAttachment attachment = (IAttachment)i$.next();
            attachment.setSourceEntity("RTDOCUMENTS");
            attachment.setSourceId(Integer.valueOf(doc.getId()));
            attachmentService.saveAttachment(attachment);
            documentAttachment = new RTDocumentAttachmentImpl();
            documentAttachment.setAttachmenter(inputId);
            documentAttachment.setAttachmentid(Integer.valueOf(attachment.getId()));
            documentAttachment.setAttachmentname(attachment.getName());
            documentAttachment.setDocumentid(Integer.valueOf(doc.getId()));
            documentAttachment.setAttachmentername(orgObject != null ? orgObject.getOrgName() : "");
            documentAttachment.setCreatetime(TimeUtil.getSysDateAsDate());
            documentAttachment.setDocumentVersion("1");
            documentAttachment.setGroupid(Integer.valueOf(groupId != 0 ? groupId : attachment.getId()));
        }

    }

    public IRTDirectory loadDirectoryByPath(String path, int dirType, int parentDirId, String inpuid)
    {
        IRTDirectoryService directoryService = BaseRoutineServiceFactory.getDirectoryService();
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        IRTDocumentAuthService documentAuthService = BaseRoutineServiceFactory.getDocumentAuthService();
        String ownerId = RequestContext.getCurrentUser().getOrgId();
        String targetPath = path.replaceAll("\\\\", "/");
        if(targetPath.length() == 0)
            return null;
        if(targetPath.startsWith("/"))
            targetPath = targetPath.substring(1);
        if(targetPath.length() == 0)
            return null;
        int index = targetPath.contains("/") ? targetPath.indexOf("/") : targetPath.length();
        String dirName = targetPath.substring(0, index);
        if(StringUtil.isStrEmpty(dirName))
            return null;
        IRTDirectory dir = directoryService.getDirectoryByNamePId(dirName, parentDirId);
        boolean isNew = false;
        if(dir == null)
        {
            dir = new RTDirectoryImpl();
            isNew = true;
        }
        dir.setDirectoryname(dirName);
        dir.setParentid(Integer.valueOf(parentDirId));
        dir.setStatus(Integer.valueOf(1));
        dir.setDirectorytype(Integer.valueOf(dirType));
        dir.setDirectoryer(inpuid);
        directoryService.saveDirectory(dir);
        if(isNew)
        {
            IRightType _rightTypes[] = new IRightType[6];
            _rightTypes[0] = new com.sinitek.sirm.org.busin.service.impl.RightServiceImpl.RightType("REDOWNLOAD");
            _rightTypes[1] = new com.sinitek.sirm.org.busin.service.impl.RightServiceImpl.RightType("REWRITE");
            _rightTypes[2] = new com.sinitek.sirm.org.busin.service.impl.RightServiceImpl.RightType("REREAD");
            _rightTypes[3] = new com.sinitek.sirm.org.busin.service.impl.RightServiceImpl.RightType("RECREATEDOC");
            _rightTypes[4] = new com.sinitek.sirm.org.busin.service.impl.RightServiceImpl.RightType("RECREATESUB");
            _rightTypes[5] = new com.sinitek.sirm.org.busin.service.impl.RightServiceImpl.RightType("REMODIFY");
            String entity = dir.getEntityName();
            IRTDocumentAuth documentAuth = new RTDocumentAuthImpl();
            documentAuth.setAuthid(Integer.valueOf(dir.getId()));
            documentAuth.setOrgid(ownerId);
            documentAuth.setSharinger(ownerId);
            documentAuth.setAuthEntity(entity);
            documentAuthService.saveCompanyDocumentAuth(documentAuth);
            if(!_rightUpdater.hasRightDefine(entity))
                _rightUpdater.createRightDefine(entity, (new StringBuilder()).append("\u6388\u6743\uFF1A").append(entity).toString(), AuthType.OBJECT_KEY, null);
            _rightUpdater.addRightAuth(ownerId, String.valueOf(documentAuth.getId()), String.valueOf(dir.getId()), entity, false, _rightTypes);
        }
        String nextPath = path.length() <= index + 1 ? "" : path.substring(index + 1);
        IRTDirectory nextDir = loadDirectoryByPath(nextPath, dirType, dir.getId(), inpuid);
        if(nextDir == null)
            return dir;
        else
            return nextDir;
    }

    public IRTDocumentAttachment getDocumentAttachmentDocIdVersion(int docId, int version)
    {
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTDOCUMENTATTACHMENT", " documentid=:documentid and DocumentVersion=:DocumentVersion");
        iMetaDBQuery.setParameter("documentid", Integer.valueOf(docId));
        iMetaDBQuery.setParameter("DocumentVersion", Integer.valueOf(version));
        iMetaDBQuery.setOrderBy(" DocumentVersion asc");
        List list = iMetaDBQuery.getResult();
        IRTDocumentAttachment documentAttachment = null;
        if(list != null && list.size() > 0)
            documentAttachment = (IRTDocumentAttachment)list.get(0);
        return documentAttachment;
    }

    public IRTDocuments getDocumentByDirIdName(int dirId, String name)
    {
        StringBuilder sql = new StringBuilder(" 1=1 ");
        Map param = new HashMap();
        sql.append(" and directoryId=:directoryId");
        sql.append(" and documentName=:documentName");
        param.put("directoryId", Integer.valueOf(dirId));
        param.put("documentName", name);
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTDOCUMENTS", sql.toString());
        iMetaDBQuery.setParameters(param);
        iMetaDBQuery.setDefaultOrderBy("createtime desc");
        List rs = iMetaDBQuery.getResult();
        return rs.size() <= 0 ? null : (IRTDocuments)rs.get(0);
    }
}
