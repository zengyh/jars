// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GetUnassignedEmployeesServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.base.metadb.query.IMetaDBQuery;
import com.sinitek.ds.core.service.*;
import com.sinitek.spirit.org.server.entity.IOrgObject;
import java.util.*;

public class GetUnassignedEmployeesServiceHandler extends AbstractServiceHandler
{

    public GetUnassignedEmployeesServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        String sql = "select obj.ORGID from SPRT_ORGOBJECT obj left join SPRT_ORGRELA rela on obj.ORGID = rela.TOOBJECTID where obj.OBJECTTYPE = 1 and rela.TOOBJECTID is null";
        IMetaDBQuery query = getMetaDBContext().createSqlQuery(sql);
        List result = query.getResult();
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
        SimpleParamObject data;
        for(Iterator i$ = result.iterator(); i$.hasNext(); response.addRecord(data))
        {
            Map row = (Map)i$.next();
            IOrgObject employee = (IOrgObject)getMetaDBContext().load(com/sinitek/spirit/org/server/entity/IOrgObject, "orgid", row.get("orgid"));
            data = new SimpleParamObject();
            data.addParam("orgid", employee.getOrgId());
            data.addParam("name", employee.getOrgName());
            data.addParam("description", employee.getDescription());
            data.addParam("versionid", Integer.valueOf(employee.getVersion()));
            data.addParam("orgtype", employee.getObjectType().getEnumItemName());
            data.addParam("userid", employee.getUserId());
            data.addParam("inservice", employee.getInservice());
        }

        return response;
    }
}
