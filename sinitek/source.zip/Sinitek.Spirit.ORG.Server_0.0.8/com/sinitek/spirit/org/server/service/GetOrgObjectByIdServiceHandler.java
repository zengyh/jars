// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GetOrgObjectByIdServiceHandler.java

package com.sinitek.spirit.org.server.service;

import com.sinitek.base.common.BaseException;
import com.sinitek.base.enumsupport.IEnumItem;
import com.sinitek.base.metadb.IMetaDBContext;
import com.sinitek.ds.core.service.*;
import com.sinitek.spirit.org.server.entity.IOrgObject;
import com.sinitek.spirit.org.server.entity.ObjectType;

// Referenced classes of package com.sinitek.spirit.org.server.service:
//            OrgServiceException

public class GetOrgObjectByIdServiceHandler extends AbstractServiceHandler
{

    public GetOrgObjectByIdServiceHandler()
    {
    }

    public IServiceResponse handle(IServiceCoreRequest request)
        throws BaseException
    {
        String orgId = request.getString("orgid");
        IOrgObject orgObject = (IOrgObject)getMetaDBContext().get(com/sinitek/spirit/org/server/entity/IOrgObject, "orgid", orgId);
        if(orgObject == null)
            throw new OrgServiceException("1001", new Object[] {
                orgId
            });
        SimpleServiceResponse response = SimpleServiceResponse.createSuccessResponse("\u67E5\u8BE2\u6210\u529F");
        response.addParam("orgid", orgObject.getOrgId());
        response.addParam("name", orgObject.getOrgName());
        response.addParam("description", orgObject.getDescription());
        response.addParam("versionid", Integer.valueOf(orgObject.getVersion()));
        response.addParam("orgtype", orgObject.getObjectType().getEnumItemName());
        if(ObjectType.EMPLOYEE == orgObject.getObjectType())
        {
            response.addParam("userid", orgObject.getUserId());
            response.addParam("inservice", orgObject.getInservice());
        } else
        {
            response.addParam("unittype", orgObject.getUnitType());
        }
        return response;
    }
}
