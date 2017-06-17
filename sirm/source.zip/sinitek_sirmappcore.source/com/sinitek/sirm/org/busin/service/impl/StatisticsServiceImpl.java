// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StatisticsServiceImpl.java

package com.sinitek.sirm.org.busin.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.sirm.common.dao.MetaDBContextSupport;
import com.sinitek.sirm.common.utils.NumberTool;
import com.sinitek.sirm.common.utils.StringUtil;
import com.sinitek.sirm.org.busin.service.IStatisticsService;
import java.util.Map;
import org.apache.log4j.Logger;

public class StatisticsServiceImpl extends MetaDBContextSupport
    implements IStatisticsService
{

    public StatisticsServiceImpl()
    {
    }

    public IMetaDBQuery userAccessStatistics(Map parammap)
    {
        String username = StringUtil.safeToString(parammap.get("username"), "");
        StringBuffer sql = new StringBuffer("select * from (select max(a.starttime) as starttime,b.orgid,b.orgname,c.username,c.userid from sprt_businlogger a ");
        sql.append(" inner join um_userinfo c on c.userid = a.userid");
        sql.append(" inner join sprt_orgobject b on a.userid = b.userid ");
        sql.append(" group by b.orgid,b.orgname,c.username,c.userid)  where 1= 1");
        if(!username.isEmpty())
            sql.append(" and (username like '%").append(username).append("%' or orgname like '%").append(username).append("%')");
        if(null != parammap.get("timestart"))
            sql.append(" and starttime >= :timestart");
        if(null != parammap.get("timeend"))
            sql.append(" and starttime <= :timeend");
        IMetaDBQuery metaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        if(parammap.size() > 0)
            metaDBQuery.setParameters(parammap);
        LOGGER.info(sql.toString());
        return metaDBQuery;
    }

    public IMetaDBQuery menuAccessStatistics(Map parammap)
    {
        String jscontextpath = StringUtil.safeToString(parammap.get("contextpath"), "");
        StringBuffer sql = new StringBuffer("");
        sql.append("select * from (select max(a.starttime) as starttime,count(1) as cnt,c.name as menuname,c.objid as menuid from sprt_businlogger a ");
        sql.append(" inner join sprt_sysmenu c on ('").append(jscontextpath).append("'||c.url = a.url or a.url = '/'||c.url)\n");
        sql.append(" group by c.name,c.objid) where 1=1");
        if(null != parammap.get("timestart"))
            sql.append(" and starttime >= :timestart");
        if(null != parammap.get("timeend"))
            sql.append(" and starttime <= :timeend");
        String menuname = StringUtil.safeToString(parammap.get("menuname"), "");
        if(!menuname.isEmpty())
            sql.append(" and menuname like '%").append(menuname).append("%'");
        IMetaDBQuery metaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        if(parammap.size() > 0)
            metaDBQuery.setParameters(parammap);
        LOGGER.info(sql.toString());
        return metaDBQuery;
    }

    public IMetaDBQuery userAccessDetail(Map parammap)
    {
        String username = StringUtil.safeToString(parammap.get("username"), "");
        StringBuffer sql = new StringBuffer("");
        sql.append("select * from (select max(a.starttime) as starttime,b.orgid,b.orgname,c.username,a.modulename,count(1) as cnt,a.userid from sprt_businlogger a");
        sql.append(" inner join um_userinfo c on c.userid = a.userid\n");
        sql.append(" inner join sprt_orgobject b on a.userid = b.userid  group by b.orgid,b.orgname,c.username,a.modulename,a.userid) where 1=1");
        if(!username.isEmpty())
            sql.append(" and (username like '%").append(username).append("%' or orgname like '%").append(username).append("%')");
        if(null != parammap.get("timestart"))
            sql.append(" and starttime >= :timestart");
        if(null != parammap.get("timeend"))
            sql.append(" and starttime <= :timeend");
        if(!StringUtil.safeToString(parammap.get("userid"), "").isEmpty())
            sql.append(" and userid = :userid");
        String modulename = StringUtil.safeToString(parammap.get("modulename"), "");
        if(!modulename.isEmpty())
            sql.append(" and modulename like '%").append(modulename).append("%'");
        IMetaDBQuery metaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        if(parammap.size() > 0)
            metaDBQuery.setParameters(parammap);
        LOGGER.info(sql.toString());
        return metaDBQuery;
    }

    public IMetaDBQuery menuAccessDetail(Map parammap)
    {
        String jscontextpath = StringUtil.safeToString(parammap.get("contextpath"), "");
        StringBuffer sql = new StringBuffer("");
        sql.append("select * from (select max(a.starttime) as starttime,count(1) as cnt,c.name as menuname,d.username,b.orgname,d.userid,c.objid as menuid from sprt_businlogger a  ");
        sql.append(" inner join sprt_orgobject b on b.userid = a.userid");
        sql.append(" inner join um_userinfo d on d.userid = a.userid");
        sql.append(" inner join sprt_sysmenu c on ('").append(jscontextpath).append("'||c.url = a.url  or a.url = '/'||c.url)\n");
        sql.append(" group by c.name,d.username,b.orgname,d.userid,c.objid) where 1=1");
        if(null != parammap.get("timestart"))
            sql.append(" and starttime >= :timestart");
        if(null != parammap.get("timeend"))
            sql.append(" and starttime <= :timeend");
        if(null != parammap.get("menuid"))
            sql.append(" and menuid = :menuid");
        String username = StringUtil.safeToString(parammap.get("username"), "");
        if(!username.isEmpty())
            sql.append(" and (username like '%").append(username).append("%' or orgname like '%").append(username).append("%')");
        IMetaDBQuery metaDBQuery = getMetaDBContext().createSqlQuery(sql.toString());
        if(parammap.size() > 0)
            metaDBQuery.setParameters(parammap);
        LOGGER.info(sql.toString());
        return metaDBQuery;
    }

    public IMetaDBQuery searchUserBussLog(Map parammap)
    {
        String userid = StringUtil.safeToString(parammap.get("userid"), "");
        String modulename = StringUtil.safeToString(parammap.get("modulename"), "");
        StringBuffer sql = new StringBuffer(" select * from sprt_businlogger a where 1=1");
        if(!userid.isEmpty())
            sql.append(" and a.userid = :userid");
        if(!modulename.isEmpty())
            sql.append(" and a.modulename = :modulename");
        IMetaDBQuery metaDBQuery = getMetaDBContext().createSqlQuery((new StringBuilder()).append("select * from (").append(sql.toString()).append(")").toString());
        if(parammap.size() > 0)
            metaDBQuery.setParameters(parammap);
        LOGGER.info(sql.toString());
        return metaDBQuery;
    }

    public IMetaDBQuery searchMenuBussLog(Map parammap)
    {
        String userid = StringUtil.safeToString(parammap.get("userid"), "");
        int menuid = NumberTool.safeToInteger(parammap.get("menuid"), Integer.valueOf(0)).intValue();
        String jscontextpath = StringUtil.safeToString(parammap.get("contextpath"), "");
        StringBuffer sql = (new StringBuffer("select a.* from sprt_businlogger a inner join sprt_sysmenu b on ('")).append(jscontextpath).append("'||b.url = a.url or a.url = '/'||b.url)\n");
        if(!userid.isEmpty())
            sql.append(" and a.userid = :userid");
        if(menuid > 0)
            sql.append(" and b.objid = :menuid");
        IMetaDBQuery metaDBQuery = getMetaDBContext().createSqlQuery((new StringBuilder()).append("select * from (").append(sql.toString()).append(")").toString());
        if(parammap.size() > 0)
            metaDBQuery.setParameters(parammap);
        LOGGER.info(sql.toString());
        return metaDBQuery;
    }

    private static final Logger LOGGER = Logger.getLogger(com/sinitek/sirm/org/busin/service/impl/StatisticsServiceImpl);

}
