// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GetUnitsServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.ds.core.service.*;
import com.sinitek.spirit.org.server.entity.IOrgObject;
import java.util.*;
import org.apache.commons.lang.StringUtils;

public class GetUnitsServiceHandler extends AbstractServiceHandler
{

    public GetUnitsServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        String unitType = request.getString("unittype");
        String unitName = StringUtils.trimToNull(request.getString("unitname"));
        Boolean fuzzy = request.getBoolean("fuzzy");
        if(fuzzy == null)
            fuzzy = Boolean.valueOf(false);
        Map paramMap = new HashMap();
        StringBuilder hql = new StringBuilder("objecttype = 2 and unittype = :unitType");
        paramMap.put("unitType", unitType);
        if(unitName != null)
            if(fuzzy.booleanValue())
            {
                paramMap.put("unitName", (new StringBuilder()).append("%").append(unitName).append("%").toString());
                hql.append(" and orgname like :unitName");
            } else
            {
                paramMap.put("unitName", unitName);
                hql.append(" and orgname = :unitName");
            }
        List result = getMetaDBContext().query(com/sinitek/spirit/org/server/entity/IOrgObject, hql.toString(), paramMap);
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
        IOrgObject orgObject;
        SimpleParamObject row;
        for(Iterator i$ = result.iterator(); i$.hasNext(); row.addParam("unittype", orgObject.getUnitType()))
        {
            orgObject = (IOrgObject)i$.next();
            row = new SimpleParamObject();
            response.addRecord(row);
            row.addParam("orgid", orgObject.getOrgId());
            row.addParam("name", orgObject.getOrgName());
            row.addParam("description", orgObject.getDescription());
            row.addParam("versionid", Integer.valueOf(orgObject.getVersion()));
            row.addParam("orgtype", orgObject.getObjectType().getEnumItemName());
        }

        return response;
    }
}
