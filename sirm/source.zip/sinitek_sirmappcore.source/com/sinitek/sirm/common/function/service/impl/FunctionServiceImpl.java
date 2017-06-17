// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FunctionServiceImpl.java

package com.sinitek.sirm.common.function.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.IMetaObject;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.function.entity.IFunctionGroup;
import com.sinitek.sirm.common.function.entity.IFunctionInfo;
import com.sinitek.sirm.common.function.service.IFunctionService;
import com.sinitek.sirm.org.busin.entity.OrgObject;
import com.sinitek.sirm.org.busin.service.IRightService;
import com.sinitek.sirm.org.busin.service.OrgServiceFactory;
import java.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

public class FunctionServiceImpl extends AbstractMetaDBContextSupport
    implements IFunctionService
{

    public FunctionServiceImpl()
    {
    }

    public IMetaDBQuery findAllFunctionInfo(int groupid)
    {
        String hql = " GroupId =:GroupId";
        Map map = new HashMap();
        map.put("GroupId", Integer.valueOf(groupid));
        IMetaDBQuery _query = getMetaDBContext().createQuery("FUNCTIONINFO", hql);
        _query.setParameters(map);
        _query.setOrderBy("sort");
        return _query;
    }

    public int getMaxInfoSort(int groupid)
    {
        StringBuilder _sql = new StringBuilder();
        _sql.append("Select max(sort) maxsort from SIRM_FUNCTIONINFO \n");
        _sql.append(" where groupid = ").append(groupid).append(" \n");
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = query.getResult();
        return _list.size() <= 0 ? 0 : MapUtils.getIntValue((Map)_list.get(0), "maxsort", 0);
    }

    public void saveFunctionInfo(IFunctionInfo functionInfo)
    {
        functionInfo.save();
    }

    public void deleteFunctionInfo(int infoId)
    {
        IFunctionInfo _functionInfo = (IFunctionInfo)getMetaDBContext().get("FUNCTIONINFO", infoId);
        if(_functionInfo != null)
            _functionInfo.delete();
    }

    public boolean checkFuntion(int groupid, String name, String code, int id)
    {
        String hql = " GroupId =:GroupId and Name=:Name or Code=:Code";
        Map map = new HashMap();
        map.put("GroupId", Integer.valueOf(groupid));
        map.put("Name", name);
        map.put("Code", code);
        List list = getMetaDBContext().query("FUNCTIONINFO", hql, map);
        if(id == 0)
            return list != null && list.size() > 0;
        for(int i = 0; i < list.size(); i++)
        {
            IFunctionInfo info = (IFunctionInfo)list.get(i);
            if(info.getId() != id)
                return true;
        }

        return false;
    }

    public boolean checkFuntionGroup(int parentid, String name, int id)
    {
        String hql = " ParentId =:ParentId and Name=:Name";
        Map map = new HashMap();
        map.put("ParentId", Integer.valueOf(parentid));
        map.put("Name", name);
        List list = getMetaDBContext().query("FUNCTIONGROUP", hql, map);
        if(id == 0)
            return list != null && list.size() > 0;
        for(int i = 0; i < list.size(); i++)
        {
            IFunctionGroup group = (IFunctionGroup)list.get(i);
            if(group.getId() != id)
                return true;
        }

        return false;
    }

    public List findAllFunctionGroup(int parentid)
    {
        String hql = " ParentId =:ParentId  Order by sort";
        Map map = new HashMap();
        map.put("ParentId", Integer.valueOf(parentid));
        List list = getMetaDBContext().query("FUNCTIONGROUP", hql, map);
        if(list == null)
            list = new ArrayList();
        return list;
    }

    public void saveFunctionGroup(IFunctionGroup functionGroup)
    {
        functionGroup.save();
    }

    public void deleteFunctionGroup(int infoId)
    {
        IFunctionGroup _functionGroup = (IFunctionGroup)getMetaDBContext().get("FUNCTIONGROUP", infoId);
        if(_functionGroup != null)
            _functionGroup.remove();
    }

    public List findFunctionGroupByName(String name)
    {
        StringBuffer _sql = new StringBuffer();
        Map _param = new HashMap();
        _sql.append("Select objid, sort, parentid, name \n");
        _sql.append("   From SIRM_FUNCTIONGROUP \n");
        _sql.append("Where 1=1 \n");
        if(StringUtils.isNotBlank(name))
        {
            _sql.append("   and ( upper(name) like :name \n");
            _sql.append("       or objid in (select objid from SIRM_FUNCTIONGROUP where F_TRANS_PINYIN_CAPITAL(name) like :name) \n");
            _sql.append("       ) \n");
            _param.put("name", (new StringBuilder()).append("%").append(name.toUpperCase()).append("%").toString());
        }
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(_sql.toString());
        query.setParameters(_param);
        return query.getResult();
    }

    public IMetaObject getGroupById(int infoId)
    {
        return getMetaDBContext().get("FUNCTIONGROUP", infoId);
    }

    public IMetaObject getFunctionById(int infoId)
    {
        return getMetaDBContext().get("FUNCTIONINFO", infoId);
    }

    public int getMaxGroupSort(int parentid)
    {
        StringBuilder _sql = new StringBuilder();
        _sql.append("Select max(sort) maxsort from SIRM_FUNCTIONGROUP \n");
        _sql.append(" where parentid = ").append(parentid).append(" \n");
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(_sql.toString());
        List _list = query.getResult();
        return _list.size() <= 0 ? 0 : MapUtils.getIntValue((Map)_list.get(0), "maxsort", 0);
    }

    public List findAllFunction()
    {
        List groupAllList = new ArrayList();
        List queryList = findAllFunctionGroup(0);
        Map map;
        for(Iterator i$ = queryList.iterator(); i$.hasNext(); groupAllList.add(map))
        {
            IFunctionGroup parentGroup = (IFunctionGroup)i$.next();
            map = new HashMap();
            map.put("id", Integer.valueOf(parentGroup.getId()));
            map.put("name", parentGroup.getName());
            List twoList = findAllFunctionGroup(parentGroup.getId());
            List twolists = new ArrayList();
            Map twoMap;
            for(Iterator i$ = twoList.iterator(); i$.hasNext(); twolists.add(twoMap))
            {
                IFunctionGroup twoGroup = (IFunctionGroup)i$.next();
                twoMap = new HashMap();
                twoMap.put("id", Integer.valueOf(twoGroup.getId()));
                twoMap.put("name", twoGroup.getName());
                IMetaDBQuery infoList = findAllFunctionInfo(twoGroup.getId());
                infoList.setDefaultOrderBy("sort asc");
                twoMap.put("children", infoList.getResult());
            }

            map.put("children", twolists);
        }

        return groupAllList;
    }

    public IFunctionInfo getFunctionByCode(String code)
    {
        String sql = "Code=:Code";
        Map param = new HashMap();
        param.put("Code", code);
        List list = getMetaDBContext().query("FUNCTIONINFO", sql, param);
        return list.size() <= 0 ? null : (IFunctionInfo)list.get(0);
    }

    public boolean checkFunctionByCodeAndOrgId(String code, String orgId)
    {
        boolean isok = false;
        if(code != null && !"".equals(code) && orgId != null && !"".equals(orgId))
        {
            IFunctionInfo func = getFunctionByCode(code);
            if(func != null)
            {
                OrgObject org = new OrgObject();
                org.setOrgId(orgId);
                isok = OrgServiceFactory.getRightService().checkRight(org, String.valueOf(func.getId()), "FUNCTIONINFO", "ACCESS");
            }
        }
        return isok;
    }

    public List getAllFunctionGroup()
    {
        List resultMap = new ArrayList();
        List oneList = findAllFunctionGroup(0);
        for(Iterator i$ = oneList.iterator(); i$.hasNext();)
        {
            IFunctionGroup oneGroup = (IFunctionGroup)i$.next();
            List secondList = findAllFunctionGroup(oneGroup.getId());
            if(secondList.size() <= 0)
            {
                Map oneMap = new HashMap();
                oneMap.put("id", Integer.valueOf(oneGroup.getId()));
                oneMap.put("first", oneGroup.getName());
                oneMap.put("second", "");
                oneMap.put("third", "");
                oneMap.put("level", Integer.valueOf(1));
                oneMap.put("type", "group");
                resultMap.add(oneMap);
            } else
            {
                Iterator i$ = secondList.iterator();
                while(i$.hasNext()) 
                {
                    IFunctionGroup secondGroup = (IFunctionGroup)i$.next();
                    List thirdList = findAllFunctionGroup(secondGroup.getId());
                    List funList = findAllFunctionInfos(secondGroup.getId());
                    if(thirdList.size() <= 0 && funList.size() <= 0)
                    {
                        Map secondMap = new HashMap();
                        secondMap.put("id", Integer.valueOf(secondGroup.getId()));
                        secondMap.put("first", oneGroup.getName());
                        secondMap.put("second", secondGroup.getName());
                        secondMap.put("third", "");
                        secondMap.put("level", Integer.valueOf(2));
                        secondMap.put("type", "group");
                        resultMap.add(secondMap);
                    } else
                    if(thirdList.size() <= 0)
                    {
                        Iterator i$ = funList.iterator();
                        while(i$.hasNext()) 
                        {
                            IFunctionInfo functionInfo = (IFunctionInfo)i$.next();
                            Map thirdMap = new HashMap();
                            thirdMap.put("id", Integer.valueOf(functionInfo.getId()));
                            thirdMap.put("first", oneGroup.getName());
                            thirdMap.put("second", secondGroup.getName());
                            thirdMap.put("third", functionInfo.getName());
                            thirdMap.put("level", Integer.valueOf(3));
                            thirdMap.put("type", "info");
                            resultMap.add(thirdMap);
                        }
                    } else
                    {
                        Iterator i$ = thirdList.iterator();
                        while(i$.hasNext()) 
                        {
                            IFunctionGroup thirdGroup = (IFunctionGroup)i$.next();
                            Map thirdMap = new HashMap();
                            thirdMap.put("id", Integer.valueOf(thirdGroup.getId()));
                            thirdMap.put("first", oneGroup.getName());
                            thirdMap.put("second", secondGroup.getName());
                            thirdMap.put("third", thirdGroup.getName());
                            thirdMap.put("level", Integer.valueOf(3));
                            thirdMap.put("type", "group");
                            resultMap.add(thirdMap);
                        }
                    }
                }
            }
        }

        return resultMap;
    }

    public List findAllFunctionInfos(int groupid)
    {
        String hql = " GroupId =:GroupId";
        Map map = new HashMap();
        map.put("GroupId", Integer.valueOf(groupid));
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("FUNCTIONINFO", hql);
        iMetaDBQuery.setParameters(map);
        iMetaDBQuery.setDefaultOrderBy("sort asc");
        return iMetaDBQuery.getResult();
    }
}
