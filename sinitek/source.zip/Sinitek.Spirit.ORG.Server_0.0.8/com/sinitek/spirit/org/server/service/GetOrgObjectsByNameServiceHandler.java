// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GetOrgObjectsByNameServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.ds.core.service.*;
import com.sinitek.spirit.org.server.entity.IOrgObject;
import com.sinitek.spirit.org.server.entity.ObjectType;
import java.util.*;

public class GetOrgObjectsByNameServiceHandler extends AbstractServiceHandler
{

    public GetOrgObjectsByNameServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        String orgName = request.getString("orgname");
        Boolean fuzzy = request.getBoolean("fuzzy");
        if(fuzzy == null)
            fuzzy = Boolean.valueOf(false);
        List result;
        if(fuzzy.booleanValue())
            result = getMetaDBContext().query(com/sinitek/spirit/org/server/entity/IOrgObject, (new StringBuilder()).append("orgname like '%").append(orgName).append("%'").toString(), Collections.emptyMap());
        else
            result = getMetaDBContext().query(com/sinitek/spirit/org/server/entity/IOrgObject, (new StringBuilder()).append("orgname = '").append(orgName).append("'").toString(), Collections.emptyMap());
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
        for(Iterator i$ = result.iterator(); i$.hasNext();)
        {
            IOrgObject orgObject = (IOrgObject)i$.next();
            SimpleParamObject row = new SimpleParamObject();
            response.addRecord(row);
            row.addParam("orgid", orgObject.getOrgId());
            row.addParam("name", orgObject.getOrgName());
            row.addParam("description", orgObject.getDescription());
            row.addParam("versionid", Integer.valueOf(orgObject.getVersion()));
            row.addParam("orgtype", orgObject.getObjectType().getEnumItemName());
            if(ObjectType.EMPLOYEE == orgObject.getObjectType())
            {
                row.addParam("userid", orgObject.getUserId());
                row.addParam("inservice", orgObject.getInservice());
            } else
            {
                row.addParam("unittype", orgObject.getUnitType());
            }
        }

        return response;
    }
}
