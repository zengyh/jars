// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GetEmployeeByUserIdServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.ds.core.service.*;
import com.sinitek.spirit.org.server.entity.IOrgObject;
import com.sinitek.spirit.org.server.entity.ObjectType;
import java.util.*;

public class GetEmployeeByUserIdServiceHandler extends AbstractServiceHandler
{

    public GetEmployeeByUserIdServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        String userId = request.getString("userid");
        List result = getMetaDBContext().query(com/sinitek/spirit/org/server/entity/IOrgObject, (new StringBuilder()).append("userid = '").append(userId).append("'").toString(), Collections.emptyMap());
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
        Iterator i$ = result.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            IOrgObject obj = (IOrgObject)i$.next();
            if(ObjectType.EMPLOYEE != obj.getObjectType())
                continue;
            response.addParam("versionid", Integer.valueOf(obj.getVersion()));
            response.addParam("orgid", obj.getOrgId());
            response.addParam("name", obj.getOrgName());
            response.addParam("description", obj.getDescription());
            response.addParam("userid", obj.getUserId());
            response.addParam("inservice", obj.getInservice());
            break;
        } while(true);
        return response;
    }
}
