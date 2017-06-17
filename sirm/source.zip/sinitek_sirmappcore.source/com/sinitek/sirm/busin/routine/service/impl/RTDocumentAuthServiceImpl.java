// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RTDocumentAuthServiceImpl.java

package com.sinitek.sirm.busin.routine.service.impl;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.busin.routine.entity.*;
import com.sinitek.sirm.busin.routine.service.*;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.sirm.org.busin.service.*;
import com.sinitek.spirit.right.client.RightFactory;
import com.sinitek.spirit.right.core.*;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.util.*;

public class RTDocumentAuthServiceImpl extends AbstractMetaDBContextSupport
    implements IRTDocumentAuthService
{

    public RTDocumentAuthServiceImpl()
    {
    }

    public List findAuthByDocumentId(int documentid)
    {
        return findAuth(null, "RTDOCUMENTS", documentid);
    }

    public List findAuthByDiretoryId(int diretoryid)
    {
        return findAuth(null, "RTDIRECTORY", diretoryid);
    }

    private List findAuth(String orgid, String entityname, int id)
    {
        Map map = new HashMap();
        StringBuilder sql = new StringBuilder("select d.*,(select orgname from sprt_orgobject where orgid=d.sharinger) sharingername\n,(select '\u53EF\u67E5\u770B' from sprt_rightauth where d.objid=authorgexp and righttype='READ' and rightdefinekey=:rightdefinekey ) read \n,(select '\u53EF\u4E0B\u8F7D' from sprt_rightauth where d.objid=authorgexp and righttype='DOWNLOAD' and rightdefinekey=:rightdefinekey) download\n,(select '\u53EF\u4FEE\u6539' from sprt_rightauth where d.objid=authorgexp and righttype='WRITE' and rightdefinekey=:rightdefinekey) write  from RT_Documentauth d where 1=1 ");
        if(orgid != null && !"".equals(orgid))
        {
            sql.append(" and d.orgid=:orgid");
            map.put("orgid", orgid);
        }
        map.put("rightdefinekey", entityname);
        if(id != 0)
        {
            sql.append(" and d.authid=:authid");
            map.put("authid", Integer.valueOf(id));
        }
        IMetaDBQuery _metaDbQuery = (new MetaDBTemplate()).createSqlQuery(sql.toString());
        _metaDbQuery.setParameters(map);
        return _metaDbQuery.getResult();
    }

    public List getDocumentRightType(int id, String entityname, String authorgid)
    {
        List result = new ArrayList();
        Map map = new HashMap();
        StringBuilder sql = new StringBuilder("select righttype from sprt_rightauth where 1=1 ");
        if(id != 0)
        {
            sql.append(" and objectkey=:objectkey");
            map.put("objectkey", Integer.valueOf(id));
        }
        if(!"".equals(authorgid))
        {
            sql.append(" and authorgid=:authorgid");
            map.put("authorgid", authorgid);
        }
        if(!"".equals(entityname))
        {
            sql.append(" and rightdefinekey=:rightdefinekey");
            map.put("rightdefinekey", entityname);
        }
        IMetaDBQuery _metaDbQuery = (new MetaDBTemplate()).createSqlQuery(sql.toString());
        _metaDbQuery.setParameters(map);
        List righttype = _metaDbQuery.getResult();
        Map rightmap;
        for(Iterator i$ = righttype.iterator(); i$.hasNext(); result.add(rightmap))
        {
            Map type = (Map)i$.next();
            rightmap = new HashMap();
            String typeStr = String.valueOf(type.get("righttype"));
            rightmap.put("readauth", "READ".equals(typeStr) ? "\u8BFB\u53D6" : "");
            rightmap.put("writeauth", "WRITE".equals(typeStr) ? "\u4FEE\u6539" : "");
            rightmap.put("downloadauth", "DOWNLOAD".equals(typeStr) ? "\u4E0B\u8F7D" : "");
        }

        return result;
    }

    public void saveDocumentAuth(IRTDocumentAuth documentAuth)
    {
        IRTDocumentAuth d = getDocumentAuthByAuthId(documentAuth.getSharinger(), documentAuth.getAuthid().intValue());
        if(null != d)
        {
            OrgServiceFactory.getRightService().deleteRightAuth(new OrgObject(d.getSharinger(), 8), String.valueOf(d.getAuthid()), d.getAuthEntity(), new String[] {
                "READ", "WRITE", "DOWNLOAD"
            });
            delDocumentAuth(d);
        }
        documentAuth.save();
    }

    public void saveCompanyDocumentAuth(IRTDocumentAuth documentAuth)
    {
        IRTDocumentAuth d = getDocumentAuthByAuthId(documentAuth.getSharinger(), documentAuth.getAuthid().intValue());
        if(null != d)
        {
            OrgServiceFactory.getRightService().deleteRightAuth(new OrgObject(d.getSharinger(), 8), String.valueOf(d.getAuthid()), d.getAuthEntity(), new String[] {
                "READ", "WRITE", "DOWNLOAD", "REREAD", "REWRITE", "REDOWNLOAD", "MODIFY", "CREATESUB", "CREATEDOC", "REMODIFY", 
                "RECREATESUB", "RECREATEDOC"
            });
            delDocumentAuth(d);
        }
        documentAuth.save();
    }

    public IMetaObject getDocumentAuth(int id)
    {
        return (new MetaDBTemplate()).get("RTDOCUMENTAUTH", id);
    }

    public IRTDocumentAuth getDocumentAuthByAuthId(String sharinger, int authid)
    {
        StringBuilder sql = new StringBuilder("1=1");
        Map map = new HashMap();
        if(sharinger != null && !"".equals(sharinger))
        {
            sql.append(" and sharinger=:sharinger");
            map.put("sharinger", sharinger);
        }
        if(authid != 0)
        {
            sql.append(" and authid=:authid");
            map.put("authid", Integer.valueOf(authid));
        }
        IMetaDBQuery iMetaDBQuery = (new MetaDBTemplate()).createQuery("RTDOCUMENTAUTH", sql.toString());
        iMetaDBQuery.setParameters(map);
        IRTDocumentAuth documentAuth = null;
        List documentAuthList = iMetaDBQuery.getResult();
        if(documentAuthList.size() > 0)
            documentAuth = (IRTDocumentAuth)documentAuthList.get(0);
        return documentAuth;
    }

    public void delDocumentAuth(IRTDocumentAuth documentAuth)
    {
        documentAuth.delete();
        List authFromParent = getDocumentAuthsByFromEntityAndId("RTDOCUMENTAUTH", documentAuth.getId());
        String allRightTypes[] = {
            "READ", "WRITE", "DOWNLOAD", "REREAD", "REWRITE", "REDOWNLOAD", "MODIFY", "CREATESUB", "CREATEDOC", "REMODIFY", 
            "RECREATESUB", "RECREATEDOC"
        };
        IRTDocumentAuth auth;
        for(Iterator i$ = authFromParent.iterator(); i$.hasNext(); OrgServiceFactory.getRightService().deleteRightAuth(new OrgObject(auth.getSharinger(), 8), String.valueOf(auth.getAuthid()), auth.getAuthEntity(), allRightTypes))
        {
            auth = (IRTDocumentAuth)i$.next();
            delDocumentAuth(auth);
        }

    }

    public List getSharingerDiretoryByOrgId(String orgid)
    {
        List _orgObject = new ArrayList();
        List orgObjectList = OrgServiceFactory.getOrgService().findParentOrgById(orgid);
        StringBuilder _sql = (new StringBuilder("select r.orgid,(select orgname from sprt_orgobject where orgid=r.orgid)orgname from(")).append("select r.orgid from rt_documentauth r where r.authid ").append("in(select distinct objectkey from sprt_rightauth where 1=1 ");
        Map _paras = new HashMap();
        QueryUtils.buildIn("rightdefinekey", new String[] {
            "RTDIRECTORY", "RTDOCUMENTS"
        }, _sql, _paras);
        QueryUtils.buildIn("righttype", new String[] {
            "READ", "WRITE", "DOWNLOAD"
        }, _sql, _paras);
        String _ids[] = new String[orgObjectList.size() + 1];
        for(int i = 0; i < orgObjectList.size(); i++)
        {
            OrgObject orgObject = (OrgObject)orgObjectList.get(i);
            _ids[i] = orgObject.getOrgId();
        }

        if(_ids.length > 0)
            QueryUtils.buildIn("authorgid", _ids, _sql, _paras);
        _sql.append(")");
        if(_ids.length > 0)
            QueryUtils.buildIn("r.sharinger", _ids, _sql, _paras);
        _sql.append(" and (nvl(r.begintime,sysdate-1)<=sysdate and nvl(r.endtime,sysdate+1)>=sysdate) group by orgid) r");
        IMetaDBQuery _query = (new MetaDBTemplate()).createSqlQuery(_sql.toString());
        _query.setParameters(_paras);
        List result = _query.getResult();
        OrgObject orgObject;
        for(Iterator i$ = result.iterator(); i$.hasNext(); _orgObject.add(orgObject))
        {
            Map map = (Map)i$.next();
            orgObject = new OrgObject();
            orgObject.setOrgId(String.valueOf(map.get("orgid")));
            orgObject.setOrgName(String.valueOf(map.get("orgname")));
        }

        return _orgObject;
    }

    public List findDocumentsByOrgId(String orgid, String sharinger)
    {
        List documentsList = new ArrayList();
        List orgObjectList = OrgServiceFactory.getOrgService().findParentOrgById(sharinger);
        StringBuilder sql = new StringBuilder("select * from rt_documents where objid in (select distinct authid from rt_documentauth where authentity=:authentity ");
        StringBuilder directorysql = new StringBuilder("select * from rt_documents where directoryid in (select distinct authid from rt_documentauth where authid in(select r.objid from rt_directory r where r.status=1 and  r.objid=authid) and orgid =:orgid and authentity=:authentity ");
        Map _paras = new HashMap();
        String _ids[] = new String[orgObjectList.size() + 1];
        for(int i = 0; i < orgObjectList.size(); i++)
        {
            OrgObject orgObject = (OrgObject)orgObjectList.get(i);
            _ids[i] = orgObject.getOrgId();
        }

        _ids[orgObjectList.size()] = sharinger;
        if(_ids.length > 0)
        {
            QueryUtils.buildIn("sharinger", _ids, sql, _paras);
            QueryUtils.buildIn("sharinger", _ids, directorysql, _paras);
        }
        _paras.put("orgid", orgid);
        sql.append(" and (nvl(begintime,sysdate-1)<=sysdate and nvl(endtime,sysdate+1)>=sysdate)) and status=1 and documenter = :orgid");
        directorysql.append(" and (nvl(begintime,sysdate-1)<=sysdate and nvl(endtime,sysdate+1)>=sysdate)) and status=1 and documenter = :orgid");
        IMetaDBQuery _query = (new MetaDBTemplate()).createSqlQuery(sql.toString());
        IMetaDBQuery _directoryquery = (new MetaDBTemplate()).createSqlQuery(directorysql.toString());
        _query.setParameter("authentity", "RTDOCUMENTS");
        _directoryquery.setParameter("authentity", "RTDIRECTORY");
        _query.setParameters(_paras);
        _directoryquery.setParameters(_paras);
        documentsList.addAll(_query.getResult());
        documentsList.addAll(_directoryquery.getResult());
        return documentsList;
    }

    public void saveKeyReader(IRT_KeyReader keyReader)
    {
        keyReader.save();
    }

    public IRT_KeyReader getKeyReaderByAuthIdAndReaderId(int id, String readerId)
    {
        Map param = new HashMap();
        param.put("authId", Integer.valueOf(id));
        param.put("readerId", readerId);
        String sql = " authId=:authId and readerId=:readerId";
        List keyReaderList = getMetaDBContext().query("RTKEYREADER", sql, param);
        if(keyReaderList.size() == 0)
            return null;
        else
            return (IRT_KeyReader)keyReaderList.get(0);
    }

    public int deleteKeyReaderByDocumentId(int documentid)
    {
        StringBuffer sb = new StringBuffer("");
        sb.append((new StringBuilder()).append("DELETE FROM rt_keyreader rk WHERE rk.authentity = 'RTDOCUMENTS' AND rk.authid = ").append(documentid).toString());
        try
        {
            ISQLUpdater updater = getMetaDBContext().createSqlUpdater(sb.toString());
            updater.executeUpdate();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return 0;
        }
        return 1;
    }

    public IMetaDBQuery findKeyReaderByDocumentId(int documentid)
    {
        Map param = new HashMap();
        param.put("authId", Integer.valueOf(documentid));
        param.put("authEntity", "RTDOCUMENTS");
        String sql = "select ( select u1.value as readerName\n    from um_userproperty u1, um_userproperty u2\n   where u1.name = 'displayname'\n     and u2.value = r.readerId\n     and u2.name='orgid'\n     and u1.userid = u2.userid) as readerName,\n   r.* from RT_KeyReader r where authId=:authId and authEntity=:authEntity ";
        IMetaDBQuery metaDBQuery = getMetaDBContext().createSqlQuery(sql);
        metaDBQuery.setParameters(param);
        return metaDBQuery;
    }

    public IRT_KeyReader getKeyReaderById(int objid)
    {
        return (IRT_KeyReader)getMetaDBContext().get("RTKEYREADER", objid);
    }

    public void delDocumentKeyReader(IRT_KeyReader keyReader)
    {
        keyReader.delete();
    }

    public List getDirAuthBydiridAndorgid(int dirid, String orgid)
    {
        StringBuffer sb = new StringBuffer("");
        sb.append((new StringBuilder()).append("SELECT   SR.RIGHTTYPE\n    FROM RT_DOCUMENTAUTH RDA, SPRT_RIGHTAUTH SR\n   WHERE SR.AUTHORGEXP = RDA.OBJID\n     AND ((RDA.BEGINTIME IS NULL AND RDA.ENDTIME IS NULL) OR\n         (RDA.BEGINTIME <= SYSDATE AND RDA.ENDTIME IS NULL) OR\n         (RDA.BEGINTIME IS NULL AND RDA.ENDTIME >= SYSDATE) OR\n         (RDA.BEGINTIME <= SYSDATE AND RDA.ENDTIME >= SYSDATE))\n     AND rda.authentity = 'RTDIRECTORY'\n     AND RDA.SHARINGER = '").append(orgid).append("'\n").append("     AND rda.authid = ").append(dirid).toString());
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        List list = _query.getResult();
        return list;
    }

    public List getDocAuthBydocidAndorgid(int docid, String orgid)
    {
        StringBuffer sb = new StringBuffer("");
        sb.append((new StringBuilder()).append("SELECT   SR.RIGHTTYPE\n    FROM RT_DOCUMENTAUTH RDA, SPRT_RIGHTAUTH SR\n   WHERE SR.AUTHORGEXP = RDA.OBJID\n     AND ((RDA.BEGINTIME IS NULL AND RDA.ENDTIME IS NULL) OR\n         (RDA.BEGINTIME <= SYSDATE AND RDA.ENDTIME IS NULL) OR\n         (RDA.BEGINTIME IS NULL AND RDA.ENDTIME >= SYSDATE))\n     AND rda.authentity = 'RTDOCUMENTS'\n     AND RDA.SHARINGER = '").append(orgid).append("'\n").append("     AND rda.authid = ").append(docid).toString());
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        List list = _query.getResult();
        return list;
    }

    public boolean checkAuth(int dirid, String orgid, String righttype, String entityname)
    {
        StringBuffer sb = new StringBuffer("");
        sb.append((new StringBuilder()).append("SELECT COUNT(1) count\n  FROM RT_DOCUMENTAUTH RDA, SPRT_RIGHTAUTH SR\n WHERE SR.AUTHORGEXP = RDA.OBJID\n   AND ((RDA.BEGINTIME IS NULL AND RDA.ENDTIME IS NULL) OR\n       (RDA.BEGINTIME <= SYSDATE AND RDA.ENDTIME IS NULL) OR\n       (RDA.BEGINTIME IS NULL AND RDA.ENDTIME >= SYSDATE) OR       (RDA.BEGINTIME <= SYSDATE AND RDA.ENDTIME >= SYSDATE))\n   AND RDA.AUTHENTITY = '").append(entityname).append("'\n").append("   AND RDA.SHARINGER = '").append(orgid).append("'\n").append("   AND RDA.AUTHID = ").append(dirid).append("\n").append("   AND SR.RIGHTTYPE in (").append(righttype).append(")").toString());
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        List list = _query.getResult();
        return list != null && list.size() > 0 && NumberTool.safeToInteger(((Map)list.get(0)).get("count"), Integer.valueOf(0)).intValue() > 0;
    }

    public IRTApplyDocAuth getApplyDocAuth(int docid, int orgid, int status)
    {
        IRTApplyDocAuth irtApplyDocAuth = null;
        String sql = "documentid = :documentid and orgid = :orgid and status <> :status";
        Map param = new HashMap();
        param.put("documentid", Integer.valueOf(docid));
        param.put("orgid", Integer.valueOf(orgid));
        param.put("status", Integer.valueOf(status));
        List list = getMetaDBContext().query("RTAPPLYDOCAUTH", sql, param);
        if(list != null && list.size() > 0)
            return (IRTApplyDocAuth)list.get(0);
        else
            return irtApplyDocAuth;
    }

    public IRTApplyDocAuth getApplyDocAuthById(int id)
    {
        return (IRTApplyDocAuth)getMetaDBContext().get("RTAPPLYDOCAUTH", id);
    }

    public void removeApplyDocAuth(IRTApplyDocAuth irtApplyDocAuth)
    {
        if(irtApplyDocAuth != null)
            irtApplyDocAuth.remove();
    }

    public void saveApplyDocAuth(IRTApplyDocAuth irtApplyDocAuth)
    {
        if(irtApplyDocAuth != null)
            irtApplyDocAuth.save();
    }

    public boolean checkAuth(String authorgid, String objectkey, String rightDefineKey)
    {
        boolean result = false;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT COUNT(1) count\n  FROM RT_DOCUMENTAUTH RD\n WHERE NVL(RD.BEGINTIME, SYSDATE - 1) <= SYSDATE\n   AND NVL(RD.ENDTIME, SYSDATE + 1) >= SYSDATE\n   AND RD.AUTHENTITY = :authentity\n   AND RD.AUTHID = :authid\n   AND RD.SHARINGER = :sharinger");
        IMetaDBQuery _query = getMetaDBContext().createSqlQuery(sb.toString());
        _query.setParameter("authentity", rightDefineKey);
        _query.setParameter("authid", objectkey);
        _query.setParameter("sharinger", authorgid);
        List list = _query.getResult();
        if(list != null && list.size() > 0 && NumberTool.safeToInteger(((Map)list.get(0)).get("count"), Integer.valueOf(-1)).intValue() > 0)
            result = true;
        return result;
    }

    public void copyToChildren(int parentDirId, IRightType rightTypes[], List auths)
    {
        IRTDirectoryService directoryService = BaseRoutineServiceFactory.getDirectoryService();
        IRightUpdater _rightUpdater = RightFactory.getRightUpdater();
        List children = directoryService.findAllChildrenDirectory(parentDirId);
        for(Iterator i$ = children.iterator(); i$.hasNext();)
        {
            Map child = (Map)i$.next();
            int id = NumberTool.safeToInteger(child.get("objid"), Integer.valueOf(0)).intValue();
            Iterator i$ = auths.iterator();
            while(i$.hasNext()) 
            {
                IRTDocumentAuth auth = (IRTDocumentAuth)i$.next();
                IRTDocumentAuth documentAuth = new RTDocumentAuthImpl();
                documentAuth.setAuthid(Integer.valueOf(id));
                documentAuth.setBegintime(auth.getBegintime());
                documentAuth.setEndtime(auth.getEndtime());
                documentAuth.setOrgid(auth.getOrgid());
                documentAuth.setSharinger(auth.getSharinger());
                documentAuth.setAuthEntity(auth.getAuthEntity());
                documentAuth.setFromEntity("RTDOCUMENTAUTH");
                documentAuth.setFromObjid(Integer.valueOf(auth.getId()));
                saveCompanyDocumentAuth(documentAuth);
                String entity = auth.getAuthEntity();
                if(!_rightUpdater.hasRightDefine(entity))
                    _rightUpdater.createRightDefine(entity, (new StringBuilder()).append("\u6388\u6743\uFF1A").append(entity).toString(), AuthType.OBJECT_KEY, null);
                _rightUpdater.addRightAuth(documentAuth.getSharinger(), String.valueOf(documentAuth.getId()), String.valueOf(id), entity, false, rightTypes);
            }
        }

    }

    public List getDocumentAuthsByFromEntityAndId(String fromEntityName, int fromId)
    {
        String sql = " FromEntity=:FromEntity and FromObjid=:FromObjid ";
        IMetaDBQuery query = getMetaDBContext().createQuery(com/sinitek/sirm/busin/routine/entity/IRTDocumentAuth, sql);
        query.setParameter("FromEntity", fromEntityName);
        query.setParameter("FromObjid", Integer.valueOf(fromId));
        return query.getResult();
    }
}
