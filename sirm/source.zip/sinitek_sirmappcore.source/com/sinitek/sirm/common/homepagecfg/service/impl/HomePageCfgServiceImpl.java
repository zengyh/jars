// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HomePageCfgServiceImpl.java

package com.sinitek.sirm.common.homepagecfg.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.homepagecfg.entity.IHomePageCfg;
import com.sinitek.sirm.common.homepagecfg.service.IHomePageCfgService;
import com.sinitek.sirm.common.utils.NumberTool;
import java.util.*;

public class HomePageCfgServiceImpl extends AbstractMetaDBContextSupport
    implements IHomePageCfgService
{

    public HomePageCfgServiceImpl()
    {
    }

    public List findHomePageCfg(String orgId, int type, int righttype)
    {
        String sql = "orgId=:OrgId and righttype=:righttype";
        if(type != 0)
            sql = (new StringBuilder()).append(sql).append(" and orgType=:stype").toString();
        sql = (new StringBuilder()).append(sql).append(" order by sort").toString();
        Map map = new HashMap();
        map.put("OrgId", NumberTool.safeToInteger(orgId, Integer.valueOf(0)));
        map.put("righttype", Integer.valueOf(righttype));
        if(type != 0)
            map.put("stype", Integer.valueOf(type));
        List mapList = getMetaDBContext().query("HOMEPAGECFG", sql, map);
        return mapList;
    }

    public List findHomePageCfgByOrgId(String orgId)
    {
        String hql = " orgId =:orgId and orgType =:type";
        Map map = new HashMap();
        map.put("orgId", NumberTool.safeToInteger(orgId, Integer.valueOf(0)));
        map.put("type", Integer.valueOf(8));
        List list = getMetaDBContext().query("HOMEPAGECFG", hql, map);
        return list;
    }

    public IHomePageCfg getHomePageCfg(String orgId, int moduleId)
    {
        String hql = " orgId =:orgId and moduleId =:moduleId";
        Map map = new HashMap();
        map.put("orgId", NumberTool.safeToInteger(orgId, Integer.valueOf(0)));
        map.put("moduleId", Integer.valueOf(moduleId));
        List list = getMetaDBContext().query("HOMEPAGECFG", hql, map);
        if(list.size() > 0)
            return (IHomePageCfg)list.get(0);
        else
            return null;
    }

    public List findOldHomePageCfg()
    {
        String sql = "select * from sirm_homepagecfg_template";
        List result = getMetaDBContext().createSqlQuery(sql).getResult();
        return result;
    }

    public void saveHomePageCfg(IHomePageCfg homePageCfg)
    {
        if(homePageCfg != null)
            homePageCfg.save();
    }

    public void deleteHomePageCfg(IHomePageCfg homePageCfg)
    {
        if(homePageCfg != null)
            homePageCfg.delete();
    }
}
