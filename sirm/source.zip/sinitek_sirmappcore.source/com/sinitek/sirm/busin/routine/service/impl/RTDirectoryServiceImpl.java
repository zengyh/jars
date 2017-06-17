// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTDirectoryServiceImpl.java

package com.sinitek.sirm.busin.routine.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.busin.routine.entity.IRTDirectory;
import com.sinitek.sirm.busin.routine.entity.IRTDirectoryAppend;
import com.sinitek.sirm.busin.routine.service.IRTDirectoryService;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import java.util.*;

public class RTDirectoryServiceImpl extends AbstractMetaDBContextSupport
    implements IRTDirectoryService
{

    public RTDirectoryServiceImpl()
    {
    }

    public List findAllDirectory(int status, int directorytype)
    {
        List directoryList = new ArrayList();
        StringBuilder sql = new StringBuilder();
        Map _map = new HashMap();
        sql.append(" 1=1 and status=:status and directorytype=:directorytype");
        _map.put("status", Integer.valueOf(status));
        _map.put("directorytype", Integer.valueOf(directorytype));
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTDIRECTORY", sql.toString());
        if(iMetaDBQuery != null)
        {
            iMetaDBQuery.setParameters(_map);
            directoryList = iMetaDBQuery.getResult();
        }
        return directoryList;
    }

    public List findAllDirectoryByParentId(int status, int directorytype, int parentid, String directoryer)
    {
        List directoryList = new ArrayList();
        StringBuilder sql = new StringBuilder();
        Map _map = new HashMap();
        sql.append(" 1=1 and status=:status and directorytype=:directorytype and parentid=:parentid");
        _map.put("status", Integer.valueOf(status));
        _map.put("directorytype", Integer.valueOf(directorytype));
        _map.put("parentid", Integer.valueOf(parentid));
        if(directoryer != null && !"".equals(directoryer))
        {
            sql.append(" and directoryer=:directoryer");
            _map.put("directoryer", directoryer);
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTDIRECTORY", sql.toString());
        if(iMetaDBQuery != null)
        {
            iMetaDBQuery.setParameters(_map);
            iMetaDBQuery.setOrderBy("sort,updatetimestamp desc");
            directoryList = iMetaDBQuery.getResult();
        }
        return directoryList;
    }

    public List findAllParentDirectory(int id)
    {
        List _result = new ArrayList();
        if(id != 0)
        {
            String sql = "select * from rt_directory start with objid=:objid connect by prior parentid = objid ";
            IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql);
            iMetaDBQuery.setParameter("objid", Integer.valueOf(id));
            _result = iMetaDBQuery.getResult();
        }
        return _result;
    }

    public List findAllChildrenDirectory(int parentId)
    {
        List _result = new ArrayList();
        if(parentId != 0)
        {
            String sql = "select * from rt_directory start with objid=:startObjid connect by prior   objid=parentid ";
            IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql);
            iMetaDBQuery.setParameter("startObjid", Integer.valueOf(parentId));
            _result = iMetaDBQuery.getResult();
        }
        List result = new ArrayList();
        Iterator i$ = _result.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Map r = (Map)i$.next();
            int objid = NumberTool.safeToInteger(r.get("objid"), Integer.valueOf(0)).intValue();
            if(objid != parentId)
                result.add(r);
        } while(true);
        return result;
    }

    public IRTDirectory getDirectoryById(int id)
    {
        return (IRTDirectory)getMetaDBContext().get("RTDIRECTORY", id);
    }

    public IRTDirectoryAppend getDirectoryAppendById(int objid)
    {
        return (IRTDirectoryAppend)getMetaDBContext().get("RTDIRECTORYAPPEND", objid);
    }

    public IRTDirectoryAppend getDirectoryAppendByDirectoryId(int directoryid)
    {
        IRTDirectoryAppend irtDirectoryAppend = null;
        IMetaDBQuery _query = getMetaDBContext().createQuery("RTDIRECTORYAPPEND", "DIRECTORYID = :directoryid");
        _query.setParameter("directoryid", Integer.valueOf(directoryid));
        List list = _query.getResult();
        if(list != null && list.size() > 0)
            return (IRTDirectoryAppend)list.get(0);
        else
            return irtDirectoryAppend;
    }

    public void saveDirectory(IRTDirectory directory)
    {
        directory.save();
    }

    public void saveDirectoryAppend(IRTDirectoryAppend directoryAppend)
    {
        if(directoryAppend != null)
            directoryAppend.save();
    }

    public void deleteDirectoryAppend(IRTDirectoryAppend irtDirectoryAppend)
    {
        if(irtDirectoryAppend != null)
            irtDirectoryAppend.remove();
    }

    public List getDirectoryByOrgid(String orgid)
    {
        IMetaDBQuery metaDBQuery = null;
        Map param = new HashMap();
        param.put("orgid", orgid);
        String sqlcmd = "select d.objid,d.directoryer,\n       d.directoryname,\n       d.directorycontent,\n       d.directorytype,\n       d.entityname,\n       d.parentid,\n       decode(r1.objid, null, decode(d.directoryer,:orgid,1,0), 1) right_dir,\n       decode(r2.objid, null, 0, 1) right_doc,\n       decode(d.directorytype,1, 1) right_company  from rt_directory d\n  left join (select *\n               from rt_documentauth\n              where authentity = 'RTDIRECTORY'\n                and sharinger = :orgid) r1\n    on d.objid = r1.authid\n  left join (select *\n               from rt_documents\n              where objid in (select authid\n                                from rt_documentauth\n                               where authentity = 'RTDOCUMENTS'\n                                 and sharinger = :orgid)) r2\n    on d.objid = r2.directoryid\n where d.status = 1\n order by directorytype asc, sort asc";
        metaDBQuery = getMetaDBContext().createSqlQuery(sqlcmd);
        metaDBQuery.setParameters(param);
        return metaDBQuery.getResult();
    }

    public List getDirectoryDetailById(int id)
    {
        StringBuffer sb = new StringBuffer("");
        sb.append((new StringBuilder()).append("\nSELECT T.*, RDA1.ALLOWSUBAPPLYAUTHOR PARENTALLOWSUB\n  FROM (SELECT RD.OBJID,\n               RD.DIRECTORYER,\n               RD.DIRECTORYNAME,\n               RD.PARENTID,\n               RD.DIRECTORYCONTENT,\n               RD.SORT,\n               RDA.ISSEARCH,\n               RDA.ALLOWAPPLYAUTOR,\n               RDA.ALLOWSUBAPPLYAUTHOR,\n               RDA.WORKFLOWID\n          FROM RT_DIRECTORY RD, RT_DIRECTORY_APPEND RDA\n         WHERE RD.OBJID = RDA.DIRECTORYID\n           AND RD.DIRECTORYTYPE = 1\n           AND RD.OBJID = ").append(id).append(") T\n").append("  LEFT JOIN RT_DIRECTORY_APPEND RDA1\n").append("    ON RDA1.DIRECTORYID = T.PARENTID ").toString());
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        return _query.getResult();
    }

    public List searchCompanyDirByParentId(int parentid)
    {
        StringBuffer sb = new StringBuffer("");
        sb.append("SELECT T.OBJID, T.DIRECTORYER, T.DIRECTORYNAME, T.SORT, T.ISSEARCH\n  FROM (SELECT RD.OBJID,\n               RD.DIRECTORYER,\n               RD.DIRECTORYNAME,\n               RD.PARENTID,\n               RD.SORT, rd.directorytype,\n               NVL(RDA.ISSEARCH, 0) ISSEARCH\n          FROM RT_DIRECTORY RD\n          LEFT JOIN RT_DIRECTORY_APPEND RDA\n            ON RDA.DIRECTORYID = RD.OBJID) T\n WHERE T.PARENTID = :parentid AND t.directorytype = 1\n   AND T.ISSEARCH = 1\n");
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        _query.setParameter("parentid", Integer.valueOf(parentid));
        _query.setDefaultOrderBy("t.sort");
        List list = _query.getResult();
        return list;
    }

    public IMetaDBQuery searchCompanyDoc(Map map)
    {
        Map _param = new HashMap();
        StringBuffer sb = new StringBuffer("");
        sb.append("SELECT RD.OBJID,\n       RD.DOCUMENTNAME,\n       RD.DOCUMENTCONTENT DOCDES,\n       (SELECT SO.ORGNAME\n          FROM SPRT_ORGOBJECT SO\n         WHERE SO.ORGID = RD.DOCUMENTER) UPLOADER,\n       RD.CREATETIME UPLOADTIME,RD.DIRECTORYID DIRCTORYID\n  FROM RT_DOCUMENTS RD, RT_DIRECTORY RDI, RT_DIRECTORY_APPEND RDA\n WHERE RD.DIRECTORYID = RDI.OBJID\n   AND RDI.OBJID = RDA.DIRECTORYID\n   AND RDA.ISSEARCH = 1 and rd.status = 1");
        int uploader = NumberTool.safeToInteger(map.get("uploader"), Integer.valueOf(-1)).intValue();
        if(uploader != -1)
        {
            sb.append(" and RD.DOCUMENTER = :uploader");
            _param.put("uploader", Integer.valueOf(uploader));
        }
        String dirids = StringUtil.safeToString(map.get("dirids"), "");
        if(!"".equals(dirids))
            sb.append((new StringBuilder()).append(" and rdi.objid in (").append(dirids).append(")").toString());
        String docname = StringUtil.safeToString(map.get("docname"), "");
        if(!"".equals(docname))
            sb.append((new StringBuilder()).append(" and rd.DOCUMENTNAME like '%").append(docname).append("%'").toString());
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        _query.setParameters(_param);
        return _query;
    }

    public List getAllParentDirByDocid(int id)
    {
        StringBuffer sb = new StringBuffer("");
        sb.append("SELECT RD.OBJID, RD.DIRECTORYER, RD.DIRECTORYNAME, RD.PARENTID\n  FROM RT_DIRECTORY RD\n START WITH RD.OBJID = :id\nCONNECT BY PRIOR RD.PARENTID = RD.OBJID\n ORDER BY RD.PARENTID");
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        _query.setParameter("id", Integer.valueOf(id));
        List list = _query.getResult();
        return list;
    }

    public IMetaDBQuery searchAllCompanyDirAuthEmpByDirId(int dirid)
    {
        StringBuffer sb = new StringBuffer("");
        sb.append("WITH DOCUMENTAUTH AS\n (SELECT RD.OBJID,\n         RD.SHARINGER,\n         RD.BEGINTIME,\n         RD.ENDTIME,\n         RD.ORGID,\n         RD.CREATETIMESTAMP,\n         (SELECT ORGNAME FROM SPRT_ORGOBJECT WHERE ORGID = RD.SHARINGER) SHARINGERNAME\n    FROM RT_DOCUMENTAUTH RD, SPRT_RIGHTAUTH SR\n   WHERE RD.AUTHENTITY = 'RTDIRECTORY'\n     AND SR.AUTHORGEXP = RD.OBJID\n     AND SR.RIGHTTYPE IN ('MODIFY',\n                          'REMODIFY',\n                          'CREATESUB',\n                          'RECREATESUB',\n                          'CREATEDOC',\n                          'RECREATEDOC')\n     AND RD.AUTHID = :dirid)\n\nSELECT DISTINCT OBJID,\n       SHARINGER,\n       SHARINGERNAME,\n       BEGINTIME,\n       ENDTIME,\n       ORGID,\n       CREATETIMESTAMP,\n       CASE\n         WHEN MODIFY1 = 'MODIFY' THEN\n          '\u53EF\u4FEE\u6539'\n         WHEN MODIFY1 = 'REMODIFY' THEN\n          '\u53EF\u4FEE\u6539,\u53EF\u518D\u6388\u6743'\n         ELSE\n          ''\n       END MODIFY1,\n       CASE\n         WHEN CREATESUB = 'CREATESUB' THEN\n          '\u53EF\u521B\u5EFA'\n         WHEN CREATESUB = 'RECREATESUB' THEN\n          '\u53EF\u521B\u5EFA,\u53EF\u518D\u6388\u6743'\n         ELSE\n          ''\n       END CREATESUB,\n       CASE\n         WHEN CREATEDOC = 'CREATEDOC' THEN\n          '\u53EF\u521B\u5EFA'\n         WHEN CREATEDOC = 'RECREATEDOC' THEN\n          '\u53EF\u521B\u5EFA,\u53EF\u518D\u6388\u6743'\n         ELSE\n          ''\n       END CREATEDOC\n  FROM (SELECT D.OBJID,\n               D.SHARINGER,\n               D.BEGINTIME,\n               D.ENDTIME,\n               D.ORGID,\n               D.CREATETIMESTAMP,\n               D.SHARINGERNAME,\n               (SELECT RIGHTTYPE\n                  FROM SPRT_RIGHTAUTH\n                 WHERE D.OBJID = AUTHORGEXP\n                   AND (RIGHTTYPE = 'MODIFY' OR RIGHTTYPE = 'REMODIFY')\n                   AND RIGHTDEFINEKEY = 'RTDIRECTORY') MODIFY1,\n               (SELECT RIGHTTYPE\n                  FROM SPRT_RIGHTAUTH\n                 WHERE D.OBJID = AUTHORGEXP\n                   AND (RIGHTTYPE = 'CREATESUB' OR RIGHTTYPE = 'RECREATESUB')\n                   AND RIGHTDEFINEKEY = 'RTDIRECTORY') CREATESUB,\n               (SELECT RIGHTTYPE\n                  FROM SPRT_RIGHTAUTH\n                 WHERE D.OBJID = AUTHORGEXP\n                   AND (RIGHTTYPE = 'CREATEDOC' OR RIGHTTYPE = 'RECREATEDOC')\n                   AND RIGHTDEFINEKEY = 'RTDIRECTORY') CREATEDOC\n          FROM DOCUMENTAUTH D)");
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        _query.setParameter("dirid", Integer.valueOf(dirid));
        return _query;
    }

    public IMetaDBQuery searchAllCompanyDirDocAuthByDirId(int directoryid)
    {
        StringBuffer sb = new StringBuffer("");
        sb.append("WITH DOCUMENTAUTH AS\n (SELECT RD.OBJID,\n         RD.SHARINGER,\n         RD.BEGINTIME,\n         RD.ENDTIME,\n         RD.ORGID,\n         RD.CREATETIMESTAMP,\n         (SELECT ORGNAME FROM SPRT_ORGOBJECT WHERE ORGID = RD.SHARINGER) SHARINGERNAME\n    FROM RT_DOCUMENTAUTH RD, SPRT_RIGHTAUTH SR\n   WHERE RD.AUTHENTITY = 'RTDIRECTORY'\n     AND SR.AUTHORGEXP = RD.OBJID\n     AND SR.RIGHTTYPE IN\n         ('READ', 'REREAD', 'WRITE', 'REWRITE', 'DOWNLOAD', 'REDOWNLOAD')\n     AND RD.AUTHID = :dirid)\n\nSELECT OBJID,\n       SHARINGER,\n       SHARINGERNAME,\n       BEGINTIME,\n       ENDTIME,\n       ORGID,\n       CREATETIMESTAMP,\n       CASE\n         WHEN READ = 'READ' THEN\n          '\u53EF\u67E5\u770B'\n         WHEN READ = 'REREAD' THEN\n          '\u53EF\u67E5\u770B,\u53EF\u518D\u6388\u6743'\n         ELSE\n          ''\n       END READ,\n       CASE\n         WHEN WRITE = 'WRITE' THEN\n          '\u53EF\u4FEE\u6539'\n         WHEN WRITE = 'REWRITE' THEN\n          '\u53EF\u4FEE\u6539,\u53EF\u518D\u6388\u6743'\n         ELSE\n          ''\n       END WRITE,\n       CASE\n         WHEN DOWLOAD = 'DOWNLOAD' THEN\n          '\u53EF\u4E0B\u8F7D'\n         WHEN DOWLOAD = 'REDOWNLOAD' THEN\n          '\u53EF\u4E0B\u8F7D,\u53EF\u518D\u6388\u6743'\n         ELSE\n          ''\n       END DOWLOAD\n  FROM (SELECT DISTINCT D.OBJID,\n                        D.SHARINGER,\n                        D.BEGINTIME,\n                        D.ENDTIME,\n                        D.ORGID,\n                        D.CREATETIMESTAMP,\n                        D.SHARINGERNAME,\n                        (SELECT RIGHTTYPE\n                           FROM SPRT_RIGHTAUTH\n                          WHERE D.OBJID = AUTHORGEXP\n                            AND (RIGHTTYPE = 'READ' OR RIGHTTYPE = 'REREAD')\n                            AND RIGHTDEFINEKEY = 'RTDIRECTORY') READ,\n                        (SELECT RIGHTTYPE\n                           FROM SPRT_RIGHTAUTH\n                          WHERE D.OBJID = AUTHORGEXP\n                            AND (RIGHTTYPE = 'WRITE' OR RIGHTTYPE = 'REWRITE')\n                            AND RIGHTDEFINEKEY = 'RTDIRECTORY') WRITE,\n                        (SELECT RIGHTTYPE\n                           FROM SPRT_RIGHTAUTH\n                          WHERE D.OBJID = AUTHORGEXP\n                            AND (RIGHTTYPE = 'DOWNLOAD' OR\n                                RIGHTTYPE = 'REDOWNLOAD')\n                            AND RIGHTDEFINEKEY = 'RTDIRECTORY') DOWLOAD\n          FROM DOCUMENTAUTH D)");
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        _query.setParameter("dirid", Integer.valueOf(directoryid));
        return _query;
    }

    public IMetaDBQuery searchCompanyDocAuthByDocid(int docid)
    {
        StringBuffer sb = new StringBuffer("");
        sb.append((new StringBuilder()).append("SELECT OBJID,\n       SHARINGER,\n       SHARINGERNAME,\n       BEGINTIME,\n       ENDTIME,\n       ORGID,\n       CREATETIMESTAMP,\n       CASE\n         WHEN READ = 'READ' THEN\n          '\u53EF\u67E5\u770B'\n         WHEN READ = 'REREAD' THEN\n          '\u53EF\u67E5\u770B,\u53EF\u518D\u6388\u6743'\n         ELSE\n          ''\n       END READ,\n       CASE\n         WHEN WRITE = 'WRITE' THEN\n          '\u53EF\u4FEE\u6539'\n         WHEN WRITE = 'REWRITE' THEN\n          '\u53EF\u4FEE\u6539,\u53EF\u518D\u6388\u6743'\n         ELSE\n          ''\n       END WRITE,\n       CASE\n         WHEN DOWLOAD = 'DOWNLOAD' THEN\n          '\u53EF\u4E0B\u8F7D'\n         WHEN DOWLOAD = 'REDOWNLOAD' THEN\n          '\u53EF\u4E0B\u8F7D,\u53EF\u518D\u6388\u6743'\n         ELSE\n          ''\n       END DOWLOAD\n  FROM (SELECT D.OBJID,\n               D.SHARINGER,\n               D.BEGINTIME,\n               D.ENDTIME,\n               D.ORGID,\n               D.CREATETIMESTAMP,\n               (SELECT ORGNAME FROM SPRT_ORGOBJECT WHERE ORGID = D.SHARINGER) SHARINGERNAME,\n               (SELECT RIGHTTYPE\n                  FROM SPRT_RIGHTAUTH\n                 WHERE D.OBJID = AUTHORGEXP\n                   AND (RIGHTTYPE = 'READ' OR RIGHTTYPE = 'REREAD')\n                   AND RIGHTDEFINEKEY = 'RTDOCUMENTS') READ,\n               (SELECT RIGHTTYPE\n                  FROM SPRT_RIGHTAUTH\n                 WHERE D.OBJID = AUTHORGEXP\n                   AND (RIGHTTYPE = 'WRITE' OR RIGHTTYPE = 'REWRITE')\n                   AND RIGHTDEFINEKEY = 'RTDOCUMENTS') WRITE,\n               (SELECT RIGHTTYPE\n                  FROM SPRT_RIGHTAUTH\n                 WHERE D.OBJID = AUTHORGEXP\n                   AND (RIGHTTYPE = 'DOWNLOAD' OR RIGHTTYPE = 'REDOWNLOAD')\n                   AND RIGHTDEFINEKEY = 'RTDOCUMENTS') DOWLOAD\n          FROM RT_DOCUMENTAUTH D\n         WHERE 1 = 1\n           AND D.AUTHID = ").append(docid).append(")").toString());
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        return _query;
    }

    public IRTDirectory getDirectoryByNamePId(String dirName, int parentId)
    {
        List directoryList = new ArrayList();
        StringBuilder sql = new StringBuilder();
        Map _map = new HashMap();
        sql.append(" 1=1 and parentid=:parentid and directoryname=:directoryname");
        _map.put("parentid", Integer.valueOf(parentId));
        _map.put("directoryname", dirName);
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("RTDIRECTORY", sql.toString());
        if(iMetaDBQuery != null)
        {
            iMetaDBQuery.setParameters(_map);
            iMetaDBQuery.setOrderBy("sort,updatetimestamp desc");
            directoryList = iMetaDBQuery.getResult();
        }
        return directoryList.size() <= 0 ? null : (IRTDirectory)directoryList.get(0);
    }
}
