// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgRelationServiceImpl.java

package com.sinitek.sirm.org.busin.service.impl;

import com.sinitek.base.metadb.*;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.org.busin.entity.*;
import com.sinitek.sirm.org.busin.service.IOrgRelationService;
import java.util.*;
import org.apache.log4j.Logger;

public class OrgRelationServiceImpl extends MetaDBContextSupport
    implements IOrgRelationService
{

    public OrgRelationServiceImpl()
    {
        logger = Logger.getLogger(getClass());
    }

    public IMetaDBQuery findAllRelationScheme()
    {
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("ORGRELATIONSCHEME", " 1=1");
        return iMetaDBQuery;
    }

    public IOrgRelationScheme getRelationSchemeById(int schemeId)
    {
        IMetaObject iMetaObject = getMetaDBContext().get("ORGRELATIONSCHEME", schemeId);
        if(iMetaObject != null)
            return (IOrgRelationScheme)iMetaObject;
        else
            return null;
    }

    public boolean checkRelationSchemeByCodeAndName(String code, String name, int schemeId)
    {
        boolean result = false;
        StringBuilder sql = new StringBuilder("1=1");
        Map map = new HashMap();
        if(code != null && !"".equals(code) && name != null && !"".equals(name))
        {
            sql.append("and (code=:code or name=:name)");
            map.put("code", code);
            map.put("name", name);
        }
        if(schemeId != 0)
        {
            sql.append(" and objid!=:schemeId");
            map.put("schemeId", Integer.valueOf(schemeId));
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("ORGRELATIONSCHEME", sql.toString());
        iMetaDBQuery.setParameters(map);
        if(iMetaDBQuery.getResult().size() > 0)
            result = true;
        return result;
    }

    public void saveRelationScheme(IOrgRelationScheme relationScheme)
    {
        relationScheme.save();
    }

    public void delRelationScheme(int schemeId)
    {
        if(schemeId != 0)
        {
            IOrgRelationScheme orgRelationScheme = getRelationSchemeById(schemeId);
            if(orgRelationScheme != null)
            {
                orgRelationScheme.delete();
                List orgRelationList = findAllRelation(schemeId);
                IOrgRelation relation;
                for(Iterator i$ = orgRelationList.iterator(); i$.hasNext(); delRelation(relation.getId()))
                    relation = (IOrgRelation)i$.next();

            }
        }
    }

    public List findAllRelation(int schemeId)
    {
        StringBuilder sql = new StringBuilder("select * from org_relation where 1=1");
        Map map = new HashMap();
        if(schemeId != 0)
        {
            sql.append("and SchemeId=:SchemeId");
            map.put("SchemeId", Integer.valueOf(schemeId));
        }
        sql.append("");
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        iMetaDBQuery.setParameters(map);
        List list = iMetaDBQuery.getResult();
        List result = new ArrayList();
        IOrgRelation relation;
        for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(relation))
        {
            Map relationMap = (Map)i$.next();
            relation = new OrgRelationImpl();
            relation.setId(NumberTool.convertMapKeyToInt(relationMap, "objid").intValue());
            relation.setOrgId(StringUtil.convertMapKeyToString(relationMap, "orgid", ""));
            relation.setParentId(NumberTool.convertMapKeyToInt(relationMap, "parentid"));
            relation.setSchemeId(NumberTool.convertMapKeyToInt(relationMap, "schemeid"));
        }

        return result;
    }

    public List getRelationByOrgId(String orgid)
    {
        StringBuilder sql = new StringBuilder("select * from org_relation\u3000CONNECT BY PRIOR objid = parentid");
        Map map = new HashMap();
        if(orgid != null && !"".equals(orgid))
        {
            sql.append("  START WITH orgid=:orgid");
            map.put("orgid", orgid);
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        iMetaDBQuery.setParameters(map);
        List list = iMetaDBQuery.getResult();
        List result = new ArrayList();
        IOrgRelation relation;
        for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(relation))
        {
            Map relationMap = (Map)i$.next();
            relation = new OrgRelationImpl();
            relation.setId(NumberTool.convertMapKeyToInt(relationMap, "objid").intValue());
            relation.setOrgId(StringUtil.convertMapKeyToString(relationMap, "orgid", ""));
            relation.setParentId(NumberTool.convertMapKeyToInt(relationMap, "parentid"));
            relation.setSchemeId(NumberTool.convertMapKeyToInt(relationMap, "schemeid"));
        }

        return result;
    }

    public List getRelationByOrgId(String orgid, String code)
    {
        StringBuilder sql = new StringBuilder("select * from org_relation\u3000CONNECT BY PRIOR objid = parentid");
        Map map = new HashMap();
        if(orgid != null && !"".equals(orgid))
        {
            sql.append("  START WITH orgid=:orgid");
            map.put("orgid", orgid);
        }
        if(code != null && !"".equals(code))
        {
            sql.append(" and schemeid in (select objid from org_relationScheme where code =:code )");
            map.put("code", code);
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        iMetaDBQuery.setParameters(map);
        List list = iMetaDBQuery.getResult();
        List result = new ArrayList();
        IOrgRelation relation;
        for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(relation))
        {
            Map relationMap = (Map)i$.next();
            relation = new OrgRelationImpl();
            relation.setId(NumberTool.convertMapKeyToInt(relationMap, "objid").intValue());
            relation.setOrgId(StringUtil.convertMapKeyToString(relationMap, "orgid", ""));
            relation.setParentId(NumberTool.convertMapKeyToInt(relationMap, "parentid"));
            relation.setSchemeId(NumberTool.convertMapKeyToInt(relationMap, "schemeid"));
        }

        return result;
    }

    public List getRelationByParentId(int schemeId, int parentId)
    {
        StringBuilder sql = new StringBuilder("1=1");
        Map map = new HashMap();
        if(schemeId != 0)
        {
            sql.append(" and SchemeId=:SchemeId");
            map.put("SchemeId", Integer.valueOf(schemeId));
        }
        if(parentId != -1)
        {
            sql.append(" and parentId=:parentId");
            map.put("parentId", Integer.valueOf(parentId));
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("ORGRELATION", sql.toString());
        iMetaDBQuery.setParameters(map);
        return iMetaDBQuery.getResult();
    }

    public List findParentsByRelation(int schemeId, int relationId)
    {
        StringBuilder sql = new StringBuilder("select * from org_relation CONNECT BY PRIOR parentid=objid");
        Map map = new HashMap();
        if(relationId != 0 && schemeId != 0)
        {
            sql.append(" START WITH objid=:objid and schemeid=:schemeid");
            map.put("objid", Integer.valueOf(relationId));
            map.put("schemeid", Integer.valueOf(schemeId));
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        iMetaDBQuery.setParameters(map);
        List list = iMetaDBQuery.getResult();
        List result = new ArrayList();
        IOrgRelation relation;
        for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(relation))
        {
            Map relationMap = (Map)i$.next();
            relation = new OrgRelationImpl();
            relation.setId(NumberTool.convertMapKeyToInt(relationMap, "objid").intValue());
            relation.setOrgId(StringUtil.convertMapKeyToString(relationMap, "orgid", ""));
            relation.setParentId(NumberTool.convertMapKeyToInt(relationMap, "parentid"));
            relation.setSchemeId(NumberTool.convertMapKeyToInt(relationMap, "schemeid"));
        }

        return result;
    }

    public List findChildrenByRelation(int schemeId, int relationId)
    {
        StringBuilder sql = new StringBuilder("select * from org_relation CONNECT BY PRIOR objid=parentid ");
        Map map = new HashMap();
        if(relationId != 0 && schemeId != 0)
        {
            sql.append(" START WITH parentid=:objid and schemeid=:schemeid");
            map.put("objid", Integer.valueOf(relationId));
            map.put("schemeid", Integer.valueOf(schemeId));
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        iMetaDBQuery.setParameters(map);
        List list = iMetaDBQuery.getResult();
        List result = new ArrayList();
        IOrgRelation relation;
        for(Iterator i$ = list.iterator(); i$.hasNext(); result.add(relation))
        {
            Map relationMap = (Map)i$.next();
            relation = new OrgRelationImpl();
            relation.setId(NumberTool.convertMapKeyToInt(relationMap, "objid").intValue());
            relation.setOrgId(StringUtil.convertMapKeyToString(relationMap, "orgid", ""));
            relation.setParentId(NumberTool.convertMapKeyToInt(relationMap, "parentid"));
            relation.setSchemeId(NumberTool.convertMapKeyToInt(relationMap, "schemeid"));
        }

        return result;
    }

    public IOrgRelation getRelationById(int objId)
    {
        IMetaObject iMetaObject = getMetaDBContext().get("ORGRELATION", objId);
        if(iMetaObject != null)
            return (IOrgRelation)iMetaObject;
        else
            return null;
    }

    public void saveRelation(IOrgRelation relation)
    {
        relation.save();
    }

    public void delRelation(int objId)
    {
        IOrgRelation orgRelation = getRelationById(objId);
        if(orgRelation != null)
            orgRelation.delete();
    }

    public void deleteOrgRelation(int schemeId, int relationId)
    {
        StringBuffer sql = new StringBuffer();
        Map param = new HashMap();
        try
        {
            if(schemeId != 0 && relationId != 0)
            {
                sql.append("delete  Org_Relation where objid in (select objid from org_relation CONNECT BY PRIOR objid=parentid ");
                sql.append(" START WITH objid = :relationId and schemeid = :schemeId)");
                param.put("relationId", Integer.valueOf(relationId));
                param.put("schemeId", Integer.valueOf(schemeId));
            }
            ISQLUpdater sqlUpdater = getMetaDBContext().createSqlUpdater(sql.toString());
            sqlUpdater.setParameters(param);
            sqlUpdater.executeUpdate();
        }
        catch(Exception e)
        {
            logger.error("\u5220\u9664\u7EC4\u7EC7\u673A\u6784\u4E0A\u4E0B\u7EA7\u5173\u7CFB\u9519\u8BEF", e);
        }
    }

    public IMetaDBQuery findOrgRelationByParam(Map param)
    {
        StringBuilder sql = new StringBuilder("select r.objid,r.parentid, s.orgid,s.orgname,s.objecttype,s.unittype,s.description from sprt_orgobject s inner join org_relation r on r.orgid=s.orgid");
        sql.append(" where r.parentid=:parentid and r.schemeid=:schemeid");
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql.toString());
        query.setParameters(param);
        return query;
    }

    public Logger logger;
}
