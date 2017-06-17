// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GetEmployeesByNameServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.ds.core.service.*;
import com.sinitek.spirit.org.server.entity.IOrgObject;
import java.util.*;

public class GetEmployeesByNameServiceHandler extends AbstractServiceHandler
{

    public GetEmployeesByNameServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        String employeeName = request.getString("employeename");
        Boolean fuzzy = request.getBoolean("fuzzy");
        if(fuzzy == null)
            fuzzy = Boolean.valueOf(false);
        Map paramMap = new HashMap();
        List result;
        if(fuzzy.booleanValue())
        {
            paramMap.put("employeeName", (new StringBuilder()).append("%").append(employeeName).append("%").toString());
            result = getMetaDBContext().query(com/sinitek/spirit/org/server/entity/IOrgObject, "objecttype = 1 and orgname like :employeeName", paramMap);
        } else
        {
            paramMap.put("employeeName", employeeName);
            result = getMetaDBContext().query(com/sinitek/spirit/org/server/entity/IOrgObject, "objecttype = 1 and orgname = :employeeName", paramMap);
        }
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
        IOrgObject orgObject;
        SimpleParamObject row;
        for(Iterator i$ = result.iterator(); i$.hasNext(); row.addParam("inservice", orgObject.getInservice()))
        {
            orgObject = (IOrgObject)i$.next();
            row = new SimpleParamObject();
            response.addRecord(row);
            row.addParam("orgid", orgObject.getOrgId());
            row.addParam("name", orgObject.getOrgName());
            row.addParam("description", orgObject.getDescription());
            row.addParam("versionid", Integer.valueOf(orgObject.getVersion()));
            row.addParam("orgtype", orgObject.getObjectType().getEnumItemName());
            row.addParam("userid", orgObject.getUserId());
        }

        return response;
    }
}
