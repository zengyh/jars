// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QualifyInfoServiceImpl.java

package com.sinitek.sirm.org.busin.service.impl;

import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.base.metadb.support.AbstractMetaDBContextSupport;
import com.sinitek.sirm.common.utils.TimeUtil;
import com.sinitek.sirm.org.busin.entity.IQualifyInfo;
import com.sinitek.sirm.org.busin.service.IQualifyInfoService;
import com.sinitek.spirit.webcontrol.utils.QueryUtils;
import java.util.*;

public class QualifyInfoServiceImpl extends AbstractMetaDBContextSupport
    implements IQualifyInfoService
{

    public QualifyInfoServiceImpl()
    {
    }

    public IMetaDBQuery findAllQualifyInfo(Map map)
    {
        StringBuilder sql = new StringBuilder("1=1");
        Map param = new HashMap();
        if(map != null)
        {
            String orgid = String.valueOf(map.get("orgids"));
            if(orgid != null && !"".equals(orgid))
                QueryUtils.buildIn("orgid", orgid, ",", sql, param);
            String qualifytype = String.valueOf(map.get("qualifytype"));
            if(map.get("qualifytype") != null && !"".equals(qualifytype))
            {
                sql.append(" and qualifytype=:qualifytype");
                param.put("qualifytype", qualifytype);
            }
            String endtime = String.valueOf(map.get("endtime"));
            if(map.get("endtime") != null && !"".equals(endtime))
            {
                sql.append(" and endtime=to_date(:endtime,'yyyy-MM-dd')");
                param.put("endtime", TimeUtil.formatDate(endtime, "yyyy-MM-dd"));
            }
            String failuredate = String.valueOf(map.get("failuredate"));
            if(map.get("failuredate") != null && !"".equals(failuredate))
            {
                sql.append(" and failuredate=to_date(:failuredate,'yyyy-MM-dd')");
                param.put("failuredate", TimeUtil.formatDate(failuredate, "yyyy-MM-dd"));
            }
            String issuingdate = String.valueOf(map.get("issuingdate"));
            if(map.get("issuingdate") != null && !"".equals(issuingdate))
            {
                sql.append(" and issuingdate=to_date(:issuingdate,'yyyy-MM-dd')");
                param.put("issuingdate", TimeUtil.formatDate(issuingdate, "yyyy-MM-dd"));
            }
            String issuingunit = String.valueOf(map.get("issuingunit"));
            if(map.get("issuingunit") != null && !"".equals(issuingunit))
                QueryUtils.buildLike("issuingunit", issuingunit, sql, param);
            String lev = String.valueOf(map.get("lev"));
            if(map.get("lev") != null && !"".equals(lev))
            {
                sql.append(" and lev=:lev");
                param.put("lev", lev);
            }
        }
        IMetaDBQuery iMetaDBQuery = getMetaDBContext().createQuery("QUALIFYINFO", sql.toString());
        iMetaDBQuery.setParameters(param);
        return iMetaDBQuery;
    }

    public IQualifyInfo getQualifyInfoById(int objid)
    {
        IQualifyInfo qualifyInfo = null;
        if(objid != 0)
            qualifyInfo = (IQualifyInfo)getMetaDBContext().get("QUALIFYINFO", objid);
        return qualifyInfo;
    }

    public IMetaDBQuery findQualifyInfoByOrgId(String orgId)
    {
        Map map = new HashMap();
        if(orgId != null && !"".equals(orgId))
            map.put("orgids", orgId);
        return findAllQualifyInfo(map);
    }

    public void saveQualifyInfo(IQualifyInfo qualifyInfo)
    {
        qualifyInfo.save();
    }

    public void deleteQualifyInfo(int objid)
    {
        if(objid != 0)
        {
            IQualifyInfo qualifyInfo = getQualifyInfoById(objid);
            if(qualifyInfo != null)
                qualifyInfo.delete();
        }
    }

    public void deleteQualifyInfoByOrgId(String orgId)
    {
        if(orgId != null && !"".equals(orgId))
        {
            List qualifyInfoList = findQualifyInfoByOrgId(orgId).getResult();
            IQualifyInfo qualifyInfo;
            for(Iterator i$ = qualifyInfoList.iterator(); i$.hasNext(); qualifyInfo.delete())
                qualifyInfo = (IQualifyInfo)i$.next();

        }
    }
}
